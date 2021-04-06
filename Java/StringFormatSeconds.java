import java.util.LinkedHashMap;
import java.util.Map;


class StringFormatSeconds {

    public static void main(String[] args) {
    }

    /**
    * Converts seconds into logical timeunits. Timeunits are: weeks,
    * days, hours, minutes, seconds, milli-and microseconds.
    *
    * @param    seconds         A double containing the seconds to
    *                           format.
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
    public static String format(double seconds, String formatString, int nDecimal) {
        String smallestUnitInFormatString = null;
        double unitSize;
        String unitSizePlainString;
        String unitLeftPadSize;

        //System.out.println(seconds);

        // Input validation
        // https://docs.oracle.com/javase/8/docs/api/java/lang/IllegalArgumentException.html
        if (seconds < 0)
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
        LinkedHashMap<String,Double> units = new LinkedHashMap<>();
        units.put("w", (double)604800); // week
        units.put("d", (double)86400); // day
        units.put("h", (double)3600); // hour
        units.put("m", (double)60); // minute
        units.put("s", (double)1); // second
        units.put("l", (double).001); // millisecond
        units.put("f", (double).000001); // microsecond

        // Determine the smallest timeunit in the formatString and
        // set variable accordingly. If no smallest timeunit found
        // return unaltered formatString.
        for (Map.Entry<String,Double> unit : units.entrySet())
            if (formatString.indexOf("%"+unit.getKey()) != -1)
                smallestUnitInFormatString = unit.getKey();

        if (smallestUnitInFormatString == null)
            return formatString;

        // Replace '%o' with the original passed value for seconds
        // remove .0$ from valueOf() using removeZeroDecimal()
        formatString = formatString.replace(
            "%o", removeZeroDecimal(String.valueOf(seconds)));

        // For every timeunit in units...
        for (Map.Entry<String,Double> unit : units.entrySet()) {
            // Check if this timeunit is defined in the formatString,
            // if not continue to next timeunit.
            if (formatString.indexOf("%"+unit.getKey()) == -1)
                continue;

            // divmod
            //
            // Process the quotient and the remainder (modulo) of this
            // timeunits seconds from the available seconds.
            //
            // Assign the quotient to unitSize
            unitSize = Math.floor(seconds / unit.getValue());

            // Assign the remainder to seconds
            //seconds = seconds - (unitSize * unit.getValue());
            seconds = seconds % unit.getValue();

            // Smallest timeunit
            if (unit.getKey() == smallestUnitInFormatString) {
                // Add the remaining seconds as fractions of this
                // timeunit's size.
                if (seconds > 0)
                    unitSize += 1 / unit.getValue() * seconds;

                // Truncate decimals after nDecimal
                // For nDecimal=0 use Math.floor()
                // For nDecimal > 0 use Math.floor(unitSize * Math.pow(10, nDecimal))
                // divide by Math.pow(10, nDecimal)
                // https://stackoverflow.com/a/25903634
                if (nDecimal == 0) {
                    unitSize = Math.floor(unitSize);
                } else {
                    unitSize = Math.floor(unitSize * Math.pow(10, nDecimal))
                    / Math.pow(10, nDecimal);
                }
            }


            // Calculations for this timeunit are complete, proceed with
            // formatting.


            // Cast double unitSize to String unitSizePlainString
            //
            // String.format() is use as it does not show scientifc
            // notation (as String.valueOf() does) also String.format()
            // allows for fixed zeroes after decimal sign.
            //
            // Smallest and non-smallest timeunits have different
            // setting for decimals.
            if (unit.getKey() != smallestUnitInFormatString ) {
                // Non smallest entries
                //
                // Non smallest timeunits are displayed without
                // decimals;
                // Use String.format() to cast unitSize to String and
                // set the number of fixed decimals to ".0f".
                // (String.format() rounds up but because value of
                // timeunit is floored no rounding up will occur.)
                unitSizePlainString = String.format(
                    "%.0f", unitSize);
            } else {

                // Smallest entry
                //
                // Smallest timeunit is displayed with nDecimal
                // decimals;
                // Use String.format() to cast unitSize to String and
                // set the number of fixed decimals ".f" to nDecimal.
                // (String.format() rounds up but because decimals are
                // truncated at the size of nDecimal no rounding up
                // will occur.)
                unitSizePlainString = String.format(
                    "%." + nDecimal + "f", unitSize);
            }

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
            formatString = formatString.replace(
                "%" + unit.getKey() + unitLeftPadSize, unitSizePlainString);

        }

        return formatString;

    }

    /**
    * Method overload for format(). Default value for precision 3.
    */
    public static String format(double seconds, String formatString) {
        return format(seconds, formatString, (int)3);
    }

    /**
    * Method overload for format(). Default values for formatString
    * "%h2:%m2:%s" and precision 3.
    */
    public static String format(double seconds) {
        return format(seconds, "%h2:%m2:%s", (int)3);
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

    // Taken care of by String.format()
    //private static String rpadZeroes( String s, int nZeroes){
    //    s = String.format("%1$-" + (s.indexOf(".") + 1 + nZeroes) +"s", s).replace(" ", "0");
    //    return s;
    //}

}
