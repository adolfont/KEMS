
if [ "$TP_HOME" = "" ] ; then
  echo "ERROR: TP_HOME not found in your environment."
  echo
  echo "Please, set the TP_HOME variable in your environment to match the"
  echo "location of the Tableau Prover version you want to use."
  exit 1
fi


$TP_HOME/bin/analysers/clean.sh

$TP_HOME/bin/analysers/cwSAT.sh

$TP_HOME/bin/analysers/satcnf2.sh
$TP_HOME/bin/analysers/satcnf.sh

$TP_HOME/bin/analysers/SATS.sh
$TP_HOME/bin/analysers/SATs2.sh
$TP_HOME/bin/analysers/SATs3.sh
$TP_HOME/bin/analysers/SATs4.sh
$TP_HOME/bin/analysers/SATs5.sh

$TP_HOME/bin/analysers/SATH.sh
$TP_HOME/bin/analysers/SATH2.sh

