
if [ "$TP_HOME" = "" ] ; then
  echo "ERROR: TP_HOME not found in your environment."
  echo
  echo "Please, set the TP_HOME variable in your environment to match the"
  echo "location of the Tableau Prover version you want to use."
  exit 1
fi

cd $TP_HOME/bin/analysers/

./build.sh jflex   -Danalyser.dir=satlib-sat-h -Dlexer.name=satlib-sat-h.flex

./build.sh cup        -Danalyser.dir=satlib-sat-h -Dparser.name=SATHParser -Dsymbols.name=SATHsym -Dcup.file.name=satlib-sat-h.cup

./build.sh compile  -Danalyser.dir=satlib-sat-h  -Danalyser.files=SATH -DlogicJarName=pclAntigo

./build.sh makejar -Danalyser.dir=satlib-sat-h -Djar.file=SATH.jar -Danalyser.files=SATH

