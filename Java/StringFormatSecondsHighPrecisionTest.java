class StringFormatSecondsHighPrecisionTest {
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

        formatStringResult = StringFormatSecondsHighPrecision.format(seconds, "%h:%m:%s", 10);
        System.out.println(formatStringResult);

        formatStringResult = StringFormatSecondsHighPrecision.format(seconds, "%f");
        System.out.println(formatStringResult);

        long elapsedTime = System.currentTimeMillis() - startTime;
        System.out.println("[Elapsed in " + elapsedTime + "ms]");
    }
}
