#! /bin/sh
# gera as linhas de plot/replot do sacript gnuplot


if [ "$1" = "" ]
  then 
    echo "gen2.sh ERROR: no csv file provided"
    echo "Usage: gen2.sh <csvFile> <dataPosition>"
fi

if [ "$2" = "" ]
  then 
    echo "gen2.sh ERROR: no data position provided"
    echo "Usage: gen2.sh <csvFile> <dataPosition>"
fi

inputfile=$1
first=2
second=$2
firstData=2

#generates plots and replots

strategies=`./getStrats.sh $inputfile`
strategyCount=`./getStrats.sh $inputfile -line | wc -l`

#echo $strategies
#echo $strategyCount

echo "" > __out.tmp


counter=0
for strat in $strategies
 do
#  ./gen2.sh time  >> __tmp3.scr

  if [ $counter -eq 0 ]
    then 
      com='#replot'
    else
      com='#replot'
  fi
    
  this=$((firstData+counter))
  
  line="$com \"$inputfile\" every $strategyCount::$this using $first:$second w lp title '$strat'"
  echo -e "$line" >> __out.tmp

  counter=$((counter+1))
 done

cat __out.tmp

