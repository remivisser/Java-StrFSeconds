// Test class illustrating difference of precision of a Double vs a
// BigDecimal.
//
// Double value used is:
//
//     550.194812
//
// Two simple calculations are applied on it modulo and subtraction.
//
// ---
//
// While testing StringFormatSeconds for seconds=550.194812 I found
// that the Modulo division returned incorrect incorrect results.
// The modulo calculation was done using Double which has limited
// precision. (Updated modulo calculation to be handled by
// BigDecimal.)
//
// # PHP - Floating Number Precision [duplicate]
// https://stackoverflow.com/a/3726761
//
// Floating point arithmetic != real number arithmetic. An
// illustration of the difference due to imprecision is;
//
//     for some floats a and b, (a+b)-b != a.
//
// This applies to any language using floats.
//
// Since floating point are binary numbers with finite precision,
// there's a finite amount of representable numbers, which leads
// accuracy problems and surprises like this.
// ---
//

import java.math.BigDecimal;
import java.math.RoundingMode;

class TestPrecisionDoubleVsBigDecimal {
    public static void main(String[] args) {
        double seconds;
        BigDecimal secondsBD;

        seconds=550.194812d;
        secondsBD=BigDecimal.valueOf(550.194812d);

        //System.out.println(secondsBD.remainder(new BigDecimal(.000001)));


        System.out.println("\n--- Milliseconds tests ---");

        secondsBD=BigDecimal.valueOf(.001001d);
        //double dMSeconds = .001001;
        //BigDecimal bdMSeconds= new BigDecimal("0.1");

        // https://stackoverflow.com/questions/3227342/modulus-with-doubles-in-java
        // https://www.javatpoint.com/java-biginteger-divideandremainder-method
        System.out.println(secondsBD.remainder(new BigDecimal(".000001")).toPlainString());
        System.out.println(secondsBD.remainder(new BigDecimal(".000001")).compareTo(BigDecimal.ZERO)==0);
        // Success

        System.out.println(secondsBD.remainder(new BigDecimal("1")).toPlainString());
        System.out.println(secondsBD.remainder(new BigDecimal("1")).compareTo(BigDecimal.ZERO)==0);

        System.out.println(secondsBD.remainder(new BigDecimal(".001")).toPlainString());
        System.out.println(secondsBD.remainder(new BigDecimal(".001")).compareTo(BigDecimal.ZERO)==0);


        System.out.println("\n--- Modulo & Subtraction tests (550.194812) ---");

        seconds=550.194812d;
        secondsBD=BigDecimal.valueOf(550.194812d);

        System.out.println("\n---");

        System.out.println(
            "Modulo using Double: " + seconds % (double)60);
        // 10.194811999999956 (incorrect)
        System.out.println(
            "Modulo using BigDecimal: " + secondsBD.remainder(new BigDecimal(60)));
        // 10.194812 (correct)

        System.out.println(
            "Subtraction using Double: " + (seconds - (double)550));
        // 0.19481199999995624 (incorrect)
        System.out.println(
            "Subtraction using BigDecimal: " + secondsBD.subtract(new BigDecimal(550)));
        // 0.194812 (correct)


        // .000001 msec tests
        //

        System.out.println("\n--- Milliseconds test ---");

        seconds=550.194812d;
        secondsBD=BigDecimal.valueOf(550.194812d);

        System.out.println(
            "Modulo using Double: " + seconds % (double).000001);
        // 0.0000009 (incorrect)
        System.out.println(
            "Modulo using BigDecimal: " + secondsBD.remainder(new BigDecimal(".000001")));
        // 0.000000 (correct)

        System.out.println(
            "Divide using Double: " + seconds / (double).000001d);
        // 550194812 (correct)

        System.out.println(
            "Divide using BigDecimal: " + secondsBD.divide(BigDecimal.valueOf(.000001), 0, RoundingMode.FLOOR));
        // 550194812 (correct)


        // Subtraction tests
        //
        System.out.println("\n--- Test with '550.194812' ---");

        seconds=550.194812d;
        secondsBD=BigDecimal.valueOf(550.194812d);

        System.out.println( seconds - 550);
        // 0.19481199999995624 (incorrect)
        System.out.println( secondsBD.subtract( BigDecimal.valueOf(550) )) ;
        // 0.194812 (correct)


    }
}
