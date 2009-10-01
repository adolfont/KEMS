if [ "$KEMS_HOME" = "" ] ; then
  echo "ERROR: KEMS_HOME environment variable must be set!"
  exit 1
fi

if [ "$1" = "" ] ; then
  echo "ERROR: You must provide a sequence name as first argument "
  echo "Usage: executeSequence.sh <sequence> <results_file>"
  echo "<sequence> is the name of the file where the prover sequence is"
  echo "<results_file> is the name of the file where the result are going to be written"
  exit 1
fi

if [ "$2" = "" ] ; then
  echo "ERROR: You must provide a file name as second argument "
  echo "Usage: executeSequence.sh <sequence> <results_file>"
  echo "<sequence> is the name of the file where the prover sequence is"
  echo "<results_file> is the name of the file where the result are going to be written"
  exit 1
fi

cd ../kems.export
echo "Executing sequence $1"
echo "Saving in results file $2"
java -Xms200m -Xmx800m -jar kems.jar $KEMS_HOME/kems.tests/$1 | tee $KEMS_HOME/kems.tests/$2
