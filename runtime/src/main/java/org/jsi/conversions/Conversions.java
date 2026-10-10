package org.jsi.conversions;

import static org.jsi.annotations.ImperialFactors.*;
import static org.jsi.annotations.SIPrefixs.*;

import org.jsi.annotations.Units.*;

/**
 * The explicit conversions between scales of one dimension. The checker reports
 * "explicit conversion required" wherever two scales meet, and a call to one of
 * these is the answer: the parameter's unit is checked on the way in and the
 * return unit on the way out, and the call compiles to a single multiply or
 * divide by a constant.
 *
 * <p>Every prefixed unit converts to and from its base unit. Each factor is the
 * same {@link org.jsi.annotations.SIPrefixs} expression the unit's {@code scalar}
 * names, so the two cannot disagree. Mass is the exception to the naming: its
 * base unit is the kilogram, so the prefixes attach to the gram and each factor
 * is the prefix divided by {@code KILO}.
 */
public final class Conversions {
    private Conversions() {}

    // Time
    public static @s double millisecondsToSeconds(@ms double value) { return value * MILLI; }
    public static @ms double secondsToMilliseconds(@s double value) { return value / MILLI; }
    public static @s double microsecondsToSeconds(@us double value) { return value * MICRO; }
    public static @us double secondsToMicroseconds(@s double value) { return value / MICRO; }
    public static @s double nanosecondsToSeconds(@ns double value) { return value * NANO; }
    public static @ns double secondsToNanoseconds(@s double value) { return value / NANO; }

    // Length
    public static @m double kilometersToMeters(@km double value) { return value * KILO; }
    public static @km double metersToKilometers(@m double value) { return value / KILO; }
    public static @m double centimetersToMeters(@cm double value) { return value * CENTI; }
    public static @cm double metersToCentimeters(@m double value) { return value / CENTI; }
    public static @m double millimetersToMeters(@mm double value) { return value * MILLI; }
    public static @mm double metersToMillimeters(@m double value) { return value / MILLI; }
    public static @m double micrometersToMeters(@um double value) { return value * MICRO; }
    public static @um double metersToMicrometers(@m double value) { return value / MICRO; }
    public static @m double nanometersToMeters(@nm double value) { return value * NANO; }
    public static @nm double metersToNanometers(@m double value) { return value / NANO; }

    // Mass
    public static @kg double gramsToKilograms(@g double value) { return value * (1 / KILO); }
    public static @g double kilogramsToGrams(@kg double value) { return value / (1 / KILO); }
    public static @kg double milligramsToKilograms(@mg double value) { return value * (MILLI / KILO); }
    public static @mg double kilogramsToMilligrams(@kg double value) { return value / (MILLI / KILO); }
    public static @kg double microgramsToKilograms(@ug double value) { return value * (MICRO / KILO); }
    public static @ug double kilogramsToMicrograms(@kg double value) { return value / (MICRO / KILO); }

    // Electric Current
    public static @A double milliamperesToAmperes(@mA double value) { return value * MILLI; }
    public static @mA double amperesToMilliamperes(@A double value) { return value / MILLI; }
    public static @A double microamperesToAmperes(@uA double value) { return value * MICRO; }
    public static @uA double amperesToMicroamperes(@A double value) { return value / MICRO; }

    // Amount of Substance
    public static @mol double millimolesToMoles(@mmol double value) { return value * MILLI; }
    public static @mmol double molesToMillimoles(@mol double value) { return value / MILLI; }
    public static @mol double micromolesToMoles(@umol double value) { return value * MICRO; }
    public static @umol double molesToMicromoles(@mol double value) { return value / MICRO; }

    // Luminous Intensity
    public static @cd double millicandelasToCandelas(@mcd double value) { return value * MILLI; }
    public static @mcd double candelasToMillicandelas(@cd double value) { return value / MILLI; }

    // Plane Angle. Degrees and rotations convert to each other directly by 360, an
    // exact whole number, rather than through radians and two inexact multiples of pi.
    public static @rad double degreesToRadians(@deg double value) { return value * DEGREE; }
    public static @deg double radiansToDegrees(@rad double value) { return value / DEGREE; }
    public static @rad double rotationsToRadians(@rot double value) { return value * ROTATION; }
    public static @rot double radiansToRotations(@rad double value) { return value / ROTATION; }
    public static @rot double degreesToRotations(@deg double value) { return value / 360; }
    public static @deg double rotationsToDegrees(@rot double value) { return value * 360; }

    // Angular Velocity
    public static @rad_s double degreesPerSecondToRadiansPerSecond(@deg_s double value) { return value * DEGREE; }
    public static @deg_s double radiansPerSecondToDegreesPerSecond(@rad_s double value) { return value / DEGREE; }
    public static @rad_s double rotationsPerSecondToRadiansPerSecond(@rot_s double value) { return value * ROTATION; }
    public static @rot_s double radiansPerSecondToRotationsPerSecond(@rad_s double value) { return value / ROTATION; }
    public static @rot_s double degreesPerSecondToRotationsPerSecond(@deg_s double value) { return value / 360; }
    public static @deg_s double rotationsPerSecondToDegreesPerSecond(@rot_s double value) { return value * 360; }

    // Angular Acceleration
    public static @rad_s2 double degreesPerSecondSquaredToRadiansPerSecondSquared(@deg_s2 double value) { return value * DEGREE; }
    public static @deg_s2 double radiansPerSecondSquaredToDegreesPerSecondSquared(@rad_s2 double value) { return value / DEGREE; }
    public static @rad_s2 double rotationsPerSecondSquaredToRadiansPerSecondSquared(@rot_s2 double value) { return value * ROTATION; }
    public static @rot_s2 double radiansPerSecondSquaredToRotationsPerSecondSquared(@rad_s2 double value) { return value / ROTATION; }
    public static @rot_s2 double degreesPerSecondSquaredToRotationsPerSecondSquared(@deg_s2 double value) { return value / 360; }
    public static @deg_s2 double rotationsPerSecondSquaredToDegreesPerSecondSquared(@rot_s2 double value) { return value * 360; }

    // Imperial Length. Each factor is the unit's size in meters, from ImperialFactors.
    public static @m double inchesToMeters(@in double value) { return value * INCH; }
    public static @in double metersToInches(@m double value) { return value / INCH; }
    public static @m double feetToMeters(@ft double value) { return value * FOOT; }
    public static @ft double metersToFeet(@m double value) { return value / FOOT; }
    public static @m double yardsToMeters(@yd double value) { return value * YARD; }
    public static @yd double metersToYards(@m double value) { return value / YARD; }
}
