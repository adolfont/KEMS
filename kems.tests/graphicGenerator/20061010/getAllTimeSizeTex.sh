#! /bin/sh 
# This script is for generating another shell scripts
# that generates a LaTex file containing all results
#

allCsvs=`ls *.csv`
#echo "Showing only *.csv"

  echo "\begin{enumerate}"

for file in $allCsvs
do
#  echo
#  echo "Arquivo: $file"
#  echo "Best size strategies - limited quantity"
#  ./getBestSizeStrategies.sh $file
#  echo
#  echo "Arquivo: $file"
#  echo "Best size strategies - only those in the limit"
#  ./getBestSizeStrategies.onlylimit.sh $file
  echo
  echo "\item Results file: \begin{verb} $file \end{verb}"
  echo
  echo "Best time strategies - limited quantity"
  echo
  ./getBestTimeStrategiesTex.sh $file $1 $2 $3 $4
done

  echo "\end{enumerate}"

