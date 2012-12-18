package main.newstrategy.simple.ag.util;

import java.util.ArrayList;

import logic.formulas.CompositeFormula;
import logic.formulas.Formula;
import logic.signedFormulas.PBCandidateList;
import logic.signedFormulas.SignedFormula;

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
	 * @return a fórmula de maior complexidade da lista PBCandidateList
	 * */
	public SignedFormula getSignedFormulaMaiorComplexidade(ArrayList<SignedFormula> selecionados, PBCandidateList pbList){
		SignedFormula rt = null;
		int i = 0; SignedFormula posSig;
		for(i = 0; i < pbList.size(); ++i){
			posSig = pbList.get(i);
			if (selecionados.contains(posSig)) {continue;} //já selecionado
			
			if (rt == null) {
				rt = posSig;
			} else {
				if (rt.getComplexity() < posSig.getComplexity()){
					rt = posSig; //update
				}
			}
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
	
	/**
	 * @return a fórmula com o maior número de conectivos
	 * */
	public SignedFormula getSignedFormulaMaiorNumeroConectivos(ArrayList<SignedFormula> selecionados, PBCandidateList pbList){
		SignedFormula rt = null;
		int i = 0; SignedFormula posSig;
		for(i = 0; i < pbList.size(); ++i){
			posSig = pbList.get(i);
			if (selecionados.contains(posSig)) {continue;} //já selecionado
			
			if (rt == null) {
				rt = posSig;
			} else {
				if (getNumeroConectivos(rt) < getNumeroConectivos(posSig)){
					rt = posSig; //update
				}
			}
		}
		return rt;
	}
	
	/**
	 * @return a fórmula de menor complexidade da lista PBCandidateList
	 * */
	public SignedFormula getSignedFormulaMenorComplexidade(ArrayList<SignedFormula> selecionados, PBCandidateList pbList){
		SignedFormula rt = null;
		int i = 0; SignedFormula posSig;
		for(i = 0; i < pbList.size(); ++i){
			posSig = pbList.get(i);
			if (selecionados.contains(posSig)) {continue;} //já selecionado
			
			if (rt == null) {
				rt = posSig;
			} else {
				if (rt.getComplexity() > posSig.getComplexity()){
					rt = posSig; //update
				}
			}
		}
		return rt;
	}
	
	public  int getFrequenciaAtomos(ArrayList<Formula> listaAtomosArvore, ArrayList<Formula> listaAtomosRegrasPB){
		int rt = 0;
		int i=0,j=0;
		for(i=0;i<listaAtomosArvore.size();i++){
			for(j=0;j<listaAtomosRegrasPB.size();j++){
				if (listaAtomosRegrasPB.get(j).equals(listaAtomosArvore.get(i))){
					rt++;
				}
			}
		}
		return rt;
	}
	public ArrayList<Formula> getAtomos(Formula f){
		ArrayList<Formula> rt = new ArrayList<Formula>();
		if (f.getImmediateSubformulas().size()==0) {
			//if (rt.contains(f)) {return rt;}
			rt.add(f);
		}
		for (int i = 0; i < f.getImmediateSubformulas().size(); ++i) {
			rt.addAll(getAtomos(f.getImmediateSubformulas().get(i)));
		}
		return rt;
	}
	
	public int getNumeroConectivos(SignedFormula sf){
		return getNumeroConectivos(sf.getFormula());
	}
	private int getNumeroConectivos(Formula f){
		int rt = 0;
		if (!(f instanceof CompositeFormula)){ 
			//System.out.println("r1: " + f.toString());
			return rt;
		} else {
			rt++;
		}
		
		if (f.getImmediateSubformulas().size()==0) {
			rt++;
			return rt;
		}
		for (int i = 0; i < f.getImmediateSubformulas().size(); ++i) {
			rt += getNumeroConectivos(f.getImmediateSubformulas().get(i));
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