import java.math.BigDecimal;

class TestStringFormatSeconds {
    public static void main(String[] args) {

        // debug timings
        long startTime = System.currentTimeMillis();

        double seconds;
        String formatStringResult;
        
        // Max size seconds is max size Double which is a lot; 
        // approx. 1.7E308
        // Make sure to append d to prevent error: integer number too
        // large (default is Integer)
        // https://stackoverflow.com/q/4331200
        //System.out.println(new BigDecimal(Double.MAX_VALUE).toPlainString());
        //System.out.println(Double.MAX_VALUE);

        // Not needed to initiate new class of StringFormatSeconds;
        // format method is defined static.
        // StringFormatSeconds StringFormatSeconds = new StringFormatSeconds();

        startTime = System.currentTimeMillis();
        seconds=90;
        formatStringResult = StringFormatSeconds.format(seconds, "%o seconds is %m minutes", 2);
        System.out.println(formatStringResult);
        System.out.println("[Elapsed in " + (System.currentTimeMillis() - startTime) + "ms]");
        
        startTime = System.currentTimeMillis();
        seconds=90;
        formatStringResult = StringFormatSeconds.format(seconds, "%o seconds is %h hours and %s seconds", 2);
        System.out.println(formatStringResult);
        System.out.println("[Elapsed in " + (System.currentTimeMillis() - startTime) + "ms]");

        startTime = System.currentTimeMillis();
        seconds = 4000.1234567890;
        formatStringResult = StringFormatSeconds.format(seconds, "%o seconds is Days=%d %h2:%m2:%s2 milliseconds=%l microseconds=%f", 6);
        System.out.println(formatStringResult);
        System.out.println("[Elapsed in " + (System.currentTimeMillis() - startTime) + "ms]");

        startTime = System.currentTimeMillis();
        seconds = 2147483647;
        formatStringResult = StringFormatSeconds.format(seconds, "%o seconds is Weeks=%w, Days=%d %h2:%m2:%s2", 3);
        System.out.println(formatStringResult);
        System.out.println("[Elapsed in " + (System.currentTimeMillis() - startTime) + "ms]");

        startTime = System.currentTimeMillis();
        seconds = 2147483647;
        formatStringResult = StringFormatSeconds.format(seconds, "%o seconds is %f microseconds", 3);
        System.out.println(formatStringResult);
        System.out.println("[Elapsed in " + (System.currentTimeMillis() - startTime) + "ms]");

        startTime = System.currentTimeMillis();
        seconds = 2147483647000.1234567890d;
        formatStringResult = StringFormatSeconds.format(seconds, "%o seconds is %f microseconds", 3);
        System.out.println(formatStringResult);
        System.out.println("[Elapsed in " + (System.currentTimeMillis() - startTime) + "ms]");

        startTime = System.currentTimeMillis();
        seconds = 0.0000000001234567890d;
        formatStringResult = StringFormatSeconds.format(seconds, "%o seconds is %f microseconds", 9);
        System.out.println(formatStringResult);
        System.out.println("[Elapsed in " + (System.currentTimeMillis() - startTime) + "ms]");

        startTime = System.currentTimeMillis();
        seconds = 4000000000d;
        formatStringResult = StringFormatSeconds.format(seconds, "%o is %f microseconds", 0);
        System.out.println(formatStringResult);
        System.out.println("[Elapsed in " + (System.currentTimeMillis() - startTime) + "ms]");

    }
}
