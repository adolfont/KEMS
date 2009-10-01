
if [ "$TP_HOME" = "" ] ; then
  echo "ERROR: TP_HOME not found in your environment."
  echo
  echo "Please, set the TP_HOME variable in your environment to match the"
  echo "location of the Tableau Prover version you want to use."
  exit 1
fi

cd $TP_HOME/bin/analysers/

./build.sh jflex   -Danalyser.dir=satlib-sat-s2 -Dlexer.name=satlib-sat-s2.flex

./build.sh cup  -Danalyser.dir=satlib-sat-s2 -Dparser.name=sats2Parser -Dsymbols.name=sats2sym -Dcup.file.name=satlib-sat-s2.cup

./build.sh compile  -Danalyser.dir=satlib-sat-s2 -Danalyser.files=sats2

./build.sh makejar -Danalyser.dir=satlib-sat-s2 -Djar.file=sats2.jar -Danalyser.files=sats2


