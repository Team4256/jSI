import static org.jsi.Expect.assertError;

import org.jsi.annotations.Units.*;

/** d op= x means d = d op x, and the result must still fit d. */
class CompoundAssignment {

  void accumulatingLikeUnits() {
    @m double total = 0;
    @m double step = 2;
    total += step;
    total -= step;
    total %= step;
  }

  void accumulatingUnlikeUnits() {
    @m double total = 0;
    @s double time = 2;
    assertError("cannot add m and s");
    total += time;
  }

  void subtractingUnlikeUnits() {
    @m double total = 0;
    @s double time = 2;
    assertError("cannot subtract m and s");
    total -= time;
  }

  void addingABareNumberIsArithmetic() {
    @m double total = 0;
    assertError("cannot add m and dimensionless");
    total += 1;
  }

  void scalingInPlace() {
    @m double distance = 10;
    distance *= 2;
    distance /= 4;
  }

  void multiplyingInPlaceCannotChangeTheUnit() {
    @m double distance = 10;
    @s double time = 2;
    assertError("cannot multiply m by s in place: result m·s does not fit m");
    distance *= time;
  }

  void dividingInPlaceCannotChangeTheUnit() {
    @m double distance = 10;
    @s double time = 2;
    assertError("cannot divide m by s in place: result m·s⁻¹ does not fit m");
    distance /= time;
  }

  void appendingToAString() {
    @m double distance = 10;
    String label = "distance ";
    label += distance;
  }
}
