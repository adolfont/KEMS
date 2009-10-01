linhas=`cat $1 | wc -l`
linhas=$((linhas-2))
cat $1 | tail -$linhas | head -$((linhas-1)) | cut -f1,2,4,5,8 -d";" | sort -b -n -k 2,2 -k 4,4 -k 5,5 -t";" | grep -v "n/a"
