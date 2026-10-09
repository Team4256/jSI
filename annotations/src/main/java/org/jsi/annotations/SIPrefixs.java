package org.jsi.annotations;

/**
 * The SI prefixes, as the single source of every scale factor. A unit's
 * {@code @Dimension(scalar = ...)} and the conversion to or from it must name the
 * same constant here, so the two can never disagree.
 *
 * <p>These are compile-time constants: javac copies their values into the
 * annotations and code that use them, so nothing needs this class at runtime.
 */
public final class SIPrefixs {
  private SIPrefixs() {}

  public static final double QUETTA = 1e30;
  public static final double RONNA  = 1e27;
  public static final double YOTTA  = 1e24;
  public static final double ZETTA  = 1e21;
  public static final double EXA    = 1e18;
  public static final double PETA   = 1e15;
  public static final double TERA   = 1e12;
  public static final double GIGA   = 1e9;
  public static final double MEGA   = 1e6;
  public static final double KILO   = 1e3;
  public static final double HECTO  = 1e2;
  public static final double DECA   = 1e1;

  public static final double DECI   = 1e-1;
  public static final double CENTI  = 1e-2;
  public static final double MILLI  = 1e-3;
  public static final double MICRO  = 1e-6;
  public static final double NANO   = 1e-9;
  public static final double PICO   = 1e-12;
  public static final double FEMTO  = 1e-15;
  public static final double ATTO   = 1e-18;
  public static final double ZEPTO  = 1e-21;
  public static final double YOCTO  = 1e-24;
  public static final double RONTO  = 1e-27;
  public static final double QUECTO = 1e-30;
}
