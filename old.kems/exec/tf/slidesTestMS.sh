
if [ "$TP_HOME" = "" ] ; then
  echo "ERROR: TP_HOME not found in your environment."
  echo
  echo "Please, set the TP_HOME variable in your environment to match the"
  echo "location of the Tableau Prover version you want to use."
  exit 1
fi

cd $TP_HOME

java -jar lib/generated/tf.jar -p TP_properties_MemorySaver.txt -r sequence -n php -b4 -e5 -s1 -m9 -t normal -u -i3
java -jar lib/generated/tf.jar -p TP_properties_MemorySaver.txt -r sequence -n php -b4 -e5 -s1 -m9 -t clausal -u -i3
#java -jar lib/generated/tf.jar -p TP_properties_collected_SATS4_MemorySaver.txt 
#-r sequence -n pelletier -b1 -e17 -s1 -m17 -t normal -u 
java -jar lib/generated/tf.jar -p TP_properties_MemorySaver.txt -r sequence -n h -b6 -e6 -s1 -m9 -t normal -u -i3
java -jar lib/generated/tf.jar -p TP_properties_MemorySaver.txt -r sequence -n gamma -b7 -e7 -s1 -m99 -t normal -u -i3
java -jar lib/generated/tf.jar -p TP_properties_MemorySaver.txt -r sequence -n gamma -b100 -e100 -s1 -m990 -t normal -u -i3
java -jar lib/generated/tf.jar -p TP_properties_MemorySaver.txt -r sequence -n statman -b6 -e6 -s1 -m9 -t normal -u -i3
java -jar lib/generated/tf.jar -p TP_properties_MemorySaver.txt -r sequence -n statman -b21 -e21 -s1 -m21 -t normal -u -i3
#java -jar lib/generated/tf.jar -p TP_properties_MemorySaver.txt -r sequence -n php -b6 -e6 -s1 -m9 -t clausal -u -i1
java -jar lib/generated/tf.jar -p TP_properties_MemorySaver.txt -r sequence -n php -b6 -e6 -s1 -m9 -t normal -u -i1

