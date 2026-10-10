import static org.jsi.Expect.assertError;

import org.jsi.annotations.Units.*;

/** Every derived unit, reached the way the physics defines it. */
class DerivedUnits {

  @m double length = 2;
  @s double time = 4;
  @kg double mass = 3;
  @A double current = 5;

  void geometry() {
    @m2 double area = length * length;
    @m3 double volume = area * length;
    @kg_m3 double density = mass / volume;
    @kg_m2 double surfaceDensity = mass / area;
  }

  void motion() {
    @m_s double speed = length / time;
    @m_s2 double accel = speed / time;
    @Hz double frequency = 1 / time;
  }

  void mechanics() {
    @m_s2 double accel = length / (time * time);
    @N double force = mass * accel;
    @J double work = force * length;
    @Nm double torque = force * length;
    @W double power = work / time;
    @Pa double pressure = force / (length * length);
  }

  void electricity() {
    @W double power = 100;
    @C double charge = current * time;
    @V double voltage = power / current;
    @Ohm double resistance = voltage / current;
    @F double capacitance = charge / voltage;
  }

  void magnetism() {
    @V double voltage = 12;
    @Wb double flux = voltage * time;
    @T double fluxDensity = flux / (length * length);
    @H double inductance = flux / current;
  }

  void forceIsNotPressure() {
    @N double force = 10;
    assertError("cannot assign N to Pa");
    @Pa double wrong = force;
  }

  void powerIsNotEnergy() {
    @W double power = 10;
    assertError("cannot assign W to J");
    @J double wrong = power;
  }
}
