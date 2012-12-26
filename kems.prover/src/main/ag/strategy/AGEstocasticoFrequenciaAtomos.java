package main.ag.strategy;

import java.util.HashMap;
import java.util.Iterator;

import logic.formulas.AtomicFormula;
import logic.signedFormulas.SignedFormula;
import main.ag.strategy.util.AnaliseNumeroAtomos;

/**
 * Retorna a SignedFormula de acordo com a roleta
 * @author Emerson Shigueo Sugimoto 10-12-2012
 * <p>
 * adaptado de Ricardo Linden, 2006
 * </p>
 * */
public class AGEstocasticoFrequenciaAtomos extends AGEstrategia {
	private AnaliseNumeroAtomos _analiseNumeroAtomos;
	private HashMap<AtomicFormula, Integer> _hAtomosEstrategia;
	public HashMap<AtomicFormula, Integer> getHAtomosEstrategia() {return _hAtomosEstrategia;}
	public void setHAtomosEstrategia(HashMap<AtomicFormula, Integer> hAtomosEstrategia) {this._hAtomosEstrategia = hAtomosEstrategia;}
	public AnaliseNumeroAtomos getAnaliseNumeroAtomos() {return _analiseNumeroAtomos;}
	public void setAnaliseNumeroAtomos(AnaliseNumeroAtomos analiseNumeroAtomos) {this._analiseNumeroAtomos = analiseNumeroAtomos;}
	
	/**
	 * @return SignedFormula de acordo com a roleta
	 * @author Emerson Shigueo Sugimoto 10-12-2012
	 * */
	@Override
	public SignedFormula getSignedFormula() {
		if (getAnaliseNumeroAtomos()==null) {setAnaliseNumeroAtomos(new AnaliseNumeroAtomos());}
		updateHashStrategy();
		
		SignedFormula rt = GetIndividuoRoleta(this.Roleta());
		//evitar a re-seleção
		while(getListaFormulasJaSelecionadas().contains(rt)) {
			rt = GetIndividuoRoleta(this.Roleta());
		}
		getListaFormulasJaSelecionadas().add(rt);
		return rt;
	}
	
	/**
	 * Atualiza o HashMap&lt;AtomicFormula, Integer&gt; _hAtomosEstrategia.<br />
	 * Este deve conter a frequ&ecirc;ncia de &aacute;tomos da &aacute;rvore de prova.<br />
	 * A frequ&ecirc;ncia ignora a f&oacute;rmulas contidas na lista PBCandidateList<br />
	 * @author Emerson Shigueo Sugimoto 10-12-2012
	 * */
	private void updateHashStrategy(){
		if (getHAtomosEstrategia() != null && getHAtomosEstrategia().size() > 0) {return;}
		this.setHAtomosEstrategia(
				getAnaliseNumeroAtomos().getHashAtomosEstrategia(
						getAnaliseNumeroAtomos().toList(
						getStrategy().getProofTree().getSignedFormulasToNodes(),
						getPblist().getListSignedFormula()
						))
		);
		if (getHAtomosEstrategia() != null && getHAtomosEstrategia().size() > 0) {return;}
		//problemas php -> SignedFormulas da árvore iguais as SignedFormulas da lista PB
		this.setHAtomosEstrategia(
				getAnaliseNumeroAtomos().getHashAtomosEstrategia(
						getAnaliseNumeroAtomos().toList(
						getStrategy().getProofTree().getSignedFormulasToNodes()
						))
		);
	}
	
	/**
	 * @param sf SignedFormula a ser avaliada<br />
	 * @param sumPbFrequency <strong>true</strong>: soma a frequ&ecirc;ncia dos &aacute;tomos das SignedFormula contidas na lista PBCandidateList<br />
	 * @param ignoreEmptyAtoms <strong>true</strong>: SignedFormulas da PBCandidateList com &aacute;tomos que n&atilde;o existem na &aacute;rvore de prova
	 * n&atilde;o possuem seu <i>fitness</i> zerados.<br />
	 * @return a avalia&ccedil;&atilde;o individual (<i>fitness</i>) de uma SignedFormula
	 * */
	private int getAvaliacaoIndividual(SignedFormula sf,boolean sumPbFrequency,boolean ignoreEmptyAtoms){
		updateHashStrategy();
		return getAnaliseNumeroAtomos().GetAvaliacaoIndividual(
				sf, 
				getHAtomosEstrategia(),
				sumPbFrequency, ignoreEmptyAtoms
			);
	}
	
	/**
	 * @return soma das avalia&ccedil;&otilde;es de todos os indiv&iacute;duos da &aacute;rvore de prova, ou seja, a soma<br />
	 * do <i>fitness</i> de cada indiv&iacute;duo.
	 * */
	public long GetSomaAvaliacoes(){
		updateHashStrategy();
		if (getPblist()==null || getPblist().size() <= 0) {return 0;}
		long rt = 0;
		for (Iterator<SignedFormula> sgF = getPblist().iterator();sgF.hasNext();) {
			rt += getAvaliacaoIndividual((SignedFormula)sgF.next(), true, true);
		}
		return rt;
	}
	
	/**
	 * A avalia&ccedil;&atilde;o de cada indiv&iacute;duo serve como peso e infere diretamente na probabilidade de que um indiv&iacute;duo seja
	 * sorteado ou n&atilde;o. Quanto mair o peso, maior &eacute; a probabilidade de que o indv&iacute;duo seja sorteado.
	 * @return Sorteia uma posi&ccedil;&atilde;o da roleta. A posi&ccedil;&atilde;o representa um indiv&iacute;duo.
	 * */
	public int Roleta(){
		updateHashStrategy();
		if (getPblist()==null || getPblist().size() <= 0) {return -1;}
		int i; double aux = 0;
		double limite = Math.random() * this.GetSomaAvaliacoes();	
		for(i = 0; ( (i < getPblist().size()) && (aux < limite) ); ++i){
			aux += getAvaliacaoIndividual(getPblist().get(i), true, true);
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
		updateHashStrategy();
		return getPblist().get(pos);
	}

}