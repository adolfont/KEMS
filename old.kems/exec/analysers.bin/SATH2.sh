
if [ "$TP_HOME" = "" ] ; then
  echo "ERROR: TP_HOME not found in your environment."
  echo
  echo "Please, set the TP_HOME variable in your environment to match the"
  echo "location of the Tableau Prover version you want to use."
  exit 1
fi

cd $TP_HOME/bin/analysers/

./build.sh jflex       -Danalyser.dir=satlib-sat-h2 -Dlexer.name=satlib-sat-h2.flex

./build.sh cup        -Danalyser.dir=satlib-sat-h2 -Dparser.name=SATH2Parser -Dsymbols.name=SATH2sym -Dcup.file.name=satlib-sat-h2.cup

./build.sh compile  -Danalyser.dir=satlib-sat-h2  -Danalyser.files=SATH2 -DlogicJarName=pcl

./build.sh makejar -Danalyser.dir=satlib-sat-h2 -Djar.file=SATH2.jar -Danalyser.files=SATH2

