package main.ag.strategy.util;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

/**
 * Contador de Bifurca&ccedil;&otilde;es da prova (aplica&ccedil;&atilde;o regra PB)
 * @author Emerson Shigueo Sugimoto 10-12-2012
 * */
public class MemoriaCompartilhada {
	private static final String _nomeArquivo = "MemoryTempPB.txt";
	
	/**
	 * @return N&uacute;mero de Bifurca&ccedil;&otilde;es
	 * */
	public static int getNumero() {return LerArquivo();}
	/**
	 * @param numero Set N&uacute;mero de Bifurca&ccedil;&otilde;es
	 * */
	public static void setNumero(int numero) {SalvarArquivo(numero);}

	/**
	 * Adiciona uma bifurca&ccedil;&atilde;o
	 * */
	public static void Add(){setNumero(getNumero()+1);}
	/**
	 * Limpa a contagem
	 * */
	public static void Clear(){ClearFile();}
	
	/**
	 * @return N&uacute;mero de Bifurca&ccedil;&otilde;es
	 * */
	private static int LerArquivo(){
		int rt = 0;
		File arquivo = new File(_nomeArquivo);
		if (!arquivo.exists()) {return 0;}
		try {
	      FileReader arq = new FileReader(arquivo);
	      BufferedReader lerArq = new BufferedReader(arq);
	 
	      String linha = lerArq.readLine();
	      rt = Integer.parseInt(linha);
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
	 * @param valor N&uacute;mero de Bifurca&ccedil;&otilde;es
	 * */
	private static void SalvarArquivo(int valor){
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