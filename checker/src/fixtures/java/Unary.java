import static org.jsi.Expect.assertError;

import org.jsi.annotations.Units.*;

/** Sign and step operators keep the unit; ! and ~ are not quantities. */
class Unary {

  void negatingKeepsTheUnit() {
    @m double distance = 10;
    @m double back = -distance;
    @m double same = +distance;
  }

  void negatingDoesNotChangeTheUnit() {
    @m double distance = 10;
    assertError("cannot assign m to s");
    @s double wrong = -distance;
  }

  void steppingKeepsTheUnit() {
    @m double distance = 10;
    distance++;
    distance--;
    ++distance;
  }

  void steppedValueKeepsTheUnit() {
    @m double distance = 10;
    assertError("cannot assign m to s");
    @s double wrong = distance++;
  }

  void notIsABoolean() {
    @m double a = 1, b = 2;
    boolean different = !(a == b);
  }
}
