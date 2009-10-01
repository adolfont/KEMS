

cat $1 | grep "<MBCSS,ins"  >> MBCSS_INS.out
cat $1 | grep "<MBCSS,rev"  >> MBCSS_REV.out
cat $1 | grep "<MBCSS,&"  >> MBCSS_AND.out
cat $1 | grep "<MBCSS,|"  >> MBCSS_OR.out
cat $1 | grep "<MBCSS,->"  >> MBCSS_IMP.out
cat $1 | grep "<MBCSS,<=>"  >> MBCSS_BIIMP.out
cat $1 | grep "<MBCSS,+"  >> MBCSS_XOR.out
cat $1 | grep "<MBCSS,T"  >> MBCSS_T.out
cat $1 | grep "<MBCSS,F"  >> MBCSS_F.out
cat $1 | grep "<MBCSS,inc"  >> MBCSS_INC.out
cat $1 | grep "<MBCSS,dec"  >> MBCSS_DEC.out

cat $1 | grep "<MBCSWORS,ins"  >> MBCSWORS_INS.out
cat $1 | grep "<MBCSWORS,rev"  >> MBCSWORS_REV.out
cat $1 | grep "<MBCSWORS,&"  >> MBCSWORS_AND.out
cat $1 | grep "<MBCSWORS,|"  >> MBCSWORS_OR.out
cat $1 | grep "<MBCSWORS,->"  >> MBCSWORS_IMP.out
cat $1 | grep "<MBCSWORS,<=>"  >> MBCSWORS_BIIMP.out
cat $1 | grep "<MBCSWORS,+"  >> MBCSWORS_XOR.out
cat $1 | grep "<MBCSWORS,T"  >> MBCSWORS_T.out
cat $1 | grep "<MBCSWORS,F"  >> MBCSWORS_F.out
cat $1 | grep "<MBCSWORS,inc"  >> MBCSWORS_INC.out
cat $1 | grep "<MBCSWORS,dec"  >> MBCSWORS_DEC.out

