package main.newstrategy.simple.ag.util.estrategias.hibrido;

import logic.signedFormulas.PBCandidateList;
import logic.signedFormulas.SignedFormula;
import main.newstrategy.simple.ag.util.AGUtil;
import main.newstrategy.simple.ag.util.estrategias.AGElitistaFrequenciaAtomos;
import main.newstrategy.simple.ag.util.estrategias.AGElitistaMaiorComplexidade;
import main.newstrategy.simple.ag.util.estrategias.AGEstrategia;

/**
 * Abordagem Elitista Híbrida<br />
 * 1) Maior Complexidade<br />
 * 2) Frequência Átomos<br />
 * */
public class AGElitistaHibridoComplexidadeFreqAtomos extends AGEstrategia {
	private AGElitistaMaiorComplexidade _aGElitistaMaiorComplexidade = null;
	private AGElitistaFrequenciaAtomos _aGElitistaFrequenciaAtomos = null;
	private AGUtil _aGUtil;
	public AGElitistaMaiorComplexidade getAGElitistaMaiorComplexidade() {return _aGElitistaMaiorComplexidade;}
	public void setAGElitistaMaiorComplexidade(AGElitistaMaiorComplexidade aGElitistaMaiorComplexidade) {this._aGElitistaMaiorComplexidade = aGElitistaMaiorComplexidade;}
	public AGElitistaFrequenciaAtomos getAGElitistaFrequenciaAtomos() {return _aGElitistaFrequenciaAtomos;}
	public void setAGElitistaFrequenciaAtomos(AGElitistaFrequenciaAtomos aGElitistaFrequenciaAtomos) { this._aGElitistaFrequenciaAtomos = aGElitistaFrequenciaAtomos;}
	public AGUtil getAGUtil() {return _aGUtil;}
	public void setAGUtil(AGUtil aGUtil) {this._aGUtil = aGUtil;}
	
	/**
	 * Abordagem Elitista Híbrida<br />
	 * 1) Maior Complexidade<br />
	 * 2) Frequência Átomos<br />
	 * @autor Emerson Shigueo Sugimoto
	 * */
	@Override
	public SignedFormula getSignedFormula() {
		configAGElitistaMaiorComplexidade();
		configAGUtil();
		SignedFormula rt = getAGElitistaMaiorComplexidade().getSignedFormula();
		//evitar a re-seleção
//		while(getListaFormulasJaSelecionadas().contains(rt)) {
//			rt = getAGElitistaMaiorComplexidade().getSignedFormula();
//		}
		PBCandidateList listaFormulasMesmaComplexi = getAGUtil().getListaMesmaComplexidade(rt,  this.getPblist());
		if (listaFormulasMesmaComplexi==null || listaFormulasMesmaComplexi.size() <= 1) {
			if (rt!=null) getListaFormulasJaSelecionadas().add(rt);
			return rt;
		}
		//existem mais fórmulas com a mesma complexidade
		configAGElitistaFrequenciaAtomos();
		rt = getAGElitistaFrequenciaAtomos().getSignedFormula();
		//evitar a re-seleção
//		while(getListaFormulasJaSelecionadas().contains(rt)) {
//			rt = getAGElitistaFrequenciaAtomos().getSignedFormula();
//		}
		if (rt!=null) getListaFormulasJaSelecionadas().add(rt);
		return rt;
	}
	
	private void configAGElitistaMaiorComplexidade(){
		if (getAGElitistaMaiorComplexidade()!=null){return;}
		setAGElitistaMaiorComplexidade(new AGElitistaMaiorComplexidade());
		getAGElitistaMaiorComplexidade().setListaFormulasJaSelecionadas(this.getListaFormulasJaSelecionadas());
		getAGElitistaMaiorComplexidade().setPblist(this.getPblist());
		getAGElitistaMaiorComplexidade().setStrategy(this.getStrategy());
	}

	private void configAGElitistaFrequenciaAtomos(){
		if (getAGElitistaFrequenciaAtomos()!=null){return;}
		setAGElitistaFrequenciaAtomos(new AGElitistaFrequenciaAtomos());
		getAGElitistaFrequenciaAtomos().setListaFormulasJaSelecionadas(this.getListaFormulasJaSelecionadas());
		getAGElitistaFrequenciaAtomos().setPblist(this.getPblist());
		getAGElitistaFrequenciaAtomos().setStrategy(this.getStrategy());
	}
	private void configAGUtil(){
		if (getAGUtil()!=null){return;}
		setAGUtil(new AGUtil());
	}

}