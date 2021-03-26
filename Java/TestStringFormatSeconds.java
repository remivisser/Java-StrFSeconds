class TestStringFormatSeconds {
    public static void main(String[] args) {

        long startTime = System.currentTimeMillis();

        // Not needed to initiate new class of StringFormatSeconds;
        // format method is defined static.
        // StringFormatSeconds StringFormatSeconds = new StringFormatSeconds();

        double seconds;
        String formatStringResult;
        //seconds=90;
        //seconds=1;
        //seconds=4000;
        seconds=4000.1234567890;
        seconds=2147483647;

        startTime = System.currentTimeMillis();
        formatStringResult = StringFormatSeconds.format(1, "%h:%m:%s", 10);
        System.out.println(formatStringResult);
        System.out.println("[Elapsed in " + (System.currentTimeMillis() - startTime) + "ms]");

        startTime = System.currentTimeMillis();
        formatStringResult = StringFormatSeconds.format(seconds, "%h:%m:%s", 10);
        System.out.println(formatStringResult);
        System.out.println("[Elapsed in " + (System.currentTimeMillis() - startTime) + "ms]");

        startTime = System.currentTimeMillis();
        formatStringResult = StringFormatSeconds.format(seconds, "%f");
        System.out.println(formatStringResult);
        System.out.println("[Elapsed in " + (System.currentTimeMillis() - startTime) + "ms]");

        startTime = System.currentTimeMillis();
        formatStringResult = StringFormatSeconds.format(seconds, "Days=%d %h2:%m2:%s2", 0);
        System.out.println(formatStringResult);
        System.out.println("[Elapsed in " + (System.currentTimeMillis() - startTime) + "ms]");

    }
}
