
if [ "$TP_HOME" = "" ] ; then
  echo "ERROR: TP_HOME not found in your environment."
  echo
  echo "Please, set the TP_HOME variable in your environment to match the"
  echo "location of the Tableau Prover version you want to use."
  exit 1
fi

cd $TP_HOME/bin/analysers/

./build.sh jflex   -Danalyser.dir=satlib-sat-s4 -Dlexer.name=satlib-sat-s4.flex

./build.sh cup  -Danalyser.dir=satlib-sat-s4 -Dparser.name=sats4Parser -Dsymbols.name=sats4sym -Dcup.file.name=satlib-sat-s4.cup

./build.sh compile  -Danalyser.dir=satlib-sat-s4 -Danalyser.files=sats4

./build.sh makejar -Danalyser.dir=satlib-sat-s4 -Djar.file=sats4.jar -Danalyser.files=sats4

