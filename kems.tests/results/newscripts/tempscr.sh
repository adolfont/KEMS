sed 's/mbc_1/mbc_2/' < mbc_1_a.scr > mbc_2_a.scr
sed 's/mbc_1/mbc_2/' < mbc_1_a.scr > tmp.txt
sed 's/mbc_2_a/mbc_2_c/' < tmp.txt > mbc_2_c.scr
sed 's/mbc_1/mbc_2/' < mbc_1_b.scr > mbc_2_b.scr
sed 's/mbc_1/mbc_2/' < mbc_1_b.scr > tmp.txt
sed 's/mbc_2_b/mbc_2_d/' < tmp.txt > mbc_2_d.scr
