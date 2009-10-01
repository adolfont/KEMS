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
PROBLEM=statman

echo Generating $PROBLEM problem instances with problem generator...
echo

cd $TP_HOME/problems/generated/wagnerNew/$PROBLEM
$TP_HOME/projects/external/$PROJECT/$PROBLEM -from 1 -to 9 
$TP_HOME/projects/external/$PROJECT/$PROBLEM -from 1 -to 9 -n
$TP_HOME/projects/external/$PROJECT/$PROBLEM -from 13 -to 29 -step 4
$TP_HOME/projects/external/$PROJECT/$PROBLEM -from 13 -to 29 -step 4 -n
echo
echo "Finished!"
