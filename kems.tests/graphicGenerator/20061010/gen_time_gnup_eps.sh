if [ "$1" = "" ]
then 
  echo "graphic type (eps/jpg) missing"
  exit
fi

if [ "$2" = "" ]
then 
  echo "filename missing"
  exit
fi

echo "Strategies chosen"
./getBestTimeStrategies.sh $2 $3 $4 $5 $6 | grep "<" | sort -n -b -k 3 
./getBestTimeStrategies.sh $2 $3 $4 $5 $6 | grep "<" | sort -n -b -k 3 | awk '{print $1}' > strats.txt
./gentime_eps.sh $2
./readLineByLine.sh  $2.t1.gscr > $2.t2.gscr
echo "Strategies chosen"
cat strats.txt
rm -rf strats.txt
echo "$2.t2.gscr generated"
gnuplot $2.t2.gscr
mv $2.$1 $2.t2.$1