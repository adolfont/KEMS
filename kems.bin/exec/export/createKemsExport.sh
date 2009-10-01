if [ "$KEMS_HOME" = "" ] ; then
  echo "ERROR: KEMS_HOME not found in your environment."
  echo
  echo "Please, set the KEMS_HOME variable in your environment to match the"
  echo "location of the KEMS Prover version you want to use."
  exit 1
fi


if [ "$1" = "" ] 
  then
    echo "Usage createKemsExport.sh <path>" 
    echo "  <path> is the path to directory where kems.export will be created"
    exit
fi


# if $1 is a real path
if [ -e "$1" ]
then
  path="$1/kems.export"
  
  if [ -e "$path" ]
  then
    echo "Do you want to remove $path and its contents (yes/no)?"
    read answer
    
    if [ "$answer" = "yes" ]
    then
      rm -rf $path
    else
      exit
    fi
  fi

  mkdir $path
  

  cp -rf -L $KEMS_HOME/kems.problems/problems $path
  cp -rf -L $KEMS_HOME/kems.export/lib $path
  cp -rf -L $KEMS_HOME/kems.export/help $path
  cp -f -L $KEMS_HOME/kems.export/*.* $path
  cp -rf -L $KEMS_HOME/kems.ext.lib/ext/*.* $path/lib/ext/
  cp -rf -L $KEMS_HOME/kems.generated.lib/generated/*.* $path/lib/generated
  cp -rf -L $KEMS_HOME/kems.tests/sequences* $path/
  
  rm -rf $path/instructions.txt
  rm -rf $path/*.problem
  rm -rf $path/dummy.txt
  rm -rf $path/*/dummy.txt
  rm -rf $path/*/*/dummy.txt
  rm -rf $path/log.txt
  rm -rf $path/CVS
  rm -rf $path/*/CVS
  rm -rf $path/*/*/CVS
  rm -rf $path/*/*/*/CVS
  rm -rf $path/*/*/*/*/*/CVS
  rm -rf $path/*/*/*/*/*/*/CVS
  rm -rf $path/*/*/*/*/*/*/*/CVS
  rm -rf $path/*/*/*/*/*/*/*/*/CVS
  rm -rf $path/.cvsignore
  rm -rf $path/*/.cvsignore
  rm -rf $path/*/*/.cvsignore
  rm -rf $path/*/*/*/.cvsignore
  rm -rf $path/*/*/*/*/*/.cvsignore
  rm -rf $path/*/*/*/*/*/*/.cvsignore
  rm -rf $path/*/*/*/*/*/*/*/.cvsignore
  rm -rf $path/*/*/*/*/*/*/*/*/.cvsignore

  echo "$path was created"
else
 echo "$1 is not a valid path"
fi 
    

