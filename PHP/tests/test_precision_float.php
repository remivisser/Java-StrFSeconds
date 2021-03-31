<?php
require_once( '../strfseconds.php');
/*
https://stackoverflow.com/a/3726761
:
Because floating point arithmetic != real number arithmetic. An
illustration of the difference due to imprecision is, for some floats
 a and b, (a+b)-b != a. This applies to any language using floats.

Since floating point are binary numbers with finite precision, there's
a finite amount of representable numbers, which leads accuracy
problems and surprises like this.
*/

// Same precision 'error' on float as in Java
echo fmod( 550.194812, 1) . "\n"; // 0.19481199999996

// Returns correct result
echo fmod( 10.194812, 1) . "\n"; // 10.194812

echo fmod( 550.123456789, 1) . "\n"; // 0.12345678899999

// Divmod should be 0 is 0.00000009
echo fmod( 550.194812, .0000001). "\n";

// Test below shows wrong number of microseconds:
// >>> echo strfseconds( 550.194812, '%o %s %f', 0) . "\n";
// 550.194812 550.194811
//
// This is 'just' float limitation, see reproduction below:
echo 550.194812 - 550 . "\n";
// Returns
// 0.19481199999996

// Incorrect
echo strfseconds( 550.194812, '%o %h:%m:%s.%f', 0) . "\n";
echo strfseconds( 550.194813, '%o %h:%m:%s.%f', 0) . "\n";
echo strfseconds( 550.194814, '%o %h:%m:%s.%f', 0) . "\n";
echo strfseconds( 550.194815, '%o %h:%m:%s.%f', 0) . "\n";
echo strfseconds( 550.194816, '%o %h:%m:%s.%f', 0) . "\n";

// Correct
echo strfseconds( 550.195917, '%o %h:%m:%s.%f', 0) . "\n";
echo strfseconds( 550.195918, '%o %h:%m:%s.%f', 0) . "\n";
