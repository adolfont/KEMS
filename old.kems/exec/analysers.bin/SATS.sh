
if [ "$TP_HOME" = "" ] ; then
  echo "ERROR: TP_HOME not found in your environment."
  echo
  echo "Please, set the TP_HOME variable in your environment to match the"
  echo "location of the Tableau Prover version you want to use."
  exit 1
fi

cd $TP_HOME/bin/analysers/

./build.sh jflex       -Danalyser.dir=satlib-sat-s -Dlexer.name=satlib-sat-s.flex

./build.sh cup        -Danalyser.dir=satlib-sat-s -Dparser.name=SATSParser -Dsymbols.name=SATSsym -Dcup.file.name=satlib-sat-s.cup

./build.sh compile  -Danalyser.dir=satlib-sat-s -Danalyser.files=SATS -DlogicJarName=pclAntigo

./build.sh makejar -Danalyser.dir=satlib-sat-s -Djar.file=SATS.jar -Danalyser.files=SATS


