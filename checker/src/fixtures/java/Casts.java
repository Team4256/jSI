import static org.jsi.Expect.assertError;

import org.jsi.annotations.Units.*;

/** A cast can label a number with a unit, but never change one. */
class Casts {

  void numericCastKeepsTheUnit() {
    @m double distance = 10.7;
    @m double whole = (int) distance;
  }

  void numericCastDoesNotChangeTheUnit() {
    @m double distance = 10.7;
    assertError("cannot assign m to s");
    @s double wrong = (int) distance;
  }

  void labellingAnUnannotatedResult() {
    @m2 double area = 9;
    @m double side = (@m double) Math.sqrt(area);
  }

  void labellingABareNumber() {
    @m double distance = (@m double) 10;
  }

  void restatingTheSameUnit() {
    @m double distance = 10;
    @m double same = (@m double) distance;
  }

  void castCannotChangeTheDimension() {
    @m double distance = 10;
    assertError("cannot cast m to s");
    @s double wrong = (@s double) distance;
  }

  void castCannotChangeTheScale() {
    @km double trip = 5;
    assertError("explicit conversion required between km and m");
    @m double wrong = (@m double) trip;
  }
}
