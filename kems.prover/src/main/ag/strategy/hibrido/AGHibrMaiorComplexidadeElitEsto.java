package main.ag.strategy.hibrido;

import logic.signedFormulas.PBCandidateList;
import logic.signedFormulas.SignedFormula;
import main.ag.strategy.AGElitistaMaiorComplexidade;
import main.ag.strategy.AGEstocasticoMaiorComplexidade;
import main.ag.strategy.AGEstrategia;
import main.ag.strategy.util.AGUtil;

/**
 * Abordagem H&iacute;brida Maior Complexidade<br />
 * 1) Elitista<br />
 * 2) Estoc&aacute;stica<br />
 * @author Emerson Shigueo Sugimoto 22-12-2012
 * */
public class AGHibrMaiorComplexidadeElitEsto extends AGEstrategia {
	private AGElitistaMaiorComplexidade _aGElitistaMaiorComplexidade = null;
	private AGEstocasticoMaiorComplexidade _aGEstocasticoMaiorComplexidade;
	private AGUtil _aGUtil;
	public AGElitistaMaiorComplexidade getAGElitistaMaiorComplexidade() {return _aGElitistaMaiorComplexidade;}
	public void setAGElitistaMaiorComplexidade(AGElitistaMaiorComplexidade aGElitistaMaiorComplexidade) {this._aGElitistaMaiorComplexidade = aGElitistaMaiorComplexidade;}
	public AGEstocasticoMaiorComplexidade getAGEstocasticoMaiorComplexidade() {return _aGEstocasticoMaiorComplexidade;}
	public void setAGEstocasticoMaiorComplexidade(AGEstocasticoMaiorComplexidade aGEstocasticoMaiorComplexidade) {this._aGEstocasticoMaiorComplexidade = aGEstocasticoMaiorComplexidade;}
	public AGUtil getAGUtil() {return _aGUtil;}
	public void setAGUtil(AGUtil aGUtil) {this._aGUtil = aGUtil;}
	
	/**
	 * Abordagem H&iacute;brida Maior Complexidade<br />
	 * 1) Elitista<br />
	 * 2) Estoc&aacute;stica<br />
	 * @author Emerson Shigueo Sugimoto 22-12-2012
	 * */
	@Override
	public SignedFormula getSignedFormula() {
		configAGElitistaMaiorComplexidade();
		configAGUtil();
		SignedFormula rt = getAGElitistaMaiorComplexidade().getSignedFormula();
		//evitar a re-seleção - é tratado pelo AGUtil
		//while(getListaFormulasJaSelecionadas().contains(rt)) { rt = getAGElitistaMaiorComplexidade().getSignedFormula(); }

		PBCandidateList listaFormulasMesmaComplexi = 
				getAGUtil().getListaMesmaComplexidade(rt,
						this.getPblist(), 
						this.getListaFormulasJaSelecionadas());
		if (listaFormulasMesmaComplexi == null || listaFormulasMesmaComplexi.size() <= 1) {
			if (rt != null) getListaFormulasJaSelecionadas().add(rt);
			return rt;
		}
		//existem mais fórmulas com a mesma complexidade
		//-------------- estocástica
		if (getAGEstocasticoMaiorComplexidade()==null){
			setAGEstocasticoMaiorComplexidade(new AGEstocasticoMaiorComplexidade());
		}
		getAGEstocasticoMaiorComplexidade().setPblist(listaFormulasMesmaComplexi);
		getAGEstocasticoMaiorComplexidade().setStrategy(this.getStrategy());
		rt = getAGEstocasticoMaiorComplexidade().getSignedFormula();
		
		if (rt != null) getListaFormulasJaSelecionadas().add(rt);
		return rt;
	}
	
	/**
	 * Configura&ccedil;&atilde;o da abordagem AGElitistaMaiorComplexidade
	 * */
	private void configAGElitistaMaiorComplexidade(){
		if (getAGElitistaMaiorComplexidade() != null){return;}
		setAGElitistaMaiorComplexidade(new AGElitistaMaiorComplexidade());
		//getAGElitistaMaiorComplexidade().setListaFormulasJaSelecionadas(this.getListaFormulasJaSelecionadas());
		getAGElitistaMaiorComplexidade().setPblist(this.getPblist());
		getAGElitistaMaiorComplexidade().setStrategy(this.getStrategy());
	}
	/**
	 * Configura&ccedil;&atilde;o do AGUtil
	 * */
	private void configAGUtil(){
		if (getAGUtil() != null){return;}
		setAGUtil(new AGUtil());
	}

}