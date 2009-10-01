
if [ "$TP_HOME" = "" ] ; then
  echo "ERROR: TP_HOME not found in your environment."
  echo
  echo "Please, set the TP_HOME variable in your environment to match the"
  echo "location of the Tableau Prover version you want to use."
  exit 1
fi

cd $TP_HOME

mkdir original/workspace/kems.export/lib
mkdir original/workspace/kems.export/lib/generated
mkdir original/workspace/kems.export/lib/ext
cp lib/generated/*.* original/workspace/kems.export/lib/generated/
cp lib/ext/*.*       original/workspace/kems.export/lib/ext
cp -r original/workspace/kems.problems/ original/workspace/kems.export/problems

