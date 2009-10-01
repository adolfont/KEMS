cat  lfi_1.csv | awk '{print $3 " ; " $7 " ; " $9}' | sort -n -rk 1 -k 5
