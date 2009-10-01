if [ "$1" == "" ]
then
  echo "Usage: $0 <jpgPathForTex>"
  exit
fi

jpgPath=$1


# gera os includes para as figuras disponíveis
allJpgs=`ls *.jpg`




for jpgFile in $allJpgs
do
  echo "This is a figure that shows the results obtained by KEMS with $jpgFile."
  echo "\begin{figure}[htbp]"
  echo "\begin{center}"
  echo "\includegraphics[width=0.95\textwidth]{$jpgPath/$jpgFile}"
  echo "\end{center}"
  echo "\caption{$jpgFile}"
  echo "\end{figure}"
  echo
done
