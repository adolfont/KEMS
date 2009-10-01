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
PROBLEM=gamma

echo Generating $PROBLEM problem instances with problem generator...
echo

cd $TP_HOME/problems/generated/wagnerNew/$PROBLEM
$TP_HOME/projects/external/$PROJECT/$PROBLEM -from 1 -to 10 -step 1
$TP_HOME/projects/external/$PROJECT/$PROBLEM -from 1 -to 10 -step 1 -n
$TP_HOME/projects/external/$PROJECT/$PROBLEM -from 20 -to 600 -step 20
#$TP_HOME/projects/external/$PROJECT/$PROBLEM -from 20 -to 600 -step 20 -l 10
$TP_HOME/projects/external/$PROJECT/$PROBLEM -from 20 -to 600 -step 20 -n
#$TP_HOME/projects/external/$PROJECT/$PROBLEM -from 20 -to 600 -step 20 -n -l 10
echo
echo "Finished!"
