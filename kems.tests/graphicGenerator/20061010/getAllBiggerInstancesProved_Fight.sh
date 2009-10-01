#! /bin/sh
if [ "$1" = "" ]
then
  echo "Usage: $0  <strategy1> <strategy2> <csvFiles>"
  exit
fi

if [ "$2" = "" ]
then
  echo "Usage: $0  <strategy1> <strategy2> <csvFiles>"
  exit
fi

if [ "$3" = "" ]
then
  echo "Usage: $0  <strategy1> <strategy2> <csvFiles>"
  exit
fi

./getAllBiggerInstancesProved.sh $1 $*
./getAllBiggerInstancesProved.sh $2 $*