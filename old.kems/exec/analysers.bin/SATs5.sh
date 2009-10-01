if [ "$TP_HOME" = "" ] ; then
  echo "ERROR: TP_HOME not found in your environment."
  echo
  echo "Please, set the TP_HOME variable in your environment to match the"
  echo "location of the Tableau Prover version you want to use."
  exit 1
fi

cd $TP_HOME/bin/analysers/

./build.sh jflex   -Danalyser.dir=satlib-sat-s5 -Dlexer.name=satlib-sat-s5.flex

./build.sh cup  -Danalyser.dir=satlib-sat-s5 -Dparser.name=sats5Parser -Dsymbols.name=sats5sym -Dcup.file.name=satlib-sat-s5.cup

./build.sh compile  -Danalyser.dir=satlib-sat-s5 -Danalyser.files=sats5

./build.sh makejar -Danalyser.dir=satlib-sat-s5 -Djar.file=sats5.jar -Danalyser.files=sats5

