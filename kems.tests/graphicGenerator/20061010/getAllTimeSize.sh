#! /bin/sh 
# This script is for generating another shell scripts
# that generates a LaTex file containing all results
#
echo "\begin{verbatim}"

allCsvs=`ls *.csv`
echo "Showing only *.csv"
for file in $allCsvs
do
  echo
  echo "Arquivo: $file"
  echo "Best size strategies - limited quantity"
  ./getBestSizeStrategies.sh $file
  echo
  echo "Arquivo: $file"
  echo "Best size strategies - only those in the limit"
  ./getBestSizeStrategies.onlylimit.sh $file
  echo
  echo "Arquivo: $file"
  echo "Best time strategies - limited quantity"
  ./getBestTimeStrategies.sh $file 
done

echo "\end{verbatim}"

