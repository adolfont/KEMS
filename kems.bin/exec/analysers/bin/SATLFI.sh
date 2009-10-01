if [ "$KEMS_HOME" = "" ] ; then
  echo "ERROR: KEMS_HOME not found in your environment."
  echo
  echo "Please, set the KEMS_HOME variable in your environment to match the"
  echo "location of the KEMS Prover version you want to use."
  exit 1
fi

cd $/bin/analysers/

./build.sh jflex   -Danalyser.dir=satlib-sat-lfi -Dlexer.name=satlib-sat-lfi.flex

./build.sh cup  -Danalyser.dir=satlib-sat-lfi -Dparser.name=satlfiParser -Dsymbols.name=satlfisym -Dcup.file.name=satlib-sat-lfi.cup

./build.sh compile  -Danalyser.dir=satlib-sat-lfi -Danalyser.files=satlfi 

./build.sh makejar -Danalyser.dir=satlib-sat-lfi -Djar.file=satlfi.jar -Danalyser.files=satlfi

