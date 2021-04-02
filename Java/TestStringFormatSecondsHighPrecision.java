import java.math.BigDecimal;


class TestStringFormatSecondsHighPrecision {
    public static void main(String[] args) {

        // For Hours, Days, Minutes etc tests see TestStringFormatSeconds.
        //
        // This class does tests on accuracy of StringFormatSecondsHighPrecision


        System.out.println("--- Small units ---");

        System.out.println(
            StringFormatSecondsHighPrecision.format(BigDecimal.valueOf(.001001d),
                "%o seconds is %s seconds, %l milliseconds and %f microseconds", 6));
        // Small units - pass Double
        System.out.println(
            StringFormatSecondsHighPrecision.format(.001001d,
                "%o seconds is %s seconds, %l milliseconds and %f microseconds", 6));


        System.out.println("--- Doubles that lost accurancy ---");
        // Known 'trouble child' 550.194812
        System.out.println(
            StringFormatSecondsHighPrecision.format(BigDecimal.valueOf(550.194812d), "Passed as BigDecimal: %o=%s.%f", 6));
        System.out.println(
            StringFormatSecondsHighPrecision.format(550.194812d, "Passed as Double: %o=%s.%f", 6));


        System.out.println("--- Large numbers ---");
        System.out.println(
            StringFormatSecondsHighPrecision.format(BigDecimal.valueOf(4000000000d), "%o %f", 6));

        // Do not use BigDecimal.valueOf(Double) if value cannot be
        // correctly enough presented by that Double...
        //System.out.println(
        //    StringFormatSecondsHighPrecision.format(BigDecimal.valueOf(2147483647000.123456789d), "%o %w weeks, %d days %h:%m:%s.%f", 6));
        // Fails
        System.out.println(
            StringFormatSecondsHighPrecision.format(new BigDecimal("2147483647000.123456789"), "%o %w weeks, %d days %h:%m:%s.%f", 6));
        // Succeeds

        System.out.println(
            StringFormatSecondsHighPrecision.format(new BigDecimal("2147483647000.123456789000000000123"), "%o %w weeks, %d days %h:%m:%s.%f", 20));
        // Succeeds

        // Small numbers
        System.out.println(
            StringFormatSecondsHighPrecision.format(new BigDecimal(".000000000123456789"), "%o %f", 12));

        System.out.println(
            StringFormatSecondsHighPrecision.format(new BigDecimal(".0000000000000000000123456789"), "%o %f", 22));

    }
}
