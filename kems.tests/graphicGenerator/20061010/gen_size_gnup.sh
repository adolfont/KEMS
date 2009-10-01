echo "Strategies chosen"
./getBestSizeStrategies.sh $1 $2 $3 $4 $5 | grep "<" | sort -n -b -k 3 
./getBestSizeStrategies.sh $1 $2 $3 $4 $5 | grep "<" | sort -n -b -k 3 | awk '{print $1}' > strats.txt
./gensize.sh $1
./readLineByLine.sh  $1.s1.gscr > $1.s2.gscr
echo "Strategies chosen"
cat strats.txt
rm -rf strats.txt
echo "$1.s2.gscr generated"
gnuplot $1.s2.gscr
mv $1.jpg $1.s2.jpg