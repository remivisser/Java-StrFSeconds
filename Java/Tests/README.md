# PHP - Floating Number Precision [duplicate]
https://stackoverflow.com/a/3726761

"for some floats a and b, (a+b)-b != a."

Floating point arithmetic != real number arithmetic. An
illustration of the difference due to imprecision is;

    for some floats a and b, (a+b)-b != a.

This applies to any language using floats.

Since floating point are binary numbers with finite precision,
there's a finite amount of representable numbers, which leads
accuracy problems and surprises like this.
