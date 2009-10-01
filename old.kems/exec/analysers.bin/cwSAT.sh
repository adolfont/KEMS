
if [ "$TP_HOME" = "" ] ; then
  echo "ERROR: TP_HOME not found in your environment."
  echo
  echo "Please, set the TP_HOME variable in your environment to match the"
  echo "location of the Tableau Prover version you want to use."
  exit 1
fi

cd $TP_HOME/bin/analysers/

./build.sh jflex -Danalyser.dir=conversor-wagner-SATLIB -Dlexer.name=conversor-wagner-SATLIB.flex

./build.sh  cup -Danalyser.dir=conversor-wagner-SATLIB -Dparser.name=ConversorWagnerSATLIBParser -Dsymbols.name=ConversorWagnerSATLIBsym -Dcup.file.name=conversor-wagner-SATLIB.cup

./build.sh  compile -Danalyser.dir=conversor-wagner-SATLIB -Danalyser.files=ConversorWagnerSATLIB

./build.sh makejar  -Danalyser.dir=conversor-wagner-SATLIB -Djar.file=cw.jar -Danalyser.files=ConversorWagnerSATLIB

