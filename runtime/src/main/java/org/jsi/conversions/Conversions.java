package org.jsi.conversions;

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
    public static @s double terasecondsToSeconds(@Ts double value) { return value * TERA; }
    public static @Ts double secondsToTeraseconds(@s double value) { return value / TERA; }
    public static @s double gigasecondsToSeconds(@Gs double value) { return value * GIGA; }
    public static @Gs double secondsToGigaseconds(@s double value) { return value / GIGA; }
    public static @s double megasecondsToSeconds(@Ms double value) { return value * MEGA; }
    public static @Ms double secondsToMegaseconds(@s double value) { return value / MEGA; }
    public static @s double kilosecondsToSeconds(@ks double value) { return value * KILO; }
    public static @ks double secondsToKiloseconds(@s double value) { return value / KILO; }
    public static @s double hectosecondsToSeconds(@hs double value) { return value * HECTO; }
    public static @hs double secondsToHectoseconds(@s double value) { return value / HECTO; }
    public static @s double decasecondsToSeconds(@das double value) { return value * DECA; }
    public static @das double secondsToDecaseconds(@s double value) { return value / DECA; }
    public static @s double decisecondsToSeconds(@ds double value) { return value * DECI; }
    public static @ds double secondsToDeciseconds(@s double value) { return value / DECI; }
    public static @s double centisecondsToSeconds(@cs double value) { return value * CENTI; }
    public static @cs double secondsToCentiseconds(@s double value) { return value / CENTI; }
    public static @s double millisecondsToSeconds(@ms double value) { return value * MILLI; }
    public static @ms double secondsToMilliseconds(@s double value) { return value / MILLI; }
    public static @s double microsecondsToSeconds(@us double value) { return value * MICRO; }
    public static @us double secondsToMicroseconds(@s double value) { return value / MICRO; }
    public static @s double nanosecondsToSeconds(@ns double value) { return value * NANO; }
    public static @ns double secondsToNanoseconds(@s double value) { return value / NANO; }
    public static @s double picosecondsToSeconds(@ps double value) { return value * PICO; }
    public static @ps double secondsToPicoseconds(@s double value) { return value / PICO; }

    // Length
    public static @m double terametersToMeters(@Tm double value) { return value * TERA; }
    public static @Tm double metersToTerameters(@m double value) { return value / TERA; }
    public static @m double gigametersToMeters(@Gm double value) { return value * GIGA; }
    public static @Gm double metersToGigameters(@m double value) { return value / GIGA; }
    public static @m double megametersToMeters(@Mm double value) { return value * MEGA; }
    public static @Mm double metersToMegameters(@m double value) { return value / MEGA; }
    public static @m double kilometersToMeters(@km double value) { return value * KILO; }
    public static @km double metersToKilometers(@m double value) { return value / KILO; }
    public static @m double hectometersToMeters(@hm double value) { return value * HECTO; }
    public static @hm double metersToHectometers(@m double value) { return value / HECTO; }
    public static @m double decametersToMeters(@dam double value) { return value * DECA; }
    public static @dam double metersToDecameters(@m double value) { return value / DECA; }
    public static @m double decimetersToMeters(@dm double value) { return value * DECI; }
    public static @dm double metersToDecimeters(@m double value) { return value / DECI; }
    public static @m double centimetersToMeters(@cm double value) { return value * CENTI; }
    public static @cm double metersToCentimeters(@m double value) { return value / CENTI; }
    public static @m double millimetersToMeters(@mm double value) { return value * MILLI; }
    public static @mm double metersToMillimeters(@m double value) { return value / MILLI; }
    public static @m double micrometersToMeters(@um double value) { return value * MICRO; }
    public static @um double metersToMicrometers(@m double value) { return value / MICRO; }
    public static @m double nanometersToMeters(@nm double value) { return value * NANO; }
    public static @nm double metersToNanometers(@m double value) { return value / NANO; }
    public static @m double picometersToMeters(@pm double value) { return value * PICO; }
    public static @pm double metersToPicometers(@m double value) { return value / PICO; }

    // Mass
    public static @kg double gramsToKilograms(@g double value) { return value * (1 / KILO); }
    public static @g double kilogramsToGrams(@kg double value) { return value / (1 / KILO); }
    public static @kg double teragramsToKilograms(@Tg double value) { return value * (TERA / KILO); }
    public static @Tg double kilogramsToTeragrams(@kg double value) { return value / (TERA / KILO); }
    public static @kg double gigagramsToKilograms(@Gg double value) { return value * (GIGA / KILO); }
    public static @Gg double kilogramsToGigagrams(@kg double value) { return value / (GIGA / KILO); }
    public static @kg double megagramsToKilograms(@Mg double value) { return value * (MEGA / KILO); }
    public static @Mg double kilogramsToMegagrams(@kg double value) { return value / (MEGA / KILO); }
    public static @kg double hectogramsToKilograms(@hg double value) { return value * (HECTO / KILO); }
    public static @hg double kilogramsToHectograms(@kg double value) { return value / (HECTO / KILO); }
    public static @kg double decagramsToKilograms(@dag double value) { return value * (DECA / KILO); }
    public static @dag double kilogramsToDecagrams(@kg double value) { return value / (DECA / KILO); }
    public static @kg double decigramsToKilograms(@dg double value) { return value * (DECI / KILO); }
    public static @dg double kilogramsToDecigrams(@kg double value) { return value / (DECI / KILO); }
    public static @kg double centigramsToKilograms(@cg double value) { return value * (CENTI / KILO); }
    public static @cg double kilogramsToCentigrams(@kg double value) { return value / (CENTI / KILO); }
    public static @kg double milligramsToKilograms(@mg double value) { return value * (MILLI / KILO); }
    public static @mg double kilogramsToMilligrams(@kg double value) { return value / (MILLI / KILO); }
    public static @kg double microgramsToKilograms(@ug double value) { return value * (MICRO / KILO); }
    public static @ug double kilogramsToMicrograms(@kg double value) { return value / (MICRO / KILO); }
    public static @kg double nanogramsToKilograms(@ng double value) { return value * (NANO / KILO); }
    public static @ng double kilogramsToNanograms(@kg double value) { return value / (NANO / KILO); }
    public static @kg double picogramsToKilograms(@pg double value) { return value * (PICO / KILO); }
    public static @pg double kilogramsToPicograms(@kg double value) { return value / (PICO / KILO); }

    // Electric Current
    public static @A double teraamperesToAmperes(@TA double value) { return value * TERA; }
    public static @TA double amperesToTeraamperes(@A double value) { return value / TERA; }
    public static @A double gigaamperesToAmperes(@GA double value) { return value * GIGA; }
    public static @GA double amperesToGigaamperes(@A double value) { return value / GIGA; }
    public static @A double megaamperesToAmperes(@MA double value) { return value * MEGA; }
    public static @MA double amperesToMegaamperes(@A double value) { return value / MEGA; }
    public static @A double kiloamperesToAmperes(@kA double value) { return value * KILO; }
    public static @kA double amperesToKiloamperes(@A double value) { return value / KILO; }
    public static @A double hectoamperesToAmperes(@hA double value) { return value * HECTO; }
    public static @hA double amperesToHectoamperes(@A double value) { return value / HECTO; }
    public static @A double decaamperesToAmperes(@daA double value) { return value * DECA; }
    public static @daA double amperesToDecaamperes(@A double value) { return value / DECA; }
    public static @A double deciamperesToAmperes(@dA double value) { return value * DECI; }
    public static @dA double amperesToDeciamperes(@A double value) { return value / DECI; }
    public static @A double centiamperesToAmperes(@cA double value) { return value * CENTI; }
    public static @cA double amperesToCentiamperes(@A double value) { return value / CENTI; }
    public static @A double milliamperesToAmperes(@mA double value) { return value * MILLI; }
    public static @mA double amperesToMilliamperes(@A double value) { return value / MILLI; }
    public static @A double microamperesToAmperes(@uA double value) { return value * MICRO; }
    public static @uA double amperesToMicroamperes(@A double value) { return value / MICRO; }
    public static @A double nanoamperesToAmperes(@nA double value) { return value * NANO; }
    public static @nA double amperesToNanoamperes(@A double value) { return value / NANO; }
    public static @A double picoamperesToAmperes(@pA double value) { return value * PICO; }
    public static @pA double amperesToPicoamperes(@A double value) { return value / PICO; }

    // Thermodynamic Temperature
    public static @K double terakelvinsToKelvins(@TK double value) { return value * TERA; }
    public static @TK double kelvinsToTerakelvins(@K double value) { return value / TERA; }
    public static @K double gigakelvinsToKelvins(@GK double value) { return value * GIGA; }
    public static @GK double kelvinsToGigakelvins(@K double value) { return value / GIGA; }
    public static @K double megakelvinsToKelvins(@MK double value) { return value * MEGA; }
    public static @MK double kelvinsToMegakelvins(@K double value) { return value / MEGA; }
    public static @K double kilokelvinsToKelvins(@kK double value) { return value * KILO; }
    public static @kK double kelvinsToKilokelvins(@K double value) { return value / KILO; }
    public static @K double hectokelvinsToKelvins(@hK double value) { return value * HECTO; }
    public static @hK double kelvinsToHectokelvins(@K double value) { return value / HECTO; }
    public static @K double decakelvinsToKelvins(@daK double value) { return value * DECA; }
    public static @daK double kelvinsToDecakelvins(@K double value) { return value / DECA; }
    public static @K double decikelvinsToKelvins(@dK double value) { return value * DECI; }
    public static @dK double kelvinsToDecikelvins(@K double value) { return value / DECI; }
    public static @K double centikelvinsToKelvins(@cK double value) { return value * CENTI; }
    public static @cK double kelvinsToCentikelvins(@K double value) { return value / CENTI; }
    public static @K double millikelvinsToKelvins(@mK double value) { return value * MILLI; }
    public static @mK double kelvinsToMillikelvins(@K double value) { return value / MILLI; }
    public static @K double microkelvinsToKelvins(@uK double value) { return value * MICRO; }
    public static @uK double kelvinsToMicrokelvins(@K double value) { return value / MICRO; }
    public static @K double nanokelvinsToKelvins(@nK double value) { return value * NANO; }
    public static @nK double kelvinsToNanokelvins(@K double value) { return value / NANO; }
    public static @K double picokelvinsToKelvins(@pK double value) { return value * PICO; }
    public static @pK double kelvinsToPicokelvins(@K double value) { return value / PICO; }

    // Amount of Substance
    public static @mol double teramolesToMoles(@Tmol double value) { return value * TERA; }
    public static @Tmol double molesToTeramoles(@mol double value) { return value / TERA; }
    public static @mol double gigamolesToMoles(@Gmol double value) { return value * GIGA; }
    public static @Gmol double molesToGigamoles(@mol double value) { return value / GIGA; }
    public static @mol double megamolesToMoles(@Mmol double value) { return value * MEGA; }
    public static @Mmol double molesToMegamoles(@mol double value) { return value / MEGA; }
    public static @mol double kilomolesToMoles(@kmol double value) { return value * KILO; }
    public static @kmol double molesToKilomoles(@mol double value) { return value / KILO; }
    public static @mol double hectomolesToMoles(@hmol double value) { return value * HECTO; }
    public static @hmol double molesToHectomoles(@mol double value) { return value / HECTO; }
    public static @mol double decamolesToMoles(@damol double value) { return value * DECA; }
    public static @damol double molesToDecamoles(@mol double value) { return value / DECA; }
    public static @mol double decimolesToMoles(@dmol double value) { return value * DECI; }
    public static @dmol double molesToDecimoles(@mol double value) { return value / DECI; }
    public static @mol double centimolesToMoles(@cmol double value) { return value * CENTI; }
    public static @cmol double molesToCentimoles(@mol double value) { return value / CENTI; }
    public static @mol double millimolesToMoles(@mmol double value) { return value * MILLI; }
    public static @mmol double molesToMillimoles(@mol double value) { return value / MILLI; }
    public static @mol double micromolesToMoles(@umol double value) { return value * MICRO; }
    public static @umol double molesToMicromoles(@mol double value) { return value / MICRO; }
    public static @mol double nanomolesToMoles(@nmol double value) { return value * NANO; }
    public static @nmol double molesToNanomoles(@mol double value) { return value / NANO; }
    public static @mol double picomolesToMoles(@pmol double value) { return value * PICO; }
    public static @pmol double molesToPicomoles(@mol double value) { return value / PICO; }

    // Luminous Intensity
    public static @cd double teracandelasToCandelas(@Tcd double value) { return value * TERA; }
    public static @Tcd double candelasToTeracandelas(@cd double value) { return value / TERA; }
    public static @cd double gigacandelasToCandelas(@Gcd double value) { return value * GIGA; }
    public static @Gcd double candelasToGigacandelas(@cd double value) { return value / GIGA; }
    public static @cd double megacandelasToCandelas(@Mcd double value) { return value * MEGA; }
    public static @Mcd double candelasToMegacandelas(@cd double value) { return value / MEGA; }
    public static @cd double kilocandelasToCandelas(@kcd double value) { return value * KILO; }
    public static @kcd double candelasToKilocandelas(@cd double value) { return value / KILO; }
    public static @cd double hectocandelasToCandelas(@hcd double value) { return value * HECTO; }
    public static @hcd double candelasToHectocandelas(@cd double value) { return value / HECTO; }
    public static @cd double decacandelasToCandelas(@dacd double value) { return value * DECA; }
    public static @dacd double candelasToDecacandelas(@cd double value) { return value / DECA; }
    public static @cd double decicandelasToCandelas(@dcd double value) { return value * DECI; }
    public static @dcd double candelasToDecicandelas(@cd double value) { return value / DECI; }
    public static @cd double centicandelasToCandelas(@ccd double value) { return value * CENTI; }
    public static @ccd double candelasToCenticandelas(@cd double value) { return value / CENTI; }
    public static @cd double millicandelasToCandelas(@mcd double value) { return value * MILLI; }
    public static @mcd double candelasToMillicandelas(@cd double value) { return value / MILLI; }
    public static @cd double microcandelasToCandelas(@ucd double value) { return value * MICRO; }
    public static @ucd double candelasToMicrocandelas(@cd double value) { return value / MICRO; }
    public static @cd double nanocandelasToCandelas(@ncd double value) { return value * NANO; }
    public static @ncd double candelasToNanocandelas(@cd double value) { return value / NANO; }
    public static @cd double picocandelasToCandelas(@pcd double value) { return value * PICO; }
    public static @pcd double candelasToPicocandelas(@cd double value) { return value / PICO; }

    // Plane Angle
    public static @rad double degreesToRadians(@deg double value) { return value * (Math.PI / 180); }
    public static @deg double radiansToDegrees(@rad double value) { return value / (Math.PI / 180); }
    public static @rad double rotationsToRadians(@rot double value) { return value * (2 * Math.PI); }
    public static @rot double radiansToRotations(@rad double value) { return value / (2 * Math.PI); }
    // Degrees and rotations convert directly by 360, an exact whole number, rather
    // than through radians and two inexact multiples of pi.
    public static @rot double degreesToRotations(@deg double value) { return value / 360; }
    public static @deg double rotationsToDegrees(@rot double value) { return value * 360; }
}
