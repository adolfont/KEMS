package main.ag.strategy.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

/**
 * Auxiliar - long to file
 * @author Emerson Shigueo Sugimoto 22-12-2012
 * */
public class MemoriaToFile {
	private static final String _nomeArquivo = "MemoryToFile.txt";
	
	/**
	 * @return N&uacute;mero long
	 * */
	public static long getNumero() {return LerArquivo();}
	/**
	 * @param numero Set N&uacute;mero long
	 * */
	public static void setNumero(long numero) {SalvarArquivo(numero);}

	/**
	 * Limpa
	 * */
	public static void Clear(){ClearFile();}
	
	/**
	 * @return N&uacute;mero long
	 * */
	private static long LerArquivo(){
		long rt = 0;
		File arquivo = new File(_nomeArquivo);
		if (!arquivo.exists()) {return 0;}
		try {
	      FileReader arq = new FileReader(arquivo);
	      BufferedReader lerArq = new BufferedReader(arq);
	 
	      String linha = lerArq.readLine();
	      rt = Long.parseLong(linha);
//	      rt += linha + System.getProperty("line.separator");
//	      while (linha != null) {
//	        //System.out.printf("%s\n", linha);
//	        linha = lerArq.readLine();
//	        if (linha==null) continue;
//	        rt += linha + System.getProperty("line.separator");
//	      }
	      
	      arquivo = null;
	      arq.close();
	      lerArq = null;
	      arq = null;
	    } catch (IOException e) {
	        //System.err.printf("Erro na abertura do arquivo: %s.\n", e.getMessage());
	        rt = 0;
	    }
		return rt;
	}
	
	/**
	 * @param valor N&uacute;mero long
	 * */
	private static void SalvarArquivo(long valor){
		File arquivo = new File(_nomeArquivo);
        FileOutputStream fos;
		try {
			fos = new FileOutputStream(arquivo);
			fos.write(String.valueOf(valor).getBytes());
	        fos.close();
	        fos = null;
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		arquivo = null;
	}
	
	/**
	 * Limpa a contagem
	 * */
	private static void ClearFile(){
		File arquivo = new File(_nomeArquivo);
		if (arquivo.exists()) {
			arquivo.delete();
		}
		arquivo = null;
	}
}