if [ "$1" = "" ] ; then
  echo "ERROR: You must provide a string as first argument "
  exit 1
fi

if [ "$2" = "" ] ; then
  echo "ERROR: You must provide a number as second argument "
  exit 1
fi


if [ "$3" = "" ] ; then
  echo "ERROR: You must provide a number as third argument "
  exit 1
fi

echo "id ($1)"
echo "number source ($2)"
echo "number dest ($3)"

echo "sed 's/"$1"_"$2"/"$1"_"$3"/' < $1_$2_a.scr > $1_$3_a.scr" > tempscr.sh


echo "sed 's/$1_$2/$1_$3/' < $1_$2_a.scr > tmp.txt" >> tempscr.sh 

echo "sed 's/$1_$3_a/$1_$3_c/' < tmp.txt > $1_$3_c.scr" >> tempscr.sh

echo "sed 's/$1_$2/$1_$3/' < $1_$2_b.scr > $1_$3_b.scr" >> tempscr.sh

echo "sed 's/$1_$2/$1_$3/' < $1_$2_b.scr > tmp.txt" >> tempscr.sh

echo "sed 's/$1_$3_b/$1_$3_d/' < tmp.txt > $1_$3_d.scr" >> tempscr.sh

chmod +x tempscr.sh
./tempscr.sh
