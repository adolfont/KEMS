if [ "$KEMS_HOME" = "" ] ; then
  echo "ERROR: KEMS_HOME not found in your environment."
  echo
  echo "Please, set the KEMS_HOME variable in your environment to match the"
  echo "location of the KEMS Prover version you want to use."
  exit 1
fi

cd $KEMS_HOME/kems.bin/exec/analysers/

./build.sh jflex   -Danalyser.dir=satlib-sat-lfi-inconsdef -Dlexer.name=satlib-sat-lfi-inconsdef.flex

./build.sh cup  -Danalyser.dir=satlib-sat-lfi-inconsdef -Dparser.name=satlfiinconsdefParser -Dsymbols.name=satlfiinconsdefsym -Dcup.file.name=satlib-sat-lfi-inconsdef.cup

./build.sh compile  -Danalyser.dir=satlib-sat-lfi-inconsdef -Danalyser.files=satlfiinconsdef 

./build.sh makejar -Danalyser.dir=satlib-sat-lfi-inconsdef -Djar.file=satlfiinconsdef.jar -Danalyser.files=satlfiinconsdef

