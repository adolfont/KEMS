allCsvs=`ls *.csv`

# count of bigger problems solved - best pairs
cbb=$1
# count of bigger problems solved - worst pairs
cbw=$2
# count of problem solved by all - best pairs
cab=$3
# count of problem solved by all - worst pairs
caw=$4

if [ "$1" = "" ]
  then cbb=3
fi

if [ "$2" = "" ]
  then cbw=0
fi

if [ "$3" = "" ]
  then cab=0
fi

if [ "$4" = "" ]
  then caw=3
fi


for csvFile in $allCsvs
do
  echo "#generate cpu time graphic" 
  echo "./gen_time_gnup_eps.sh eps $csvFile $cbb $cbw $cab $caw"
  echo "#generate proof size graphic" 
  echo "./gen_size_gnup_eps.sh eps $csvFile $cbb $cbw $cab $caw"
done
echo "rename 's/.csv.s2.eps/-csv-s2.eps/' *.eps"
echo "rename 's/.csv.t2.eps/-csv-t2.eps/' *.eps"
echo "rename 's/_/-/' *.eps"
echo "rename 's/_/-/' *.eps"
echo "rename 's/_/-/' *.eps"
echo "rename 's/_/-/' *.eps"
echo "rename 's/_/-/' *.eps"
