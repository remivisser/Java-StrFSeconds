# String Format Seconds

String Format Seconds is a simple program that converts seconds into 
logical timeunits. Timeunits are: weeks, days, hours, minutes, seconds, 
milli-and microseconds. 

This repository contains versions in various programming 
languages. 

# Description

Depending on the programming language the program name is in Camel case 
or in Snake case:

**Camel case**
```
StringFormatSeconds[->format]( seconds, formatString [ = '%d2:%m2:%s2'], nDecimal [ = 3 ]) 
```

**Snake case** 
```
strfseconds[->format]( seconds, formatstring [ = '%d2:%m2:%s2'], ndecimal [ = 3 ])
```
*The examples in this README use the Snake case variant.*

# String Format Seconds

Return a string representing `seconds` formatted according to an explicit 
format string. The parameter `formatstring` controls which and how 
timeunits should be shown. *(the parameter section contains a list of 
all available timeunit specifiers)*

## Timeunit accumulation
All timeunits accumulate until their next greater timeunit specified 
in the `formatstring` is reached. If a greater timeunit is reached then 
the timeunit will reset to 0 and the greater timeunit will accumulate.

*Example:*  
Hours value ranges from 0 to 23 if Days are specified in `formatstring`; 
when the 24th hour is reached Days will accumulate with 1 and Hours is 
reset to 0. If Days is *not* specified in the `formatstring` Hours 
value will accumulate to 24 and beyond.  
Likewise Minutes will accumulate over 59 when Hours is not specified, 
Seconds will accumulate over 59 when Minutes is not specified, etc.

## Timeunit rounding
This function rounds down. Decimals of the smallest timeunit are 
rounded down after the `ndecimal` parameter. This is designed to 
enforce that a timeunit will only accumulate when "all it's seconds 
have passed". 
  
# Quick start
```
strfseconds( 98412.194812, '%o seconds is %h hours, %m minutes, %s seconds, %l milliseconds and %f microseconds', 0 )

// Display as clock, notice ndecimal=6 to show fractions of seconds until ms
strfseconds( 98412.194812, '%h2:%m2:%s2', 6 )

// Add days, notice hours value 'decrease/reset'
strfseconds( 98412.194812, '%d Day(s) %h2:%m2:%s2', 6 )

// Show only days
strfseconds( 98412.194812, '%d Day(s)', 6 )
```
```
98412.194812 seconds is 27 hours, 20 minutes, 12 seconds, 194 milliseconds and 812 microseconds
27:20:12.194812
1 Day(s) 03:20:12.194812
1.139030 Day(s)
```

# Parameters

## seconds | seconds
A double or float containing the seconds to format. 

## formatstring | formatString
A string containing timeunit specifiers controlling the display of 
`seconds`. If `formatstring` is omitted it defaults to 
`%d2:%m2:%s2`.

### Timeunit specifiers
Timeunits are displayed using the following timeunit specifiers:

Timeunit Specifier | Description                 | Example returned values
:--                | :--                                  | :--
%w                 | weeks                                | 0 through (max(seconds)/604800)
%d                 | days                                 | 0 through 6. If weeks is not specified value may exceed 6
%h                 | hours                                | 0 through 23. If days is not specified value may exceed 23
%m                 | minutes                              | 0 through 59. If hours is not specified value may exceed 59
%s                 | seconds                              | 0 through 59. If minutes is not specified value may exceed 59
%l                 | milliseconds (0.001 second)          | 0 through 999. If seconds is not specified value may exceed 999
%f                 | microseconds (0.000001 second)       | 0 through 999999. If seconds is not specified value may exceed 999999
%o                 | the unchanged seconds value

*Examples: (ndecimal set to 2)*  
```
strfseconds( 3601, '%h:%m:%s', 2 )
strfseconds( 3601, '%d', 2 )
```
```
1:0:1.00
0.04
```  
### Left padding timeunits
Timeunits can be left padded with zeroes by adding a number from 1 
to 9 directly after the timeunit specifier, eg like `%s2`. The integer 
specifies the padding length. 

*Examples:*  
```
// specify padding length of 3 on seconds 
strfseconds( 12, '%s3', 0 )
// specify padding length of 5 on minutes, 2 on seconds
strfseconds( 62, '%m5:%s2', 0 )
```
```
012
00001:02
```

## ndecimal | nDecimal 
A positive integer containing the number of decimals applied to the 
smallest timeunit. Decimals are shown matching the size of `ndecimal`. 
If `ndecimal` is omitted it defaults to `3`.

*Examples:*  
```
strfseconds( 0.5, '%s', 3 )
strfseconds( 0.999, '%s', 2 )
strfseconds( 0.999, '%s', 0 )
```
```
0.500
0.99
0
```
# Return Values
Returns a string with all timeunit specifiers replaced for the given 
seconds. 

# Errors
- Throws an error when seconds is less than 0.  
- Throws an error when ndecimal is less than 0.

# Examples

## Hours, Days, Minutes
```
// (180000 seconds is 50 hours)
// Show days and hours
strfseconds( 180000, '%o seconds is %d days and %h hours', 2 )
// Show days only, notice ndecimal=2
strfseconds( 180000, '%o seconds is %d days', 2 )
// Show hours only
strfseconds( 180000, '%o seconds is %h hours', 2 )
// Skip adjecent timeunit hours; show days and minutes
strfseconds( 180000, '%o seconds is %d days and %m minutes', 2 )

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
strfseconds( 1948.194812, '%o seconds is %h2:%m2:%s2', 6 )
// Specify microseconds manually specifying %f 
strfseconds( 1948.194812, '%o seconds is %h2:%m2:%s2 %f', 0 )
```
```
1948.194812 seconds is 00:32:28.194812
1948.194812 seconds is 00:32:28 194812
```

##  Fractions and Rounding
```
// Show seconds as minutes 
strfseconds( 90, '%o seconds is %m minutes', 2 )

// Show .999999 fraction with three decimals
strfseconds( .999999, '%o seconds is %s seconds', 3 )
// Show without decimals - will show 0 seconds, rounding mode is down.
strfseconds( .999999, '%o seconds is %s seconds', 0 )
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
StringFormatSecondsHighPrecision[->format]( seconds, formatString [ = '%d2:%m2:%s2'], nDecimal [ = 3 ]) 
```
```
strfsecondshp[->format]( seconds, formatstring [ = '%d2:%m2:%s2'], ndecimal [ = 3 ])
```
---
*There is a Beta Python package available in 
[Python-strfseconds](https://github.com/remivisser/Python-strfseconds)*
