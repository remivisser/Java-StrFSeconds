# String Format Seconds - Summary

StringFormatSeconds is a simple Java Program converting seconds into 
logical time units being: weeks, days, hours, minutes milli-and 
microseconds. It can be used to display seconds in a user friendly way.

# String Format Seconds

Return a string representing number of seconds formatted according an
explicit format string. Format string specifies the time units to 
display.  
Time untis are weeks, days, hours, minutes, seconds, milliseconds 
and microseconds. All time units will fill up until their adjecent  
(greater) time unit is reached.  
For example: Hours may accumulate over 23 if days are not specified in 
the formatString. Minutes may accumulate over 59 if hours are not 
specified in the formatString.
This function rounds down. A time unit will only accumulate when "all 
it's seconds have passed".

## Description
---
```
StringFormatSeconds->format(double seconds, String formatString, int nDecimal) :
String|IllegalArgumentException
```

## Parameters
---

### seconds
The seconds to format. 

**Seconds max size**  
- In `StringFormatSeconds` seconds is a (double) and the max size is 
Integer.MAX_VALUE=2147483647. This equals approximately 68 years.
- In `StringFormatSecondsHighPrecision` seconds is a BigDecimal and the 
max size is the same as BigDecimal.

### formatString
**The following characters are recognized in the formatString parameter**

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
  
  
  
**Left padding format specifiers**
Format specifiers can be left padded with zeroes by adding an
integer from 1 to 9 directly after the format specifier:

Example: %s3 will show 12 seconds as 012  
Example: %s5 will show 123 seconds as 00123
  
  
### nDecimal
The number of decimals applied to the smallest format specifier unit.  
Decimals are shown matching the size of nDecimal.

Example: nDecimal=3 will show 0.5 seconds as 0.500


## Return Values
---
Returns a string formatted according formatString using the given 
seconds. 


## Errors
---
Throws IllegalArgumentException when seconds is smaller than 0.  
Throws IllegalArgumentException when nDecimal is smaller than 0.



# Python
This is a Java version of the Python package
[Python-strfseconds](https://github.com/remivisser/Python-strfseconds)
