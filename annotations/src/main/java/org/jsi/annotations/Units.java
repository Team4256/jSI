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

  @Dimension(s = 1, scalar = TERA) @Retention(CLASS) @Target({FIELD, LOCAL_VARIABLE, METHOD, PARAMETER, TYPE_USE})
  public @interface Ts {} //terasecond
  @Dimension(s = 1, scalar = GIGA) @Retention(CLASS) @Target({FIELD, LOCAL_VARIABLE, METHOD, PARAMETER, TYPE_USE})
  public @interface Gs {} //gigasecond
  @Dimension(s = 1, scalar = MEGA) @Retention(CLASS) @Target({FIELD, LOCAL_VARIABLE, METHOD, PARAMETER, TYPE_USE})
  public @interface Ms {} //megasecond
  @Dimension(s = 1, scalar = KILO) @Retention(CLASS) @Target({FIELD, LOCAL_VARIABLE, METHOD, PARAMETER, TYPE_USE})
  public @interface ks {} //kilosecond
  @Dimension(s = 1, scalar = HECTO) @Retention(CLASS) @Target({FIELD, LOCAL_VARIABLE, METHOD, PARAMETER, TYPE_USE})
  public @interface hs {} //hectosecond
  @Dimension(s = 1, scalar = DECA) @Retention(CLASS) @Target({FIELD, LOCAL_VARIABLE, METHOD, PARAMETER, TYPE_USE})
  public @interface das {} //decasecond
  @Dimension(s = 1, scalar = DECI) @Retention(CLASS) @Target({FIELD, LOCAL_VARIABLE, METHOD, PARAMETER, TYPE_USE})
  public @interface ds {} //decisecond
  @Dimension(s = 1, scalar = CENTI) @Retention(CLASS) @Target({FIELD, LOCAL_VARIABLE, METHOD, PARAMETER, TYPE_USE})
  public @interface cs {} //centisecond
  @Dimension(s = 1, scalar = MILLI) @Retention(CLASS) @Target({FIELD, LOCAL_VARIABLE, METHOD, PARAMETER, TYPE_USE})
  public @interface ms {} //millisecond
  @Dimension(s = 1, scalar = MICRO) @Retention(CLASS) @Target({FIELD, LOCAL_VARIABLE, METHOD, PARAMETER, TYPE_USE})
  public @interface us {} //microsecond
  @Dimension(s = 1, scalar = NANO) @Retention(CLASS) @Target({FIELD, LOCAL_VARIABLE, METHOD, PARAMETER, TYPE_USE})
  public @interface ns {} //nanosecond
  @Dimension(s = 1, scalar = PICO) @Retention(CLASS) @Target({FIELD, LOCAL_VARIABLE, METHOD, PARAMETER, TYPE_USE})
  public @interface ps {} //picosecond

  //Length
  @Dimension(m = 1) @Retention(CLASS) @Target({FIELD, LOCAL_VARIABLE, METHOD, PARAMETER, TYPE_USE})
  public @interface m {} //meters

  @Dimension(m = 1, scalar = TERA) @Retention(CLASS) @Target({FIELD, LOCAL_VARIABLE, METHOD, PARAMETER, TYPE_USE})
  public @interface Tm {} //terameter
  @Dimension(m = 1, scalar = GIGA) @Retention(CLASS) @Target({FIELD, LOCAL_VARIABLE, METHOD, PARAMETER, TYPE_USE})
  public @interface Gm {} //gigameter
  @Dimension(m = 1, scalar = MEGA) @Retention(CLASS) @Target({FIELD, LOCAL_VARIABLE, METHOD, PARAMETER, TYPE_USE})
  public @interface Mm {} //megameter
  @Dimension(m = 1, scalar = KILO) @Retention(CLASS) @Target({FIELD, LOCAL_VARIABLE, METHOD, PARAMETER, TYPE_USE})
  public @interface km {} //kilometer
  @Dimension(m = 1, scalar = HECTO) @Retention(CLASS) @Target({FIELD, LOCAL_VARIABLE, METHOD, PARAMETER, TYPE_USE})
  public @interface hm {} //hectometer
  @Dimension(m = 1, scalar = DECA) @Retention(CLASS) @Target({FIELD, LOCAL_VARIABLE, METHOD, PARAMETER, TYPE_USE})
  public @interface dam {} //decameter
  @Dimension(m = 1, scalar = DECI) @Retention(CLASS) @Target({FIELD, LOCAL_VARIABLE, METHOD, PARAMETER, TYPE_USE})
  public @interface dm {} //decimeter
  @Dimension(m = 1, scalar = CENTI) @Retention(CLASS) @Target({FIELD, LOCAL_VARIABLE, METHOD, PARAMETER, TYPE_USE})
  public @interface cm {} //centimeter
  @Dimension(m = 1, scalar = MILLI) @Retention(CLASS) @Target({FIELD, LOCAL_VARIABLE, METHOD, PARAMETER, TYPE_USE})
  public @interface mm {} //millimeter
  @Dimension(m = 1, scalar = MICRO) @Retention(CLASS) @Target({FIELD, LOCAL_VARIABLE, METHOD, PARAMETER, TYPE_USE})
  public @interface um {} //micrometer
  @Dimension(m = 1, scalar = NANO) @Retention(CLASS) @Target({FIELD, LOCAL_VARIABLE, METHOD, PARAMETER, TYPE_USE})
  public @interface nm {} //nanometer
  @Dimension(m = 1, scalar = PICO) @Retention(CLASS) @Target({FIELD, LOCAL_VARIABLE, METHOD, PARAMETER, TYPE_USE})
  public @interface pm {} //picometer

  //Mass
  @Dimension(kg = 1) @Retention(CLASS) @Target({FIELD, LOCAL_VARIABLE, METHOD, PARAMETER, TYPE_USE})
  public @interface kg {} //kilogram
  @Dimension(kg = 1, scalar = 1 / KILO) @Retention(CLASS) @Target({FIELD, LOCAL_VARIABLE, METHOD, PARAMETER, TYPE_USE})
  public @interface g {} //gram

  @Dimension(kg = 1, scalar = TERA / KILO) @Retention(CLASS) @Target({FIELD, LOCAL_VARIABLE, METHOD, PARAMETER, TYPE_USE})
  public @interface Tg {} //teragram
  @Dimension(kg = 1, scalar = GIGA / KILO) @Retention(CLASS) @Target({FIELD, LOCAL_VARIABLE, METHOD, PARAMETER, TYPE_USE})
  public @interface Gg {} //gigagram
  @Dimension(kg = 1, scalar = MEGA / KILO) @Retention(CLASS) @Target({FIELD, LOCAL_VARIABLE, METHOD, PARAMETER, TYPE_USE})
  public @interface Mg {} //megagram
  @Dimension(kg = 1, scalar = HECTO / KILO) @Retention(CLASS) @Target({FIELD, LOCAL_VARIABLE, METHOD, PARAMETER, TYPE_USE})
  public @interface hg {} //hectogram
  @Dimension(kg = 1, scalar = DECA / KILO) @Retention(CLASS) @Target({FIELD, LOCAL_VARIABLE, METHOD, PARAMETER, TYPE_USE})
  public @interface dag {} //decagram
  @Dimension(kg = 1, scalar = DECI / KILO) @Retention(CLASS) @Target({FIELD, LOCAL_VARIABLE, METHOD, PARAMETER, TYPE_USE})
  public @interface dg {} //decigram
  @Dimension(kg = 1, scalar = CENTI / KILO) @Retention(CLASS) @Target({FIELD, LOCAL_VARIABLE, METHOD, PARAMETER, TYPE_USE})
  public @interface cg {} //centigram
  @Dimension(kg = 1, scalar = MILLI / KILO) @Retention(CLASS) @Target({FIELD, LOCAL_VARIABLE, METHOD, PARAMETER, TYPE_USE})
  public @interface mg {} //milligram
  @Dimension(kg = 1, scalar = MICRO / KILO) @Retention(CLASS) @Target({FIELD, LOCAL_VARIABLE, METHOD, PARAMETER, TYPE_USE})
  public @interface ug {} //microgram
  @Dimension(kg = 1, scalar = NANO / KILO) @Retention(CLASS) @Target({FIELD, LOCAL_VARIABLE, METHOD, PARAMETER, TYPE_USE})
  public @interface ng {} //nanogram
  @Dimension(kg = 1, scalar = PICO / KILO) @Retention(CLASS) @Target({FIELD, LOCAL_VARIABLE, METHOD, PARAMETER, TYPE_USE})
  public @interface pg {} //picogram

  //Electric Current
  @Dimension(A = 1) @Retention(CLASS) @Target({FIELD, LOCAL_VARIABLE, METHOD, PARAMETER, TYPE_USE})
  public @interface A {} //ampere

  @Dimension(A = 1, scalar = TERA) @Retention(CLASS) @Target({FIELD, LOCAL_VARIABLE, METHOD, PARAMETER, TYPE_USE})
  public @interface TA {} //teraampere
  @Dimension(A = 1, scalar = GIGA) @Retention(CLASS) @Target({FIELD, LOCAL_VARIABLE, METHOD, PARAMETER, TYPE_USE})
  public @interface GA {} //gigaampere
  @Dimension(A = 1, scalar = MEGA) @Retention(CLASS) @Target({FIELD, LOCAL_VARIABLE, METHOD, PARAMETER, TYPE_USE})
  public @interface MA {} //megaampere
  @Dimension(A = 1, scalar = KILO) @Retention(CLASS) @Target({FIELD, LOCAL_VARIABLE, METHOD, PARAMETER, TYPE_USE})
  public @interface kA {} //kiloampere
  @Dimension(A = 1, scalar = HECTO) @Retention(CLASS) @Target({FIELD, LOCAL_VARIABLE, METHOD, PARAMETER, TYPE_USE})
  public @interface hA {} //hectoampere
  @Dimension(A = 1, scalar = DECA) @Retention(CLASS) @Target({FIELD, LOCAL_VARIABLE, METHOD, PARAMETER, TYPE_USE})
  public @interface daA {} //decaampere
  @Dimension(A = 1, scalar = DECI) @Retention(CLASS) @Target({FIELD, LOCAL_VARIABLE, METHOD, PARAMETER, TYPE_USE})
  public @interface dA {} //deciampere
  @Dimension(A = 1, scalar = CENTI) @Retention(CLASS) @Target({FIELD, LOCAL_VARIABLE, METHOD, PARAMETER, TYPE_USE})
  public @interface cA {} //centiampere
  @Dimension(A = 1, scalar = MILLI) @Retention(CLASS) @Target({FIELD, LOCAL_VARIABLE, METHOD, PARAMETER, TYPE_USE})
  public @interface mA {} //milliampere
  @Dimension(A = 1, scalar = MICRO) @Retention(CLASS) @Target({FIELD, LOCAL_VARIABLE, METHOD, PARAMETER, TYPE_USE})
  public @interface uA {} //microampere
  @Dimension(A = 1, scalar = NANO) @Retention(CLASS) @Target({FIELD, LOCAL_VARIABLE, METHOD, PARAMETER, TYPE_USE})
  public @interface nA {} //nanoampere
  @Dimension(A = 1, scalar = PICO) @Retention(CLASS) @Target({FIELD, LOCAL_VARIABLE, METHOD, PARAMETER, TYPE_USE})
  public @interface pA {} //picoampere

  //Thermodynamic Temperature
  @Dimension(K = 1) @Retention(CLASS) @Target({FIELD, LOCAL_VARIABLE, METHOD, PARAMETER, TYPE_USE})
  public @interface K {} //kelvin

  @Dimension(K = 1, scalar = TERA) @Retention(CLASS) @Target({FIELD, LOCAL_VARIABLE, METHOD, PARAMETER, TYPE_USE})
  public @interface TK {} //terakelvin
  @Dimension(K = 1, scalar = GIGA) @Retention(CLASS) @Target({FIELD, LOCAL_VARIABLE, METHOD, PARAMETER, TYPE_USE})
  public @interface GK {} //gigakelvin
  @Dimension(K = 1, scalar = MEGA) @Retention(CLASS) @Target({FIELD, LOCAL_VARIABLE, METHOD, PARAMETER, TYPE_USE})
  public @interface MK {} //megakelvin
  @Dimension(K = 1, scalar = KILO) @Retention(CLASS) @Target({FIELD, LOCAL_VARIABLE, METHOD, PARAMETER, TYPE_USE})
  public @interface kK {} //kilokelvin
  @Dimension(K = 1, scalar = HECTO) @Retention(CLASS) @Target({FIELD, LOCAL_VARIABLE, METHOD, PARAMETER, TYPE_USE})
  public @interface hK {} //hectokelvin
  @Dimension(K = 1, scalar = DECA) @Retention(CLASS) @Target({FIELD, LOCAL_VARIABLE, METHOD, PARAMETER, TYPE_USE})
  public @interface daK {} //decakelvin
  @Dimension(K = 1, scalar = DECI) @Retention(CLASS) @Target({FIELD, LOCAL_VARIABLE, METHOD, PARAMETER, TYPE_USE})
  public @interface dK {} //decikelvin
  @Dimension(K = 1, scalar = CENTI) @Retention(CLASS) @Target({FIELD, LOCAL_VARIABLE, METHOD, PARAMETER, TYPE_USE})
  public @interface cK {} //centikelvin
  @Dimension(K = 1, scalar = MILLI) @Retention(CLASS) @Target({FIELD, LOCAL_VARIABLE, METHOD, PARAMETER, TYPE_USE})
  public @interface mK {} //millikelvin
  @Dimension(K = 1, scalar = MICRO) @Retention(CLASS) @Target({FIELD, LOCAL_VARIABLE, METHOD, PARAMETER, TYPE_USE})
  public @interface uK {} //microkelvin
  @Dimension(K = 1, scalar = NANO) @Retention(CLASS) @Target({FIELD, LOCAL_VARIABLE, METHOD, PARAMETER, TYPE_USE})
  public @interface nK {} //nanokelvin
  @Dimension(K = 1, scalar = PICO) @Retention(CLASS) @Target({FIELD, LOCAL_VARIABLE, METHOD, PARAMETER, TYPE_USE})
  public @interface pK {} //picokelvin

  //Amount of Substance
  @Dimension(mol = 1) @Retention(CLASS) @Target({FIELD, LOCAL_VARIABLE, METHOD, PARAMETER, TYPE_USE})
  public @interface mol {} //mol

  @Dimension(mol = 1, scalar = TERA) @Retention(CLASS) @Target({FIELD, LOCAL_VARIABLE, METHOD, PARAMETER, TYPE_USE})
  public @interface Tmol {} //teramole
  @Dimension(mol = 1, scalar = GIGA) @Retention(CLASS) @Target({FIELD, LOCAL_VARIABLE, METHOD, PARAMETER, TYPE_USE})
  public @interface Gmol {} //gigamole
  @Dimension(mol = 1, scalar = MEGA) @Retention(CLASS) @Target({FIELD, LOCAL_VARIABLE, METHOD, PARAMETER, TYPE_USE})
  public @interface Mmol {} //megamole
  @Dimension(mol = 1, scalar = KILO) @Retention(CLASS) @Target({FIELD, LOCAL_VARIABLE, METHOD, PARAMETER, TYPE_USE})
  public @interface kmol {} //kilomole
  @Dimension(mol = 1, scalar = HECTO) @Retention(CLASS) @Target({FIELD, LOCAL_VARIABLE, METHOD, PARAMETER, TYPE_USE})
  public @interface hmol {} //hectomole
  @Dimension(mol = 1, scalar = DECA) @Retention(CLASS) @Target({FIELD, LOCAL_VARIABLE, METHOD, PARAMETER, TYPE_USE})
  public @interface damol {} //decamole
  @Dimension(mol = 1, scalar = DECI) @Retention(CLASS) @Target({FIELD, LOCAL_VARIABLE, METHOD, PARAMETER, TYPE_USE})
  public @interface dmol {} //decimole
  @Dimension(mol = 1, scalar = CENTI) @Retention(CLASS) @Target({FIELD, LOCAL_VARIABLE, METHOD, PARAMETER, TYPE_USE})
  public @interface cmol {} //centimole
  @Dimension(mol = 1, scalar = MILLI) @Retention(CLASS) @Target({FIELD, LOCAL_VARIABLE, METHOD, PARAMETER, TYPE_USE})
  public @interface mmol {} //millimole
  @Dimension(mol = 1, scalar = MICRO) @Retention(CLASS) @Target({FIELD, LOCAL_VARIABLE, METHOD, PARAMETER, TYPE_USE})
  public @interface umol {} //micromole
  @Dimension(mol = 1, scalar = NANO) @Retention(CLASS) @Target({FIELD, LOCAL_VARIABLE, METHOD, PARAMETER, TYPE_USE})
  public @interface nmol {} //nanomole
  @Dimension(mol = 1, scalar = PICO) @Retention(CLASS) @Target({FIELD, LOCAL_VARIABLE, METHOD, PARAMETER, TYPE_USE})
  public @interface pmol {} //picomole

  //Luminous Intensity
  @Dimension(cd = 1) @Retention(CLASS) @Target({FIELD, LOCAL_VARIABLE, METHOD, PARAMETER, TYPE_USE})
  public @interface cd {} //candela

  @Dimension(cd = 1, scalar = TERA) @Retention(CLASS) @Target({FIELD, LOCAL_VARIABLE, METHOD, PARAMETER, TYPE_USE})
  public @interface Tcd {} //teracandela
  @Dimension(cd = 1, scalar = GIGA) @Retention(CLASS) @Target({FIELD, LOCAL_VARIABLE, METHOD, PARAMETER, TYPE_USE})
  public @interface Gcd {} //gigacandela
  @Dimension(cd = 1, scalar = MEGA) @Retention(CLASS) @Target({FIELD, LOCAL_VARIABLE, METHOD, PARAMETER, TYPE_USE})
  public @interface Mcd {} //megacandela
  @Dimension(cd = 1, scalar = KILO) @Retention(CLASS) @Target({FIELD, LOCAL_VARIABLE, METHOD, PARAMETER, TYPE_USE})
  public @interface kcd {} //kilocandela
  @Dimension(cd = 1, scalar = HECTO) @Retention(CLASS) @Target({FIELD, LOCAL_VARIABLE, METHOD, PARAMETER, TYPE_USE})
  public @interface hcd {} //hectocandela
  @Dimension(cd = 1, scalar = DECA) @Retention(CLASS) @Target({FIELD, LOCAL_VARIABLE, METHOD, PARAMETER, TYPE_USE})
  public @interface dacd {} //decacandela
  @Dimension(cd = 1, scalar = DECI) @Retention(CLASS) @Target({FIELD, LOCAL_VARIABLE, METHOD, PARAMETER, TYPE_USE})
  public @interface dcd {} //decicandela
  @Dimension(cd = 1, scalar = CENTI) @Retention(CLASS) @Target({FIELD, LOCAL_VARIABLE, METHOD, PARAMETER, TYPE_USE})
  public @interface ccd {} //centicandela
  @Dimension(cd = 1, scalar = MILLI) @Retention(CLASS) @Target({FIELD, LOCAL_VARIABLE, METHOD, PARAMETER, TYPE_USE})
  public @interface mcd {} //millicandela
  @Dimension(cd = 1, scalar = MICRO) @Retention(CLASS) @Target({FIELD, LOCAL_VARIABLE, METHOD, PARAMETER, TYPE_USE})
  public @interface ucd {} //microcandela
  @Dimension(cd = 1, scalar = NANO) @Retention(CLASS) @Target({FIELD, LOCAL_VARIABLE, METHOD, PARAMETER, TYPE_USE})
  public @interface ncd {} //nanocandela
  @Dimension(cd = 1, scalar = PICO) @Retention(CLASS) @Target({FIELD, LOCAL_VARIABLE, METHOD, PARAMETER, TYPE_USE})
  public @interface pcd {} //picocandela

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

  //Acceleration
  @Dimension(m = 1, s = -2) @Retention(CLASS) @Target({FIELD, LOCAL_VARIABLE, METHOD, PARAMETER, TYPE_USE})
  public @interface m_s2 {} // meters per second per second

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
  public @interface Hz {} //Watts

  // Pressure
  @Dimension(kg =  1, m = -1, s = -2) @Retention(CLASS) @Target({FIELD, LOCAL_VARIABLE, METHOD, PARAMETER, TYPE_USE})
  public @interface Pa {} //Pascal

  // Electric Charge
  @Dimension(s = 1, A = 1) @Retention(CLASS) @Target({FIELD, LOCAL_VARIABLE, METHOD, PARAMETER, TYPE_USE})
  public @interface C {} //Coulomb

  // Electrical Potential Difference
  @Dimension(kg = 1, m = 2, s = -3, A = -2) @Retention(CLASS) @Target({FIELD, LOCAL_VARIABLE, METHOD, PARAMETER, TYPE_USE})
  public @interface V {} //Volt

  // Electrical Resistance
  @Dimension(kg = 1, m = 2, s = -3, A = -2) @Retention(CLASS) @Target({FIELD, LOCAL_VARIABLE, METHOD, PARAMETER, TYPE_USE})
  public @interface Ohm {} //Ohm

  // Capacitance
  @Dimension(kg = -1, m = -2, s = 4, A = 2) @Retention(CLASS) @Target({FIELD, LOCAL_VARIABLE, METHOD, PARAMETER, TYPE_USE})
  public @interface F {} //Farad

  // Inducatance
  @Dimension(kg = 1, m = 2, s = -2, A = 2) @Retention(CLASS) @Target({FIELD, LOCAL_VARIABLE, METHOD, PARAMETER, TYPE_USE})
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
  @Dimension(scalar = Math.PI / 180) @Retention(CLASS) @Target({FIELD, LOCAL_VARIABLE, METHOD, PARAMETER, TYPE_USE})
  public @interface deg {} //degree
  @Dimension(scalar = 2 * Math.PI) @Retention(CLASS) @Target({FIELD, LOCAL_VARIABLE, METHOD, PARAMETER, TYPE_USE})
  public @interface rot {} //rotations
  @Dimension() @Retention(CLASS) @Target({FIELD, LOCAL_VARIABLE, METHOD, PARAMETER, TYPE_USE})
  public @interface sr {} //steradian
}
