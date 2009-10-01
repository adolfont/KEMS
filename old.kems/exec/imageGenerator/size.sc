set title "Size of problemName proof trees"
set out "problemName_size.ps"
set xlabel "Instance number"
set ylabel "Size of proof tree"
set autoscale
set terminal postscript landscape enhanced mono "Helvetica" 10
plot \
"problemName_results.tex.dat" using 1:8 title 'problem size' with linespoints, \
"problemName_results.tex.dat" using 1:9 title 'proof size' with linespoints
