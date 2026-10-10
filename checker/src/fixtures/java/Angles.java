import static org.jsi.Expect.assertError;
import static org.jsi.conversions.Conversions.*;

import org.jsi.annotations.Units.*;

/** Angles are dimensionless, as in the SI, but degrees and radians never mix. */
class Angles {

  void writingAngles() {
    @deg double heading = 90;
    @rad double small = 0.1;
    @rot double turns = 2.5;
  }

  void degreesAreNotRadians() {
    @deg double heading = 90;
    assertError("explicit conversion required between deg and rad");
    @rad double wrong = heading;
  }

  void radiansAreNotDegrees() {
    @rad double small = 0.1;
    assertError("explicit conversion required between rad and deg");
    @deg double wrong = small;
  }

  void rotationsAreNotDegrees() {
    @rot double turns = 2;
    assertError("explicit conversion required between rot and deg");
    @deg double wrong = turns;
  }

  void anglesAreNotLengths() {
    @deg double heading = 90;
    @rad double small = 0.1;
    assertError("cannot assign deg to m");
    @m double wrong = heading;
    assertError("cannot assign rad to m");
    @m double alsoWrong = small;
  }

  void arcLengthNeedsNoCast() {
    @m double radius = 2;
    @rad double theta = 0.5;
    @m double arc = radius * theta;
  }

  void arcLengthInDegreesIsCaught() {
    @m double radius = 2;
    @deg double theta = 30;
    assertError("explicit conversion required between 0.017453292519943295·m and m");
    @m double arc = radius * theta;
  }

  void angleFromARatio() {
    @m double arc = 1, radius = 2;
    @rad double theta = arc / radius;
  }

  void solidAngle() {
    @m2 double area = 4;
    @m double r = 2;
    @sr double omega = area / (r * r);
  }

  void castCannotConvertAngles() {
    @deg double heading = 90;
    assertError("explicit conversion required between deg and rad");
    @rad double wrong = (@rad double) heading;
  }

  void scalingAnAngleInPlace(boolean flip) {
    @deg double heading = 90;
    heading *= 2;
    @deg double chosen = flip ? heading : 0;
  }

  void lengthScaledByRadiansInPlace() {
    @m double d = 1;
    @rad double r = 2;
    d *= r;
  }

  void lengthScaledByDegreesInPlace() {
    @m double d = 1;
    @deg double a = 90;
    assertError("cannot multiply m by deg in place: result 0.017453292519943295·m does not fit m");
    d *= a;
  }

  void angularSpeedFromAngleOverTime() {
    @rot double turns = 3;
    @s double time = 2;
    @rot_s double speed = turns / time;
    @rot_s2 double accel = speed / time;
  }

  void rotationsPerSecondAreNotRadiansPerSecond() {
    @rot_s double rps = 2;
    assertError("explicit conversion required between rot_s and rad_s");
    @rad_s double wrong = rps;
  }

  void speedIsNotAcceleration() {
    @rad_s double omega = 1;
    assertError("cannot assign rad_s to rad_s2");
    @rad_s2 double wrong = omega;
  }
}
