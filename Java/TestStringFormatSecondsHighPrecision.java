import java.math.BigDecimal;


class TestStringFormatSecondsHighPrecision {
    public static void main(String[] args) {

        long startTime = System.currentTimeMillis();

        // Not needed to initiate new class of StringFormatSecondsHighPrecision;
        // format method is defined static.
        // StringFormatSecondsHighPrecision StringFormatSecondsHighPrecision = new StringFormatSecondsHighPrecision();

        BigDecimal seconds;
        String formatStringResult;
        

        startTime = System.currentTimeMillis();
        seconds=BigDecimal.valueOf(90);
        formatStringResult = StringFormatSecondsHighPrecision.format(seconds, "%o seconds is %h hours and %s seconds", 2);
        System.out.println(formatStringResult);
        System.out.println("[Elapsed in " + (System.currentTimeMillis() - startTime) + "ms]");


        startTime = System.currentTimeMillis();
        seconds = BigDecimal.valueOf(4000.1234567890);
        formatStringResult = StringFormatSecondsHighPrecision.format(seconds, "%o seconds is Days=%d %h2:%m2:%s2 milliseconds=%l microseconds=%f", 6);
        System.out.println(formatStringResult);
        System.out.println("[Elapsed in " + (System.currentTimeMillis() - startTime) + "ms]");

        startTime = System.currentTimeMillis();
        seconds = BigDecimal.valueOf(2147483647);
        formatStringResult = StringFormatSecondsHighPrecision.format(seconds, "%o seconds is Weeks=%w, Days=%d %h2:%m2:%s2", 3);
        System.out.println(formatStringResult);
        System.out.println("[Elapsed in " + (System.currentTimeMillis() - startTime) + "ms]");

        startTime = System.currentTimeMillis();
        seconds = BigDecimal.valueOf(2147483647);
        formatStringResult = StringFormatSecondsHighPrecision.format(seconds, "%o seconds is %f microseconds", 3);
        System.out.println(formatStringResult);
        System.out.println("[Elapsed in " + (System.currentTimeMillis() - startTime) + "ms]");

        startTime = System.currentTimeMillis();
        seconds = BigDecimal.valueOf(2147483647000.1234567890d);
        formatStringResult = StringFormatSecondsHighPrecision.format(seconds, "%o seconds is %f microseconds", 3);
        System.out.println(formatStringResult);
        System.out.println("[Elapsed in " + (System.currentTimeMillis() - startTime) + "ms]");

        startTime = System.currentTimeMillis();
        seconds = BigDecimal.valueOf(0.0000000001234567890d);
        formatStringResult = StringFormatSecondsHighPrecision.format(seconds, "%o seconds is %f microseconds", 9);
        System.out.println(formatStringResult);
        System.out.println("[Elapsed in " + (System.currentTimeMillis() - startTime) + "ms]");

        startTime = System.currentTimeMillis();
        seconds = BigDecimal.valueOf(4000000000d);
        formatStringResult = StringFormatSecondsHighPrecision.format(seconds, "%o is %f microseconds", 0);
        System.out.println(formatStringResult);
        System.out.println("[Elapsed in " + (System.currentTimeMillis() - startTime) + "ms]");

    }
}
