package org.jsi;

import java.util.List;

import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.ArrayType;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.tools.Diagnostic;

import com.sun.source.tree.ArrayAccessTree;
import com.sun.source.tree.AssignmentTree;
import com.sun.source.tree.BinaryTree;
import com.sun.source.tree.CompilationUnitTree;
import com.sun.source.tree.CompoundAssignmentTree;
import com.sun.source.tree.ConditionalExpressionTree;
import com.sun.source.tree.EnhancedForLoopTree;
import com.sun.source.tree.ExpressionTree;
import com.sun.source.tree.IdentifierTree;
import com.sun.source.tree.LambdaExpressionTree;
import com.sun.source.tree.LiteralTree;
import com.sun.source.tree.MemberReferenceTree;
import com.sun.source.tree.MemberSelectTree;
import com.sun.source.tree.MethodInvocationTree;
import com.sun.source.tree.MethodTree;
import com.sun.source.tree.NewArrayTree;
import com.sun.source.tree.NewClassTree;
import com.sun.source.tree.ParenthesizedTree;
import com.sun.source.tree.ReturnTree;
import com.sun.source.tree.Tree;
import com.sun.source.tree.Tree.Kind;
import com.sun.source.tree.TypeCastTree;
import com.sun.source.tree.UnaryTree;
import com.sun.source.tree.VariableTree;
import com.sun.source.util.TreePath;
import com.sun.source.util.TreePathScanner;
import com.sun.source.util.Trees;

public class UnitTreeScanner extends TreePathScanner<SIUnit, Object> {
  private final Trees trees;
  private final CompilationUnitTree unit;

  public UnitTreeScanner(Trees trees, CompilationUnitTree unit){
    this.trees = trees;
    this.unit = unit;
  }

  @Override
  public SIUnit visitIdentifier(IdentifierTree node, Object p) {
    Element e = trees.getElement(getCurrentPath());
    return e == null ? null : UnitOf.unitOf(e);
  }

  @Override
  public SIUnit visitParenthesized(ParenthesizedTree node, Object p) {
    return scan(node.getExpression(), p);
  }

  @Override
  public SIUnit visitLiteral(LiteralTree node, Object p) {
    return SIUnit.DIMENSIONLESS;
  }


  /**
   * Checks a declaration's initializer against its declared unit, and yields the
   * declared unit as the variable's own.
   *
   * <p>A dimensionless initializer is accepted for any declared unit, so that
   * {@code @m double d = 10;} is how a united value is introduced. Without that
   * exception no annotated variable could ever be given a starting value.
   */
  @Override
  public SIUnit visitVariable(VariableTree node, Object p) {
    SIUnit init = scan(node.getInitializer(), p);
    SIUnit u = UnitOf.unitOf(trees.getElement(getCurrentPath()));

    // No initializer (parameters, catch clauses) or an unannotated side: nothing to check.
    if (init == null || u == null) return u;

    return assignable(node.getInitializer(), init, u) ? u : null;
  }


  /**
   * {@code d = t} carries the same rule as a declaration: the value must fit the
   * variable. Yields the variable's unit, since a Java assignment is an expression
   * whose value is the one assigned.
   */
  @Override
  public SIUnit visitAssignment(AssignmentTree node, Object p) {
    SIUnit target = scan(node.getVariable(), p);
    SIUnit value  = scan(node.getExpression(), p);
    if (target == null || value == null) return target;

    return assignable(node.getExpression(), value, target) ? target : null;
  }

  @Override
  public SIUnit visitMemberSelect(MemberSelectTree node, Object p) {
      scan(node.getExpression(), p);
      Element e = trees.getElement(getCurrentPath());
      return e == null ? null : UnitOf.unitOf(e);
  }

  /**
   * A method call such as {@code speed()}, {@code car.speed(t)} or {@code Math.abs(x)}.
   * Each argument must fit the unit of the parameter it binds to, and the call yields
   * the unit on the invoked method's return type.
   *
   * <p>That return unit is exactly what scanning the method select produces: an
   * identifier or member select naming a method resolves to the method itself, and
   * {@link UnitOf#unitOf(Element)} reads a method's unit off its return type.
   */
  @Override
  public SIUnit visitMethodInvocation(MethodInvocationTree node, Object p) {
    SIUnit result = scan(node.getMethodSelect(), p);
    Element callee = trees.getElement(new TreePath(getCurrentPath(), node.getMethodSelect()));
    checkArguments(callee, node.getArguments(), p);
    return result;
  }

  /**
   * A returned value must fit the enclosing method's return unit, by the same rule as
   * an assignment. A {@code return} inside a lambda is left alone: the unit expected of
   * it would come from a functional interface's type arguments, which are not tracked.
   */
  @Override
  public SIUnit visitReturn(ReturnTree node, Object p) {
    SIUnit value = scan(node.getExpression(), p);
    if (value == null) return null;

    for (TreePath path = getCurrentPath(); path != null; path = path.getParentPath()) {
      Tree t = path.getLeaf();
      if (t instanceof LambdaExpressionTree) return null;
      if (t instanceof MethodTree) {
        Element method = trees.getElement(path);
        SIUnit declared = method == null ? null : UnitOf.unitOf(method);
        if (declared != null) {
          assignable(node.getExpression(), value, declared, "return", "from a method returning");
        }
        return null;
      }
    }
    return null;
  }

  /**
   * Sign and step operators keep their operand's unit: {@code -d} is still a length, and
   * {@code d++} steps it by one of its own unit — unlike {@code d + 1.0}, there is no
   * other unit the step could be in. {@code !} and {@code ~} yield a boolean and a bit
   * pattern, neither of which is a quantity.
   */
  @Override
  public SIUnit visitUnary(UnaryTree node, Object p) {
    SIUnit operand = scan(node.getExpression(), p);
    if (operand == null) return null;

    return switch (node.getKind()) {
      case LOGICAL_COMPLEMENT, BITWISE_COMPLEMENT -> SIUnit.DIMENSIONLESS;
      default -> operand; // +, -, ++, --
    };
  }

  /**
   * A numeric cast such as {@code (int) d} changes representation, not quantity, so it
   * keeps the operand's unit.
   *
   * <p>A cast to a unit-annotated type such as {@code (@m double) Math.sqrt(area)} gives a
   * unit to a value that has none — the way to bring a result back from unannotated
   * code. It may restate a value's existing unit, but it cannot change one: a cast
   * re-labels a number without scaling it, so {@code (@s double) d} is a dimension error
   * and a cast between scales of one dimension still needs an explicit conversion.
   */
  @Override
  public SIUnit visitTypeCast(TypeCastTree node, Object p) {
    SIUnit value = scan(node.getExpression(), p);
    SIUnit cast  = UnitOf.unitOf(trees.getTypeMirror(new TreePath(getCurrentPath(), node.getType())));
    if (cast == null) return value;
    if (value == null || value.isBareNumber()) return cast;

    return compatible(node, value, cast, "cast", "to") ? cast : null;
  }

  /**
   * {@code c ? a : b} yields one of two values, so the branches must agree. A plain
   * number in one branch takes the other's unit, as it would on assignment, which keeps
   * {@code a > b ? a : 0} legal.
   */
  @Override
  public SIUnit visitConditionalExpression(ConditionalExpressionTree node, Object p) {
    scan(node.getCondition(), p);
    SIUnit a = scan(node.getTrueExpression(), p);
    SIUnit b = scan(node.getFalseExpression(), p);
    return join(node, a, b, "choose between");
  }

  /**
   * {@code new Car(d, t)} checks its arguments against the constructor's parameters just
   * as a method call does. The object it creates is not a quantity, so it has no unit.
   */
  @Override
  public SIUnit visitNewClass(NewClassTree node, Object p) {
    scan(node.getEnclosingExpression(), p);
    checkArguments(trees.getElement(getCurrentPath()), node.getArguments(), p);
    scan(node.getClassBody(), p);
    return null;
  }

  /**
   * An element has its array's unit: {@code @m double[] xs} declares an array of
   * lengths, so {@code xs[i]} is a length. The index contributes nothing.
   */
  @Override
  public SIUnit visitArrayAccess(ArrayAccessTree node, Object p) {
    SIUnit array = scan(node.getExpression(), p);
    scan(node.getIndex(), p);
    return array;
  }

  /**
   * An array initializer's elements must agree, and the array carries their unit.
   * {@code new double[n]} starts out as zeros, which are dimensionless and so fit an
   * array of any unit.
   */
  @Override
  public SIUnit visitNewArray(NewArrayTree node, Object p) {
    scan(node.getDimensions(), p);
    if (node.getInitializers() == null) return SIUnit.DIMENSIONLESS;

    SIUnit elements = SIUnit.DIMENSIONLESS;
    for (ExpressionTree e : node.getInitializers()) {
      SIUnit u = scan(e, p); // scanned even once elements is null, for nested errors
      elements = elements == null ? null : join(e, elements, u, "mix");
    }
    return elements;
  }

  /**
   * {@code for (@s double t : xs)} assigns each element of {@code xs} to {@code t}, so
   * the elements must fit the loop variable by the same rule as an assignment.
   */
  @Override
  public SIUnit visitEnhancedForLoop(EnhancedForLoopTree node, Object p) {
    SIUnit target   = scan(node.getVariable(), p);
    SIUnit elements = scan(node.getExpression(), p);
    if (target != null && elements != null) {
      assignable(node.getExpression(), elements, target);
    }
    scan(node.getStatement(), p);
    return null;
  }

  /**
   * A declaration, not a value, so it yields no unit. Its returns are checked against
   * it by {@link #visitReturn}, which finds the enclosing method from the path.
   */
  @Override
  public SIUnit visitMethod(MethodTree node, Object p) {
    super.visitMethod(node, p);
    return null;
  }

  /**
   * A lambda is a function, not a quantity, so it yields no unit. Its body is still
   * checked internally; see {@link #visitReturn} for why its result is not.
   */
  @Override
  public SIUnit visitLambdaExpression(LambdaExpressionTree node, Object p) {
    scan(node.getParameters(), p);
    scan(node.getBody(), p);
    return null;
  }

  /** {@code Car::speed} names a method without calling it, so it has no unit. */
  @Override
  public SIUnit visitMemberReference(MemberReferenceTree node, Object p) {
    scan(node.getQualifierExpression(), p);
    return null;
  }


  /**
   * {@code d op= t} means {@code d = d op t}, so unlike the matching binary operator the
   * result has to fit back into {@code d}, whose unit is fixed by its declaration.
   *
   * <p>That splits the operators in two. {@code +=}, {@code -=} and {@code %=} demand
   * agreeing operands exactly as {@code +} does. {@code *=} and {@code /=} cannot mint a
   * new unit the way {@code *} and {@code /} freely do — {@code dist * time} is a
   * perfectly good {@code m·s}, but {@code dist *= time} has nowhere to put it — so the
   * result must fit the variable. Scaling a length by a number, or by radians, is fine;
   * re-dimensioning or re-scaling one in place is not, so {@code dist *= angleInDegrees}
   * is an error too.
   */
  @Override
  public SIUnit visitCompoundAssignment(CompoundAssignmentTree node, Object p) {
    // `msg += dist` is concatenation, not arithmetic. Checked before anything else,
    // since there is no BinaryTree here for visitBinary to catch.
    if (isStringConcat()) return null;

    SIUnit target = scan(node.getVariable(), p);
    SIUnit value  = scan(node.getExpression(), p);
    if (target == null || value == null) return target;

    Tree at = node.getExpression();

    return switch (node.getKind()) {
      case PLUS_ASSIGNMENT, MINUS_ASSIGNMENT ->
          compatible(at, target, value,
              node.getKind() == Kind.PLUS_ASSIGNMENT ? "add" : "subtract") ? target : null;

      case REMAINDER_ASSIGNMENT ->
          compatible(at, target, value, "take the remainder of") ? target : null;

      case MULTIPLY_ASSIGNMENT, DIVIDE_ASSIGNMENT -> {
        boolean times = node.getKind() == Kind.MULTIPLY_ASSIGNMENT;
        SIUnit result = times ? target.multiply(value) : target.divide(value);
        if (result.sameDimension(target) && result.sameScalar(target)) yield target;
        report(at, "cannot " + (times ? "multiply" : "divide") + " " + target + " by "
                + value + " in place: result " + result + " does not fit " + target);
        yield null;
      }

      default -> target; // shifts and bitwise ops are integer-only; no units involved
    };
  }


  @Override
  public SIUnit reduce(SIUnit r1, SIUnit r2) {
      return null;
  }

  @Override
  public SIUnit visitBinary(BinaryTree node, Object p) {
    SIUnit l = scan(node.getLeftOperand(), p);
    SIUnit r = scan(node.getRightOperand(), p);
    if (l == null || r == null) return null; //unannotated

    return switch (node.getKind()) {
      case PLUS, MINUS -> {
          if (isStringConcat()) yield null;
          yield compatible(node, l, r, node.getKind() == Kind.PLUS ? "add" : "subtract") ? l : null;
      }

      case REMAINDER ->
          compatible(node, l, r, "take the remainder of") ? l : null;

      case MULTIPLY -> l.multiply(r);
      case DIVIDE   -> l.divide(r);

      case LESS_THAN, GREATER_THAN, LESS_THAN_EQUAL,
            GREATER_THAN_EQUAL, EQUAL_TO, NOT_EQUAL_TO -> {
        if (!isZero(node.getLeftOperand()) && !isZero(node.getRightOperand())) {
          compatible(node, l, r, "compare");
        }
        yield SIUnit.DIMENSIONLESS; // the boolean result carries no unit
      }

      default -> SIUnit.DIMENSIONLESS; // &&, ||, shifts, bitwise
    };
  }

  /**
   * Whether a value may be stored in a target of the given unit, reporting if not.
   *
   * <p>A bare number is accepted into any target. That exception is how a united value
   * is first introduced ({@code @m double d = 10;}, {@code @deg double a = 90;}) and
   * later re-set ({@code d = 20;}); without it nothing could ever bootstrap a unit. It
   * covers bare numbers only, not every dimensionless value: degrees carry a scale, and
   * a declared {@code @rad} is still radians, so neither fits a length or the other
   * angle. It deliberately does not extend to arithmetic, where {@code d + 1.0} stays
   * an error.
   */
  private boolean assignable(Tree at, SIUnit value, SIUnit target) {
    return assignable(at, value, target, "assign", "to");
  }

  /** As {@link #assignable(Tree, SIUnit, SIUnit)}, worded for passing or returning. */
  private boolean assignable(Tree at, SIUnit value, SIUnit target, String verb, String joiner) {
    if (value.isBareNumber()) return true;
    return compatible(at, value, target, verb, joiner);
  }

  /**
   * The one unit of two values that end up in the same place: the branches of a
   * conditional, the elements of an array. A bare number adopts the other's unit, on
   * the same grounds that {@link #assignable} accepts one into any target.
   */
  private SIUnit join(Tree at, SIUnit a, SIUnit b, String verb) {
    if (a == null || b == null) return null;
    if (a.isBareNumber()) return b;
    if (b.isBareNumber()) return a;
    return compatible(at, a, b, verb) ? a : null;
  }

  /**
   * Checks each argument against the unit of the parameter it binds to. A variable-arity
   * parameter {@code @m double... xs} carries its unit on the element type, so arguments
   * spread across it are checked against that; an array passed straight through is
   * checked against the parameter as a whole.
   */
  private void checkArguments(Element callee, List<? extends ExpressionTree> args, Object p) {
    if (!(callee instanceof ExecutableElement method)) { // unresolved; javac reports that
      scan(args, p);
      return;
    }
    List<? extends VariableElement> params = method.getParameters();
    int last = params.size() - 1;
    boolean spread = method.isVarArgs()
        && !(args.size() == params.size() && isArray(args.get(last)));

    for (int i = 0; i < args.size(); i++) {
      SIUnit value = scan(args.get(i), p);
      SIUnit param = spread && i >= last
          ? UnitOf.unitOf(((ArrayType) params.get(last).asType()).getComponentType())
          : UnitOf.unitOf(params.get(i));
      if (value != null && param != null) {
        assignable(args.get(i), value, param, "pass", "as");
      }
    }
  }

  /**
   * Reports a mismatch between two operands that must agree, and says whether they do.
   * Dimension and scale are separate diagnostics: the first is a mistake, the second
   * just needs an explicit conversion.
   */
  private boolean compatible(Tree at, SIUnit l, SIUnit r, String verb) {
    return compatible(at, l, r, verb, "and");
  }

  /**
   * As {@link #compatible(Tree, SIUnit, SIUnit, String)}, but with the word joining the
   * two units spelled out, so directional checks read as "cannot assign m to s" rather
   * than the symmetric "and" that suits operands.
   */
  private boolean compatible(Tree at, SIUnit l, SIUnit r, String verb, String joiner) {
    if (!l.sameDimension(r)) {
      report(at, "cannot " + verb + " " + l + " " + joiner + " " + r);
      return false;
    }
    if (!l.sameScalar(r)) {
      report(at, "explicit conversion required between " + l + " and " + r);
      return false;
    }
    return true;
  }

  /**
   * Reports a unit mistake as a warning, not an error, so that it never stops a build:
   * robot code with a unit warning still compiles and deploys. Warnings are easy to
   * miss in a long build log, and javac drops them under {@code -nowarn} and stops
   * printing them after {@code -Xmaxwarns} (100 by default).
   */
  private void report(Tree at, String msg) {
    trees.printMessage(Diagnostic.Kind.WARNING, msg, at, unit);
  }

  /**
   * Whether an expression is a literal zero: {@code 0}, {@code 0.0}, {@code -0} or any of
   * them in parentheses. Comparing a quantity with zero tests only its sign, which means
   * the same in every unit and scale (0 rot/s is 0 rad/s is 0 m/s), so it is the one
   * bare number a comparison accepts. {@code diff <= 5} stays an error: 5 of what?
   *
   * <p>Only a literal counts. A variable or constant holding zero could hold anything,
   * and arithmetic such as {@code d + 0} is unaffected.
   */
  private static boolean isZero(ExpressionTree e) {
    while (e instanceof ParenthesizedTree paren) e = paren.getExpression();
    if (e instanceof UnaryTree unary
        && (unary.getKind() == Kind.UNARY_MINUS || unary.getKind() == Kind.UNARY_PLUS)) {
      return isZero(unary.getExpression());
    }
    return e instanceof LiteralTree literal
        && literal.getValue() instanceof Number n
        && n.doubleValue() == 0;
  }

  private boolean isArray(Tree t) {
    TypeMirror type = trees.getTypeMirror(new TreePath(getCurrentPath(), t));
    return type != null && type.getKind() == TypeKind.ARRAY;
  }

  private boolean isStringConcat() {
    TypeMirror t = trees.getTypeMirror(getCurrentPath());
    return t != null &&
      t.getKind() == TypeKind.DECLARED &&
      ((TypeElement) ((DeclaredType) t).asElement())
        .getQualifiedName().contentEquals("java.lang.String");
  }
}
