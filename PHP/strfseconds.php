<?php
// strfseconds.php
//




// echo strfseconds( 550.194812, '%o %s.%f', 0) . "\n";

// echo strfseconds( 550.194812, '%f') . "\n";



//echo (strfseconds(3600, '%o %h2:%m2:%s2') . "\n");

// echo (strfseconds((float)550.194812, '%o %h:%m:%s.%f')) . "\n";
//echo (strfseconds((float)550.194810, '%f')) . "\n";

//echo (strfseconds( 123.75, '%s6')) . "\n";

// echo (strfseconds((float)550.194812, '%f')) . "\n";

// echo (strfseconds((float)550.194813123456, '%f6')) . "\n";

//echo (strfseconds((float)550.1948132, '%f')) . "\n";
//echo (strfseconds((float)550.1948331234, '%f')) . "\n";
//echo (strfseconds((float)123456789, '%s')) . "\n";
// echo (strfseconds((float)10.194812, '%o %h:%m:%s.%f')) . "\n";

//echo fmod( 550.194812, .0000001). "\n";


function strfseconds( float $seconds, string $formatstring = '%d2:%m2:%s2', int $ndecimal = 3) {

    $smallest_unit_in_formatstring = null;

	if ($seconds < 0)
		throw new Exception(
			"Seconds must be greater than or equal to 0");
	if ($ndecimal < 0)
		throw new Exception(
			"Decimal must be greater than or equal to 0");

    $units = array(
        'w' => (float)604800, // week
        'd' => (float)86400, // day
        'h' => (float)3600, // hour
        'm' => (float)60, // minute
        's' => (float)1, // second
        'f' => (float).000001 // microsecond
    );

    // Replace %o by the value passed for seconds
    $formatstring = str_replace( '%o', $seconds, $formatstring);

    // Determine the smallest time unit in the formatstring and
    // set variable accordingly. If no smallest time unit found
    // return unaltered formatstring.
    foreach ( $units as $unit_name => $unit_secs)
        if ( strpos($formatstring, '%' . $unit_name) !== false)
            $smallest_unit_in_formatstring = $unit_name;
    if ( $smallest_unit_in_formatstring == null)
        return $formatstring;


    // For every time unit ...
    foreach ( $units as $unit_name => $unit_secs) {

        // Check if the time unit is defined in the formatstring, if
        // not continue to the next unit.
        if ( strpos($formatstring, '%' . $unit_name) === false)
            continue;

        // Execute divmod on seconds for this units size: divide the
        // number of seconds by this unit's number of seconds.
        // The unit_size is assigned the quotient, seconds is assigned
        // the remainder.
        // unit_size, seconds = divmod(seconds, unit['secs'])
        $unit_size = floor( $seconds / $unit_secs );

        // On very small numers (microseconds) fmod leaves fractions
        // that should not be there. Rewrote to manual calculation.
        // $seconds = fmod( $seconds, $unit_secs );
        //
        // Float limited precision may show one ms off:
        //
        // >>> echo strfseconds( 550.194812, '%o %s.%f', 0);
        // 550.194812 550.194811
        //
        // >>> echo 550.194812 - 550;
        // 0.19481199999996

        $seconds = $seconds - ( $unit_size * $unit_secs );

        // If this unit is the smallest unit add the remaing seconds as
        // fractions of this time unit's size.
        if ( $smallest_unit_in_formatstring == $unit_name )
            $unit_size += 1 / $unit_secs * $seconds;


        // Calculations are done. Convert unit_size to string and format
        // appearance.


        $unit_size = (string)$unit_size;


        if ( $smallest_unit_in_formatstring == $unit_name ) {
            // Truncate decimals after ndecimal
            // https://stackoverflow.com/a/12710283
            // (string functions are available on float values)
            if ( ($posdecimal = strpos( $unit_size, '.')) !== false)
                $unit_size = floatval( substr( $unit_size, 0, $posdecimal + $ndecimal + 1));

            // Apply sprintf to ensure fixed number of decimals
            // (if ndecimal=3 '.5' should display as '.500')
            //
            $unit_size = sprintf( '%.' . $ndecimal . 'f', $unit_size);
        }

        // Left pad unit_size with zeroes
        $unit_rpad_length = '';
        if ( preg_match('/%' . $unit_name . '([0-9])/', $formatstring, $m) ) {
            // Assign the zeroes specifier parameter to $unit_rpad_length
            // (used also in final formatstring replacement)
            $unit_rpad_length = $m[1];
            // Check for decimals; decimals have difference length
            // value in str_pad().
            if ( strpos( $unit_size, '.' ) ) {
                $unit_size = str_pad(
                    $unit_size, strlen( $unit_size ) - strpos( $unit_size, '.' ) + $unit_rpad_length, '0', STR_PAD_LEFT);
            } else {
                $unit_size = str_pad(
                    $unit_size, $unit_rpad_length, '0', STR_PAD_LEFT);
            }
        }

        // Replace $unit_name . $unit_rpad_length by $unit_size.
        // Eg; replace '%h2' by '03'
        $formatstring = str_replace('%' . $unit_name . $unit_rpad_length, $unit_size, $formatstring);

    }

	return $formatstring;

}
