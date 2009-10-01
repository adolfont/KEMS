all=`ls *7.seq; ls *7_v?.seq; ls *8.seq; ls *9.seq`
echo $all

for file in $all
do
  psed -f psed.script.new.path $file > tmp.seq
  mv tmp.seq $file
done