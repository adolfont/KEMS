package main.newstrategy.simple.ag.util.estrategias;

import logic.signedFormulas.SignedFormula;
import main.newstrategy.simple.ag.util.AnaliseNumeroAtomos;

/**
 * Seleciona com base na frequência de átomos das regras PB e das fórmulas da árvore
 * @author Emerson Shigueo Sugimoto
 * */
public class AGElitistaFrequenciaAtomos extends AGEstrategia {

	@Override
	public SignedFormula getSignedFormula() {
		AnaliseNumeroAtomos ana = new AnaliseNumeroAtomos();
		SignedFormula rt = ana.getSFAnaliseAtomos(getListaFormulasJaSelecionadas(), getPblist().getListSignedFormula(),getStrategy(),true,true);
		//if (rt == null){rt = ana.getSFAnaliseAtomos(getListaFormulasJaSelecionadas(), getPblist().getListSignedFormula(),getStrategy(),false,false);}
		//evitar a re-seleção
		while(getListaFormulasJaSelecionadas().contains(rt)) {
			rt = ana.getSFAnaliseAtomos(getListaFormulasJaSelecionadas(), getPblist().getListSignedFormula(),getStrategy(),true,false);
		}
		if (rt!=null) getListaFormulasJaSelecionadas().add(rt);
		return rt;
	}

}