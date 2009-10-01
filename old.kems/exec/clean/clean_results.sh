if [ "$TP_HOME" = "" ] ; then
  echo "ERROR: TP_HOME not found in your environment."
  echo
  echo "Please, set the TP_HOME variable in your environment to match the"
  echo "location of the Tableau Prover version you want to use."
  exit 1
fi

rm -Rf $TP_HOME/results/*.proof
rm -Rf $TP_HOME/results/*.simpleproof
rm -Rf $TP_HOME/results/*.xml
rm -Rf $TP_HOME/results/*.tex

rm -Rf $TP_HOME/results/*/*.proof
rm -Rf $TP_HOME/results/*/*.simpleproof
rm -Rf $TP_HOME/results/*/*.xml
rm -Rf $TP_HOME/results/*/*.tex

rm -Rf $TP_HOME/results/*/*/*.proof
rm -Rf $TP_HOME/results/*/*/*.simpleproof
rm -Rf $TP_HOME/results/*/*/*.xml
rm -Rf $TP_HOME/results/*/*/*.tex

rm -Rf $TP_HOME/results/*/*/*/*.proof
rm -Rf $TP_HOME/results/*/*/*/*.simpleproof
rm -Rf $TP_HOME/results/*/*/*/*.xml
rm -Rf $TP_HOME/results/*/*/*/*.tex

