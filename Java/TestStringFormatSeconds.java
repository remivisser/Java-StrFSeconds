import java.math.BigDecimal;

class TestStringFormatSeconds {
    public static void main(String[] args) {
        // Debug timings
        // to view timings uncomment below and wrap them around
        // function calls
        //long startTime;
        //startTime = System.currentTimeMillis();
        //System.out.println("[Elapsed in " + (System.currentTimeMillis() - startTime) + "ms]");

        double seconds;

        System.out.println("--- Hours, Days, Minutes test ---");
        // Define seconds 50 hours
        seconds=(60*60*50);
        // Show days and minutes
        System.out.println(
            StringFormatSeconds.format(seconds, "%o seconds is %d days and %h hours", 2));
        // Show days only, notice nDecimal=2
        System.out.println(
            StringFormatSeconds.format(seconds, "%o seconds is %d days", 2));
        // Show hours only
        System.out.println(
            StringFormatSeconds.format(seconds, "%o seconds is %h hours", 2));
        // Skip adjecent time unit hours; show days and minutes
        System.out.println(
            StringFormatSeconds.format(seconds, "%o seconds is %d days and %m minutes", 2));


        System.out.println("\n--- Stopwatch timer test ---");
        // Show seconds as a stopwatch
        seconds=1948.194812;
        // Display microseconds by specifying seconds and setting nDecimal to 6
        System.out.println(
            StringFormatSeconds.format(seconds, "%o seconds is %h2:%m2:%s2", 6));
        // Specify microseconds manually using %f
        System.out.println(
            StringFormatSeconds.format(seconds, "%o seconds is %h2:%m2:%s2.%f", 0));


        System.out.println("\n--- Fractions and Rounding test ---");
        seconds=90;
        // Show seconds as minutes
        System.out.println(
            StringFormatSeconds.format(seconds, "%o seconds is %m minutes", 2));

        seconds=.999999;
        // Show with three decimals
        System.out.println(
            StringFormatSeconds.format(seconds, "%o seconds is %s seconds", 3));
        // Show without decimals - will show 0 seconds, rounding mode is down
        System.out.println(
            StringFormatSeconds.format(seconds, "%o seconds is %s seconds", 0));


        System.out.println("\n--- Lost precision on microseconds tests ---");
        // Millisecond precision failures.
        // See Tests/TestPrecisionDoubleVsBigDecimal.java

        seconds = 550.194812;
        System.out.println(
            StringFormatSeconds.format(seconds, "StringFormatSeconds: %o %s.%f", 6));
        System.out.println(
            StringFormatSecondsHighPrecision.format(seconds, "StringFormatSecondsHighPrecision: %o %s.%f", 6));

        seconds = 550.195917;
        System.out.println(
            StringFormatSeconds.format(seconds, "StringFormatSeconds: %o %s.%f", 6));
        System.out.println(
            StringFormatSecondsHighPrecision.format(seconds, "StringFormatSecondsHighPrecision: %o %s.%f", 6));

        // 'Loses' one msec in StringFormatSeconds, correct in StringFormatSecondsHighPrecision
        seconds=.001001d;
        System.out.println(
            StringFormatSeconds.format(seconds, "StringFormatSeconds: %o %s", 6));
        System.out.println(
            StringFormatSecondsHighPrecision.format(seconds, "StringFormatSecondsHighPrecision: %o %s", 6));

    }
}
