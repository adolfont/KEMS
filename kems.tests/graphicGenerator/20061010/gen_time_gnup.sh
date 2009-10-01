echo "Strategies chosen"
./getBestTimeStrategies.sh $1 $2 $3 $4 $5 | grep "<" | sort -n -b -k 3 
./getBestTimeStrategies.sh $1 $2 $3 $4 $5 | grep "<" | sort -n -b -k 3 | awk '{print $1}' > strats.txt
./gentime.sh $1
./readLineByLine.sh  $1.t1.gscr > $1.t2.gscr
echo "Strategies chosen"
cat strats.txt
rm -rf strats.txt
echo "$1.t2.gscr generated"
gnuplot $1.t2.gscr
mv $1.jpg $1.t2.jpg