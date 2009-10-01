
if [ "$KEMS_HOME" = "" ] ; then
  echo "ERROR: KEMS_HOME not found in your environment."
  echo
  echo "Please, set the KEMS_HOME variable in your environment to match the"
  exit 1
fi

cd $KEMS_HOME/kems.bin/exec/analysers/

./build.sh jflex   -Danalyser.dir=satlib-sat-s3 -Dlexer.name=satlib-sat-s3.flex

./build.sh cup  -Danalyser.dir=satlib-sat-s3 -Dparser.name=sats3Parser -Dsymbols.name=sats3sym -Dcup.file.name=satlib-sat-s3.cup

./build.sh compile  -Danalyser.dir=satlib-sat-s3 -Danalyser.files=sats3

./build.sh makejar -Danalyser.dir=satlib-sat-s3 -Djar.file=sats3.jar -Danalyser.files=sats3

