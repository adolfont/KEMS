

cat $1 | grep "<MCISS,ins"  >> MCISS_INS.out
cat $1 | grep "<MCISS,rev"  >> MCISS_REV.out
cat $1 | grep "<MCISS,&"  >> MCISS_AND.out
cat $1 | grep "<MCISS,|"  >> MCISS_OR.out
cat $1 | grep "<MCISS,->"  >> MCISS_IMP.out
cat $1 | grep "<MCISS,<=>"  >> MCISS_BIIMP.out
cat $1 | grep "<MCISS,+"  >> MCISS_XOR.out
cat $1 | grep "<MCISS,T"  >> MCISS_T.out
cat $1 | grep "<MCISS,F"  >> MCISS_F.out
cat $1 | grep "<MCISS,inc"  >> MCISS_INC.out
cat $1 | grep "<MCISS,dec"  >> MCISS_DEC.out

cat $1 | grep "<MCISWORS,ins"  >> MCISWORS_INS.out
cat $1 | grep "<MCISWORS,rev"  >> MCISWORS_REV.out
cat $1 | grep "<MCISWORS,&"  >> MCISWORS_AND.out
cat $1 | grep "<MCISWORS,|"  >> MCISWORS_OR.out
cat $1 | grep "<MCISWORS,->"  >> MCISWORS_IMP.out
cat $1 | grep "<MCISWORS,<=>"  >> MCISWORS_BIIMP.out
cat $1 | grep "<MCISWORS,+"  >> MCISWORS_XOR.out
cat $1 | grep "<MCISWORS,T"  >> MCISWORS_T.out
cat $1 | grep "<MCISWORS,F"  >> MCISWORS_F.out
cat $1 | grep "<MCISWORS,inc"  >> MCISWORS_INC.out
cat $1 | grep "<MCISWORS,dec"  >> MCISWORS_DEC.out

