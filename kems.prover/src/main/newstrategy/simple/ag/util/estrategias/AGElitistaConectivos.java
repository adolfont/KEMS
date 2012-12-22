package main.newstrategy.simple.ag.util.estrategias;

import logic.formulas.CompositeFormula;
import logic.formulas.Formula;
import logic.signedFormulas.SignedFormula;

/**
 * Seleciona a fórmula com maior número de conectivos da lista PBCandidateList
 * @Deprecated Resultados semelhantes a abordagem Elitista de Maior complexidade, pois, de forma semelhante,
 * quanto maior o número de conectivos, maior a complexidade. Desta forma use a classe AGElitistaMaiorComplexidade. 
 * @author Emerson Shigueo Sugimoto
 * */
@Deprecated
public class AGElitistaConectivos extends AGEstrategia {

	@Deprecated
	@Override
	public SignedFormula getSignedFormula() {
		SignedFormula rt = getSignedFormulaMaiorNumeroConectivos(); //evita a re-seleção
		getListaFormulasJaSelecionadas().add(rt);
		return rt;
	}
	
	/**
	 * @return a fórmula com maior número de conectivos da lista PBCandidateList
	 * */
	@Deprecated
	public SignedFormula getSignedFormulaMaiorNumeroConectivos(){
		SignedFormula rt = null;
		int i = 0; SignedFormula posSig;
		for(i = 0; i < getPblist().size(); ++i){
			posSig = getPblist().get(i);
			if (getListaFormulasJaSelecionadas().contains(posSig)) {continue;} //já selecionado
			
			if (rt == null) {
				rt = posSig;
			} else {
				if (getNumeroConectivos(rt) < getNumeroConectivos(posSig)){
					rt = posSig; //update
				}
			}
		}
		return rt;
	}
	
	/**
	 * @return numero de conectivos de uma SignedFormula
	 * */
	@Deprecated
	public int getNumeroConectivos(SignedFormula sf){
		return getNumeroConectivos(sf.getFormula());
	}
	@Deprecated
	private int getNumeroConectivos(Formula f){
		int rt = 0;
		if (!(f instanceof CompositeFormula)){ 
			//System.out.println("r1: " + f.toString());
			return rt;
		} else {
			rt++;
		}
		
		if (f.getImmediateSubformulas().size()==0) {
			rt++;
			return rt;
		}
		for (int i = 0; i < f.getImmediateSubformulas().size(); ++i) {
			rt += getNumeroConectivos(f.getImmediateSubformulas().get(i));
		}
		return rt;
	}
	
}