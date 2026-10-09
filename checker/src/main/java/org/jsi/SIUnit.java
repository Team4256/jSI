package org.jsi;

public class SIUnit {
  private final double time;
  private final double length;
  private final double mass;
  private final double electricCurrent;
  private final double thermodynamicTemperature;
  private final double amountOfSubstance;
  private final double luminousIntensity;
  private final double scalar;

  /**
   * The annotation this unit was read from — {@code ms}, {@code km}, {@code N} — or
   * null for a unit computed by arithmetic. Mostly for display: two units with the same
   * exponents and scalar are the same unit whatever they are called. Its one effect on
   * checking is {@link #isBareNumber}: a declared unit is never a bare number.
   */
  private final String name;

  public static final SIUnit DIMENSIONLESS = new SIUnit(0, 0, 0, 0, 0, 0, 0, 1);

  public SIUnit(
    double time, 
    double length, 
    double mass, 
    double electricCurrent, 
    double thermodynamicTemperature, 
    double amountOfSubstance, 
    double luminoisIntensity,
    double scalar
  ) {
      this.time = time;
      this.length = length;
      this.mass = mass;
      this.electricCurrent = electricCurrent;
      this.thermodynamicTemperature = thermodynamicTemperature;
      this.amountOfSubstance = amountOfSubstance;
      this.luminousIntensity = luminoisIntensity;
      this.scalar = scalar;
      this.name = null;
    }

  private SIUnit(SIUnit unit, String name) {
    this.time = unit.time;
    this.length = unit.length;
    this.mass = unit.mass;
    this.electricCurrent = unit.electricCurrent;
    this.thermodynamicTemperature = unit.thermodynamicTemperature;
    this.amountOfSubstance = unit.amountOfSubstance;
    this.luminousIntensity = unit.luminousIntensity;
    this.scalar = unit.scalar;
    this.name = name;
  }

  /** This unit, displayed under the name of the annotation that declared it. */
  public SIUnit named(String name) {
    return new SIUnit(this, name);
  }

  public boolean sameDimension(SIUnit unit) {
    return 
      this.time == unit.time && 
      this.length == unit.length &&
      this.mass == unit.mass &&
      this.electricCurrent == unit.electricCurrent &&
      this.thermodynamicTemperature == unit.thermodynamicTemperature &&
      this.amountOfSubstance == unit.amountOfSubstance &&
      this.luminousIntensity == unit.luminousIntensity;
  }

  /** True when every base-unit exponent is zero, whatever the scalar. */
  public boolean isDimensionless() {
    return sameDimension(DIMENSIONLESS);
  }

  public boolean sameScalar(SIUnit unit) {
    return this.scalar == unit.scalar;
  }

  /**
   * A plain number: no dimension and no scale. Multiplying by one changes nothing, and
   * a literal such as {@code 10} is one. Distinct from {@link #isDimensionless}, which
   * also holds for a scaled dimensionless unit such as degrees, which is not a plain
   * number: 90 degrees is not 90.
   */
  public boolean isPlainNumber() {
    return isDimensionless() && scalar == 1;
  }

  /**
   * A plain number that no annotation declared: a literal, or a ratio computed by
   * arithmetic such as {@code arc / radius}. Only these may become any unit on
   * assignment. A declared {@code @rad} value is a plain number too — radians are
   * dimensionless — but it is still radians, and must not slip into degrees or metres.
   */
  public boolean isBareNumber() {
    return isPlainNumber() && name == null;
  }

  /**
   * The product's unit. Scaling by a plain number returns the other unit itself
   * rather than an equal copy, so that {@code 2 * t} keeps {@code t}'s name in
   * diagnostics instead of degrading to a formula.
   */
  public SIUnit multiply(SIUnit unit) {
    if (unit.isPlainNumber()) return this;
    if (isPlainNumber()) return unit;
    return new SIUnit(
      time + unit.time, 
      length + unit.length, 
      mass + unit.mass, 
      electricCurrent + unit.electricCurrent, 
      thermodynamicTemperature + unit.thermodynamicTemperature, 
      amountOfSubstance + unit.amountOfSubstance, 
      luminousIntensity + unit.luminousIntensity, 
      scalar * unit.scalar
    );
  }

  /** The quotient's unit. As with {@link #multiply}, dividing by a plain number keeps the name. */
  public SIUnit divide(SIUnit unit) {
    if (unit.isPlainNumber()) return this;
    return new SIUnit(
      time - unit.time, 
      length - unit.length, 
      mass - unit.mass, 
      electricCurrent - unit.electricCurrent, 
      thermodynamicTemperature - unit.thermodynamicTemperature, 
      amountOfSubstance - unit.amountOfSubstance, 
      luminousIntensity - unit.luminousIntensity, 
      scalar / unit.scalar
    );
  }

  /**
   * The user-facing name of a unit; every diagnostic the checker emits interpolates
   * this. A unit read from an annotation is shown as written — {@code ms}, not
   * {@code 0.001·s} — so a message names the units the way the code does.
   *
   * <p>A unit computed by arithmetic has no annotation, and is rendered the way it
   * would be written by hand — {@code kg·m·s⁻²} — in the conventional SI order,
   * omitting base units with a zero exponent.
   */
  @Override
  public String toString() {
    if (name != null) return name;

    StringBuilder sb = new StringBuilder();
    append(sb, mass,                     "kg");
    append(sb, length,                   "m");
    append(sb, time,                     "s");
    append(sb, electricCurrent,          "A");
    append(sb, thermodynamicTemperature, "K");
    append(sb, amountOfSubstance,        "mol");
    append(sb, luminousIntensity,        "cd");

    if (sb.length() == 0) return scalar == 1 ? "dimensionless" : scalarOf(scalar);
    if (scalar != 1) sb.insert(0, scalarOf(scalar) + "·");
    return sb.toString();
  }

  /**
   * A scalar as a reader would write it: plainly when short ({@code 1000},
   * {@code 0.001}), and as a power of ten beyond that ({@code 10⁻⁶}), where
   * {@code 1.0E-6} would otherwise appear. The power-of-ten test allows for the
   * rounding a scalar picks up from arithmetic such as {@code MILLI / KILO}.
   */
  private static String scalarOf(double scalar) {
    if (scalar > 0) {
      long n = Math.round(Math.log10(scalar));
      if (Math.abs(n) > 3 && Math.abs(scalar / Math.pow(10, n) - 1) < 1e-9) {
        return "10" + exponentOf(n);
      }
    }
    return plain(scalar);
  }

  private static void append(StringBuilder sb, double exponent, String symbol) {
    if (exponent == 0) return;
    if (sb.length() > 0) sb.append('·');
    sb.append(symbol).append(exponentOf(exponent));
  }

  /**
   * An exponent as a suffix: empty for 1, superscript digits for whole numbers, and a
   * caret form for fractional ones, since there are no superscript decimal points.
   */
  private static String exponentOf(double exponent) {
    if (exponent == 1) return "";
    if (exponent != Math.rint(exponent) || Double.isInfinite(exponent)) {
      return "^" + plain(exponent);
    }
    StringBuilder out = new StringBuilder(exponent < 0 ? "⁻" : "");
    for (char c : Long.toString(Math.abs((long) exponent)).toCharArray()) {
      out.append("⁰¹²³⁴⁵⁶⁷⁸⁹".charAt(c - '0'));
    }
    return out.toString();
  }

  /**
   * Drops the trailing {@code .0} that {@link Double#toString} adds to whole numbers.
   * Only below 10¹⁵, where a double still holds every whole number exactly; past
   * that the cast to long would overflow, printing 10³⁰ as {@code 9223372036854775807}.
   */
  private static String plain(double d) {
    return d == Math.rint(d) && Math.abs(d) < 1e15 ? Long.toString((long) d)
                                                   : Double.toString(d);
  }
}
