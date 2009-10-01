i=0
while [ $i -lt 10 ] 
do
  echo Teste$i
  ((i=i+1))
done

VAR=$(find . -name "*.csv")

#echo $VAR


for arq in $VAR
do
  echo $arq
done
