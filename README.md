# StringFormatSeconds - Summary

StringFormatSeconds is a simple program that converts seconds into 
logical time units. Time units are: weeks, days, hours, minutes, 
seconds, milli-and microseconds. 

This repository contains versions in the various programming 
languages. 

# Description

There are two versions for it's naming; CamelCase and snake_case. The 
examples in this README uses the snake_case version.

#### CamelCase Languages
```
StringFormatSeconds[->format]( seconds, formatString [= '%d2:%m2:%s2'], nDecimal [ = 3 ]) 
```

#### Snakecase Languages
```
strfseconds[->format]( seconds, formatstring [= '%d2:%m2:%s2'], ndecimal [ = 3 ])
```

# String Format Seconds

Return a string representing number of seconds formatted according an
explicit format string. The formatstring contains format specifiers 
controlling which time units should be shown. *(see list of all available 
format specifiers in the parameter secion)*  
  
### Time unit accumulation
All time units accumulate until their next greater time unit specified 
in the formatstring is reached. If a greater time unit is reached then 
the time unit will reset to 0 and the greater time unit will accumalate.

Example:  
Hours values range from 0 to 23 if Days are specified in formatstring; 
when the 24th hour is reached Days will accumulate with 1 and Hours is 
reset to 0.  
If Days is not specified in the formatstring, Hours value will 
accumulate to 24 and beyond.
Likewise Minutes will accumulate over 59 when Hours is not defined etc 
etc.

### Rounding
This function rounds down, decimals after ndecimal parameter are 
truncated. This is designed to enforce that a time unit will only 
accumulate when "all it's seconds have passed". 


# Parameters

## seconds | seconds
The seconds to format. Datatype is Double or Float.

## formatstring | formatString
A String containing format specifiers controlling the display of the 
seconds.

#### The following format specifiers are recognized in the formatString parameter

Format specifier | Description                          | Example returned values
:--              | :--                                  | :--
%w               | weeks                                | 0 through (max(seconds)/604800)
%d               | days                                 | 0 through 6. If weeks is not specified value may exceed 6.
%h               | hours                                | 0 through 23. If days is not specified value may exceed 23.
%m               | minutes                              | 0 through 59. If hours is not specified value may exceed 59.
%s               | seconds                              | 0 through 59. If minutes is not specified value may exceed 59.
%f               | microseconds (0.000001 second)       | Example: 7000000 for 7 seconds
%o               | the unchanged seconds value
  
  
#### Left padding format specifiers with zeroes
Format specifiers can be left padded with zeroes by adding an
integer from 1 to 9 directly after the format specifier specifying 
the padding length. 

Example: %s**3** will show 12 seconds as 012  
Example: %s**5** will show 123 seconds as 00123
  
  
## ndecimal | nDecimal 
The number of decimals applied to the smallest format specifier unit. 
Decimals are shown matching the size of nDecimal. Datatype is Integer.

Example: ndecimal=3 will show 0.5 as 0.500


# Return Values
Returns a String with all format specifiers replaced for the given 
seconds. 


# Errors
- Throws an error when seconds is less than 0.  
- Throws an error when ndecimal is less than 0.


# Examples

## Hours, Days, Minutes
```
// (180000 seconds is 50 hours)
// Show days and hours
strfseconds( 180000, "%o seconds is %d days and %h hours", 2);
// Show days only, notice ndecimal=2
strfseconds( 180000, "%o seconds is %d days", 2);
// Show hours only
strfseconds( 180000, "%o seconds is %h hours", 2);
// Skip adjecent time unit hours; show days and minutes
strfseconds( 180000, "%o seconds is %d days and %m minutes", 2);

```
```
180000 seconds is 2 days and 2.00 hours
180000 seconds is 2.08 days
180000 seconds is 50.00 hours
180000 seconds is 2 days and 120.00 minutes
```


## Stopwatch timer 
```
// Show seconds as a stopwatch
// Display decimals of seconds to level of microseconds by specifying seconds and setting ndecimal to 6
strfseconds( 1948.194812, "%o seconds is %h2:%m2:%s2", 6);
// Specify microseconds manually specifying %f 
strfseconds( 1948.194812, "%o seconds is %h2:%m2:%s2 %f", 0);
```

```
1948.194812 seconds is 00:32:28.194812
1948.194812 seconds is 00:32:28 194812
```

##  Fractions and Rounding
```
// Show seconds as minutes 
strfseconds( 90, "%o seconds is %m minutes", 2);
// Show with three decimals
strfseconds( .999999, "%o seconds is %s seconds", 3);
// Show without decimals - will show 0 seconds, rounding mode is down.
strfseconds( .999999, "%o seconds is %s seconds", 0);
        
```

```
90 seconds is 1.50 minutes
0.999999 seconds is 0.999 seconds
0.999999 seconds is 0 seconds
```

# Limitations
The default version of this program uses a double or a float and thus 
has limited precision. Because rounding mode is down it can loose a 
microsecond. Every progam language has it's addons for higher precision 
and if they are availble they will be named as follows:

```
StringFormatSecondsHighPrecision[->format]( seconds, formatString [= '%d2:%m2:%s2'], nDecimal [ = 3 ]) 
```
```
strfsecondshp[->format]( seconds, formatstring [= '%d2:%m2:%s2'], ndecimal [ = 3 ])
```

---

*This is a Java version of the Python package
[Python-strfseconds](https://github.com/remivisser/Python-strfseconds)*
