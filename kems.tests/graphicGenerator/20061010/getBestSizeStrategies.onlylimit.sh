csvFile=$1
biggerProblem=`./sortSzTm.sh $csvFile | tail -1 | awk '{print $1}'`
nSolvedBigger=`./sortSzTm.sh $csvFile | grep -c $biggerProblem`
echo "Bigger problem instance solved: "$biggerProblem
#echo $nSolvedBigger


### BEST IN SIZE  WHO SOLVED BIGGER
best=`./sortSzTm.sh $csvFile | tail -$nSolvedBigger | head -1 | awk '{print $9}'`
#echo $bigger

echo "./sortSzTm.sh $csvFile | awk '\$9=/$best/ {print \$5 \" & \" $best \" & \" \$7 }'" > __tmp.sh
#cat __tmp.sh
chmod +x __tmp.sh
#
echo
echo "best in size who solved bigger instance:"
echo "pair & best size & time"
./__tmp.sh
rm -rf ./__tmp.sh

#WORST IN SIZE WHO SOLVED BIGGER

worst=`./sortSzTm.sh $csvFile | tail -1 | awk '{print $9}'`
#echo $worst

echo "./sortSzTm.sh $csvFile | awk '\$9=/$worst/ {print \$5 \" & \" $worst \" & \" \$7 }'" > __tmp.sh
#cat __tmp.sh
chmod +x __tmp.sh
#
echo
echo "worst in size who solved bigger instance:"
echo "pair & worst size & time"
./__tmp.sh
rm -rf ./__tmp.sh

##  FIRST NOT SOLVED AND BIGGER ALL SOLVED
echo 

firstNotSolved=`cat $csvFile | grep Error | head -1 | awk '{print $1}'`



### MUDAR NO OUTRO - testar com um sem erros
# O resto não faz sentido 
if [ "$firstNotSolved" == "" ]
then
  echo "All instances were solved by all pairs!"
  exit
else
  echo "First not solved:"
  echo $firstNotSolved
fi

#biggerAllSolved
biggerAllSolved=`cat $csvFile | grep $firstNotSolved -1 | head -1 | awk '{print $1}'`
echo
echo "Bigger all solved:"
echo $biggerAllSolved

### BEST IN SIZE  BETWEEN THOSE WHO SOLVED ALL
best=`./sortSzTm.sh $csvFile | grep $biggerAllSolved  | head -1 | awk '{print $9}'`
#echo $bigger

echo "./sortSzTm.sh $csvFile | awk '\$9=/$best/ {print \$5 \" & \" $best \" & \" \$7 }'" > __tmp.sh
#cat __tmp.sh
chmod +x __tmp.sh
#
echo
echo "best in size between those who solved the bigger all solved:"
echo "pair & best size & time"
./__tmp.sh
rm -rf ./__tmp.sh


#WORST IN SIZE BETWEEN THOSE WHO SOLVED ALL

worst=`./sortSzTm.sh $csvFile | grep $biggerAllSolved | tail -1 | awk '{print $9}'`
#echo $worst

echo "./sortSzTm.sh $csvFile | awk '\$9=/$worst/ {print \$5 \" & \" $worst \" & \" \$7 }'" > __tmp.sh
#cat __tmp.sh
chmod +x __tmp.sh
#
echo
echo "worst in size between those who solved the bigger all solved:"
echo "pair & worst size & time"
./__tmp.sh
rm -rf ./__tmp.sh


