import static org.jsi.Expect.assertError;

import org.jsi.annotations.Units.*;

/** An array carries its elements' unit, through indexing, initializers and loops. */
class Arrays {

  @m double[] lengths = {1, 2, 3};
  @s double time = 2;

  void elementHasTheArraysUnit() {
    @m double first = lengths[0];
  }

  void elementIntoTheWrongUnit() {
    assertError("cannot assign m to s");
    @s double wrong = lengths[0];
  }

  void storingTheWrongUnit() {
    assertError("cannot assign s to m");
    lengths[1] = time;
  }

  void newArrayStartsAsZeros() {
    @m double[] buffer = new double[8];
  }

  void initializerElementsAgree() {
    @m double a = 1;
    @m double[] pair = {a, a};
    @m double[] withNumbers = {a, 0, 5};
  }

  void initializerElementsDisagree() {
    @m double a = 1;
    assertError("cannot mix m and s");
    @m double[] mixed = {a, time};
  }

  void loopVariableMatches() {
    for (@m double length : lengths) {}
  }

  void loopVariableWrongUnit() {
    assertError("cannot assign m to s");
    for (@s double wrong : lengths) {}
  }

  void unannotatedLoopVariable() {
    for (double plain : lengths) {}
  }

  void arrayParameterKeepsItsUnit(@m double[] parts) {
    assertError("cannot assign m to s");
    @s double wrong = parts[0];
  }
}
