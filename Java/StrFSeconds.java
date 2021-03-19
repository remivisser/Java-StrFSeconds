// import java.util.stream.Collectors;
// import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import java.math.BigDecimal;
import java.math.RoundingMode;

//import java.util.*;


class StrFSeconds {
    public static void main(String[] args) {
        // See div mod calculations, using a double or float loses
        // precision? 4000 seconds returns 4000000010.099 milliseconds.
        //
        // >>> System.out.println(4000/.000001);
        // 4000000010.099029064178466796875
        //
        // Maybe proceed to BigDecimal for more precision:
        //
        // >>> BigDecimal bD = new BigDecimal(4000/.000001);
        // >>> System.out.println(bD.toPlainString());
        // 4000000000
        //
        // BigDecimal Floor
        //BigDecimal bd1 = new BigDecimal(7200);
        //BigDecimal bd2 = new BigDecimal(3600);
        //BigDecimal bd2 = new BigDecimal(3600.0000000000000001);
        //System.out.println( bd1.divide(bd2, 30, RoundingMode.CEILING));
        //System.out.println( bd1.divideToIntegralValue(bd2));

        // unitSize = unitSize.add( secondsDivMod.divide(unit.getValue(), 2, RoundingMode.FLOOR));

        // Why is Java 'String' type written in capital letter while
        // 'int'"' is not?
        // https://stackoverflow.com/a/4006311
        //

        long startTime = System.currentTimeMillis();


        double seconds;
        String formatStringResult;

        //seconds=90;
        //seconds=1;
        //seconds=4000;
        seconds=4000.1234567890;
        //seconds=4000;

        formatStringResult = StrFSeconds(seconds, "%h:%m:%s", 10);
        System.out.println(formatStringResult);

        formatStringResult = StrFSeconds(seconds, "%f");
        System.out.println(formatStringResult);


        long elapsedTime = System.currentTimeMillis() - startTime;
        System.out.println("[Elapsed in " + elapsedTime + "ms]");
    }


    public static String StrFSeconds(double seconds, String formatString, int nDecimal) {

        String smallestUnitInFormatString = null;
        BigDecimal unitSize = new BigDecimal(0);
        String unitSizePlainString;
        BigDecimal secondsDivMod = new BigDecimal(seconds);

        //System.out.println(seconds);

        // Arrays in Java
        // Instead of a normal Hashmap create a LinkedHashMap that will
        // iterate in the order in which the entries were put into the map
        // https://stackoverflow.com/a/17910409
        // https://docs.oracle.com/en/java/javase/14/docs/api/java.base/java/util/LinkedHashMap.html

        LinkedHashMap<String,BigDecimal> units = new LinkedHashMap<>();
        units.put("w", new BigDecimal(604800));
        units.put("d", new BigDecimal(86400));
        units.put("h", new BigDecimal(3600));
        units.put("m", new BigDecimal(60));
        units.put("s", new BigDecimal(1));
        units.put("l", new BigDecimal(.001));
        units.put("f", new BigDecimal(.000001));

        // Determine the smallest time unit in the formatString, if no
        // smallest time unit found return formatString.
        for (Map.Entry<String,BigDecimal> unit : units.entrySet())
            if (formatString.indexOf("%"+unit.getKey()) != -1)
                smallestUnitInFormatString = unit.getKey();

        if (smallestUnitInFormatString == null)
            return formatString;

        // For every time unit in units...
        for (Map.Entry<String,BigDecimal> unit : units.entrySet()) {
            // Check if this time unit is in formatString, if not
            // continue.
            if (formatString.indexOf("%"+unit.getKey()) == -1)
                continue;

            // Since BigDecimal implementation, (re)set value of
            // unitSize.
            // https://coderanch.com/t/534944/java/setting-BigDecimal-variable
            unitSize = BigDecimal.ZERO;

            // divmod > div
            //
            // Get the quotient of this units seconds and the
            // available seconds.
            //
            // unit_size, seconds = divmod(seconds, unit['secs'])
            //
            // # Cast to int Math.floor to prevent 'error: incompatible types:
            // possible lossy conversion from double to double'
            // # Do not cast to int! Causes unexpected results on large
            // numbers. (error: integer number too large)
            //unitSize = (int)Math.floor(seconds / unit.getValue());
            //unitSize = Math.floor(seconds / unit.getValue());
            //unitSize = unitSize.add(new BigDecimal(Math.floor(seconds / unit.getValue())));

            // Divide with precision set to 0
            unitSize = unitSize.add(secondsDivMod.divide(unit.getValue(), 0, RoundingMode.FLOOR));


            // divmod > mod
            //
            // Assign modulus (remainder) to seconds
            //
            //seconds = seconds % unit.getValue();
            secondsDivMod = secondsDivMod.remainder(unit.getValue());

            //System.out.println("Time unit: %" + unit.getKey());
            //System.out.println(seconds);
            //System.out.println(unit.getValue());
            //System.out.println(seconds / unit.getValue());
            //System.out.println(new BigDecimal(Math.floor(seconds / unit.getValue())));
            //System.out.println(unitSize);
            //System.out.println();

            // Smallest unit, add the remaing seconds as fractions of
            // unit size.
            if (unit.getKey() == smallestUnitInFormatString)
                // unit_size += 1 / unit['secs'] * seconds
                // unitSize += 1 / unit.getValue() * seconds;
                // unitSize = unitSize.add(new BigDecimal(1 / unit.getValue() * seconds));
                unitSize = unitSize.add(secondsDivMod.divide(unit.getValue(), nDecimal, RoundingMode.FLOOR));


            // calculations are done, proceed with formatting

            // Disable scientific notation from double
            // https://stackoverflow.com/a/47809639
            // BigDecimal d = new BigDecimal(unitSize);
            // unitSizeString = d.toPlainString();
            // unitSizeString = String.valueOf(unitSize);
            //unitSizePlainString = new BigDecimal(unitSize).toPlainString();
            unitSizePlainString = unitSize.toPlainString();

            //System.out.println(unitSize);

            formatString = formatString.replace("%"+unit.getKey(), unitSizePlainString);
        }


        return formatString;
    }

    // Default parameter values?
    // Use method overloading.
    // https://stackoverflow.com/a/1038401
    public static String StrFSeconds( double seconds, String formatString) {
        return StrFSeconds( seconds, formatString, 3);
    }

    public static String StrFSeconds( double seconds) {
        return StrFSeconds( seconds, "%h2:%m2:%s", 3);
    }

}
