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
./getBestSizeStrategies.sh $2 $3 $4 $5 $6 | grep "<" | sort -n -b -k 3 
./getBestSizeStrategies.sh $2 $3 $4 $5 $6 | grep "<" | sort -n -b -k 3 | awk '{print $1}' > strats.txt
./gensize_eps.sh $2
./readLineByLine.sh  $2.s1.gscr > $2.s2.gscr
echo "Strategies chosen"
cat strats.txt
rm -rf strats.txt
echo "$2.s2.gscr generated"
gnuplot $2.s2.gscr
mv $2.$1 $2.s2.$1