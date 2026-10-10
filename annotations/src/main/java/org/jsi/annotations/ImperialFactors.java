package org.jsi.annotations;

/**
 * The imperial units, each as its size in the matching SI base unit: {@code INCH}
 * is the number of meters in an inch. Like {@link SIPrefixs}, this is the single
 * source of each factor: a unit's {@code @Dimension(scalar = ...)} and the
 * conversion to or from it name the same constant here, so the two cannot disagree.
 *
 * <p>The values are the exact international definitions (1959), written as
 * literals rather than derived from one another, since {@code 12 * INCH} rounds in
 * floating point and would not equal {@code FOOT}.
 *
 * <p>These are compile-time constants: javac copies their values into the
 * annotations and code that use them, so nothing needs this class at runtime.
 */
public final class ImperialFactors {
  private ImperialFactors() {}

  // Length, in meters
  public static final double INCH = 0.0254;
  public static final double FOOT = 0.3048;
  public static final double YARD = 0.9144;
}
