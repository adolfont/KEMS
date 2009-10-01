
if [ "$TP_HOME" = "" ] ; then
  echo "ERROR: TP_HOME not found in your environment."
  echo
  echo "Please, set the TP_HOME variable in your environment to match the"
  echo "location of the Tableau Prover version you want to use."
  exit 1
fi

cd $TP_HOME/bin/analysers/


./build.sh jflex   -Danalyser.dir=satlib-cnf2 -Dlexer.name=satlib-cnf2.flex

./build.sh cup  -Danalyser.dir=satlib-cnf2 -Dparser.name=satcnf2Parser -Dsymbols.name=satcnf2sym -Dcup.file.name=satlib-cnf2.cup

./build.sh compile  -Danalyser.dir=satlib-cnf2 -Danalyser.files=satcnf2

./build.sh makejar -Danalyser.dir=satlib-cnf2 -Djar.file=satcnf2.jar -Danalyser.files=satcnf2

