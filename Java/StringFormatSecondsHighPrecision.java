import java.util.LinkedHashMap;
import java.util.Map;
import java.math.BigDecimal;
import java.math.RoundingMode;


public class StringFormatSecondsHighPrecision {
    public static void main(String[] args) {
    }

    /**
    * Converts seconds into logical timeunits. Timeunits are: weeks,
    * days, hours, minutes, seconds, milli-and microseconds.
    *
    * @param    seconds         A BigDecimal containing the seconds to
    *                           format. Make sure to correctly pass
    *                           it, see TestStringFormatSecondsHighPrecision.
    *                           For testing use `new BigDecimal("{Double}")`
    *                           - with quotes.
    * @param    formatString    A string containing timeunit specifiers
    *                           controlling the display of seconds.
    *                           Timeunits are displayed using the
    *                           following timeunit specifiers:
    *                           %w for weeks
    *                           %d for days
    *                           %h for hours
    *                           %m for minutes
    *                           %s for seconds
    *                           %l for milliseconds (0.001 second)
    *                           %f for microseconds (0.000001 second)
    *                           %o for the unchanged seconds value
    * @param    nDecimal        A positive integer containing the
    *                           number of decimals shown. Decimals are
    *                           only applicable for the smallest
    *                           timeunit.
    * @return                   A String with all timeunit specifiers
    *                           replaced for the given seconds.
    */
    public static String format(BigDecimal seconds, String formatString, int nDecimal) {
    // public static String format(double seconds, String formatString, int nDecimal) {
        BigDecimal unitSize = new BigDecimal(0);
        String smallestUnitInFormatString = null;
        String unitSizePlainString;
        String unitLeftPadSize;

        // Input validation
        // https://docs.oracle.com/javase/8/docs/api/java/lang/IllegalArgumentException.html
        if (seconds.compareTo(BigDecimal.ZERO) == -1)
            throw new IllegalArgumentException(
                "Seconds must be greater than or equal to 0");

        if (nDecimal < 0)
            throw new IllegalArgumentException(
                "Decimal must be greater than or equal to 0");

        // Available timeunits LinkedHashMap (Array)
        //
        // It is crucial that timeunits will be processed from large
        // (weeks) to small (microseconds). To achieve this create a
        // LinkedHashMap instead of a normal HashMap. A LinkedHashMap
        // iterates in the order in which the entries were put into the
        // map.
        // https://stackoverflow.com/a/17910409
        // https://docs.oracle.com/en/java/javase/14/docs/api/java.base/java/util/LinkedHashMap.html
        LinkedHashMap<String,BigDecimal> units = new LinkedHashMap<>();
        // !!! Make sure to use `BigDecimal.valueOf(1)` or
        // `new BigDecimal()` with double quotes !!!
        // Test with:
        // >>> System.out.println(format(.001001, "%o %s %l %f", 6));
        // https://stackoverflow.com/a/16774279
        units.put("w", BigDecimal.valueOf(604800));
        units.put("d", BigDecimal.valueOf(86400));
        units.put("h", BigDecimal.valueOf(3600));
        units.put("m", BigDecimal.valueOf(60));
        units.put("s", BigDecimal.valueOf(1));
        units.put("l", BigDecimal.valueOf(.001));
        units.put("f", BigDecimal.valueOf(.000001));

        // Determine the smallest time unit in the formatString and
        // set variable accordingly. If no smallest time unit found
        // return unaltered formatString.
        for (Map.Entry<String,BigDecimal> unit : units.entrySet())
            if (formatString.indexOf("%"+unit.getKey()) != -1)
                smallestUnitInFormatString = unit.getKey();

        if (smallestUnitInFormatString == null)
            return formatString;

        // Replace '%o' with the original passed value for seconds
        // remove .0$ from valueOf() using removeZeroDecimal()
        formatString = formatString.replace(
            "%o", removeZeroDecimal(seconds.toPlainString()));

        // For every timeunit in units...
        for (Map.Entry<String,BigDecimal> unit : units.entrySet()) {
            // Check if this time unit is defined in the formatString,
            // if not continue to next timeunit.
            if (formatString.indexOf("%"+unit.getKey()) == -1)
                continue;

            // Since BigDecimal implementation; reset value of unitSize
            // BigDecimal.
            unitSize = BigDecimal.ZERO;

            // Process the quotient and the remainder (modulo) of this
            // time units seconds from the available seconds.
            //
            // Assign the quotient to unitSize. Divide with precision
            // set to 0 and RoundingMode to Floor.
            unitSize = unitSize.add(seconds.divide(unit.getValue(), 0, RoundingMode.FLOOR));
            // Assign the remainder to seconds
            seconds = seconds.remainder(unit.getValue());

            // Smallest timeunit
            if (unit.getKey() == smallestUnitInFormatString)
                // Add the remaining seconds as fractions of this
                // timeunit's size.
                //
                // unitSize += 1 / unit.getValue() * seconds;
                // Setting int scale to nDecimal does all the work;
                // - will add '0' for '.999' if nDecimal = 0
                // - will add '0.9' for '.999' if nDecimal = 1
                unitSize = unitSize.add(seconds.divide(unit.getValue(), nDecimal, RoundingMode.FLOOR));


            // Calculations for this timeunit are complete, proceed with
            // formatting.


            unitSizePlainString = unitSize.toPlainString();


            // Zeroes left padding
            //
            // This is configured by a single integer [0-9] directly
            // following the timeunit specifier (%s2). The
            // unitLeftPadSize variable is a String for replacing in
            // formatString, it is cast to an integer in the
            // String.format() call.
            unitLeftPadSize = leftPadParameterValue(formatString, unit.getKey());
            if (unitLeftPadSize != "")
                unitSizePlainString = leftPadZeroes(unitSizePlainString, Integer.parseInt(unitLeftPadSize));

            // Replacement
            //
            // Replace current timeunit plus left padd parameter (%s2)
            // by unitSizePlainString.
            formatString = formatString.replace("%" + unit.getKey() + unitLeftPadSize, unitSizePlainString);

        }

        return formatString;

    }

    /**
    * Method overload for format(). Default value for precision 3.
    */
    public static String format(BigDecimal seconds, String formatString) {
        return format(seconds, formatString, (int)3);
    }

    /**
    * Method overload for format(). Default values for formatString
    * "%h2:%m2:%s" and precision 3.
    */
    public static String format(BigDecimal seconds) {
        return format(seconds, "%h2:%m2:%s", (int)3);
    }

    /**
    * Method overload for format(). Double seconds is converted to
    * BigDecimal.
    */
    public static String format(double seconds, String formatString, int nDecimal) {
        return format(BigDecimal.valueOf(seconds), formatString, nDecimal);
    }

    /**
    * Method overload for format() (Double version). Default value for
    * precision 3.
    */
    public static String format(double seconds, String formatString) {
        return format(BigDecimal.valueOf(seconds), formatString, 3);
    }

    /**
    * Method overload for format() (Double version). Default values for
    * formatString "%h2:%m2:%s" and precision 3.
    */
    public static String format(double seconds) {
        return format(BigDecimal.valueOf(seconds), "%h2:%m2:%s", 3);
    }


    /**
    * Remove .0$ from string, used for original passed seconds %o
    * replacement
    * @param    s               String representing seconds passed
    * @return                   Seconds with .0$ removed or unaltered
    */
    private static String removeZeroDecimal(String s) {
        // Make sure to add check for existance of decimal sign to
        // prevent StringIndexOutOfBoundsException
        if (s.indexOf(".") != -1 && s.substring(s.indexOf("."), s.length()).equals(".0"))
            s = s.replace(".0", "");
        return s;
    }

    /**
    * Determine if String s is an integer.
    * (Used in left pad zeroes parameters to test if adjecent character
    * of a timeunit specifier is an integer.)
    * https://simplesolution.dev/java-check-string-is-valid-integer/
    *
    * @param  s                 String to be evaluated
    * @return                   true if the String s is an integer;
    *                           false otherwise.
    */
    private static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    /**
    * Retrieve the zero left padding parameter for unitName in
    * formatString. Retrieves '2' in '%s2'
    *
    * @param  formatString      The complete formatstring
    * @param  unitName          The unit name (w/d/h/m/s/l/f)
    * @return                   The adjecent left padd parameter integer
    *                           for %unitName or an empty String if none
    *                           found
    */
    private static String leftPadParameterValue(String formatString, String unitName){
        // Set default value to empty string
        String leftPadValue = "";
        int indexOfUnit = formatString.indexOf("%" + unitName);

        if (formatString.length() > indexOfUnit + 2)
            if (isInteger(formatString.substring(indexOfUnit+2, indexOfUnit+3)))
               leftPadValue = formatString.substring(indexOfUnit+2, indexOfUnit+3);

        return leftPadValue;
    }

    /**
    * Returns a String left-padded with zeroes to the amount of
    * nZeroes, nZeroes applies to the amount of zeroes added before the
    * decimal sign. Modifies 3.0 into 03.0 if nZeroes is 2
    *
    * @param  s                 The String to be left padded
    * @param  nZeroes           The amount of zeroes to add
    * @return                   The String s leftpadded with nZeroes
    */
    private static String leftPadZeroes(String s, int nZeroes){
        if (s.indexOf(".") == -1) {
            s = String.format("%" + String.valueOf(nZeroes) +"s", s);
        } else {
            s = String.format("%" + String.valueOf(s.length() - s.indexOf(".") + nZeroes) +"s", s);
        }
        s = s.replace(" ", "0");

        return s;
    }

}
