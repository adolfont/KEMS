#
# Tableau Prover Gnuplot script
#
# 21-03-2005
#
# Generates graphics for families of problems after <family>_results.tex
# has been generated.
#
if [ "$TP_HOME" = "" ] ; then
  echo "ERROR: TP_HOME not found in your environment."
  echo
  echo "Please, set the TP_HOME variable in your environment to match the"
  echo "location of the Tableau Prover version you want to use."
  exit 1
fi
#
#verifies correct number of parameters
if [ $# -lt 2 ] ; then
  echo Usage: \"apply.sh resultsFileName nameOfProblem [classOfProblems]\"
  echo
  exit 1
fi
# gets name of the problem
NAME=$2
#
SUMMARIES_HOME=$TP_HOME/results/summaries/$3
IMAGE_BINARIES_HOME=$TP_HOME/bin/imageGenerator/
cd $SUMMARIES_HOME
#
# substitute & (latex separator) by # (gnuplot separator)
tr '&' '#' < $1 > $1.dat
# remove end of line
tr '\\ \hline' ' ' < $1.dat > $1.dat.dat
#substitute name by empty string
tr $NAME' #' ' ' < $1.dat.dat > $1.dat
sed 's/problemName/'$NAME'/g' < $IMAGE_BINARIES_HOME/length.sc > length$NAME.sc
gnuplot length$NAME.sc
sed 's/problemName/'$NAME'/g' < $IMAGE_BINARIES_HOME/time.sc > time$NAME.sc
gnuplot time$NAME.sc
sed 's/problemName/'$NAME'/g' < $IMAGE_BINARIES_HOME/problemsize.sc > problemsize$NAME.sc 
gnuplot problemsize$NAME.sc
sed 's/problemName/'$NAME'/g' < $IMAGE_BINARIES_HOME/proofsize.sc > proofsize$NAME.sc
gnuplot proofsize$NAME.sc
sed 's/problemName/'$NAME'/g' < $IMAGE_BINARIES_HOME/problemToproofsize.sc > problemToproofsize$NAME.sc
gnuplot problemToproofsize$NAME.sc

rm -rf $1.dat
rm -rf $1.dat.dat
rm -rf length$NAME.sc
rm -rf time$NAME.sc
rm -rf problemsize$NAME.sc
rm -rf proofsize$NAME.sc
rm -rf problemToproofsize$NAME.sc
mv *.ps $TP_HOME/results/images/

