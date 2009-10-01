#! /bin/sh
if [ "$1" = "" ]
then
  echo "Usage: $0 <csvFiles>"
  exit
fi

echo "Obs.: The first csv file must contain all strategies" 
strats=`./getStrats.sh $1`
#echo $strats


for strat in $strats
do
  ./getAllBiggerInstancesProved.sh $strat $*
done

exit

i=2
allfiles="$*"
#echo $allfiles
echo "Prover Configuration ; Problem ; Time Spent ; Size"
for file in $allfiles
do
  if [ -f $file ]
  then
     ./getBiggerInstanceProved.sh $file $1 | awk '{print $7" ; "$3" ; "$1" ; "$9" ; "$15 }'
  fi
done
