import java.math.BigDecimal;


class TestStringFormatSecondsHighPrecision {
    public static void main(String[] args) {

        StringFormatSecondsHighPrecision sfsHighPrecision = new StringFormatSecondsHighPrecision();

        // Small units
        System.out.println(sfsHighPrecision.format(new BigDecimal(".001001"), "%o seconds is %s seconds, %l milliseconds and %f microseconds", 6));

        // Known trouble child
        System.out.println(sfsHighPrecision.format(new BigDecimal("550.194812"), "%o %s.%f", 6));

        System.out.println(sfsHighPrecision.format(new BigDecimal("1948.194812"), "%o %s.%f", 6));

        // Large numbers
        System.out.println(sfsHighPrecision.format(new BigDecimal("4000000000"), "%o %f", 6));

        // System.out.println(sfsHighPrecision.format(BigDecimal.valueOf(2147483647000.1234567890), "%o %f", 6));
        // Fails
        // Use new BigDecimal("Double") (with quotes!)
        System.out.println(sfsHighPrecision.format(new BigDecimal("2147483647000.1234567890"), "%o %f", 6));

        // Small numbers
        System.out.println(sfsHighPrecision.format(new BigDecimal(".000000000123456789"), "%o %f", 12));

    }
}
