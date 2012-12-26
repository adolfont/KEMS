package main.ag.strategy.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import logic.formulas.AtomicFormula;
import logic.formulas.CompositeFormula;
import logic.formulas.Formula;
import logic.signedFormulas.PBCandidateList;
import logic.signedFormulas.SignedFormula;
import main.newstrategy.ISimpleStrategy;
import main.proofTree.INode;

/**
 * Contem fun&ccedil;&otilde;es compartilhadas entre as estret&eacute;gias de AG desenvolvidas
 * @author Emerson Shigueo Sugimoto 10-12-2012
 * */
public class AGUtil {
	
	/**
	 * @param formulaComparar SignedFormula a ser comparada
	 * @param pbList PBCandidateList
	 * @param listaSelecionados lista de SignedFormula selecionadas
	 * @return Retorna uma lista de SignedFormula que tenham a mesma complexidade
	 * que {@link formulaComparar} a partir da lista PBCandidateList
	 * @see logic.signedFormulas.PBCandidateList
	 * */
	public PBCandidateList getListaMesmaComplexidade(
			SignedFormula formulaComparar, PBCandidateList pbList,
			ArrayList<SignedFormula> listaSelecionados
			) {
		PBCandidateList rt = null;
		SignedFormula posSig;
		for (int i = 0; i < pbList.size(); ++i) {
			posSig = pbList.get(i);
			if (listaSelecionados.contains(posSig)) {continue;}
			if (formulaComparar.equals(posSig)) {
				continue;
			}
			if (formulaComparar.getComplexity() != posSig.getComplexity()) {
				continue;
			}
			if (rt == null) {
				rt = new PBCandidateList();
				//rt.add(formulaComparar);
			}
			rt.add(posSig);
		}
		return rt;
	}
	
	/**
	 * Retorna uma lista de SignedFormula que tenham a mesma frequ&ecirc;ncia de &aacute;tomos
	 * que {@link formulaComparar} a partir da lista PBCandidateList
	 * @param formulaComparar SignedFormula a ser comparada
	 * @param pbList PBCandidateList
	 * @param ana AnaliseNumeroAtomos
	 * @param strategy ISimpleStrategy
	 * @param listaSelecionados lista de SignedFormula selecionadas
	 * @see logic.signedFormulas.PBCandidateList
	 * @see main.ag.strategy.util.AnaliseNumeroAtomos
	 * */
	public PBCandidateList getListaMesmaFrequenciaAtomos(
			SignedFormula formulaComparar, 
			PBCandidateList pbList,
			AnaliseNumeroAtomos ana,
			ISimpleStrategy strategy,
			ArrayList<SignedFormula> listaSelecionados
			) {
		return getListaMesmaFrequenciaAtomos(
				formulaComparar,
				pbList,
				ana,
				strategy.getProofTree().getSignedFormulasToNodes(),
				listaSelecionados
				);		
	}

	/**
	 * Retorna uma lista de SignedFormula que tenham a mesma frequ&ecirc;ncia de &aacute;tomos
	 * que {@link formulaComparar} a partir da lista PBCandidateList
	 * @param formulaComparar SignedFormula a ser comparada
	 * @param pbList PBCandidateList
	 * @param ana AnaliseNumeroAtomos
	 * @param map Map&lt;SignedFormula, INode&gt; - &aacute;rvore de prova
	 * @param listaSelecionados lista de SignedFormula selecionadas
	 * @see logic.signedFormulas.PBCandidateList
	 * @see main.ag.strategy.util.AnaliseNumeroAtomos
	 * */
	public PBCandidateList getListaMesmaFrequenciaAtomos(
			SignedFormula formulaComparar, 
			PBCandidateList pbList,
			AnaliseNumeroAtomos ana,
			Map<SignedFormula, INode> map,
			ArrayList<SignedFormula> listaSelecionados
			) {
		return getListaMesmaFrequenciaAtomos(
				formulaComparar, 
				pbList,
				ana,
				ana.getHashAtomosEstrategia(ana.toList(map, pbList.getListSignedFormula())),
				listaSelecionados
				);
	}
	
	/**
	 * Retorna uma lista de SignedFormula que tenham a mesma frequ&ecirc;ncia de &aacute;tomos
	 * que {@link formulaComparar} a partir da lista PBCandidateList
	 * @param formulaComparar SignedFormula a ser comparada
	 * @param pbList PBCandidateList
	 * @param ana AnaliseNumeroAtomos
	 * @param hAtomosEstrategia HashMap&lt;AtomicFormula, Integer&gt; - hash com as frequ&ecirc;ncias 
	 * dos &aacute;tomos das f&oacute;rmulas da &aacute;rvore
	 * @param listaSelecionados lista de SignedFormula selecionadas
	 * @see logic.signedFormulas.PBCandidateList
	 * @see main.ag.strategy.util.AnaliseNumeroAtomos
	 * */
	public PBCandidateList getListaMesmaFrequenciaAtomos(
			SignedFormula formulaComparar, 
			PBCandidateList pbList,
			AnaliseNumeroAtomos ana,
			HashMap<AtomicFormula, Integer> hAtomosEstrategia,
			ArrayList<SignedFormula> listaSelecionados
			) {

		PBCandidateList rt = null;
		SignedFormula posSig;
		for (int i = 0; i < pbList.size(); ++i) {
			posSig = pbList.get(i);
			if (listaSelecionados.contains(posSig)) {continue;}
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
	 * @param listaMesmaComplexidade lista de SignedFormula com mesma valoração de <i>fitness</i>
	 * @return Retorna a f&oacute;rmula com o maior n&uacute;mero de conectivos priorit&aacute;rios
	 * @see #getNumeroConectivosPrioridade(SignedFormula)
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
	 * Prioriza o conectivo 'ou'.
	 * @param listaMesmaComplexidade lista de SignedFormula com mesma valoração de <i>fitness</i>
	 * @return Retorna, de {@link listaMesmaComplexidade} a SignedFormula com mairo frequ&ecirc;ncia de conectivos 'ou' 
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
	 * @param sf SignedFormula
	 * @return a frequ&ecirc;ncia dos conectivos priorit&aacute;rios ({@link #verificaSimboloPrioritario(String, String)})
	 * @see #verificaSimboloPrioritario(String, String)
	 * */
	public int getNumeroConectivosPrioridade(SignedFormula sf){
		return getNumeroConectivosPrioridade(sf.getFormula(), sf.getSign().toString());
	}
	/**
	 * @param f Formula
	 * @param signo sinal
	 * @return a frequ&ecirc;ncia dos conectivos priorit&aacute;rios ({@link #verificaSimboloPrioritario(String, String)})
	 * @see #verificaSimboloPrioritario(String, String)
	 * */
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
	/**
	 * @param simbolo s&iacute;mbolo
	 * @param signo sinal
	 * @return true se o {@link simbolo} ou {@link signo} for igual a um dos conectivos priorit&aacute;rios
	 * */
	private boolean verificaSimboloPrioritario(String simbolo, String signo){
		return (signo.equals("T") && simbolo.equals("&")) ||
				(signo.equals("F") && simbolo.equals("|")) ||
				(signo.equals("F") && simbolo.equals("->")) ||
				simbolo.equals("!");
	}

	/**
	 * @param sf SignedFormula
	 * @return N&uacute;mero de conectivos 'ou'
	 * */
	public int getNumeroConectivosPrioridadeOu(SignedFormula sf){
		return getNumeroConectivosPrioridadeOu(sf.getFormula());
	}
	/**
	 * @param f Formula
	 * @return N&uacute;mero de conectivos 'ou'
	 * */
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