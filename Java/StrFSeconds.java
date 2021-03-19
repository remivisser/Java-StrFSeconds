// import java.util.stream.Collectors;
// import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.math.BigDecimal;

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
        // >>> BigDecimal test1 = new BigDecimal(4000/.000001);
        // >>> System.out.println( test1.toPlainString());
        // 4000000000
        //

        double seconds;
        String formatStringResult;

        //seconds=90;
        //seconds=1;
        //seconds=4000;
        seconds=4000.1234567890;

        formatStringResult = StrFSeconds(seconds, "%h:%m:%s");
        System.out.println(formatStringResult);

        formatStringResult = StrFSeconds(seconds, "%f");
        System.out.println(formatStringResult);
    }


    public static String StrFSeconds( double seconds, String formatString, int nDecimal) {
        String smallestTimeUnitInFormatString = null;
        //double unitSize;
        double unitSize;
        // BigDecimal unitSize;

        String unitSizeString;

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
            // # Cast to int Math.floor to prevent 'error: incompatible types:
            // possible lossy conversion from double to double'
            // # Do not cast to int! Causes unexpected results on large
            // numbers. (error: integer number too large)
            //unitSize = (int)Math.floor(seconds / unit.getValue());
            unitSize = Math.floor(seconds / unit.getValue());
            //System.out.println(seconds);
            //System.out.println(unit.getValue());
            //System.out.println(seconds / unit.getValue());
            //System.out.println(unitSize);

            // unitSize = new BigDecimal(Math.floor(seconds / unit.getValue()));
            seconds = seconds % unit.getValue();
            // System.out.println( "divmod seconds % unit seconds " + seconds + " " + unit.getValue());

            if (unit.getKey() == smallestTimeUnitInFormatString) {
                // unit_size += 1 / unit['secs'] * seconds
                unitSize += 1 / unit.getValue() * seconds;
                // unitSize = unitSize.add(new BigDecimal(seconds));
            }


            // calculations are done, proceed with formatting

            // Disable scientific notation from double
            // https://stackoverflow.com/a/47809639
            // BigDecimal d = new BigDecimal(unitSize);
            // unitSizeString = d.toPlainString();
            // unitSizeString = String.valueOf(unitSize);
            unitSizeString = new BigDecimal(unitSize).toPlainString();

            formatString = formatString.replace("%"+unit.getKey(), unitSizeString);
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


