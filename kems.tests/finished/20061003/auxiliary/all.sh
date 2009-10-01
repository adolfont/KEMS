if [ "$KEMS_HOME" = "" ]
then
  echo "Please set the KEMS_HOME envirnoment variable first"
  exit
fi
  


./executeSequence.sh sequences/lfi/mbc_1.seq mbc_1.csv
./executeSequence.sh sequences/lfi/mci_1.seq mci_1.csv
./executeSequence.sh sequences/lfi/mbc_2.seq mbc_2.csv
./executeSequence.sh sequences/lfi/mci_2.seq mci_2.csv
./executeSequence.sh sequences/lfi/mbc_3.seq mbc_3.csv
./executeSequence.sh sequences/lfi/mci_3.seq mci_3.csv
./executeSequence.sh sequences/lfi/mbc_4.seq mbc_4.csv
./executeSequence.sh sequences/lfi/mci_4.seq mci_4.csv
./executeSequence.sh sequences/lfi/mbc_7.seq mbc_7.csv
./executeSequence.sh sequences/lfi/mci_7.seq mci_7.csv
./executeSequence.sh sequences/lfi/mbc_7_v2.seq mbc_7_v2.csv
./executeSequence.sh sequences/lfi/mci_7_v2.seq mci_7_v2.csv
./executeSequence.sh sequences/lfi/mbc_7_v3.seq mbc_7_v3.csv
./executeSequence.sh sequences/lfi/mci_7_v3.seq mci_7_v3.csv
./executeSequence.sh sequences/lfi/mbc_8.seq mbc_8.csv
./executeSequence.sh sequences/lfi/mci_8.seq mci_8.csv
./executeSequence.sh sequences/lfi/mbc_9.seq mbc_9.csv
./executeSequence.sh sequences/lfi/mci_9.seq mci_9.csv
./executeSequence.sh sequences/cpl/cpl_01.seq cpl_01.csv
./executeSequence.sh sequences/cpl/cpl_02.seq cpl_02.csv
./executeSequence.sh sequences/cpl/cpl_03.seq cpl_03.csv
./executeSequence.sh sequences/cpl/cpl_04.seq cpl_04.csv
./executeSequence.sh sequences/cpl/cpl_05.seq cpl_05.csv
./executeSequence.sh sequences/cpl/cpl_06.seq cpl_06.csv
#./executeSequence.sh sequences/cpl/cpl_07.seq cpl_07.csv
./executeSequence.sh sequences/cpl/cpl_08.seq cpl_08.csv
./executeSequence.sh sequences/cpl/cpl_09.seq cpl_09.csv
#./executeSequence.sh sequences/cpl/cpl_10.seq cpl_10.csv
./executeSequence.sh sequences/cpl/cpl_11.seq cpl_11.csv
./executeSequence.sh sequences/cpl/cpl_12.seq cpl_12.csv
./executeSequence.sh sequences/cpl/cpl_13.seq cpl_13.csv
./executeSequence.sh sequences/cpl/cpl_14.seq cpl_14.csv
./executeSequence.sh sequences/cpl/cpl_15.seq cpl_15.csv
./executeSequence.sh sequences/cpl/cpl_15_v2.seq cpl_15_v2.csv
./executeSequence.sh sequences/cpl/cpl_15_v3.seq cpl_15_v3.csv
./executeSequence.sh sequences/cpl/cpl_16.seq cpl_16.csv
./executeSequence.sh sequences/cpl/cpl_17.seq cpl_17.csv
