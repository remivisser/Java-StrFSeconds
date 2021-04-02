# StringFormatSeconds - Summary

StringFormatSeconds is a simple Java program converting seconds into 
logical time units being weeks, days, hours, minutes, seconds, 
milli-and microseconds. For a full description including examples and 
limitations view the [README.md](https://github.com/remivisser/strfseconds/blob/main/README.md) 
of this parent directory.

# Description

There are two classes;
- The default class - StringFormatSeconds in StringFormatSeconds.java. This class uses a Double.
- A 'High Precision' class - StringFormatSecondsHighPrecision in 
StringFormatSecondsHighPrecision.java. This class uses a BigDecimal. 

## StringFormatSeconds
```
// StringFormatSeconds.java
public class StringFormatSeconds
|_ public static String format(double seconds, String formatString, int nDecimal)
// Default parameters
|_ public static String format(double seconds, String formatString) // nDecimal=3 
|_ public static String format(double seconds) // formatString="%h2:%m2:%s", nDecimal=3 
```

## StringFormatSecondsHighPrecision
```
// StringFormatSecondsHighPrecision.java
public class StringFormatSecondsHighPrecision
|_ public static String format(BigDecimal seconds, String formatString, int nDecimal)
// Default parameters
|_ public static String format(double seconds, String formatString) // nDecimal=3 
|_ public static String format(double seconds) // formatString="%h2:%m2:%s", nDecimal=3 

// Overload for Double, allows seconds to be passed as a Double 
|_ public static String format(double seconds, String formatString, int nDecimal)
// Default parameters for Double version 
|_ public static String format(double seconds, String formatString) // nDecimal=3 
|_ public static String format(double seconds) // formatString="%h2:%m2:%s", nDecimal=3 
```

## Test class TestStringFormatSeconds
```
// TestStringFormatSeconds.java
public class TestStringFormatSeconds
|_ private static void main(String[] args)
```

## Test class TestStringFormatSecondsHighPrecision
```
// TestStringFormatSecondsHighPrecision.java
public class TestStringFormatSecondsHighPrecision
|_ public static void main(String[] args)
```

## Test class Tests/TestPrecisionDoubleVsBigDecimal
Class with math tests demonstrating the decimal precision accurancy of 
a Double versus a BigDecimal.
```
// ./Tests/TestPrecisionDoubleVsBigDecimal.java
public class TestPrecisionDoubleVsBigDecimal
// Various calculations on a Double and a BigDecimal
|_ public static void main(String[] args)
// Test on .001001 second
|_ public static void milliMicroSecondTest
```
