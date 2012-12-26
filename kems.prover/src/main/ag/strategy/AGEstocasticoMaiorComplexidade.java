package main.ag.strategy;

import java.util.Iterator;

import logic.signedFormulas.SignedFormula;

/**
 * Retorna a SignedFormula de acordo com a roleta
 * @author Emerson Shigueo Sugimoto 10-12-2012
 * <p>
 * adaptado de Ricardo Linden, 2006
 * </p>
 * */
public class AGEstocasticoMaiorComplexidade extends AGEstrategia {

	/**
	 * @return a SignedFormula de acordo com a roleta
	 * @author Emerson Shigueo Sugimoto 10-12-2012
	 * */
	@Override
	public SignedFormula getSignedFormula() {
		SignedFormula rt = GetIndividuoRoleta(this.Roleta());
		//evitar a re-seleção
		while(getListaFormulasJaSelecionadas().contains(rt)) {
			rt = GetIndividuoRoleta(this.Roleta());
		}
		getListaFormulasJaSelecionadas().add(rt);
		return rt;
	}

	/**
	 * @return soma das avalia&ccedil;&otilde;es de todos os indiv&iacute;duos da &aacute;rvore de prova, ou seja, a soma<br />
	 * do <i>fitness</i> de cada indiv&iacute;duo.
	 * */
	public long GetSomaAvaliacoes(){
		if (getPblist()==null || getPblist().size() <= 0) {return 0;}
		long rt = 0;
		for (Iterator<SignedFormula> sgF = getPblist().iterator();sgF.hasNext();) {
			rt += ((SignedFormula)sgF.next()).getComplexity();
		}
		return rt;
	}
	
	/**
	 * A avalia&ccedil;&atilde;o de cada indiv&iacute;duo serve como peso e infere diretamente na probabilidade de que um indiv&iacute;duo seja
	 * sorteado ou n&atilde;o. Quanto mair o peso, maior &eacute; a probabilidade de que o indv&iacute;duo seja sorteado.
	 * @return Sorteia uma posi&ccedil;&atilde;o da roleta. A posi&ccedil;&atilde;o representa um indiv&iacute;duo.
	 * */
	public int Roleta(){
		if (getPblist()==null || getPblist().size() <= 0) {return -1;}
		int i; double aux = 0;
		double limite = Math.random() * this.GetSomaAvaliacoes();
		for(i = 0; ( (i < getPblist().size()) && (aux < limite) ); ++i){
			aux += getPblist().get(i).getComplexity();
		}
		i--;
		return i;
	}
	
	/**
	 * @param pos Posi&ccedil;&atilde;o do indiv&iacute;duo na roleta
	 * @return O indiv&iacute;duo a partir de sua posi&ccedil;&atilde;o. 
	 * */
	public SignedFormula GetIndividuoRoleta(int pos){
		if (pos < 0) { return null; }
		return getPblist().get(pos);
	}
	
	/*//código retirado de Linden, 2006. página 64.
	public int roleta(){
		int i;
		double aux = 0;
		calculaSomaAvaliacoes();
		double limite = Math.random() * this.somaAvaliacoes();
		for(i = 0; ( (i < this.populacao.size()) && (aux < limite) ); ++i){
			aux += ((ElementoGA) populacao.get(i)).getAvaliacao();
		}
		i--;
		return i;
	}*/

	
}