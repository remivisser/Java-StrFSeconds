// Test class illustrating difference of precision of a Double vs a
// BigDecimal.
// A number of calculations are done; subtraction, division and
// remainder, they are done first using Double and then using
// BigDecimal.
//
// While testing StringFormatSeconds for seconds=550.194812 I found
// that the Modulo division returned incorrect incorrect results.
// It turns out that the modulo calculation being done using Double
// has limited precision.
//
//     550.194812
//
// BigDecimal:
// ---
// !!! Make sure to use `BigDecimal.valueOf(1)` or
// `new BigDecimal()`. Make sure to use `new BigDecimal` with double
// quotes !!!
//
// >>> System.out.println(new BigDecimal(1).remainder(new BigDecimal(.000001d)));
// 4.5
// >>> System.out.println(new BigDecimal(1).remainder(new BigDecimal(".000001")));
// 0
// >>> System.out.println(BigDecimal.valueOf(1).remainder(new BigDecimal(.000001d)));
// 4.5
// >>> System.out.println(BigDecimal.valueOf(1).remainder(new BigDecimal(".000001")));
// 0
//
// When testing extremely large numbers:
// ---
// For large numbers use `new BigDecimal` to not be hit by limitation
// of Double. In other words, do not use `BigDecimal.valueOf(Double)`
// if the value passed to valueOf() cannot be correctly enough
// presented by a Double...
//
// See test below:
// >>> System.out.println(
// >>>    StringFormatSecondsHighPrecision.format(BigDecimal.valueOf(2147483647000.123456789d), "%o %w weeks, %d days %h:%m:%s.%f", 6));
// Fails
// >>> System.out.println(
// >>>    StringFormatSecondsHighPrecision.format(new BigDecimal("2147483647000.123456789"), "%o %w weeks, %d days %h:%m:%s.%f", 6));
// Succeeds


import java.math.BigDecimal;
import java.math.RoundingMode;

class TestPrecisionDoubleVsBigDecimal {
    public static void main(String[] args) {
        double seconds;
        BigDecimal secondsBD;

        seconds=550.194812d;
        secondsBD=BigDecimal.valueOf(550.194812d);

        // Doubles below look like to be a bit more accurate;
        // (same as in PHP);
        // .001 & .000001 divisions are exact matches.
        //
        seconds=550.195917d;
        secondsBD=BigDecimal.valueOf(550.195917d);
        seconds=550.195918d;
        secondsBD=BigDecimal.valueOf(550.195918d);

        // Subtraction tests
        //
        System.out.println("--- Substraction test (550.194812-550) ---");

        System.out.println(seconds + " - " + (int)seconds + " (Double): " + (seconds - (int)seconds) );
        //System.out.println(seconds + " - " + (int)seconds + " (BigDecimal): " + secondsBD.subtract(BigDecimal.valueOf((int)seconds))) ;
        System.out.println(seconds + " - " + (int)seconds + " (BigDecimal): " + secondsBD.subtract(new BigDecimal((int)seconds))) ;


        // Modulo tests from 1 (second) to .000001 (msecs)
        //
        System.out.println("\n--- Modulo tests from 1 to .000001 (msecs) ---");

        System.out.println(seconds + " % 1 (Double): " + seconds % 1d);
        //System.out.println(seconds + " % 1 (BigDecimal): " + secondsBD.remainder(BigDecimal.valueOf(1d)));
        System.out.println(seconds + " % 1 (BigDecimal): " + secondsBD.remainder(new BigDecimal("1")));

        System.out.println(seconds + " % .001 (Double): " + seconds % .001d);
        //System.out.println(seconds + " % .001 (BigDecimal): " + secondsBD.remainder(BigDecimal.valueOf(.001d)));
        System.out.println(seconds + " % .001 (BigDecimal): " + secondsBD.remainder(new BigDecimal(".001")));

        System.out.println(seconds + " % .000001 (Double): " + seconds % .000001d);
        // Notice different value for 0 (0.000000 & 0.0000007) when
        // using BigDecimal.valueOf & new BigDecimal()
        //System.out.println(seconds + " % .000001 (BigDecimal): " + secondsBD.remainder(BigDecimal.valueOf(.000001d)));
        // 0E-7 (correct)
        System.out.println(seconds + " % .000001 (BigDecimal): " + secondsBD.remainder(new BigDecimal(".000001")));

        //System.out.println(secondsBD.remainder(new BigDecimal(".000001")).compareTo(BigDecimal.ZERO)==0);
        //System.out.println(seconds + " % .000001 (BigDecimal (new BigDecimal()): " + secondsBD.remainder(new BigDecimal(".000001")));


        // Divide tests from 1 (second) to .000001 (msecs)
        //
        System.out.println("\n--- Divide tests from 1 to .000001 (msecs) ---");

        System.out.println(seconds + " / 1 (Double): " + seconds / 1d);
        System.out.println(seconds + " / 1 (BigDecimal): " + secondsBD.divide(new BigDecimal("1")));

        System.out.println(seconds + " / .001 (Double): " + seconds / .001d);
        System.out.println(seconds + " / .001 (BigDecimal): " + secondsBD.divide(new BigDecimal(".001")));

        System.out.println(seconds + " / .000001 (Double): " + seconds / .000001d);
        System.out.println(seconds + " / .000001 (BigDecimal): " + secondsBD.divide(new BigDecimal(".000001")));

        // Uncomment to display results for .001001 milli-,
        // microsecond test.
        // milliMicroSecondTest();

    }

    // .001001 tests on one milliseconds & one microseconds
    //
    private static void milliMicroSecondTest() {

        double seconds = .001001d;
        BigDecimal secondsBD = BigDecimal.valueOf(.001001d);

        System.out.println("\n--- One milliseconds & one microseconds test (.001001 seconds) ---");

        // https://stackoverflow.com/questions/3227342/modulus-with-doubles-in-java
        // https://www.javatpoint.com/java-biginteger-divideandremainder-method
        System.out.println(seconds + " % .000001 (Double): " + seconds % .000001d);
        // 0.0000009 (incorrect)
        System.out.println(seconds + " % .000001 (BigDecimal): " + secondsBD.remainder(new BigDecimal(".000001")));
        // 0.000000 (correct)
        // System.out.println(secondsBD.remainder(new BigDecimal(".000001")).compareTo(BigDecimal.ZERO)==0);

        System.out.println(seconds + " % .001 (Double): " + seconds % .001d );
        // 0.0000009 (incorrect)
        System.out.println(seconds + " % .001 (BigDecimal): " + secondsBD.remainder(new BigDecimal(".001")));
        // .000001 (correct)
        // System.out.println(secondsBD.remainder(new BigDecimal(".001")).compareTo(BigDecimal.ZERO)==0);

    }
}
