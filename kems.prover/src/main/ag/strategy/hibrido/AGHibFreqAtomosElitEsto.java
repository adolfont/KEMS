package main.ag.strategy.hibrido;

import logic.signedFormulas.PBCandidateList;
import logic.signedFormulas.SignedFormula;
import main.ag.strategy.AGElitistaFrequenciaAtomos;
import main.ag.strategy.AGEstocasticoFrequenciaAtomos;
import main.ag.strategy.AGEstrategia;
import main.ag.strategy.util.AGUtil;
import main.ag.strategy.util.AnaliseNumeroAtomos;

/**
 * Abordagem H&iacute;brida Frequ&ecirc;ncia &Aacute;tomos<br />
 * <ul><li>Elitista</li>
 * <li>Estoc&aacute;stica</li></ul>
 * @author Emerson Shigueo Sugimoto 22-12-2012
 * */
public class AGHibFreqAtomosElitEsto extends AGEstrategia {
	private AGElitistaFrequenciaAtomos _aGElitistaFrequenciaAtomos = null;
	private AGEstocasticoFrequenciaAtomos _aGEstocasticoFrequenciaAtomos;
	private AGUtil _aGUtil;
	private AnaliseNumeroAtomos _ana;

	public AGElitistaFrequenciaAtomos getAGElitistaFrequenciaAtomos() {return _aGElitistaFrequenciaAtomos;}
	public void setAGElitistaFrequenciaAtomos(AGElitistaFrequenciaAtomos aGElitistaFrequenciaAtomos) { this._aGElitistaFrequenciaAtomos = aGElitistaFrequenciaAtomos;}
	public AGUtil getAGUtil() {return _aGUtil;}
	public void setAGUtil(AGUtil aGUtil) {this._aGUtil = aGUtil;}
	public AnaliseNumeroAtomos getAnaliseNumeroAtomos() { return _ana;}
	public void setAnaliseNumeroAtomos(AnaliseNumeroAtomos ana) {this._ana = ana;}
	public AGEstocasticoFrequenciaAtomos getAGEstocasticoFrequenciaAtomos() {return _aGEstocasticoFrequenciaAtomos;}
	public void setAGEstocasticoFrequenciaAtomos(AGEstocasticoFrequenciaAtomos aGEstocasticoFrequenciaAtomos) {this._aGEstocasticoFrequenciaAtomos = aGEstocasticoFrequenciaAtomos;}
	
	/**
	 * Abordagem H&iacute;brida Frequ&ecirc;ncia &Aacute;tomos<br />
	 * <ul><li>Elitista</li>
	 * <li>Estoc&aacute;stica</li></ul>
	 * @Override implementação de {@link main.ag.strategy.IEstrategiaAG} e sobrecarga em 
	 * {@link main.ag.strategy.AGEstrategia}
	 * @author Emerson Shigueo Sugimoto 22-12-2012
	 * */
	@Override
	public SignedFormula getSignedFormula() {
		configAGUtil();
		configAGElitistaFrequenciaAtomos();
		SignedFormula rt = getAGElitistaFrequenciaAtomos().getSignedFormula();
		
		//evitar a re-seleção - é tratado pelo AGUtil
		//while(getListaFormulasJaSelecionadas().contains(rt)) { rt = getAGElitistaFrequenciaAtomos().getSignedFormula(); }
		
		//lista de SignedFormulas com a mesma frequência de átomos
		configAnaliseNumeroAtomos();
		PBCandidateList listaFormulasMesmaFrequenciaAtomos = getAGUtil().getListaMesmaFrequenciaAtomos(
					rt,
					this.getPblist(),
					getAnaliseNumeroAtomos(),
					this.getStrategy(),
					this.getListaFormulasJaSelecionadas()
				);

		if (listaFormulasMesmaFrequenciaAtomos == null || listaFormulasMesmaFrequenciaAtomos.size() <= 1) {
			if (rt!=null) getListaFormulasJaSelecionadas().add(rt);
			return rt;
		}
		//existem mais fórmulas com a mesma complexidade
		//-------------- estocástica
		if (getAGEstocasticoFrequenciaAtomos() == null) {
			setAGEstocasticoFrequenciaAtomos(new AGEstocasticoFrequenciaAtomos());
		}
		getAGEstocasticoFrequenciaAtomos().setPblist(listaFormulasMesmaFrequenciaAtomos);
		getAGEstocasticoFrequenciaAtomos().setStrategy(this.getStrategy());
		rt = getAGEstocasticoFrequenciaAtomos().getSignedFormula();

		if (rt!=null) getListaFormulasJaSelecionadas().add(rt);
		return rt;
	}
	
	/**
	 * Configura&ccedil;&atilde;o da abordagem Elitista
	 * */
	private void configAGElitistaFrequenciaAtomos(){
		if (getAGElitistaFrequenciaAtomos()!=null){return;}
		setAGElitistaFrequenciaAtomos(new AGElitistaFrequenciaAtomos());
		//getAGElitistaFrequenciaAtomos().setListaFormulasJaSelecionadas(this.getListaFormulasJaSelecionadas());
		getAGElitistaFrequenciaAtomos().setPblist(this.getPblist());
		getAGElitistaFrequenciaAtomos().setStrategy(this.getStrategy());
	}
	/**
	 * Configura&ccedil;&atilde;o do AGUtil
	 * */
	private void configAGUtil(){
		if (getAGUtil() != null) { return; }
		setAGUtil(new AGUtil());
	}
	/**
	 * Configura&ccedil;&atilde;o de AnaliseNumeroAtomos
	 * */
	private void configAnaliseNumeroAtomos(){
		if (getAnaliseNumeroAtomos() != null){ return; }
		setAnaliseNumeroAtomos(new AnaliseNumeroAtomos());
	}
}