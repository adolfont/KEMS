#! /bin/sh

# function to change "_" by "\_" for latex
function poeTexUnderline(){
  result=$1
  result=`echo $result | sed 's/\_/UNDERLINE/'`
  result=`echo $result | sed 's/\_/UNDERLINE/'`
  result=`echo $result | sed 's/\_/UNDERLINE/'`
  result=`echo $result | sed 's/\_/UNDERLINE/'`
  result=`echo $result | sed 's/UNDERLINE/\\\_/'`
  result=`echo $result | sed 's/UNDERLINE/\\\_/'`
  result=`echo $result | sed 's/UNDERLINE/\\\_/'`
  result=`echo $result | sed 's/UNDERLINE/\\\_/'`
  echo $result
}

# GETS BIGGER PROBLEM SOLVED AND SHOWS ITS NAME
csvFile=$1
biggerProblem=`./sortSzTm.sh $csvFile | tail -1 | awk '{print $1}'`
biggerProblemToShow=`poeTexUnderline $biggerProblem`
#echo $biggerProblem
nSolvedBigger=`./sortSzTm.sh $csvFile | grep -c $biggerProblem`
#echo $nSolvedBigger
echo "The bigger problem instance solved in the \begin{verb} $1 \end{verb} file was  "$biggerProblemToShow". "
echo "It was solved by $nSolvedBigger \textless strategy, comparator\textgreater pairs. "


### BEST IN TIME  WHICH SOLVED BIGGER
# troquei 9 por 7
best=`./sortTmSz.sh $csvFile | tail -$nSolvedBigger | head -1 | awk '{print $5 " ; " $7 " ; " $9}'`
#echo $best

#moreAfterBest is the number of best pairs shown
if [ "$2" = "" ]
 then
  moreAfterBest=2
 else
  moreAfterBest=$2
  moreAfterBest=$((moreAfterBest-1))
fi

# troquei 9 por 7 e vice-versa
echo "./sortTmSz.sh $csvFile | grep $biggerProblem | grep -A $moreAfterBest \"$best\" | awk '{print \$5 \" & \" \$7 \" & \" \$9 \" \\\\\\\ \" }'" > __tmp.sh
#cat __tmp.sh
chmod +x __tmp.sh
#
bestCount=`./sortTmSz.sh $csvFile | grep $biggerProblem | grep -A $moreAfterBest "$best" | wc -l`
#echo $bestCount

echo "These were the $bestCount best pairs which solved the bigger instance solved, sorted by time: \\\\"
echo
echo
echo "\begin{tabular}{|c|c|c|} \hline"
echo "pair & time & size  \\\\ \hline"
./__tmp.sh | ./trocaCaracteresParaTex.sh
#./__tmp.sh 
echo "\end{tabular}"
rm -rf ./__tmp.sh


### WORST IN TIME  WHICH SOLVED BIGGER
# troquei 9 por 7
worst=`./sortTmSz.sh $csvFile | tail -1 | awk '{print $5 " ; " $7 " ; " $9 }'`
#echo $worst

#moreAfterWorst is the number of worst pairs shown
if [ "$3" = "" ]
 then
  moreAfterWorst=2
 else
  moreAfterWorst=$3
  moreAfterWorst=$((moreAfterWorst-1))
fi

# troquei 9 por 7 e vice-versa
echo "./sortTmSz.sh $csvFile | grep $biggerProblem | grep -B $moreAfterWorst \"$worst\" | awk '{print \$5 \" & \" \$7 \" & \" \$9 \" \\\\\\\ \" }'" > __tmp.sh

worstCount=`./sortTmSz.sh $csvFile | grep $biggerProblem | grep -B $moreAfterWorst "$worst" |  wc -l`
#echo $worstCount


#cat __tmp.sh
chmod +x __tmp.sh
#
echo "\\\\"
echo "\\\\"
echo
echo "And these are the $worstCount worst which solved the bigger instance solved, sorted by time: \\\\"
echo
echo "\begin{tabular}{|c|c|c|} \hline"
echo "pair & time & size  \\\\ \hline"
./__tmp.sh | ./trocaCaracteresParaTex.sh
echo "\end{tabular}"
rm -rf ./__tmp.sh


##  FIRST NOT SOLVED AND BIGGER ALL SOLVED

firstNotSolved=`cat $csvFile | grep Error | head -1 | awk '{print $1}' `
firstNotSolvedShow=`poeTexUnderline $firstNotSolved`

echo "\\\\"
echo "\\\\"
echo
# O resto não faz sentido 
#echo "DEBUG $firstNotSolved"
if [ "$firstNotSolved" == "" ]
then
  echo "Note: in this file there was no instance that was not proved by any pair. \\\\"
  echo
  exit
fi


echo "The first problem instance not solved by some pair in this file was "
echo $firstNotSolvedShow" and "

biggerAllSolved=$(cat $csvFile | grep $firstNotSolved -1 | head -1 | awk '{print $1}')
#echo "DEBUG $biggerAllSolved"

if [ $biggerAllSolved = "Processing" ]
then
  echo " no instance was solved by all pairs."
  exit
else
  biggerAllSolvedShow=`poeTexUnderline $biggerAllSolved`
  allCount=`./sortTmSz.sh $csvFile | grep $biggerAllSolved | wc -l`
  echo " the bigger problem instance solved by all $allCount pairs was "
  echo $biggerAllSolvedShow". "
fi

### BEST IN TIME  BETWEEN THOSE WHICH SOLVED ALL
best=`./sortTmSz.sh $csvFile | grep $biggerAllSolved  | head -1 | awk '{print $5 " ; " $7 " ; " $9 }'`
#echo $best


#moreAfterBest is the number of worst pairs shown
if [ "$4" = "" ]
 then
  moreAfterBest=2
 else
  moreAfterBest=$4
  moreAfterBest=$((moreAfterBest-1))
fi


echo "./sortTmSz.sh $csvFile | grep $biggerAllSolved | grep -A $moreAfterBest \"$best\" | awk '{print \$5 \" & \" \$7 \" & \" \$9 \" \\\\\\\ \"}'" > __tmp.sh
bestCount=`./sortTmSz.sh $csvFile | grep $biggerAllSolved | grep -A $moreAfterBest "$best" | wc -l`
#echo $bestCount


#cat __tmp.sh
chmod +x __tmp.sh
#
echo " The pairs below are the $bestCount best pairs that solved the bigger instance solved by all pairs, sorted by time: \\\\"
echo
echo "\begin{tabular}{|c|c|c|} \hline"
echo "pair & time & size  \\\\ \hline"
./__tmp.sh | ./trocaCaracteresParaTex.sh
echo "\end{tabular}"
rm -rf ./__tmp.sh

echo "\\\\"
echo "\\\\"
echo


#WORST IN TIME BETWEEN THOSE WHICH SOLVED ALL

worst=`./sortTmSz.sh $csvFile | grep $biggerAllSolved | tail -1 | awk '{print $5 " ; " $7 " ; " $9 }'`
#echo $worst


#moreAfterWorst is the number of worst pairs shown
if [ "$5" = "" ]
 then
  moreAfterWorst=2
 else
  moreAfterWorst=$5
  moreAfterWorst=$((moreAfterWorst-1))
fi



echo "./sortTmSz.sh $csvFile | grep $biggerAllSolved | grep -B $moreAfterWorst \"$worst\" | awk '{print \$5 \" & \" \$7 \" & \" \$9  \" \\\\\\\ \"}'" > __tmp.sh
worstCount=`./sortTmSz.sh $csvFile | grep $biggerAllSolved | grep -B $moreAfterWorst "$worst" |  wc -l`
#echo $worstCount

#cat __tmp.sh
chmod +x __tmp.sh
#
echo
echo " And the pairs below are the $worstCount worst pairs that solved the bigger instance solved by all pairs, sorted by time: \\\\"
echo
echo "\begin{tabular}{|c|c|c|} \hline"
echo "pair & time & size  \\\\ \hline"
./__tmp.sh | ./trocaCaracteresParaTex.sh
echo "\end{tabular}"
rm -rf ./__tmp.sh

echo "\\\\"
echo "\\\\"
echo

