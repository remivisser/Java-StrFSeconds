// import java.util.stream.Collectors;
// import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import java.math.BigDecimal;
import java.math.RoundingMode;

//import java.util.*;


class StringFormatSeconds {
    public static void main(String[] args) throws Exception {
        // See div mod calculations, using a double or float loses
        // precision? 4000 seconds returns 4000000010.099 milliseconds.
        //
        // >>> System.out.println(4000/.000001);
        // 4000000010.099029064178466796875
        //
        // Maybe proceed to BigDecimal for more precision:
        //
        // >>> BigDecimal test1 = new BigDecimal(4000/.000001);
        // >>> System.out.println( test1.toPlainString());
        // 4000000000

        double seconds;
        String formatStringResult;

        // debug timings
        long startTime = System.currentTimeMillis();

        //seconds=90;
        //seconds=1;
        //seconds=4000;
        seconds=4000.1234567890;
        seconds=4000.9999;

        // formatStringResult = format(0, "%h:%m:%s", 2);
        // System.out.println(formatStringResult);

        formatStringResult = format(seconds, "%h2:%m2:%s2", 3);
        System.out.println(formatStringResult);

        formatStringResult = format(seconds, "%f", 3);
        System.out.println(formatStringResult);

        long elapsedTime = System.currentTimeMillis() - startTime;
        System.out.println("[Elapsed in " + elapsedTime + "ms]");

    }


    public static String format(double seconds, String formatString, int nDecimal) throws Exception {
        String smallestTimeUnitInFormatString = null;
        //double unitSize;
        double unitSize;
        String unitSizeString;
        String unitLeftPad;
        int i;

        // Input validation
        if (seconds < 0)
            throw new IncorrectSeconds("Seconds must be greater than or equal to 0");

        if (nDecimal < 0)
            throw new IncorrectNDecimal("Decimal must be greater than or equal to 0");


        //System.out.println(seconds);

        // Arrays in Java
        // Instead of a normal Hashmap create a LinkedHashMap that will
        // iterate in the order in which the entries were put into the map
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

        for (Map.Entry<String,Float> unit : units.entrySet())
            //System.out.println(unit.getKey() + " = " + unit.getValue());
            //System.out.println(formatString.indexOf( "%"+unit.getKey()));
            if (formatString.indexOf("%"+unit.getKey()) != -1)
                smallestTimeUnitInFormatString = unit.getKey();

        //System.out.println("smallestTimeUnitInFormatString = %" + smallestTimeUnitInFormatString);
        if (smallestTimeUnitInFormatString == null)
            return formatString;

        for (Map.Entry<String,Float> unit : units.entrySet()) {
            if (formatString.indexOf("%"+unit.getKey()) == -1)
                continue;

            // divmod
            // unit_size, seconds = divmod(seconds, unit['secs'])
            //
            // # Cast to int Math.floor to prevent 'error:
            // incompatible types: possible lossy conversion from
            // double to double'
            // # Do not cast to int! Causes unexpected results on large
            // numbers. (error: integer number too large)
            //unitSize = (int)Math.floor(seconds / unit.getValue());
            unitSize = Math.floor(seconds / unit.getValue());
            //System.out.println(seconds);
            //System.out.println(unit.getValue());
            //System.out.println(seconds / unit.getValue());
            //System.out.println(unitSize);
            seconds = seconds % unit.getValue();
            // System.out.println( "divmod seconds % unit seconds " + seconds + " " + unit.getValue());

            if (unit.getKey() == smallestTimeUnitInFormatString)
                // unit_size += 1 / unit['secs'] * seconds
                unitSize += 1 / unit.getValue() * seconds;


            // calculations are done, proceed with formatting


            // Zeroes left padding, this is a String datatype extracted
            // integer from formatString, make sure to cast in String.
            // format() function
            unitLeftPad = "";
            i = formatString.indexOf("%" + unit.getKey());
            try {
                Integer.parseInt(formatString.substring(i+2, i+3));
                unitLeftPad = formatString.substring(i+2, i+3);
            } catch (Exception e) {
            }

            // Check for smallest and non smallest entries
            if (unit.getKey() != smallestTimeUnitInFormatString){
                // Non smallest entries
                // All non smallest timeunits do not have decimals.
                // Disable scientific notation from double
                // https://stackoverflow.com/a/47809639
                //unitSizeString = String.valueOf((int)unitSize);
                unitSizeString = new BigDecimal(unitSize).toPlainString();
                // Leading zeroes, no decimals, nor decimal sign set
                // amount of spaces to unitLeftPad.
                if (unitLeftPad != "")
                    unitSizeString = String.format("%" + (int)Integer.parseInt(unitLeftPad) +"s", unitSizeString).replace(" ", "0");

            } else {

                // Smallest entries
                // BigDecimal scale
                // https://stackoverflow.com/a/13136669
                // BigDecimal Oneliner
                // https://stackoverflow.com/a/63067040
                // The BigDecimal.setScale() will always return zeroes
                // as much as the scale is set (nDecimal)
                // >>> System.out.println(new BigDecimal(0).setScale(3, RoundingMode.FLOOR).toPlainString());
                // 0.000
                unitSizeString = new BigDecimal(unitSize).setScale(nDecimal, RoundingMode.FLOOR).toPlainString();
                // Leading zeroes
                if (unitLeftPad != "")
                    // # Leading zeroes: Set '0%s.' to {ndecimal} +
                    // # {unit_rjust_length} + 1 (the decimal sign)
                    unitSizeString = String.format("%" + ((int)Integer.parseInt(unitLeftPad) + nDecimal + 1) +"s", unitSizeString).replace(" ", "0");

            }

            // Replacement
            formatString = formatString.replace("%" + unit.getKey() + unitLeftPad, unitSizeString);
        }

        return formatString;
    }

    // Default parameter values?
    // Use method overloading.
    // https://stackoverflow.com/a/1038401
    public static String format( double seconds, String formatString) throws Exception {
        return format(seconds, formatString, 3);
    }

    public static String format( double seconds) throws Exception {
        return format(seconds, "%h2:%m2:%s", 3);
    }

}


// Exceptions
class IncorrectSeconds extends Exception {
    public IncorrectSeconds(String errorMessage) {
        super(errorMessage);
    }
}

class IncorrectNDecimal extends Exception {
    public IncorrectNDecimal(String errorMessage) {
        super(errorMessage);
    }
}
