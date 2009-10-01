inputfile=$1

#gets last problem
lastProblem=`cat $inputfile | tail -2 | head -1 | awk '{print $1}'`
#echo $lastProblem

#counts number of strategy pairs
strategyCount=`cat $inputfile | grep -c $lastProblem`
echo $strategyCount
