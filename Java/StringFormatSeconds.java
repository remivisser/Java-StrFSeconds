import java.util.LinkedHashMap;
import java.util.Map;
import java.math.BigDecimal;
import java.math.RoundingMode;
//import java.util.*;
//import java.lang.*;

class StringFormatSeconds {

    public static void main(String[] args) {

        // Max size seconds (double) = Integer.MAX_VALUE = 2147483647
        // https://stackoverflow.com/a/32252952
        // See StringFormatSecondsHighPrecision.java for higher
        // precision.

        // debug timings
        long startTime = System.currentTimeMillis();

        double seconds;
        String formatStringResult;

        //seconds=90;
        //seconds=1;
        //seconds=4000;
        seconds=4000.1234567890;
        seconds=4000.9999;
        seconds=2147483647;

        //formatStringResult = format(90, "%h:%m:%s", 2);
        //System.out.println(formatStringResult);

        //formatStringResult = format(seconds, "%h2:%m2:%s2", 3);
        formatStringResult = format(seconds, "Days=%d7 %h2:%m2:%s2", 0);
        System.out.println(formatStringResult);

        formatStringResult = format(seconds, "%f", 3);
        System.out.println(formatStringResult);

        long elapsedTime = System.currentTimeMillis() - startTime;
        System.out.println("[Elapsed in " + elapsedTime + "ms]");
    }

    public static String format(double seconds, String formatString, int nDecimal) {
        String smallestUnitInFormatString = null;
        double unitSize;
        String unitSizePlainString;
        String unitLeftPad;
        int indexOfUnit;

        // Input validation
        // https://docs.oracle.com/javase/8/docs/api/java/lang/IllegalArgumentException.html
        if (seconds < 0)
            throw new IllegalArgumentException(
                "Seconds must be greater than or equal to 0");
        if (nDecimal < 0)
            throw new IllegalArgumentException(
                "Decimal must be greater than or equal to 0");

        //System.out.println(seconds);

        // Arrays in Java
        // Instead of a normal Hashmap create a LinkedHashMap that will
        // iterate in the order in which the entries were put into the map.
        // https://stackoverflow.com/a/17910409
        // https://docs.oracle.com/en/java/javase/14/docs/api/java.base/java/util/LinkedHashMap.html
        LinkedHashMap<String,Float> units = new LinkedHashMap<>();
        units.put("w", (float)604800);
        units.put("d", (float)86400);
        units.put("h", (float)3600);
        units.put("m", (float)60);
        units.put("s", (float)1);
        units.put("l", (float).001);
        units.put("f", (float).000001);

        // Determine the smallest time unit in the formatString and
        // set variable accordingly. If no smallest unit found return
        // unaltered formatString.
        for (Map.Entry<String,Float> unit : units.entrySet())
            if (formatString.indexOf("%"+unit.getKey()) != -1)
                smallestUnitInFormatString = unit.getKey();
        if (smallestUnitInFormatString == null)
            return formatString;

        // For every time unit in units...
        for (Map.Entry<String,Float> unit : units.entrySet()) {
            // Check if this time unit is in formatString, if not
            // continue.
            if (formatString.indexOf("%"+unit.getKey()) == -1)
                continue;

            // divmod
            // unit_size, seconds = divmod(seconds, unit['secs'])
            //
            // Get the quotient of this units seconds and the
            // available seconds by flooring the division.
            //
            // # Cast to int Math.floor to prevent 'error:
            // incompatible types: possible lossy conversion from
            // double to double'
            // # Do not cast to int! Causes unexpected results on large
            // numbers. (error: integer number too large)
            //unitSize = (int)Math.floor(seconds / unit.getValue());
            unitSize = Math.floor(seconds / unit.getValue());
            // Assign the modulus (remainder) to seconds.
            seconds = seconds % unit.getValue();


            // Smallest time unit; add the remaing seconds as
            // fractions of unit size.
            if (unit.getKey() == smallestUnitInFormatString)
                // unit_size += 1 / unit['secs'] * seconds
                unitSize += 1 / unit.getValue() * seconds;


            // calculations for this unit are done, proceed with
            // formatting.


            // Zeroes left padding, this is the one integer directly
            // following the format specifier (%s2). (It is a String
            // for the replacement, later cast to an integer in
            // String.format())
            //
            // Determine if this unit has a left padding integer and
            // set unitLeftPad accordingly.
            unitLeftPad = "";
            indexOfUnit = formatString.indexOf("%" + unit.getKey());
            if (formatString.length() > indexOfUnit + 2)
                if (isInteger(formatString.substring(indexOfUnit+2, indexOfUnit+3)))
                   unitLeftPad = formatString.substring(indexOfUnit+2, indexOfUnit+3);

            // Check for smallest and non-smallest entries
            if (unit.getKey() != smallestUnitInFormatString) {

                // Non smallest entries
                //
                // All non smallest timeunits do not have decimals.
                // Use BigDecimal.toPlainString to cast the double
                // unitSize to the String unitSizePlainString.
                // BigDecimal does not show decimals (.0) nor does it
                // show scientific notation as String.valueOf does.
                unitSizePlainString = new BigDecimal(unitSize).toPlainString();

                // Add leading zeroes, no decimals nor decimal sign
                // set size to size of unitLefPad.
                if (unitLeftPad != "")
                    //unitSizePlainString = String.format("%" + Integer.parseInt(unitLeftPad) +"s", unitSizePlainString).replace(" ", "0");
                    unitSizePlainString = String.format("%" + unitLeftPad +"s", unitSizePlainString).replace(" ", "0");

            } else {

                // Smallest entry
                //
                // Use BigDecimal.setScale(); it will always return
                // zeroes as much as the scale is set.
                // >>> System.out.println(new BigDecimal(0).setScale(3, RoundingMode.FLOOR).toPlainString());
                // 0.000
                // https://stackoverflow.com/a/13136669
                // https://stackoverflow.com/a/63067040
                unitSizePlainString = new BigDecimal(unitSize).setScale(nDecimal, RoundingMode.FLOOR).toPlainString();

                // Leading zeroes
                if (unitLeftPad != "") {
                    // Add leading zeroes size of unitLefPad. Different
                    // handling required for units with and without decimals.
                    if (nDecimal == 0) {
                        unitSizePlainString = String.format("%" + unitLeftPad +"s", unitSizePlainString).replace(" ", "0");
                    } else {
                        unitSizePlainString = String.format("%" + String.valueOf(Integer.parseInt(unitLeftPad) + nDecimal + 1) +"s", unitSizePlainString).replace(" ", "0");
                    }
                }

            }

            // Replacement
            formatString = formatString.replace("%" + unit.getKey() + unitLeftPad, unitSizePlainString);
        }

        return formatString;
    }

    // Default parameter values?
    // Use method overloading.
    // https://stackoverflow.com/a/1038401
    public static String format( double seconds, String formatString) {
        return format(seconds, formatString, 3);
    }

    public static String format( double seconds) {
        return format(seconds, "%h2:%m2:%s", 3);
    }

    // Used to determine if adjecent character of a format specifier
    // is an integer.
    // https://simplesolution.dev/java-check-string-is-valid-integer/
    private static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (Exception ex) {
            return false;
        }

   }

}
