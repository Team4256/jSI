import static org.jsi.Expect.assertError;
import static org.jsi.conversions.Conversions.*;

import org.jsi.annotations.Units.*;

/** The conversions, called from code: checked on the way in and the way out. */
class UsingConversions {

  void eachFamilyConverts() {
    @s double seconds = millisecondsToSeconds(20);
    @km double trip = metersToKilometers(1500);
    @kg double mass = gramsToKilograms(500);
    @mA double small = amperesToMilliamperes(0.2);
    @mol double amount = millimolesToMoles(3);
    @mcd double led = candelasToMillicandelas(0.05);
    @rad double angle = degreesToRadians(90);
    @rad_s double omega = rotationsPerSecondToRadiansPerSecond(2);
    @deg_s2 double alpha = radiansPerSecondSquaredToDegreesPerSecondSquared(1);
    @m double bumper = inchesToMeters(3.5);
  }

  void conversionsChain() {
    @ft double field = 54;
    @yd double same = metersToYards(feetToMeters(field));
    @rot double turns = degreesToRotations(radiansToDegrees(3.14));
  }

  void wrongInput() {
    @s double time = 3;
    assertError("cannot pass s as km");
    kilometersToMeters(time);
  }

  void wrongScaleInput() {
    @mm double small = 5;
    assertError("explicit conversion required between mm and km");
    kilometersToMeters(small);
  }

  void wrongDirection() {
    @km double trip = 5;
    assertError("explicit conversion required between km and m");
    metersToKilometers(trip);
  }

  void resultIntoTheWrongScale() {
    @km double trip = 5;
    assertError("explicit conversion required between m and km");
    @km double wrong = kilometersToMeters(trip);
  }
}
