package org.jsi.annotations;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;
import static org.jsi.annotations.SIPrefixs.*;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

public class Units {
  @Retention(RUNTIME)
  @Target({ANNOTATION_TYPE, TYPE_USE})
  public @interface Dimension {
    double s()      default 0;
    double m()      default 0;
    double kg()     default 0;
    double A()      default 0;
    double K()      default 0;
    double mol()    default 0;
    double cd()     default 0;
    double scalar() default 1;
  } //base unit

  // Every unit targets METHOD and PARAMETER as well as TYPE_USE. Before JDK 22
  // javac does not read type annotations back out of compiled classes, so a unit
  // on a precompiled method's signature (Conversions, or any library) would be
  // invisible to the checker. The declaration annotations these two targets add
  // are read by every JDK.

  //Time
  @Dimension(s = 1) @Retention(CLASS) @Target({FIELD, LOCAL_VARIABLE, METHOD, PARAMETER, TYPE_USE})
  public @interface s {} //second

  @Dimension(s = 1, scalar = MILLI) @Retention(CLASS) @Target({FIELD, LOCAL_VARIABLE, METHOD, PARAMETER, TYPE_USE})
  public @interface ms {} //millisecond
  @Dimension(s = 1, scalar = MICRO) @Retention(CLASS) @Target({FIELD, LOCAL_VARIABLE, METHOD, PARAMETER, TYPE_USE})
  public @interface us {} //microsecond
  @Dimension(s = 1, scalar = NANO) @Retention(CLASS) @Target({FIELD, LOCAL_VARIABLE, METHOD, PARAMETER, TYPE_USE})
  public @interface ns {} //nanosecond

  //Length
  @Dimension(m = 1) @Retention(CLASS) @Target({FIELD, LOCAL_VARIABLE, METHOD, PARAMETER, TYPE_USE})
  public @interface m {} //meters

  @Dimension(m = 1, scalar = KILO) @Retention(CLASS) @Target({FIELD, LOCAL_VARIABLE, METHOD, PARAMETER, TYPE_USE})
  public @interface km {} //kilometer
  @Dimension(m = 1, scalar = CENTI) @Retention(CLASS) @Target({FIELD, LOCAL_VARIABLE, METHOD, PARAMETER, TYPE_USE})
  public @interface cm {} //centimeter
  @Dimension(m = 1, scalar = MILLI) @Retention(CLASS) @Target({FIELD, LOCAL_VARIABLE, METHOD, PARAMETER, TYPE_USE})
  public @interface mm {} //millimeter
  @Dimension(m = 1, scalar = MICRO) @Retention(CLASS) @Target({FIELD, LOCAL_VARIABLE, METHOD, PARAMETER, TYPE_USE})
  public @interface um {} //micrometer
  @Dimension(m = 1, scalar = NANO) @Retention(CLASS) @Target({FIELD, LOCAL_VARIABLE, METHOD, PARAMETER, TYPE_USE})
  public @interface nm {} //nanometer

  //Mass
  @Dimension(kg = 1) @Retention(CLASS) @Target({FIELD, LOCAL_VARIABLE, METHOD, PARAMETER, TYPE_USE})
  public @interface kg {} //kilogram
  @Dimension(kg = 1, scalar = 1 / KILO) @Retention(CLASS) @Target({FIELD, LOCAL_VARIABLE, METHOD, PARAMETER, TYPE_USE})
  public @interface g {} //gram

  @Dimension(kg = 1, scalar = MILLI / KILO) @Retention(CLASS) @Target({FIELD, LOCAL_VARIABLE, METHOD, PARAMETER, TYPE_USE})
  public @interface mg {} //milligram
  @Dimension(kg = 1, scalar = MICRO / KILO) @Retention(CLASS) @Target({FIELD, LOCAL_VARIABLE, METHOD, PARAMETER, TYPE_USE})
  public @interface ug {} //microgram

  //Electric Current
  @Dimension(A = 1) @Retention(CLASS) @Target({FIELD, LOCAL_VARIABLE, METHOD, PARAMETER, TYPE_USE})
  public @interface A {} //ampere

  @Dimension(A = 1, scalar = MILLI) @Retention(CLASS) @Target({FIELD, LOCAL_VARIABLE, METHOD, PARAMETER, TYPE_USE})
  public @interface mA {} //milliampere
  @Dimension(A = 1, scalar = MICRO) @Retention(CLASS) @Target({FIELD, LOCAL_VARIABLE, METHOD, PARAMETER, TYPE_USE})
  public @interface uA {} //microampere

  //Thermodynamic Temperature
  @Dimension(K = 1) @Retention(CLASS) @Target({FIELD, LOCAL_VARIABLE, METHOD, PARAMETER, TYPE_USE})
  public @interface K {} //kelvin

  //Amount of Substance
  @Dimension(mol = 1) @Retention(CLASS) @Target({FIELD, LOCAL_VARIABLE, METHOD, PARAMETER, TYPE_USE})
  public @interface mol {} //mol

  @Dimension(mol = 1, scalar = MILLI) @Retention(CLASS) @Target({FIELD, LOCAL_VARIABLE, METHOD, PARAMETER, TYPE_USE})
  public @interface mmol {} //millimole
  @Dimension(mol = 1, scalar = MICRO) @Retention(CLASS) @Target({FIELD, LOCAL_VARIABLE, METHOD, PARAMETER, TYPE_USE})
  public @interface umol {} //micromole

  //Luminous Intensity
  @Dimension(cd = 1) @Retention(CLASS) @Target({FIELD, LOCAL_VARIABLE, METHOD, PARAMETER, TYPE_USE})
  public @interface cd {} //candela

  @Dimension(cd = 1, scalar = MILLI) @Retention(CLASS) @Target({FIELD, LOCAL_VARIABLE, METHOD, PARAMETER, TYPE_USE})
  public @interface mcd {} //millicandela

  // Area
  @Dimension(m = 2) @Retention(CLASS) @Target({FIELD, LOCAL_VARIABLE, METHOD, PARAMETER, TYPE_USE})
  public @interface m2 {} //square meter

  // Volume
  @Dimension(m = 3) @Retention(CLASS) @Target({FIELD, LOCAL_VARIABLE, METHOD, PARAMETER, TYPE_USE})
  public @interface m3 {} //cubic meter

  //Velocity
  @Dimension(m = 1, s = -1) @Retention(CLASS) @Target({FIELD, LOCAL_VARIABLE, METHOD, PARAMETER, TYPE_USE})
  public @interface m_s {} //meters per second

  @Dimension(s = -1) @Retention(CLASS) @Target({FIELD, LOCAL_VARIABLE, METHOD, PARAMETER, TYPE_USE})
  public @interface rad_s {} //radian per second

  @Dimension(s = -1, scalar = SIPrefixs.DEGREE ) @Retention(CLASS) @Target({FIELD, LOCAL_VARIABLE, METHOD, PARAMETER, TYPE_USE})
  public @interface deg_s {} //degree per second

  @Dimension(s = -1, scalar = SIPrefixs.ROTATION) @Retention(CLASS) @Target({FIELD, LOCAL_VARIABLE, METHOD, PARAMETER, TYPE_USE})
  public @interface rot_s {} //rotation per second

  //Acceleration
  @Dimension(m = 1, s = -2) @Retention(CLASS) @Target({FIELD, LOCAL_VARIABLE, METHOD, PARAMETER, TYPE_USE})
  public @interface m_s2 {} // meters per second per second

  @Dimension(s = -2) @Retention(CLASS) @Target({FIELD, LOCAL_VARIABLE, METHOD, PARAMETER, TYPE_USE})
  public @interface rad_s2 {} //radian per second per second

  @Dimension(s = -2, scalar = SIPrefixs.DEGREE ) @Retention(CLASS) @Target({FIELD, LOCAL_VARIABLE, METHOD, PARAMETER, TYPE_USE})
  public @interface deg_s2 {} //degree per second per second

  @Dimension(s = -2, scalar = SIPrefixs.ROTATION) @Retention(CLASS) @Target({FIELD, LOCAL_VARIABLE, METHOD, PARAMETER, TYPE_USE})
  public @interface rot_s2 {} //rotation per second per second

  // Density
  @Dimension(kg = 1, m = -3) @Retention(CLASS) @Target({FIELD, LOCAL_VARIABLE, METHOD, PARAMETER, TYPE_USE})
  public @interface kg_m3 {} //kilogram per cubic meter

  // surface density
  @Dimension(kg = 1, m = -2) @Retention(CLASS) @Target({FIELD, LOCAL_VARIABLE, METHOD, PARAMETER, TYPE_USE})
  public @interface kg_m2 {} //kilogram per square meter

  // Force
  @Dimension(kg = 1, m = 1, s = -2) @Retention(CLASS) @Target({FIELD, LOCAL_VARIABLE, METHOD, PARAMETER, TYPE_USE})
  public @interface N {} // Newtons

  // Torque
  @Dimension(kg = 1, m = 2, s = -2) @Retention(CLASS) @Target({FIELD, LOCAL_VARIABLE, METHOD, PARAMETER, TYPE_USE})
  public @interface Nm {} // Newton Meter

  // Work
  @Dimension(kg = 1, m = 2, s = -2) @Retention(CLASS) @Target({FIELD, LOCAL_VARIABLE, METHOD, PARAMETER, TYPE_USE})
  public @interface J {} // Joule

  // Power
  @Dimension(kg = 1, m = 2, s = -3) @Retention(CLASS) @Target({FIELD, LOCAL_VARIABLE, METHOD, PARAMETER, TYPE_USE})
  public @interface W {} //Watts

  //Frequency
  @Dimension(s = -1) @Retention(CLASS) @Target({FIELD, LOCAL_VARIABLE, METHOD, PARAMETER, TYPE_USE})
  public @interface Hz {} //Hertz

  // Pressure
  @Dimension(kg =  1, m = -1, s = -2) @Retention(CLASS) @Target({FIELD, LOCAL_VARIABLE, METHOD, PARAMETER, TYPE_USE})
  public @interface Pa {} //Pascal

  // Electric Charge
  @Dimension(s = 1, A = 1) @Retention(CLASS) @Target({FIELD, LOCAL_VARIABLE, METHOD, PARAMETER, TYPE_USE})
  public @interface C {} //Coulomb

  // Electrical Potential Difference
  @Dimension(kg = 1, m = 2, s = -3, A = -1) @Retention(CLASS) @Target({FIELD, LOCAL_VARIABLE, METHOD, PARAMETER, TYPE_USE})
  public @interface V {} //Volt

  // Electrical Resistance
  @Dimension(kg = 1, m = 2, s = -3, A = -2) @Retention(CLASS) @Target({FIELD, LOCAL_VARIABLE, METHOD, PARAMETER, TYPE_USE})
  public @interface Ohm {} //Ohm

  // Capacitance
  @Dimension(kg = -1, m = -2, s = 4, A = 2) @Retention(CLASS) @Target({FIELD, LOCAL_VARIABLE, METHOD, PARAMETER, TYPE_USE})
  public @interface F {} //Farad

  // Inducatance
  @Dimension(kg = 1, m = 2, s = -2, A = -2) @Retention(CLASS) @Target({FIELD, LOCAL_VARIABLE, METHOD, PARAMETER, TYPE_USE})
  public @interface H {} //Henery

  // Magnetic Flux Density
  @Dimension(kg = 1, s = -2, A = -1) @Retention(CLASS) @Target({FIELD, LOCAL_VARIABLE, METHOD, PARAMETER, TYPE_USE})
  public @interface T {} //Tesla

  // Magnetic Flux
  @Dimension(kg = 1, m = 2, s = -2, A = -1) @Retention(CLASS) @Target({FIELD, LOCAL_VARIABLE, METHOD, PARAMETER, TYPE_USE})
  public @interface Wb {} //Weber

  //Angles
  @Dimension() @Retention(CLASS) @Target({FIELD, LOCAL_VARIABLE, METHOD, PARAMETER, TYPE_USE})
  public @interface rad {} //radian
  @Dimension(scalar = SIPrefixs.DEGREE) @Retention(CLASS) @Target({FIELD, LOCAL_VARIABLE, METHOD, PARAMETER, TYPE_USE})
  public @interface deg {} //degree
  @Dimension(scalar = SIPrefixs.ROTATION) @Retention(CLASS) @Target({FIELD, LOCAL_VARIABLE, METHOD, PARAMETER, TYPE_USE})
  public @interface rot {} //rotations
  @Dimension() @Retention(CLASS) @Target({FIELD, LOCAL_VARIABLE, METHOD, PARAMETER, TYPE_USE})
  public @interface sr {} //steradian

  // Imperial Units

  // Distance
  @Dimension(m = 1, scalar = ImperialFactors.INCH) @Retention(CLASS) @Target({FIELD, LOCAL_VARIABLE, METHOD, PARAMETER, TYPE_USE})
  public @interface in {} //Inches
  @Dimension(m = 1, scalar = ImperialFactors.FOOT) @Retention(CLASS) @Target({FIELD, LOCAL_VARIABLE, METHOD, PARAMETER, TYPE_USE})
  public @interface ft {} //feet
  @Dimension(m = 1, scalar = ImperialFactors.YARD) @Retention(CLASS) @Target({FIELD, LOCAL_VARIABLE, METHOD, PARAMETER, TYPE_USE})
  public @interface yd {} //yard
}
