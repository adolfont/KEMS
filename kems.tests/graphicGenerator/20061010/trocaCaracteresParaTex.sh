function processLine(){

  stratName=$1
#  echo $stratName


  stratName=`echo $stratName | sed 's/\&/$\\\\AND$/'`
  stratName=`echo $stratName | sed 's/|/$\\\\OR$/'`
  stratName=`echo $stratName | sed 's/->/$\\\\\impli$/'`
  stratName=`echo $stratName | sed 's/<=>/$\\\\\bimpli$/'`
  stratName=`echo $stratName | sed 's/+/$\\\\\XOR$/'`
  stratName=`echo $stratName | sed 's/</\\\\textless /'`
  stratName=`echo $stratName | sed 's/D>/D\\\\textgreater/'`
  echo $stratName $2 $3 $4 $5 "\\\\ \\hline"
}


exec 3<&0
#exec 0<$FILE
while read line
do
	
	# use $line variable to process line in processLine() function
       processLine $line
done
exec 0<&3

exit 0