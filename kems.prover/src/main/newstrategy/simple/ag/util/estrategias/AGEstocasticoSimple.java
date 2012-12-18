package main.newstrategy.simple.ag.util.estrategias;

import java.util.Iterator;

import logic.signedFormulas.SignedFormula;

/**
 * Retorna a SignedFormula de acordo com a roleta
 * @author Emerson Shigueo Sugimoto
 * adaptado de Ricardo Linden, 2006
 * */
public class AGEstocasticoSimple extends AGEstrategia {

	/**
	 * @return a SignedFormula de acordo com a roleta
	 * @author Emerson Shigueo Sugimoto
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

	public long GetSomaAvaliacoes(){
		if (getPblist()==null || getPblist().size() <= 0) {return 0;}
		long rt = 0;
		for (Iterator<SignedFormula> sgF = getPblist().iterator();sgF.hasNext();) {
			rt += ((SignedFormula)sgF.next()).getComplexity();
		}
		return rt;
	}
	
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