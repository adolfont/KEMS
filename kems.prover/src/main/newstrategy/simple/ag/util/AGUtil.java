package main.newstrategy.simple.ag.util;

import java.util.HashMap;
import java.util.Map;

import logic.formulas.AtomicFormula;
import logic.formulas.CompositeFormula;
import logic.formulas.Formula;
import logic.signedFormulas.PBCandidateList;
import logic.signedFormulas.SignedFormula;
import main.newstrategy.ISimpleStrategy;
import main.proofTree.INode;

public class AGUtil {
	
	/**
	 * Retorna uma lista de SignedFormula que tenham a mesma complexidade
	 * que o param formulaComparar a partir da lista PBCandidateList
	 * */
	public PBCandidateList getListaMesmaComplexidade(SignedFormula formulaComparar, PBCandidateList pbList) {
		PBCandidateList rt = null;
		SignedFormula posSig;
		for (int i = 0; i < pbList.size(); ++i) {
			posSig = pbList.get(i);
			if (formulaComparar.equals(posSig)) {
				continue;
			}
			if (formulaComparar.getComplexity() != posSig.getComplexity()) {
				continue;
			}
			if (rt == null) {
				rt = new PBCandidateList();
				rt.add(formulaComparar);
			}
			rt.add(posSig);
		}
		return rt;
	}
	
	/**
	 * Retorna uma lista de SignedFormula que tenham a mesma frequência de átomos
	 * que o param formulaComparar a partir da lista PBCandidateList
	 * */
	public PBCandidateList getListaMesmaFrequenciaAtomos(
			SignedFormula formulaComparar, 
			PBCandidateList pbList,
			AnaliseNumeroAtomos ana,
			ISimpleStrategy strategy
			) {
		return getListaMesmaFrequenciaAtomos(
				formulaComparar,
				pbList,
				ana,
				strategy.getProofTree().getSignedFormulasToNodes()
				);		
	}

	/**
	 * Retorna uma lista de SignedFormula que tenham a mesma frequência de átomos
	 * que o param formulaComparar a partir da lista PBCandidateList
	 * */
	public PBCandidateList getListaMesmaFrequenciaAtomos(
			SignedFormula formulaComparar, 
			PBCandidateList pbList,
			AnaliseNumeroAtomos ana,
			Map<SignedFormula, INode> map
			) {
		return getListaMesmaFrequenciaAtomos(
				formulaComparar, 
				pbList,
				ana,
				ana.getHashAtomosEstrategia(ana.toList(map, pbList.getListSignedFormula()))
				);
	}
	
	/**
	 * Retorna uma lista de SignedFormula que tenham a mesma frequência de átomos
	 * que o param formulaComparar a partir da lista PBCandidateList
	 * */
	public PBCandidateList getListaMesmaFrequenciaAtomos(
			SignedFormula formulaComparar, 
			PBCandidateList pbList,
			AnaliseNumeroAtomos ana,
			HashMap<AtomicFormula, Integer> hAtomosEstrategia
			) {

		PBCandidateList rt = null;
		SignedFormula posSig;
		for (int i = 0; i < pbList.size(); ++i) {
			posSig = pbList.get(i);
			if (formulaComparar.equals(posSig)) {
				continue;
			}
			
			if (ana.GetAvaliacaoIndividual(
					formulaComparar, 
					hAtomosEstrategia,
					true, true
				)
				!= 
				ana.GetAvaliacaoIndividual(
						posSig, 
						hAtomosEstrategia,
						true, true
					)
					) {
				continue;
			}
			
			if (rt == null) {
				rt = new PBCandidateList();
				rt.add(formulaComparar);
			}
			rt.add(posSig);
		}
		return rt;
	}
	

	/**
	 * @return Retorna a fórmula com o maior número de conectivos prioritários
	 * @see getNumeroConectivosPrioridade(SignedFormula sf);
	 * */
	public SignedFormula getSFPrioridadeConectivos(PBCandidateList listaMesmaComplexidade) {
		int i = 0;
		int nCp = 0;
		int nCpSel = 0;
		SignedFormula sgF = null, rt = null;
		for (i = 0; i < listaMesmaComplexidade.size(); i++) {
			sgF = listaMesmaComplexidade.get(i);
			nCp = getNumeroConectivosPrioridade(sgF);
			if (i == 0) {
				nCpSel = nCp;
				rt = sgF;
			} else if (nCpSel < nCp) { // update
				nCpSel = nCp;
				rt = sgF;
			}
		}
		return rt;
	}
	
	/**
	 * Prioriza o conectivo 'ou'
	 * */
	public SignedFormula getSFPrioridadeConectivoOu(PBCandidateList listaMesmaComplexidade) {
		int i = 0;
		int nCp = 0;
		int nCpSel = 0;
		SignedFormula sgF = null, rt = null;
		for (i = 0; i < listaMesmaComplexidade.size(); i++) {
			sgF = listaMesmaComplexidade.get(i);
			nCp = getNumeroConectivosPrioridadeOu(sgF);
			if (i == 0) {
				nCpSel = nCp;
				rt = sgF;
			} else if (nCpSel < nCp) { // update
				nCpSel = nCp;
				rt = sgF;
			}
		}
		return rt;
	}
	
	public int getNumeroConectivosPrioridade(SignedFormula sf){
		return getNumeroConectivosPrioridade(sf.getFormula(), sf.getSign().toString());
	}
	private int getNumeroConectivosPrioridade(Formula f, String signo){
		int rt = 0;
		if (!(f instanceof CompositeFormula)){ 
			//System.out.println("r1: " + f.toString());
			return rt;
		} else {
			if ((verificaSimboloPrioritario(((CompositeFormula)f).getConnective().getSymbol(), signo))){
				rt++;			
			}
		}
		
		if (f.getImmediateSubformulas().size()==0) {
			if (verificaSimboloPrioritario(((CompositeFormula)f).getConnective().getSymbol(), signo)) {
				rt++;
			}
			return rt;
		}
		for (int i = 0; i < f.getImmediateSubformulas().size(); ++i) {
			rt += getNumeroConectivosPrioridade(f.getImmediateSubformulas().get(i), signo);
		}
		return rt;
	}
	private boolean verificaSimboloPrioritario(String simbolo, String signo){
		return (signo.equals("T") && simbolo.equals("&")) ||
				(signo.equals("F") && simbolo.equals("|")) ||
				(signo.equals("F") && simbolo.equals("->")) ||
				simbolo.equals("!");
	}

	//conectivo 'ou'
	public int getNumeroConectivosPrioridadeOu(SignedFormula sf){
		return getNumeroConectivosPrioridadeOu(sf.getFormula());
	}
	private int getNumeroConectivosPrioridadeOu(Formula f){
		int rt = 0;
		if (!(f instanceof CompositeFormula)){ 
			//System.out.println("r1: " + f.toString());
			return rt;
		} else {
			if (((CompositeFormula)f).getConnective().getSymbol().equals("|")) {
				rt++;
			}
		}
		
		if (f.getImmediateSubformulas().size()==0) {
			if (((CompositeFormula)f).getConnective().getSymbol().equals("|")) {
				rt++;
			}
			return rt;
		}
		for (int i = 0; i < f.getImmediateSubformulas().size(); ++i) {
			rt += getNumeroConectivosPrioridadeOu(f.getImmediateSubformulas().get(i));
		}
		return rt;
	}
	
}