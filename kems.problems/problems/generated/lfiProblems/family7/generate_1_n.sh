if [ "$1" = "" ]
  then 
   echo "You have to provide two numeric arguments"
   exit
fi

if [ "$2" = "" ]
  then 
   echo "You have to provide two numeric arguments"
   exit
fi

i=$1
while [ $i -le $2 ]; do
   
   zero=""
   if [ $i -lt 10 ]
     then
      zero="0"
   fi
      
   ./family7_v1.sh $i > "family7_$zero$i.prove"
    i=$((i+1))
done
