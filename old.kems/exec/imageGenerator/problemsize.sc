set title "Size of problemName problems"
set out "problemName_problemsize.ps"
set xlabel "Instance number"
set ylabel "Size of problem"
set autoscale
set terminal postscript landscape enhanced mono "Helvetica" 10
plot \
"problemName_results.tex.dat" using 1:8 title 'problem size' with linespoints
