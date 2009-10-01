#! /bin/sh
if [ "$1" = "" ]
then
  echo "Usage: $0  <strategy> <csvFiles>"
  exit
fi

if [ "$2" = "" ]
then
  echo "Usage: $0  <strategy> <csvFile> "
  exit
fi

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
exit

echo "ls $1"
files=`ls $1`
echo "Adolfo $files"
#for file in $files
#do
#  ./getBiggerInstanceProved.sh $file $2
#done