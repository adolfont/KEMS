
if [ "$TP_HOME" = "" ] ; then
  echo "ERROR: TP_HOME not found in your environment."
  echo
  echo "Please, set the TP_HOME variable in your environment to match the"
  echo "location of the Tableau Prover version you want to use."
  exit 1
fi

cd $TP_HOME

echo measuring time
java -jar lib/generated/tf.jar -p TP_properties_generated_SATS4_MS.txt -r sequence -n U -b1 -e5 -s1 -m15 -t normal -u  -i3 -x100 
java -jar lib/generated/tf.jar -p TP_properties_generated_SATS4_MS.txt -r sequence -n UPrime -b1 -e5 -s1 -m15 -t normal -u  -i3 -x100 
java -jar lib/generated/tf.jar -p TP_properties_generated_SATS5_MS.txt -r sequence -n SquareTseitin -b1 -e4 -s1 -m15 -t normal -u  -i3 -x100 


echo producing simple proofs
#java -jar lib/generated/tf.jar -p TP_properties_generated_SATS4.txt -r sequence -n U -b1 -e20 -s1 -m15 -t normal -u  -i1 -x80 -l
#java -jar lib/generated/tf.jar -p TP_properties_generated_SATS4.txt -r sequence -n UPrime -b1 -e20 -s1 -m15 -t normal -u  -i1 -x80 -l
#java -jar lib/generated/tf.jar -p TP_properties_generated_SATS5_MS.txt -r sequence -n SquareTseitin -b1 -e5 -s1 -m15 -t normal -u  -i1 -x100 -l

