package main.ag.strategy;

import logic.signedFormulas.SignedFormula;
import main.ag.strategy.util.AnaliseNumeroAtomos;

/**
 * Seleciona com base na frequ&ecirc;ncia de &aacute;tomos das regras PB e das f&oacute;rmulas da &aacute;rvore
 * @author Emerson Shigueo Sugimoto 10-12-2012
 * */
public class AGElitistaFrequenciaAtomos extends AGEstrategia {
	
	/**
	 * @return SignedFormula com maior frequ&ecirc;ncia da &aacute;rvore 
	 * */
	@Override
	public SignedFormula getSignedFormula() {
		AnaliseNumeroAtomos ana = new AnaliseNumeroAtomos();
		SignedFormula rt = ana.getSFAnaliseAtomos(getListaFormulasJaSelecionadas(), getPblist().getListSignedFormula(),getStrategy(),true,true);
		//if (rt == null){rt = ana.getSFAnaliseAtomos(getListaFormulasJaSelecionadas(), getPblist().getListSignedFormula(),getStrategy(),false,false);}
		//evitar a re-seleção
		while(getListaFormulasJaSelecionadas().contains(rt)) {
			rt = ana.getSFAnaliseAtomos(getListaFormulasJaSelecionadas(), getPblist().getListSignedFormula(),getStrategy(),true,true);
		}
		if (rt!=null) getListaFormulasJaSelecionadas().add(rt);
		return rt;
	}

}