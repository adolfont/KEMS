
#! /bin/sh
if [ "$1" == "" ]
then 
  echo "Usage: $0 <csvFile> <strategy>"
  exit
fi 

if [ "$2" == "" ]
then 
  echo "Usage: $0 <csvFile> <strategy>"
  exit
fi 

cat $1 | grep $2 | grep -v Error | tail -1

