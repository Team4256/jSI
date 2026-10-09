package org.jsi;

import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import com.sun.source.tree.BlockTree;
import com.sun.source.tree.CompilationUnitTree;
import com.sun.source.tree.ExpressionStatementTree;
import com.sun.source.tree.IdentifierTree;
import com.sun.source.tree.LiteralTree;
import com.sun.source.tree.MemberSelectTree;
import com.sun.source.tree.MethodInvocationTree;
import com.sun.source.tree.MethodTree;
import com.sun.source.tree.StatementTree;
import com.sun.source.tree.Tree;
import com.sun.source.util.JavacTask;
import com.sun.source.util.SourcePositions;
import com.sun.source.util.TreePath;
import com.sun.source.util.TreePathScanner;
import com.sun.source.util.Trees;

/**
 * Compiles every file under {@code src/fixtures/java} with the jSI plugin, one test per
 * file, and checks what javac reports against the file's {@link Expect#assertError}
 * markers.
 *
 * <p>The checker reports unit mistakes as warnings, so warnings count as well as errors.
 * Each must fall inside the statement following a marker with the same message, and
 * every marker must be met. Anything else — a warning or error in unmarked code, which
 * includes ordinary Java mistakes and javac's own warnings, or a marker nothing
 * satisfied — fails the file.
 */
class FixtureTest {
  private static final Path FIXTURES = Path.of("src/fixtures/java");

  @TestFactory
  Stream<DynamicTest> fixtures() throws IOException {
    return Files.walk(FIXTURES)
        .filter(f -> f.toString().endsWith(".java"))
        .sorted()
        .map(f -> DynamicTest.dynamicTest(FIXTURES.relativize(f).toString(), () -> check(f)));
  }

  /** What one {@code assertError} call demands: this message, within these lines. */
  private record Expected(String message, long firstLine, long lastLine, String method) {
    boolean metBy(Diagnostic<?> d) {
      return d.getMessage(null).equals(message)
          && d.getLineNumber() >= firstLine && d.getLineNumber() <= lastLine;
    }
  }

  /** A method's extent, so a failure can say which fixture method it came from. */
  private record Method(String name, long firstLine, long lastLine) {}

  private void check(Path fixture) throws IOException {
    JavaCompiler javac = ToolProvider.getSystemJavaCompiler();
    DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();

    try (StandardJavaFileManager files = javac.getStandardFileManager(null, null, null)) {
      // The classpath is the test JVM's own. javac looks for plugins on the classpath
      // it is given, not the one it runs in, so without this it finds neither the
      // plugin nor the unit annotations nor Expect.
      List<String> options = List.of("-Xplugin:jSI", "-classpath", System.getProperty("java.class.path"));
      JavacTask task = (JavacTask) javac.getTask(
          null, files, diagnostics, options, null, files.getJavaFileObjects(fixture));

      CompilationUnitTree unit = task.parse().iterator().next();
      task.analyze(); // runs the plugin; nothing needs generating

      Markers markers = new Markers(Trees.instance(task).getSourcePositions(), unit);
      markers.scan(unit, null);
      report(fixture, markers, diagnostics.getDiagnostics());
    }
  }

  private void report(Path fixture, Markers markers, List<Diagnostic<? extends JavaFileObject>> all) {
    List<Expected> unmet = new ArrayList<>(markers.expected);
    List<String> problems = new ArrayList<>();

    for (Diagnostic<?> d : all) {
      if (d.getKind() != Diagnostic.Kind.ERROR && d.getKind() != Diagnostic.Kind.WARNING) continue;
      Expected match = unmet.stream().filter(e -> e.metBy(d)).findFirst().orElse(null);
      if (match != null) {
        unmet.remove(match);
      } else {
        problems.add(String.format("unexpected  line %d in %s: %s",
            d.getLineNumber(), markers.methodAt(d.getLineNumber()), d.getMessage(null)));
      }
    }
    for (Expected e : unmet) {
      problems.add(String.format("missed      line %d in %s: %s", e.firstLine, e.method, e.message));
    }

    if (!problems.isEmpty()) fail(fixture + "\n  " + String.join("\n  ", problems));
  }

  /** Collects the {@code assertError} markers in a fixture, and its methods' extents. */
  private static final class Markers extends TreePathScanner<Void, Void> {
    private final SourcePositions positions;
    private final CompilationUnitTree unit;
    final List<Expected> expected = new ArrayList<>();
    final List<Method> methods = new ArrayList<>();

    Markers(SourcePositions positions, CompilationUnitTree unit) {
      this.positions = positions;
      this.unit = unit;
    }

    @Override
    public Void visitMethod(MethodTree node, Void p) {
      methods.add(new Method(node.getName() + "()", firstLine(node), lastLine(node)));
      return super.visitMethod(node, p);
    }

    @Override
    public Void visitMethodInvocation(MethodInvocationTree node, Void p) {
      if (isMarker(node)) {
        if (!(node.getArguments().get(0) instanceof LiteralTree literal)) {
          throw new IllegalArgumentException("assertError takes a string literal, at line " + firstLine(node));
        }
        String message = (String) literal.getValue();
        StatementTree target = statementAfter(getCurrentPath());
        Tree at = target != null ? target : node; // nothing follows: can only be missed
        expected.add(new Expected(message, firstLine(at), lastLine(at), methodAt(firstLine(node))));
      }
      return super.visitMethodInvocation(node, p);
    }

    /**
     * The statement a marker applies to: the next one in its block that is not itself
     * a marker, so that consecutive markers all apply to the same statement.
     */
    private StatementTree statementAfter(TreePath marker) {
      TreePath statement = marker.getParentPath();
      if (!(statement.getParentPath().getLeaf() instanceof BlockTree block)) return null;

      List<? extends StatementTree> statements = block.getStatements();
      for (int i = statements.indexOf(statement.getLeaf()) + 1; i < statements.size(); i++) {
        StatementTree s = statements.get(i);
        boolean isMarker = s instanceof ExpressionStatementTree es
            && es.getExpression() instanceof MethodInvocationTree call && isMarker(call);
        if (!isMarker) return s;
      }
      return null;
    }

    private static boolean isMarker(MethodInvocationTree call) {
      Tree select = call.getMethodSelect();
      String name = select instanceof IdentifierTree id ? id.getName().toString()
                  : select instanceof MemberSelectTree ms ? ms.getIdentifier().toString()
                  : "";
      return name.equals("assertError") && call.getArguments().size() == 1;
    }

    /** The innermost method containing a line. */
    String methodAt(long line) {
      return methods.stream()
          .filter(m -> m.firstLine <= line && line <= m.lastLine)
          .min((a, b) -> Long.compare(a.lastLine - a.firstLine, b.lastLine - b.firstLine))
          .map(Method::name)
          .orElse("class body");
    }

    private long firstLine(Tree t) {
      return unit.getLineMap().getLineNumber(positions.getStartPosition(unit, t));
    }

    private long lastLine(Tree t) {
      return unit.getLineMap().getLineNumber(positions.getEndPosition(unit, t));
    }
  }
}
