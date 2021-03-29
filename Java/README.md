# String Format Seconds - Summary

StringFormatSeconds is a simple Java Program converting seconds into 
logical time units. Available time units are: weeks, days, hours, 
minutes, milli-and microseconds. It can be used to display seconds in a
user friendly way.

# String Format Seconds

Return a string representing number of seconds formatted according an
explicit format string. The formatString contains format specifiers 
controlling which time units should be shown. (see list of format 
specifiers below)
Time units are weeks, days, hours, minutes, seconds, milli- and 
microseconds. 
All time units accumulate until their next greater time unit that is 
specified in the formatstring is found. If a greater time unit is 
reached then the time unit will reset to 0 and the greater time will 
accumalate.

Example:  
Hours values are 0 through 23 if Days are specified in formatString;
When 24th hour is reached Days will accumulate with 1 and Hours is 
reset to 0.  
If Days is not specified in the formatString, hours value will 
accumulate to 24 and beyond.

Likewise Minutes will accumulate over 59 when hours is not defined etc 
etc.

This function rounds down, decimals after nDecimal are truncated. This 
is designed to enforce that a time unit will only accumulate when "all 
it's seconds have passed".

## Description
```
StringFormatSeconds->format(double seconds, String formatString, int nDecimal) :
String|IllegalArgumentException
```

## Parameters

### seconds
The seconds to format. Datatype is a double.

### formatString
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
%l               | milliseconds (0.001 second)          | Example: 3000 for 3 seconds
%f               | microseconds (0.000001 second)       | Example: 7000000 for 7 seconds
%o               | the unchanged seconds value
  
  
**Left padding format specifiers with zeroes**
Format specifiers can be left padded with zeroes by adding an
integer from 1 to 9 directly after the format specifier:

Example: %s**3** will show 12 seconds as 012  
Example: %s**5** will show 123 seconds as 00123
  
  
### nDecimal
The number of decimals applied to the smallest format specifier unit.  
Decimals are shown matching the size of nDecimal.

Example: nDecimal=3 will show 0.5 seconds as 0.500


## Return Values
Returns a String with all format specifiers replaced for the given 
seconds. 


## Errors
Throws IllegalArgumentException when seconds is less than 0.  
Throws IllegalArgumentException when nDecimal is less than 0.


## Examples
```
    // Show seconds in minutes. Two decimals
    double seconds=90;
    String formatStringResult = StringFormatSeconds.format(seconds, "%o seconds is %m minutes", 2);
    System.out.println(formatStringResult);

90 seconds is 1.50 minutes
```
```
    seconds = 4000.1234567890;
    formatStringResult = StringFormatSeconds.format(seconds, "%o seconds is Days=%d %h2:%m2:%s2 milliseconds=%l microseconds=%f", 6);
    System.out.println(formatStringResult);

4000.123456789 seconds is Days=0 01:06:40 milliseconds=123 microseconds=456.789000
```
```
    seconds = 2147483647;
    formatStringResult = StringFormatSeconds.format(seconds, "%o seconds is Weeks=%w, Days=%d %h2:%m2:%s2", 3);
    System.out.println(formatStringResult);

2.147483647E9 seconds is Weeks=3550, Days=5 03:14:07.000
```

```
# Python
This is a Java version of the Python package
[Python-strfseconds](https://github.com/remivisser/Python-strfseconds)
