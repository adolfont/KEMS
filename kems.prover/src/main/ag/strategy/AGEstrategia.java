package main.ag.strategy;

import java.util.ArrayList;

import logic.signedFormulas.PBCandidateList;
import logic.signedFormulas.SignedFormula;
import main.newstrategy.ISimpleStrategy;

/**
 * Classe Abstrata das estrat&eacute;gias
 * @author Emerson Shigueo Sugimoto 10-12-2012
 * */
public abstract class AGEstrategia implements IEstrategiaAG {
	private PBCandidateList _pbList;
	private ISimpleStrategy _strategy;
	private ArrayList<SignedFormula> _listaFormulasJaSelecionadas = null;
	
	@Override
	public PBCandidateList getPblist() {return _pbList;}
	@Override
	public void setPblist(PBCandidateList pblist) {_pbList = pblist;}

	@Override
	public ISimpleStrategy getStrategy() {return _strategy;}
	@Override
	public void setStrategy(ISimpleStrategy strategy) {_strategy = strategy;}

	@Override
	public ArrayList<SignedFormula> getListaFormulasJaSelecionadas() {
		if (_listaFormulasJaSelecionadas==null) _listaFormulasJaSelecionadas = new ArrayList<SignedFormula>();
		return _listaFormulasJaSelecionadas;
	}
	@Override
	public void setListaFormulasJaSelecionadas(ArrayList<SignedFormula> listaFormulasJaSelecionadas) {_listaFormulasJaSelecionadas = listaFormulasJaSelecionadas;}
	
	@Override
	public abstract SignedFormula getSignedFormula();
}