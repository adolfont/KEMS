package main.newstrategy.simple.ag.util.estrategias;

import java.util.HashMap;
import java.util.Iterator;

import logic.formulas.AtomicFormula;
import logic.signedFormulas.SignedFormula;
import main.newstrategy.simple.ag.util.AnaliseNumeroAtomos;

/**
 * Retorna a SignedFormula de acordo com a roleta
 * @author Emerson Shigueo Sugimoto
 * adaptado de Ricardo Linden, 2006
 * */
public class AGEstocasticoFrequenciaAtomos extends AGEstrategia {
	
	private AnaliseNumeroAtomos _analiseNumeroAtomos;
	private HashMap<AtomicFormula, Integer> _hAtomosEstrategia;
	public HashMap<AtomicFormula, Integer> getHAtomosEstrategia() {return _hAtomosEstrategia;}
	public void setHAtomosEstrategia(HashMap<AtomicFormula, Integer> hAtomosEstrategia) {this._hAtomosEstrategia = hAtomosEstrategia;}
	public AnaliseNumeroAtomos getAnaliseNumeroAtomos() {return _analiseNumeroAtomos;}
	public void setAnaliseNumeroAtomos(AnaliseNumeroAtomos analiseNumeroAtomos) {this._analiseNumeroAtomos = analiseNumeroAtomos;}
	
	/**
	 * @return a SignedFormula de acordo com a roleta
	 * @author Emerson Shigueo Sugimoto
	 * */
	@Override
	public SignedFormula getSignedFormula() {
		if (getAnaliseNumeroAtomos()==null) {setAnaliseNumeroAtomos(new AnaliseNumeroAtomos());}
		updateHashStrategy();
		
		SignedFormula rt = GetIndividuoRoleta(this.Roleta());
		//evitar a re-seleção
		while(getListaFormulasJaSelecionadas().contains(rt)) {
			rt = GetIndividuoRoleta(this.Roleta());
		}
		getListaFormulasJaSelecionadas().add(rt);
		return rt;
	}
	
	private void updateHashStrategy(){
		if (getHAtomosEstrategia()!=null && getHAtomosEstrategia().size() > 0) {return;}
		this.setHAtomosEstrategia(
				getAnaliseNumeroAtomos().getHashAtomosEstrategia(
						getAnaliseNumeroAtomos().toList(
						getStrategy().getProofTree().getSignedFormulasToNodes(),
						getPblist().getListSignedFormula()
						))
		);
		if (getHAtomosEstrategia()!=null && getHAtomosEstrategia().size() > 0) {return;}
		//problemas php -> SignedFormulas da árvore iguais as SignedFormulas da lista PB
		this.setHAtomosEstrategia(
				getAnaliseNumeroAtomos().getHashAtomosEstrategia(
						getAnaliseNumeroAtomos().toList(
						getStrategy().getProofTree().getSignedFormulasToNodes()
						))
		);
	}
	
	private int getAvaliacaoIndividual(SignedFormula sf,boolean sumPbFrequency,boolean ignoreEmptyAtoms){
		updateHashStrategy();
		return getAnaliseNumeroAtomos().GetAvaliacaoIndividual(
				sf, 
				getHAtomosEstrategia(),
				sumPbFrequency, ignoreEmptyAtoms
			);
	}

	public long GetSomaAvaliacoes(){
		updateHashStrategy();
		if (getPblist()==null || getPblist().size() <= 0) {return 0;}
		long rt = 0;
		for (Iterator<SignedFormula> sgF = getPblist().iterator();sgF.hasNext();) {
			rt += getAvaliacaoIndividual((SignedFormula)sgF.next(), true, true);
		}
		return rt;
	}
	
	public int Roleta(){
		updateHashStrategy();
		if (getPblist()==null || getPblist().size() <= 0) {return -1;}
		int i; double aux = 0;
		double limite = Math.random() * this.GetSomaAvaliacoes();	
		for(i = 0; ( (i < getPblist().size()) && (aux < limite) ); ++i){
			aux += getAvaliacaoIndividual(getPblist().get(i), true, true);
		}
		i--;
		return i;
	}
	
	public SignedFormula GetIndividuoRoleta(int pos){
		if (pos < 0) { return null; }
		updateHashStrategy();
		return getPblist().get(pos);
	}

}