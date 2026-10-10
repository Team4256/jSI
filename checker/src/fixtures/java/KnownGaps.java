import java.util.List;

import org.jsi.annotations.Units.*;

/**
 * Mistakes the checker does not catch yet, written as they compile today: cleanly.
 * Each is a real gap, not intended behaviour. When one is fixed, its method gains
 * the assertError it should have had, and this file shows it.
 */
class KnownGaps {

  static class Wheel {
    Wheel(@m double radius) {}
  }

  /** Units on generic type arguments are not tracked. */
  void genericElementsAreUnchecked(List<@m Double> lengths) {
    for (@s double wrong : lengths) {}
  }

  /** An anonymous subclass's constructor drops its parameters' units. */
  void anonymousClassArgumentsAreUnchecked() {
    @s double time = 2;
    new Wheel(time) {};
  }

  /** A ratio computed in code is a bare number, so it becomes any angle. */
  void computedRatioBecomesAnyAngle() {
    @m double arc = 1, radius = 2;
    @deg double notReallyDegrees = arc / radius;
  }

  /** Radians per second and hertz are both s⁻¹, so they are interchangeable. */
  void angularSpeedIsFrequency() {
    @rad_s double omega = 6.28;
    @Hz double f = omega;
  }

  /** Torque and energy are both kg·m²·s⁻², so they are interchangeable. */
  void torqueIsEnergy() {
    @Nm double torque = 10;
    @J double work = torque;
  }
}
