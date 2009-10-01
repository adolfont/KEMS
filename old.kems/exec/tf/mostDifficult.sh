
if [ "$TP_HOME" = "" ] ; then
  echo "ERROR: TP_HOME not found in your environment."
  echo
  echo "Please, set the TP_HOME variable in your environment to match the"
  echo "location of the Tableau Prover version you want to use."
  exit 1
fi

cd $TP_HOME

java -jar lib/generated/tf.jar -r sequence -n gamma -b120 -e120 -s40 -m400 -t normal -u -h -l -f -i3
java -jar lib/generated/tf.jar -r sequence -n gamma -b120 -e120 -s40 -m400 -t clausal -u -h -l -f -i3
java -jar lib/generated/tf.jar -r sequence -n php -b4 -e4 -s1 -m9 -t normal -u -h -l -f -i3
java -jar lib/generated/tf.jar -r sequence -n php -b4 -e4 -s1 -m9 -t clausal -u -h -l -f -i3
java -jar lib/generated/tf.jar -r sequence -n statman -b17 -e17 -s1 -m25 -t normal -u -h -l -f -i3
java -jar lib/generated/tf.jar -r sequence -n statman -b17 -e17 -s1 -m25 -t clausal -u -h -l -f -i3
java -jar lib/generated/tf.jar -r sequence -n h -b7 -e7 -s1 -m9 -t normal -u -h -l -f -i3
java -jar lib/generated/tf.jar -r sequence -n h -b7 -e7 -s1 -m9 -t clausal -u -h -l -f -i3

