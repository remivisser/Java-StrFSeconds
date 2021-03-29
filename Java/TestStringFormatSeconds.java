import java.math.BigDecimal;

class TestStringFormatSeconds {
    public static void main(String[] args) {
        // It is not not needed to initiate a new class of
        // StringFormatSeconds; as the format() method is defined
        // static.
        // StringFormatSeconds StringFormatSeconds = new StringFormatSeconds();

        double seconds;
        BigDecimal secondsBD;
        String sfsResult;

        // Max size seconds is max size Double which is a lot;
        // approx. 1.7E308
        // Make sure to append d to prevent error: integer number too
        // large (default is Integer)
        // https://stackoverflow.com/q/4331200
        //System.out.println(Double.MAX_VALUE);
        //System.out.println(new BigDecimal(Double.MAX_VALUE).toPlainString());

        // debug timings
        // to view timings uncomment below and wrap them
        // around function calls
        //long startTime;;
        //startTime = System.currentTimeMillis();
        //System.out.println("[Elapsed in " + (System.currentTimeMillis() - startTime) + "ms]");

        System.out.println("Hours, Days, Minutes test\n---");
        // Define seconds 50 hours
        seconds=(60*60*50);
        // Show days and minutes
        sfsResult = StringFormatSeconds.format(seconds, "%o seconds is %d days and %h hours", 2);
        System.out.println(sfsResult);
        // Show days only, notice nDecimal=2
        sfsResult = StringFormatSeconds.format(seconds, "%o seconds is %d days", 2);
        System.out.println(sfsResult);
        // Show hours only
        sfsResult = StringFormatSeconds.format(seconds, "%o seconds is %h hours", 2);
        System.out.println(sfsResult);
        // Skip adjecent time unit hours; show days and minutes
        sfsResult = StringFormatSeconds.format(seconds, "%o seconds is %d days and %m minutes", 2);
        System.out.println(sfsResult);


        System.out.println("Stopwatch timer test\n---");
        // Show seconds as a stopwatch
        seconds=1948.194812;
        // Display microseconds by specifying seconds and setting nDecimal to 6
        sfsResult = StringFormatSeconds.format(seconds, "%o seconds is %h2:%m2:%s2", 6);
        System.out.println(sfsResult);
        // Specify microseconds manually using %f
        sfsResult = StringFormatSeconds.format(seconds, "%o seconds is %h2:%m2:%s2.%f", 0);
        System.out.println(sfsResult);


        System.out.println("Fractions and Rounding test\n---");
        seconds=90;
        // Show seconds as minutes
        sfsResult = StringFormatSeconds.format(seconds, "%o seconds is %m minutes", 2);
        System.out.println(sfsResult);

        // 0.99999 seconds
        seconds=.999999;
        // Show with three decimals
        sfsResult = StringFormatSeconds.format(seconds, "%o seconds is %s seconds", 3);
        System.out.println(sfsResult);
        // Show without decimals - will show 0 seconds, rounding mode is down
        sfsResult = StringFormatSeconds.format(seconds, "%o seconds is %s seconds", 0);
        System.out.println(sfsResult);



        System.out.println("Fractions (modulo) test\n---");
        // Modulo
        // Test below failed in earlier version, changed modulo
        // into using BigDecimal
        seconds=550.194812;
        sfsResult = StringFormatSeconds.format(seconds, "%o seconds is %h2:%m2:%s2", 6);
        System.out.println(sfsResult);

        secondsBD=BigDecimal.valueOf(1948.194812);
        System.out.println("Modulo using BigDecimal: " + secondsBD.remainder(new BigDecimal(60)));
        System.out.println("Modulo using Double: " + 1948.194812 % 60);


    }
}
