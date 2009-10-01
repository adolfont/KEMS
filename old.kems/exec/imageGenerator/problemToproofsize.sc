set title "Size of problemName proof trees related to problem sizes"
set out "problemName_problemToproofsize.ps"
set xlabel "Problem size"
set ylabel "Size of proof tree"
set autoscale
set terminal postscript landscape enhanced mono "Helvetica" 10
plot \
"problemName_results.tex.dat" using 8:9 title 'proof size' with linespoints
