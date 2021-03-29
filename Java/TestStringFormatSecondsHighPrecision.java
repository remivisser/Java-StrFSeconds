import java.math.BigDecimal;


class TestStringFormatSecondsHighPrecision {
    public static void main(String[] args) {
        // StringFormatSecondsHighPrecision StringFormatSecondsHighPrecision = new StringFormatSecondsHighPrecision();
        // It is not not needed to initiate a new class of
        // StringFormatSecondsHighPrecision; as the format() method is
        // defined static.

        BigDecimal seconds;
        String sfsResult;


        seconds=BigDecimal.valueOf(1948.194812);
        sfsResult = StringFormatSecondsHighPrecision.format(seconds, "%o seconds is %h2:%m2:%s2.%l%f", 0);
        System.out.println(sfsResult);
        sfsResult = StringFormatSecondsHighPrecision.format(seconds, "%o seconds is %h2:%m2:%s2.%f", 0);
        System.out.println(sfsResult);


        seconds=BigDecimal.valueOf(550.194812);
        sfsResult = StringFormatSecondsHighPrecision.format(seconds, "%o seconds is %h hours and %s seconds", 2);
        System.out.println(sfsResult);


        seconds=BigDecimal.valueOf(90);
        sfsResult = StringFormatSecondsHighPrecision.format(seconds, "%o seconds is %h hours and %s seconds", 2);
        System.out.println(sfsResult);

        seconds = BigDecimal.valueOf(4000.1234567890);
        sfsResult = StringFormatSecondsHighPrecision.format(seconds, "%o seconds is Days=%d %h2:%m2:%s2 milliseconds=%l microseconds=%f", 6);
        System.out.println(sfsResult);

        seconds = BigDecimal.valueOf(2147483647);
        sfsResult = StringFormatSecondsHighPrecision.format(seconds, "%o seconds is Weeks=%w, Days=%d %h2:%m2:%s2", 3);
        System.out.println(sfsResult);

        seconds = BigDecimal.valueOf(2147483647);
        sfsResult = StringFormatSecondsHighPrecision.format(seconds, "%o seconds is %f microseconds", 3);
        System.out.println(sfsResult);

        seconds = BigDecimal.valueOf(2147483647000.1234567890d);
        sfsResult = StringFormatSecondsHighPrecision.format(seconds, "%o seconds is %f microseconds", 3);
        System.out.println(sfsResult);

        seconds = BigDecimal.valueOf(0.0000000001234567890d);
        sfsResult = StringFormatSecondsHighPrecision.format(seconds, "%o seconds is %f microseconds", 9);
        System.out.println(sfsResult);

        seconds = BigDecimal.valueOf(4000000000d);
        sfsResult = StringFormatSecondsHighPrecision.format(seconds, "%o is %f microseconds", 0);
        System.out.println(sfsResult);

    }
}
