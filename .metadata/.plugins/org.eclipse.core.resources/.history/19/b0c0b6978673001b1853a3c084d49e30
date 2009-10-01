if [ "$1" = "" ]
  then 
   echo "You have to provide a numeric argument"
   exit
fi

i=1
j='T '
while [ $i -le $1 ]; do
  j=$j"@A$i"
  if [ $i -ne $1 ]; then j=$j"|"; fi
  i=$((i+1))
done

echo $j

i=1
j='T '
while [ $i -le $1 ]; do
  j=$j"(B$i->*A$i)"
  if [ $i -ne $1 ]; then j=$j"&"; fi
  i=$((i+1))
done

echo $j

i=1
j='F '
while [ $i -le $1 ]; do
  j=$j"!(@@A$i->B$i)"
  if [ $i -ne $1 ]; then j=$j"|"; fi
  i=$((i+1))
done

echo $j
