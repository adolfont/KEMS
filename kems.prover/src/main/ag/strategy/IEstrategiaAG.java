package main.ag.strategy;

import java.util.ArrayList;

import logic.signedFormulas.PBCandidateList;
import logic.signedFormulas.SignedFormula;
import main.newstrategy.ISimpleStrategy;

/**
 * Interface das estrat&eacute;gias de AG desenvolvidas
 * @author Emerson Shigueo Sugimoto 10-12-2012
 * */
public interface IEstrategiaAG {
	
	/**
	 * @return Lista PBCandidateList
	 * */
	public PBCandidateList getPblist();
	/**
	 * Set PBCandidateList
	 * @param pblist PBCandidateList
	 * */
	public void setPblist(PBCandidateList pblist);
	/**
	 * @return Estrat&eacute;gia. Usado principalmente para recuperar a &aacute;rvore de prova.
	 * */
	public ISimpleStrategy getStrategy();
	/**
	 * Set Strategy
	 * @param strategy ISimpleStrategy
	 * @see #ISimpleStrategy
	 * */
	public void setStrategy(ISimpleStrategy strategy);
	/**
	 * @return Lista de controle da f&oacute;rmulas (SignedFormula) escolhidas
	 * */
	public ArrayList<SignedFormula> getListaFormulasJaSelecionadas();
	/**
	 * Set ListaFormulasJaSelecionadas
	 * @param listaFormulasJaSelecionadas Lista de F&oacute;rmulas j&aacute; selecionadas.
	 * */
	public void setListaFormulasJaSelecionadas(ArrayList<SignedFormula> listaFormulasJaSelecionadas);
	
	/**
	 * M&eacute;todo principal<br />
	 * Escolhe a f&oacute;rmula da listas de regras PB (PBCandidateList)<br />
	 * Atualiza a lista de f&oacute;rmulas PB selecionadas
	 * @see #setListaFormulasJaSelecionadas(ArrayList)
	 * @author Emerson Shigueo Sugimoto
	 * */
	public SignedFormula getSignedFormula();
}