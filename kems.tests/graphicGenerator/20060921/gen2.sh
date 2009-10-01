
inputfile=$1
first=2
second=5
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
  
  if [ $counter -eq 0 ]
    then 
      com='plot'
    else
      com='replot'
  fi
    
  this=$((firstData+counter))
  
  line="$com \"$inputfile\" every $strategyCount::$this using $first:$second w lp title '$strat'"
  echo -e "$line" >> __out.tmp

  counter=$((counter+1))
 done

cat __out.tmp

