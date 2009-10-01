# Usage:
# 1) termOption - expressão para o terminal:
#  jpg
#  eps
# 2) labelOption:
#  time
#  size
# 3) inputfile
# 3) outputfile


termOption=$1
labelOption=$2
inputfile=$3
outputfile=$4

if [ "$termOption" = "jpg" ]
then
  term="jpeg"
  termination=".jpg"
elif [ "$termOption" = "eps" ]
  then
    term="post eps enhanced color"
    termination=".eps"
fi

if [ "$labelOption" = "time" ]
then
  label="time spent (ms)"
elif [ "$labelOption" = "size" ]
  then 
    label="proof size"
fi

echo "./gen2.sh $inputfile >> __tmp3.scr"  > ./__temp_exec.sh
chmod +x ./__temp_exec.sh
./__temp_exec.sh

#exit

echo "sed 's/<terminal>/$term/' < base.scr > __tmp1.scr" > __temp_exec.sh
echo "sed 's/<rotulovertical>/$label/' < __tmp1.scr > __tmp2.scr" >> __temp_exec.sh
#echo "sed 's/<arquivosaida>/$outputfile$termination/' < __tmp2.scr > __tmp1.scr" >> __temp_exec.sh
#echo "sed 's/<plots>/$plots/' < __tmp1.scr > __tmp2.scr" >> __temp_exec.sh

chmod +x ./__temp_exec.sh
./__temp_exec.sh
cat __tmp2.scr 

#for line in $plots
#do
#  echo $line
#done

cat __tmp3.scr

echo "set output \"$outputfile$termination\""
echo "replot"

rm -rf __*.*

