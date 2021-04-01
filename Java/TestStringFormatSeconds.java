import java.math.BigDecimal;

class TestStringFormatSeconds {
    public static void main(String[] args) {
        // It is not not needed to initiate a new class of
        // StringFormatSeconds; as the format() method is defined
        // static.
        // StringFormatSeconds StringFormatSeconds = new StringFormatSeconds();
        //
        // Debug timings
        // to view timings uncomment below and wrap them around
        // function calls
        //long startTime;
        //startTime = System.currentTimeMillis();
        //System.out.println("[Elapsed in " + (System.currentTimeMillis() - startTime) + "ms]");

        double seconds;
        String sfsResult;

        System.out.println("\n--- Hours, Days, Minutes test---");
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
        // This one fails in StringFormatSeconds but succeeds in
        // StringFormatSecondsHighPrecision (fails in PHP as well)
        seconds = 550.194812;
        System.out.println(
            StringFormatSeconds.format(seconds, "%o %s.%f", 6));
        // 550.194811.999999 (fails)
        seconds=550.195917;
        System.out.println(
            StringFormatSeconds.format(seconds, "%o %s.%f", 6));
        // 550.195917 550.195917.000000 (success)

    }
}
