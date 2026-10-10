import static org.jsi.Expect.assertError;

import org.jsi.annotations.Units.*;

/** Both branches of c ? a : b must agree; a bare number takes the other's unit. */
class Conditionals {

  void branchesAgree(boolean far) {
    @m double a = 1, b = 2;
    @m double chosen = far ? a : b;
  }

  void branchesDisagree(boolean far) {
    @m double distance = 1;
    @s double time = 2;
    assertError("cannot choose between m and s");
    double nonsense = far ? distance : time;
  }

  void numberBranchTakesTheUnit(boolean far) {
    @m double distance = 1;
    @m double clamped = far ? distance : 0;
  }

  void numberBranchStillGivesTheUnit(boolean far) {
    @m double distance = 1;
    assertError("cannot assign m to s");
    @s double wrong = far ? distance : 0;
  }
}
