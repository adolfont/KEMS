cp kems.jar kems_notsigned.jar
scp kems_notsigned.jar adolfo@dainf.ct.utfpr.edu.br:~/public_html/KEMS/JavaWebStart
ssh adolfo@dainf.ct.utfpr.edu.br 'cd ~/public_html/KEMS/JavaWebStart ; jarsigner -keystore mykeystore -storepass 123456 -signedjar kems.jar kems_notsigned.jar chaveKEMS'
