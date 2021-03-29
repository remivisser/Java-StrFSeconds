# String Format Seconds - Summary

StringFormatSeconds is a simple Java Program converting seconds into 
logical time units being: weeks, days, hours, minutes, milli-and 
microseconds. It can be used to display seconds in a user friendly way.

# String Format Seconds

Return a string representing number of seconds formatted according an
explicit format string. The formatString contains format specifiers 
controlling which time units should be shown. (see list of format 
specifiers below)
Time untis are weeks, days, hours, minutes, seconds, milli- and 
microseconds. All time units will fill up until their adjecent  
(greater) time unit is reached.  
For example: Hours may accumulate over 23 if Days are not specified in 
the formatString. Likewise Minutes may accumulate over 59 if Hours are 
not specified in the formatString.
This function rounds down. A time unit will only accumulate when "all 
it's seconds have passed".

## Description
```
StringFormatSeconds->format(double seconds, String formatString, int nDecimal) :
String|IllegalArgumentException
```

## Parameters

### seconds
The seconds to format. 

### formatString
A String containing format specifiers controlling the display of the 
seconds.

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
String formatStringResult;
seconds=4000.123456;

formatStringResult = format(seconds, "Days=%d7 %h2:%m2:%s2.%f", 0);
System.out.println(formatStringResult);
```



# Python
This is a Java version of the Python package
[Python-strfseconds](https://github.com/remivisser/Python-strfseconds)
