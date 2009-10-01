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
PROBLEM1=gamma
PROBLEM2=h
PROBLEM3=php
PROBLEM4=statman

echo Generating many problem instances with problem generator...
echo

rm -rf $TP_HOME/problems/generated/wagnerNew/$PROBLEM1/*.prove
rm -rf $TP_HOME/problems/generated/wagnerNew/$PROBLEM2/*.prove
rm -rf $TP_HOME/problems/generated/wagnerNew/$PROBLEM3/*.prove
rm -rf $TP_HOME/problems/generated/wagnerNew/$PROBLEM4/*.prove
echo
echo "Finished!"
