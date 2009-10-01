output=jpg
./gen1.sh $output size $1 $1 > $1.s1.gscr
#gnuplot $1.s1.gscr
echo "$1.s1.gscr foi criado e pode ser modificado para rodar gnuplot de novo"
#kuickshow $1.$output

