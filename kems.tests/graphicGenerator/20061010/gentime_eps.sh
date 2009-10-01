output=eps
./gen1.sh $output time $1 $1 > $1.t1.gscr
#gnuplot $1.t1.gscr
echo "$1.t1.gscr foi criado e pode ser modificado para rodar gnuplot de novo"
#kuickshow $1.$output

