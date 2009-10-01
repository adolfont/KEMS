
if [ "$TP_HOME" = "" ] ; then
  echo "ERROR: TP_HOME not found in your environment."
  echo
  echo "Please, set the TP_HOME variable in your environment to match the"
  echo "location of the Tableau Prover version you want to use."
  exit 1
fi

cd $TP_HOME

java -jar lib/generated/tf.jar -r sequence -n php -b4 -e6 -s1 -m9 -t normal -u -h -l -f -x80
java -jar lib/generated/tf.jar -p TP_properties_MemorySaver.txt -r sequence -n php -b1 -e9 -s1 -m9 -t normal -u
java -jar lib/generated/tf.jar -p TP_properties_collected_SATS4.txt -r sequence -n pelletier -b1 -e17 -s1 -m17 -t normal -u -h -l -f -i3
java -jar lib/generated/tf.jar -r sequence -n gamma -b40 -e120 -s40 -m400 -t normal -u -h -l -f -i3
java -jar lib/generated/tf.jar -r sequence -n php -b1 -e4 -s1 -m9 -t normal -u -h -l -f -i3
java -jar lib/generated/tf.jar -r sequence -n statman -b1 -e9 -s1 -m9 -t normal -u -h -l -f -i3
java -jar lib/generated/tf.jar -r sequence -n h -b1 -e7 -s1 -m9 -t normal -u -h -l -f -i3

