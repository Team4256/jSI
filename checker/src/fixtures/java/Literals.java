import static org.jsi.Expect.assertError;

import org.jsi.annotations.Units.*;

/** A bare number may become any unit on assignment, but not in arithmetic. */
class Literals {

  void numberStartsAUnit() {
    @m double distance = 10;
    @deg double angle = 90;
    @kg double mass = 0.5;
  }

  void numberResetsAUnit() {
    @m double distance = 10;
    distance = 20;
  }

  void numberInArithmeticIsNotAUnit() {
    @m double distance = 10;
    assertError("cannot add m and dimensionless");
    double next = distance + 1.0;
  }

  void numberInAComparisonIsNotAUnit() {
    @m double distance = 10;
    assertError("cannot compare m and dimensionless");
    boolean far = distance > 5;
  }

  void tinyNumberIsStillNotZero() {
    @m double distance = 10;
    assertError("cannot compare m and dimensionless");
    boolean far = distance > 1e-9;
  }

  void zeroComparesWithAnyUnit() {
    @m double distance = 10;
    @rot_s double diff = -2;
    @km double trip = 5;
    boolean positive = distance > 0;
    boolean slowing = diff <= 0.0;
    boolean reversed = 0 < distance;
    boolean signed = trip >= -0;
    boolean bracketed = (diff) != (0);
    boolean stopped = diff == 0;
  }

  void zeroInArithmeticIsStillANumber() {
    @m double distance = 10;
    assertError("cannot add m and dimensionless");
    double same = distance + 0;
  }

  void zeroOfTheRightUnitCompares() {
    @m double distance = 10;
    @m double zero = 0;
    boolean far = distance > zero;
  }
}
