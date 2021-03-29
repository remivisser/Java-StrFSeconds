# String Format Seconds - Summary

StringFormatSeconds is a simple Java Program converting seconds into 
logical time units. Available time units are: weeks, days, hours, 
minutes, seconds and microseconds. It can be used to display seconds in a
customizable way or it can be used in calculations. 

# String Format Seconds

Return a string representing number of seconds formatted according an
explicit format string. The formatString contains format specifiers 
controlling which time units should be shown. (see list of all available 
format specifiers below)  
  
All time units accumulate until their next greater time unit that is 
specified in the formatstring is found. If a greater time unit is 
reached then the time unit will reset to 0 and the greater time unit 
will accumalate.

Example:  
Hours values are 0 through 23 if Days are specified in formatString;
When 24th hour is reached Days will accumulate with 1 and Hours is 
reset to 0.  
If Days is not specified in the formatString, Hours value will 
accumulate to 24 and beyond.

Likewise Minutes will accumulate over 59 when hours is not defined etc 
etc.

This function rounds down, decimals after nDecimal are truncated. This 
is designed to enforce that a time unit will only accumulate when "all 
it's seconds have passed".

# Description
```
StringFormatSeconds->format(double seconds, String formatString, int nDecimal) :
String|IllegalArgumentException
```

# Parameters

## seconds
The seconds to format. Datatype is a double.

## formatString
A String containing format specifiers controlling the display of the 
seconds.

**The following format specifiers are recognized in the formatString parameter**

Format specifier | Description                          | Example returned values
:--              | :--                                  | :--
%w               | weeks                                | 0 through (max(seconds)/604800)
%d               | days                                 | 0 through 6. If weeks is not specified value may exceed 6.
%h               | hours                                | 0 through 23. If days is not specified value may exceed 23.
%m               | minutes                              | 0 through 59. If hours is not specified value may exceed 59.
%s               | seconds                              | 0 through 59. If minutes is not specified value may exceed 59.
%f               | microseconds (0.000001 second)       | Example: 7000000 for 7 seconds
%o               | the unchanged seconds value
  
  
**Left padding format specifiers with zeroes**  
Format specifiers can be left padded with zeroes by adding an
integer from 1 to 9 directly after the format specifier. 

Example: %s**3** will show 12 seconds as 012  
Example: %s**5** will show 123 seconds as 00123
  
  
## nDecimal
The number of decimals applied to the smallest format specifier unit.  
Decimals are shown matching the size of nDecimal.

Example: nDecimal=3 will show 0.5 seconds as 0.500


# Return Values
Returns a String with all format specifiers replaced for the given 
seconds. 


# Errors
Throws IllegalArgumentException when seconds is less than 0.  
Throws IllegalArgumentException when nDecimal is less than 0.


# Examples

## Hours, Days, Minutes
```
        // Define seconds 50 hours
        seconds=(60*60*50);
        
        // Show days and minutes
        sfsResult = StringFormatSeconds.format(seconds, "%o seconds is %d days and %h hours", 2);

180000 seconds is 2 days and 2.00 hours

        // Show days only, notice nDecimal=2
        sfsResult = StringFormatSeconds.format(seconds, "%o seconds is %d days", 2);

180000 seconds is 2.08 days

        // Show hours only
        sfsResult = StringFormatSeconds.format(seconds, "%o seconds is %h hours", 2);

180000 seconds is 50.00 hours

        // Skip adjecent time unit hours; show days and minutes
        sfsResult = StringFormatSeconds.format(seconds, "%o seconds is %d days and %m minutes", 2);

180000 seconds is 2 days and 120.00 minutes

```

## Stopwatch timer 
```
        // Show seconds as a stopwatch
        seconds=1948.194812;
        // Display microseconds by specifying seconds and setting nDecimal to 6
        sfsResult = StringFormatSeconds.format(seconds, "%o seconds is %h2:%m2:%s2", 6);

1948.194812 seconds is 00:32:28.194812

        // Specify microseconds manually using %f 
        sfsResult = StringFormatSeconds.format(seconds, "%o seconds is %h2:%m2:%s2.%f", 0);

1948.194812 seconds is 00:32:28.194812
```

##  Fractions and Rounding
```
        seconds=90;
        // Show seconds as minutes 
        sfsResult = StringFormatSeconds.format(seconds, "%o seconds is %m minutes", 2);

90 seconds is 1.50 minutes

        seconds=.999999;
        // Show with three decimals
        sfsResult = StringFormatSeconds.format(seconds, "%o seconds is %s seconds", 3);

0.999999 seconds is 0.999 seconds

        // Show without decimals - will show 0 seconds, rounding mode is down
        sfsResult = StringFormatSeconds.format(seconds, "%o seconds is %s seconds", 0);
        
0.999999 seconds is 0 seconds
```

---

*This is a Java version of the Python package
[Python-strfseconds](https://github.com/remivisser/Python-strfseconds)*
