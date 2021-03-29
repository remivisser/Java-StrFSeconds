import java.math.BigDecimal;

class TestPrecisionBigDecimalvsDouble {
    public static void main(String[] args) {
        double seconds;
        BigDecimal secondsBD;

        // Modulo division in StringFormatSeconds returned incorrect
        // results for '550.194812'
        // Test below reproduces it. (limitation of precision double)
        // Updated module calculation to be handled by BigDecimal.
        seconds=550.194812;
        secondsBD=BigDecimal.valueOf(550.194812);

        System.out.println("Modulo using BigDecimal: " + secondsBD.remainder(new BigDecimal(60)));
        System.out.println("Modulo using Double: " + seconds % 60);
    }
}
