#!/bin/sh

echo
echo "Problem Generator Script"
echo "------------------------"
echo

if [ "$TP_HOME" = "" ] ; then
  echo "ERROR: TP_HOME not found in your environment."
  echo
  echo "Please, set the TP_HOME variable in your environment to match the"
  echo "location of the Tableau Prover version you want to use."
  exit 1
fi

if [ `uname | grep -n CYGWIN` ]; then
  PS=";"
elif [ `uname | grep -n Windows` ]; then
  PS=";"
else
  PS=":"
fi

PROJECT=problemGenerator
PROBLEM1=Gamma
PROBLEM2=H
PROBLEM3=PHP
PROBLEM4=Statman

echo Generating many problem instances with problem generator...
echo

$TP_HOME/bin/$PROJECT/generate"$PROBLEM1"Instances.sh
$TP_HOME/bin/$PROJECT/generate"$PROBLEM2"Instances.sh
$TP_HOME/bin/$PROJECT/generate"$PROBLEM3"Instances.sh
$TP_HOME/bin/$PROJECT/generate"$PROBLEM4"Instances.sh
echo
echo "Finished! Problem were created in directories in $TP_HOME/problems/generated/wagnerNew"
echo "Finished!"
