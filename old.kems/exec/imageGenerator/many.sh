if [ "$TP_HOME" = "" ] ; then
  echo "ERROR: TP_HOME not found in your environment."
  echo
  echo "Please, set the TP_HOME variable in your environment to match the"
  echo "location of the Tableau Prover version you want to use."
  exit 1
fi

cd $TP_HOME
bin/imageGenerator/apply.sh UPrime_results.tex UPrime pelletier
bin/imageGenerator/apply.sh SquareTseitin_results.tex SquareTseitin pelletier
bin/imageGenerator/apply.sh U_results.tex U pelletier
bin/imageGenerator/apply.sh pelletier_results.tex pelletier 
bin/imageGenerator/apply.sh gamma_results.tex gamma wagnerNew
bin/imageGenerator/apply.sh php_results.tex php wagnerNew
bin/imageGenerator/apply.sh statman_results.tex statman wagnerNew
bin/imageGenerator/apply.sh h_results.tex h wagnerNew
