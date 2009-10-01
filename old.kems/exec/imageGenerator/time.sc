set title "Time spent - problemName problem files"
set out "problemName_time.ps"
set xlabel "Instance number"
set ylabel "Time spent (in seconds)"
set terminal postscript landscape enhanced mono "Helvetica" 10
plot \
"problemName_results.tex.dat" using 1:3 title 'time spent' with linespoints
