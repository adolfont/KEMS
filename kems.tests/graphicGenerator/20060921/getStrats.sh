inputfile=$1
# if it is "-lines" one in each line
outputOption=$2


#gets last problem
lastProblem=`cat $inputfile | tail -2 | head -1 | awk '{print $1}'`
#echo $lastProblem

#counts number of strategy pairs
strategyCount=`cat $inputfile | grep -c $lastProblem`
#echo $strategyCount
strategies=`cat $inputfile | grep $lastProblem | awk '{print $7}'`


if [ "$outputOption" != "-line" ]
 then
   echo $strategies
 else
   for strat in $strategies
     do
       echo $strat
     done
fi

