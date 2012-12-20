package main.newstrategy.simple.ag.util.estrategias;

import logic.formulas.CompositeFormula;
import logic.formulas.Formula;
import logic.signedFormulas.SignedFormula;

/**
 * Seleciona a fórmula com maior número de conectivos da lista PBCandidateList
 * @author Emerson Shigueo Sugimoto
 * */
public class AGElitistaConectivos extends AGEstrategia {

	@Override
	public SignedFormula getSignedFormula() {
		SignedFormula rt = getSignedFormulaMaiorNumeroConectivos(); //evita a re-seleção
		getListaFormulasJaSelecionadas().add(rt);
		return rt;
	}
	
	/**
	 * @return a fórmula com maior número de conectivos da lista PBCandidateList
	 * */
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
	public int getNumeroConectivos(SignedFormula sf){
		return getNumeroConectivos(sf.getFormula());
	}
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