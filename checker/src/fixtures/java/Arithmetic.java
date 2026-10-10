import static org.jsi.Expect.assertError;

import org.jsi.annotations.Units.*;

/** Binary operators: + - % need agreeing units, * and / build new ones. */
class Arithmetic {

  void addingLikeUnits() {
    @m double a = 1, b = 2;
    @m double total = a + b;
    @m double gap = b - a;
    @m double rest = b % a;
  }

  void addingUnlikeUnits() {
    @m double distance = 1;
    @s double time = 2;
    assertError("cannot add m and s");
    double nonsense = distance + time;
  }

  void subtractingUnlikeUnits() {
    @m double distance = 1;
    @s double time = 2;
    assertError("cannot subtract m and s");
    double nonsense = distance - time;
  }

  void remainderOfUnlikeUnits() {
    @m double distance = 1;
    @s double time = 2;
    assertError("cannot take the remainder of m and s");
    double nonsense = distance % time;
  }

  void comparingLikeUnits() {
    @m double a = 1, b = 2;
    boolean shorter = a < b;
    boolean same = a == b;
  }

  void comparingUnlikeUnits() {
    @m double distance = 1;
    @s double time = 2;
    assertError("cannot compare m and s");
    boolean nonsense = distance < time;
  }

  void multiplyingBuildsAUnit() {
    @m double width = 2, height = 3;
    @m2 double area = width * height;
  }

  void dividingBuildsAUnit() {
    @m double distance = 10;
    @s double time = 2;
    @m_s double speed = distance / time;
  }

  void builtUnitMustFitItsTarget() {
    @m double distance = 10;
    @s double time = 2;
    assertError("cannot assign m·s⁻¹ to s");
    @s double wrong = distance / time;
  }

  void scalingByANumberKeepsTheUnit() {
    @m double distance = 10;
    @m double twice = distance * 2;
    @m double half = distance / 2;
    @m double alsoTwice = 2 * distance;
  }

  void parenthesesChangeNothing() {
    @m double distance = 10;
    @s double time = 2;
    @m_s2 double accel = distance / ((time) * (time));
  }

  void stringConcatenationIsNotArithmetic() {
    @m double distance = 10;
    @s double time = 2;
    String label = "distance " + distance + " time " + time;
  }
}
