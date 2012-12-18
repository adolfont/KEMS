package main.newstrategy.simple.ag.util.estrategias;

import java.util.ArrayList;

import logic.signedFormulas.PBCandidateList;
import logic.signedFormulas.SignedFormula;
import main.newstrategy.ISimpleStrategy;

/**
 * Interface das estratégias de AG desenvolvidas
 * @author Emerson Shigueo Sugimoto
 * */
public interface IEstrategiaAG {
	
	public PBCandidateList getPblist();
	public void setPblist(PBCandidateList pblist);
	public ISimpleStrategy getStrategy();
	public void setStrategy(ISimpleStrategy strategy);
	public ArrayList<SignedFormula> getListaFormulasJaSelecionadas();
	public void setListaFormulasJaSelecionadas(ArrayList<SignedFormula> listaFormulasJaSelecionadas);
	
	/**
	 * Método principal<br />
	 * Escolhe a fórmula da listas de regras PB (PBCandidateList)<br />
	 * Atualiza a lista de fórmulas PB selecionadas
	 * @see setListaFormulasJaSelecionadas(ArrayList&lt;SignedFormula&gt; listaFormulasJaSelecionadas);
	 * @author Emerson Shigueo Sugimoto
	 * */
	public SignedFormula getSignedFormula();
}