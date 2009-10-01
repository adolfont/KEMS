
if [ "$TP_HOME" = "" ] ; then
  echo "ERROR: TP_HOME not found in your environment."
  echo
  echo "Please, set the TP_HOME variable in your environment to match the"
  echo "location of the Tableau Prover version you want to use."
  exit 1
fi

cd $TP_HOME/bin/analysers/

./build.sh jflex   -Danalyser.dir=satlib-cnf -Dlexer.name=satlib-cnf.flex

./build.sh cup  -Danalyser.dir=satlib-cnf -Dparser.name=satcnfParser -Dsymbols.name=satcnfsym -Dcup.file.name=satlib-cnf.cup

./build.sh compile  -Danalyser.dir=satlib-cnf -Danalyser.files=satcnf

./build.sh makejar -Danalyser.dir=satlib-cnf -Djar.file=satcnf.jar -Danalyser.files=satcnf

