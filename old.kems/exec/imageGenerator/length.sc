set title "Length of problemName problem files"
set out "problemName_length.ps"
set xlabel "Instance number"
set ylabel "Length of file in bytes"
set terminal postscript landscape enhanced mono "Helvetica" 10
plot \
"problemName_results.tex.dat" using 1:2 title 'length' with linespoints
