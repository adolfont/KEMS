
if [ "$KEMS_HOME" = "" ] ; then
  echo "ERROR: KEMS_HOME not found in your environment."
  echo
  echo "Please, set the KEMS_HOME variable in your environment to match the"
  echo "location of the KEMS Prover version you want to use."
  exit 1
fi

cd $KEMS_HOME/kems.bin/exec/analysers/

./build.sh jflex -Danalyser.dir=conversor-wagner-SATLIB -Dlexer.name=conversor-wagner-SATLIB.flex

./build.sh  cup -Danalyser.dir=conversor-wagner-SATLIB -Dparser.name=ConversorWagnerSATLIBParser -Dsymbols.name=ConversorWagnerSATLIBsym -Dcup.file.name=conversor-wagner-SATLIB.cup

./build.sh  compile -Danalyser.dir=conversor-wagner-SATLIB -Danalyser.files=ConversorWagnerSATLIB

./build.sh makejar  -Danalyser.dir=conversor-wagner-SATLIB -Djar.file=cw.jar -Danalyser.files=ConversorWagnerSATLIB

