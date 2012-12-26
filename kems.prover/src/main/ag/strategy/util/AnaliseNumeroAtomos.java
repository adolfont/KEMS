package main.ag.strategy.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import logic.formulas.AtomicFormula;
import logic.formulas.Formula;
import logic.signedFormulas.SignedFormula;
import main.newstrategy.ISimpleStrategy;
import main.proofTree.INode;

/**
 * An&aacute;lisa a frequ&ecirc;ncia dos &aacute;tomos das regras PB (PBCandidateList) e compara com
 * os &aacute;tomos das demais f&oacute;rmulas da base de conhecimentos
 * @author Emerson Shigueo Sugimoto 10-12-2012
 * */
public class AnaliseNumeroAtomos {

	/**
	 * {@link map} pode ser obtido a partir de ISimpleStrategy strategy
	 * @param map Map&lt;SignedFormula, INode&gt; - SignedFormula da &aacute;rvore de prova
	 * @param listaPbIgnore SignedFormula da lista PB que devem ser ignorados
	 * @return Cria uma lista de SignedFormula a partir de {@link map}, ignorando as SignedFormula de {@link listaPbIgnore}
	 * @author Emerson Shigueo Sugimoto 10-12-2012
	 * @see main.newstrategy.ISimpleStrategy
	 * @see main.strategy.ClassicalProofTree
	 * @see <code>main.newstrategy.ISimpleStrategy.getProofTree()</code>
	 * @see <code>main.strategy.ClassicalProofTree.getSignedFormulasToNodes()</code>
	 * */
	public ArrayList<SignedFormula> toList(Map<SignedFormula, INode> map, List<SignedFormula> listaPbIgnore){
		ArrayList<SignedFormula> rt = new ArrayList<SignedFormula>();
		for (Map.Entry<SignedFormula, INode> pair : map.entrySet()) {
			
			if (pair.getKey().toString().indexOf("BOTTOM") >= 0 ||
					pair.getKey().toString().indexOf("TOP") >= 0) {
				continue;
			}
			
			if (rt.contains(pair.getKey())) continue;
			if (listaPbIgnore!=null){
				if (listaPbIgnore.contains(pair.getKey())){continue;}
			}
			//System.out.println(" -> pair.getKey(): " + pair.getKey());
			rt.add(pair.getKey());
		}
		
		if (rt!=null && rt.size() > 0) {return rt;}
		
		//problemas da família PHP
		for (Map.Entry<SignedFormula, INode> pair : map.entrySet()) {
			if (pair.getKey().toString().indexOf("BOTTOM") >= 0 ||
					pair.getKey().toString().indexOf("TOP") >= 0) {
				continue;
			}
			if (rt.contains(pair.getKey())) continue;
			//System.out.println(" -> pair.getKey(): " + pair.getKey());
			rt.add(pair.getKey());
		}
		return rt;
	}
	
	/**
	 * {@link map} pode ser obtido a partir de ISimpleStrategy strategy
	 * @param map Map&lt;SignedFormula, INode&gt; - SignedFormula da &aacute;rvore de prova
	 * @return Cria uma lista de SignedFormula a partir de {@link map}, sem ignorar as SignedFormula da lista PB
	 * @author Emerson Shigueo Sugimoto 10-12-2012
	 * @see main.newstrategy.ISimpleStrategy
	 * @see main.strategy.ClassicalProofTree
	 * @see <code>main.newstrategy.ISimpleStrategy.getProofTree()</code>
	 * @see <code>main.strategy.ClassicalProofTree.getSignedFormulasToNodes()</code>
	 * */
	public ArrayList<SignedFormula> toList(Map<SignedFormula, INode> map){
		return toList(map, null);
	}
	
	/**
	 * Converte em list as f&oacute;rmulas de uma estrat&eacute;gia
	 * @param strategy ISimpleStrategy
	 * @param listaPbIgnore SignedFormula a serem ignorados da lista PB
	 * @see main.newstrategy.ISimpleStrategy
	 * */
	public ArrayList<SignedFormula> toList(ISimpleStrategy strategy, List<SignedFormula> listaPbIgnore){
		return toList(strategy.getProofTree().getSignedFormulasToNodes(), listaPbIgnore);
	}
	
	/**
	 * M&eacute;todo final
	 * @param listaFormulasJaSelecionadas lista de SignedFormula selecionadas
	 * @param listaPB lista de SignedFormula da lista PB
	 * @param strategy ISimpleStrategy
	 * @param sumPbFrequency <strong>true</strong>: soma a frequ&ecirc;ncia dos &aacute;tomos das SignedFormula contidas na lista PBCandidateList<br />
	 * @param ignoreEmptyAtoms <strong>true</strong>: SignedFormulas da PBCandidateList com &aacute;tomos que n&atilde;o existem na &aacute;rvore de prova
	 * n&atilde;o possuem seu <i>fitness</i> zerados.<br />
	 * @return Seleciona a SignedFormula com a maior frequ&ecirc;ncia de &aacute;tomos da base de conhecimentos  
	 * */
	public SignedFormula getSFAnaliseAtomos(
			ArrayList<SignedFormula> listaFormulasJaSelecionadas,
			List<SignedFormula> listaPB,
			ISimpleStrategy strategy,
			boolean sumPbFrequency,
			boolean ignoreEmptyAtoms){
		return getSFAnaliseAtomos(
				listaFormulasJaSelecionadas,
				getHashFromList(listaPB), 
				getHashAtomosEstrategia(toList(strategy, listaPB)), 
				sumPbFrequency, ignoreEmptyAtoms);
	}
	
	/**
	 * M&eacute;todo final<br>
	 * {@link map} pode ser obtido a partir de ISimpleStrategy strategy
	 * @param listaFormulasJaSelecionadas lista de SignedFormula selecionadas
	 * @param listaPB lista de SignedFormula da lista PB
	 * @param map Map&lt;SignedFormula, INode&gt; - SignedFormula da &aacute;rvore de prova
	 * @param sumPbFrequency <strong>true</strong>: soma a frequ&ecirc;ncia dos &aacute;tomos das SignedFormula contidas na lista PBCandidateList<br />
	 * @param ignoreEmptyAtoms <strong>true</strong>: SignedFormulas da PBCandidateList com &aacute;tomos que n&atilde;o existem na &aacute;rvore de prova
	 * n&atilde;o possuem seu <i>fitness</i> zerados.<br />
	 * @return Seleciona a SignedFormula com a maior frequ&ecirc;ncia de &aacute;tomos da base de conhecimentos  
	 * */
	public SignedFormula getSFAnaliseAtomos(
			ArrayList<SignedFormula> listaFormulasJaSelecionadas,
			List<SignedFormula> listaPB,
			Map<SignedFormula, INode> map,
			boolean sumPbFrequency,
			boolean ignoreEmptyAtoms){
		return getSFAnaliseAtomos(
				listaFormulasJaSelecionadas,
				getHashFromList(listaPB), 
				getHashAtomosEstrategia(toList(map, listaPB)), 
				sumPbFrequency, ignoreEmptyAtoms);
	}
	
	/**
	 * M&eacute;todo final<br>
	 * @param listaFormulasJaSelecionadas lista de SignedFormula selecionadas
	 * @param hListaAtomicPB HashMap&lt;SignedFormula, HashMap&lt;AtomicFormula, Integer&gt;&gt;<br>
	 * <code>Hash</code> contendo, para cada SignedFormula da lista PB, um <code>Hash</code> com as frequ&ecirc;ncias
	 * dos respectivos &aacute;tomos das SignedFormulas da lista PB
	 * @param lAtomosEstrategia <code>Hash</code> contendo as frequ&ecirc;ncias dos &aacute;tomos de
	 * todas as SignedFormulas da &aacute;rvore de prova, exceto as frequ&ecirc;ncias dos &aacute;tomos das
	 * SignedFormula da lista PB.
	 * @param sumPbFrequency <strong>true</strong>: soma a frequ&ecirc;ncia dos &aacute;tomos das SignedFormula contidas na lista PBCandidateList<br />
	 * @param ignoreEmptyAtoms <strong>true</strong>: SignedFormulas da PBCandidateList com &aacute;tomos que n&atilde;o existem na &aacute;rvore de prova
	 * n&atilde;o possuem seu <i>fitness</i> zerados.<br />
	 * @return Seleciona a SignedFormula com a maior frequ&ecirc;ncia de &aacute;tomos da base de conhecimentos  
	 * */
	public SignedFormula getSFAnaliseAtomos(
			ArrayList<SignedFormula> listaFormulasJaSelecionadas,
			HashMap<SignedFormula, HashMap<AtomicFormula, Integer>> hListaAtomicPB, 
			HashMap<AtomicFormula, Integer> lAtomosEstrategia,
			boolean sumPbFrequency,
			boolean ignoreEmptyAtoms
			){
		SignedFormula rt = null;
		int analiseEscolhida = 0, tmpAnalise = 0;
		for (Map.Entry<SignedFormula, HashMap<AtomicFormula, Integer>> pairPB : hListaAtomicPB.entrySet()) {
			if (listaFormulasJaSelecionadas.contains(pairPB.getKey())) {continue;} //já selecionado
			tmpAnalise = CompareMapFrequenciasAtomos(pairPB.getValue(), lAtomosEstrategia, sumPbFrequency, ignoreEmptyAtoms);
			if (rt==null){
				rt = pairPB.getKey();
				analiseEscolhida = tmpAnalise;
			} else if (analiseEscolhida < tmpAnalise) {
				rt = pairPB.getKey();
				analiseEscolhida = tmpAnalise;
			}
//			System.out.println(
//					pairPB.getKey() + " ["+pairPB.getValue()+"] : " + 
//					CompareMapFrequenciasAtomos(pairPB.getValue(), lAtomosEstrategia, true, false)
//					);
		}
		if (rt==null && !ignoreEmptyAtoms) { //ignora átomos que não estão na base de conhecimentos
			for (Map.Entry<SignedFormula, HashMap<AtomicFormula, Integer>> pairPB : hListaAtomicPB.entrySet()) {
				if (listaFormulasJaSelecionadas.contains(pairPB.getKey())) {continue;} //já selecionado
				tmpAnalise = CompareMapFrequenciasAtomos(pairPB.getValue(), lAtomosEstrategia, sumPbFrequency, true);
				if (rt==null){
					rt = pairPB.getKey();
					analiseEscolhida = tmpAnalise;
				} else if (analiseEscolhida < tmpAnalise) {
					rt = pairPB.getKey();
					analiseEscolhida = tmpAnalise;
				}
			}
		}
		return rt;
	}
	
	/**
	 * {@link map} pode ser obtido a partir de ISimpleStrategy strategy
	 * @param signedFormulaPBAvaliar SignedFormula a ser avaliada
	 * @param map Map&lt;SignedFormula, INode&gt; - SignedFormula da &aacute;rvore de prova
	 * @param listaPbIgnore lista de SignedFormula da lista PB
	 * @param sumPbFrequency <strong>true</strong>: soma a frequ&ecirc;ncia dos &aacute;tomos das SignedFormula contidas na lista PBCandidateList<br />
	 * @param ignoreEmptyAtoms <strong>true</strong>: SignedFormulas da PBCandidateList com &aacute;tomos que n&atilde;o existem na &aacute;rvore de prova
	 * n&atilde;o possuem seu <i>fitness</i> zerados.<br />
	 * @return a avalia&ccedil;&atilde;o indiv&iacute;dual de uma SignedFormula
	 * @see main.newstrategy.ISimpleStrategy
	 * @see main.strategy.ClassicalProofTree
	 * @see <code>main.newstrategy.ISimpleStrategy.getProofTree()</code>
	 * @see <code>main.strategy.ClassicalProofTree.getSignedFormulasToNodes()</code>
	 * @author Emerson Shigueo Sugimoto
	 * */
	public int GetAvaliacaoIndividual(
			SignedFormula signedFormulaPBAvaliar,
			Map<SignedFormula, INode> map,
			List<SignedFormula> listaPbIgnore,
			boolean sumPbFrequency,
			boolean ignoreEmptyAtoms
			){
		return GetAvaliacaoIndividual(
				signedFormulaPBAvaliar,
				getHashAtomosEstrategia(toList(map, listaPbIgnore)),
				sumPbFrequency, ignoreEmptyAtoms
				);
	}
	
	/**
	 * 
	 * HashMap&lt;AtomicFormula, Integer&gt; {@link hAtomosEstrategia} pode ser obtido de
	 * Map&lt;SignedFormula, INode&gt; map:
	 * <p>
	 * <code>
	 * HashMap&lt;AtomicFormula, Integer&gt; hAtomosEstrategia = {@link #getHashAtomosEstrategia(ArrayList)}<br>
	 * Onde {@link ArrayList} prov&eacute;m de: {@link #toList(Map, List)};
	 * </code>
	 * </p>
	 * @param signedFormulaPBAvaliar SignedFormula da lista Pb a ser avaliada
	 * @param hAtomosEstrategia <code>Hash</code> com a frequ&ecirc;ncia dos &aacute;tomos da &aacute;rvore de prova
	 * @param sumPbFrequency <strong>true</strong>: soma a frequ&ecirc;ncia dos &aacute;tomos das SignedFormula contidas na lista PBCandidateList<br />
	 * @param ignoreEmptyAtoms <strong>true</strong>: SignedFormulas da PBCandidateList com &aacute;tomos que n&atilde;o existem na &aacute;rvore de prova
	 * n&atilde;o possuem seu <i>fitness</i> zerados.<br />
	 * @see main.newstrategy.ISimpleStrategy
	 * @see main.strategy.ClassicalProofTree
	 * @see <code>main.newstrategy.ISimpleStrategy.getProofTree()</code>
	 * @see <code>main.strategy.ClassicalProofTree.getSignedFormulasToNodes()</code>
	 * @author Emerson Shigueo Sugimoto
	 * */
	public int GetAvaliacaoIndividual(
			SignedFormula signedFormulaPBAvaliar,
			HashMap<AtomicFormula, Integer> hAtomosEstrategia,
			boolean sumPbFrequency,
			boolean ignoreEmptyAtoms){
		return CompareMapFrequenciasAtomos(
				getHashFromFormula(signedFormulaPBAvaliar.getFormula()), 
						hAtomosEstrategia, sumPbFrequency, ignoreEmptyAtoms);
	}
	
	/**
	 * Compara as frequ&ecirc;ncias de &aacute;tomos de 2 hashmaps <code>HashMap&lt;AtomicFormula, Integer&gt;</code>
	 * @param hPb <code>HashMap&lt;AtomicFormula, Integer&gt;</code> de frequ&ecirc;ncias de SignedFormula da lista PB
	 * @param hFormulasEstrategia <code>HashMap&lt;AtomicFormula, Integer&gt;</code> 
	 * de frequ&ecirc;ncias de SignedFormula da &aacute;rvore de prova
	 * @param sumPbFrequency <strong>true</strong>: soma a frequ&ecirc;ncia dos &aacute;tomos das SignedFormula contidas na lista PBCandidateList<br />
	 * @param ignoreEmptyAtoms <strong>true</strong>: SignedFormulas da PBCandidateList com &aacute;tomos que n&atilde;o existem na &aacute;rvore de prova
	 * n&atilde;o possuem seu <i>fitness</i> zerados.<br />
	 * @return A frequ&ecirc;ncia do <code>Hash</code> PB no <code>Hash</code> da &aacute;rvore de prova.
	 * Ser&aacute; o <i>fitness</i> da SignedFormula (A qual deu origem ao {@link hPb}).
	 * @author Emerson Shigueo Sugimoto
	 * */
	public int CompareMapFrequenciasAtomos(
			HashMap<AtomicFormula, Integer> hPb, 
			HashMap<AtomicFormula, Integer> hFormulasEstrategia,
			boolean sumPbFrequency,
			boolean ignoreEmptyAtoms){
		int rt = 0; int f=0;
		for (Map.Entry<AtomicFormula, Integer> pairPb : hPb.entrySet()) {
			//se a lista de fórmulas da estratégia não contém um átomo das regras PB e !ignoreEmptyAtoms, retorna 0
			if (!hFormulasEstrategia.containsKey(pairPb.getKey())) {
				if (!ignoreEmptyAtoms){
					//System.out.println("ZERO to: " + pairPb.getKey());
					return 0;
				}
				continue;
			}
			//if (pairPb.getKey()==null){continue;}
			f = hFormulasEstrategia.get(pairPb.getKey()) + (sumPbFrequency ? pairPb.getValue() : 0);
			//System.out.println(pairPb.getKey() + ", f: " + f);
			rt += f; //soma a frequência da lista de fórmulas da estratégia
		}
		return rt;
	}
	
	/**
	 * Converte uma lista de SignedFormula em um <code>Hash</code> de frequ&ecirc;ncia dos &aacute;tomos
	 * @param lista Lista de SignedFormula
	 * @return Retorna uma lista de &aacute;tomos das f&oacute;rmulas da estrat&eacute;gia
	 * */
	public HashMap<AtomicFormula, Integer> getHashAtomosEstrategia(ArrayList<SignedFormula> lista){
		if (lista==null || lista.size() <= 0) {return null;}
		HashMap<AtomicFormula, Integer> rt = new HashMap<AtomicFormula, Integer>();
		HashMap<AtomicFormula, Integer> tmp;
		for(int i = 0; i <lista.size(); i++){
			tmp = getHashFromFormula(lista.get(i).getFormula());
			if (tmp==null){continue;}
			if (tmp.toString().contains("TOP") || tmp.toString().contains("BOTTOM")){continue;}
			addHash(tmp, rt);
		}
		return rt;
	}
	
	/**
	 * @param lista Lista de SignedFormula
	 * @return Para Cada SignedFormula cria um map com as frequ&ecirc;ncias das f&oacute;rmulas at&ocirc;micas
	 * @author Emerson Shigueo Sugimoto
	 * */
	public HashMap<SignedFormula, HashMap<AtomicFormula, Integer>> getHashFromList(
			List<SignedFormula> lista){
		if (lista==null || lista.size() <= 0) {return null;}
		HashMap<SignedFormula, HashMap<AtomicFormula, Integer>> rt = new HashMap<SignedFormula, HashMap<AtomicFormula, Integer>>();
		HashMap<AtomicFormula, Integer> tmp = null;
		for(int i = 0; i <lista.size(); i++){
			if (rt.containsKey(lista.get(i))) {continue;}
			tmp = getHashFromFormula(lista.get(i).getFormula());
			rt.put(lista.get(i), tmp);
		}
		return rt;
	}
	
	/**
	 * M&eacute;todo recursivo
	 * @param f Formula
	 * @return Retorna um Map com as frequ&ecirc;ncias de uma f&oacute;rmula
	 * */
	public HashMap<AtomicFormula, Integer> getHashFromFormula(Formula f){
		HashMap<AtomicFormula, Integer> rt = new HashMap<AtomicFormula, Integer>();
		if (f instanceof AtomicFormula) {
			rt.put((AtomicFormula)f, 1);
			return rt;
		}
		if (f.getImmediateSubformulas().size()==0) {return null;}
		HashMap<AtomicFormula, Integer> tmp = new HashMap<AtomicFormula, Integer>();
		for(int i = 0; i < f.getImmediateSubformulas().size(); i++){
			tmp = getHashFromFormula(f.getImmediateSubformulas().get(i));
			if (tmp == null || tmp.size() <= 0) {continue;}
			addHash(tmp, rt);
		}
		return rt;
	}
	
	/**
	 * M&eacute;todo auxiliar para controle das frequ&ecirc;ncias dos &aacute;tomos
	 * @param hMap HashMap&lt;AtomicFormula, Integer&gt; <code>Hash</code>
	 * de frequ&ecirc;ncias a ser adicionado em {@link hMapFinal} 
	 * @param hMapFinal HashMap&lt;AtomicFormula, Integer&gt; <code>Hash</code> final
	 * */
	private void addHash(HashMap<AtomicFormula, Integer> hMap, HashMap<AtomicFormula, Integer> hMapFinal){
		for (Map.Entry<AtomicFormula, Integer> pair : hMap.entrySet()) {
			addHash(pair.getKey(), pair.getValue(), hMapFinal);
		}
	}
	/**
	 * M&eacute;todo auxiliar para controle das frequ&ecirc;ncias dos &aacute;tomos
	 * @param f Formula
	 * @param freq frequ&ecirc;ncia
	 * @param hMapFinal HashMap&lt;AtomicFormula, Integer&gt; <code>Hash</code> final
	 * */
	private void addHash(Formula f, int freq, HashMap<AtomicFormula, Integer> hMapFinal){
		if (hMapFinal.containsKey((AtomicFormula)f)) {
			hMapFinal.put((AtomicFormula)f, (hMapFinal.get((AtomicFormula)f)+freq));
		} else {
			hMapFinal.put((AtomicFormula)f, freq);
		}
	}
	
	/**
	 * M&eacute;todo auxiliar para print() com System.out.println(); de uma lista
	 * @param lista lista de SignedFormula
	 * */
	public void print(ArrayList<SignedFormula> lista){
		for(int i = 0; i < lista.size(); i++) {
			System.out.println(lista.get(i));
		}
	}
	/**
	 * M&eacute;todo auxiliar para print() com System.out.println(); de um <code>HashMap</code>
	 * @param hMap <code>HashMap</code>
	 * */
	public void print(HashMap<AtomicFormula, Integer> hMap) {
		for (Map.Entry<AtomicFormula, Integer> pair : hMap.entrySet()) {
			System.out.println(pair.getKey() + " : " + pair.getValue());
		}
	}
	/**
	 * M&eacute;todo auxiliar para print() com System.out.println(); de um 
	 * <code>HashMap&lt;SignedFormula, HashMap&lt;AtomicFormula, Integer&gt;&gt;</code>
	 * @param hMap <code>HashMap&lt;SignedFormula, HashMap&lt;AtomicFormula, Integer&gt;&gt;</code>
	 * */
	public void printHMapList(HashMap<SignedFormula, HashMap<AtomicFormula, Integer>> hMap){
		for (Map.Entry<SignedFormula, HashMap<AtomicFormula, Integer>> pair : hMap.entrySet()) {
			System.out.println("-" + pair.getKey());
			print(pair.getValue());
		}
	}
	
}

/*
------------------------------------------
Testa a Classe
------------------------------------------
private static void testeAnaliseDeAtomos(){
	//SignedFormula sf0 = sfc.parseString("T(A->B)->(C|A)&C");
	//System.out.println(sf0);
	
	ArrayList<SignedFormula> listaPB = new ArrayList<SignedFormula>();
	listaPB.add(sfc.parseString("F(B->C)&(D|A)"));
	listaPB.add(sfc.parseString("T(C->B)&(B|D)"));
	
	Map<SignedFormula, INode> map = new HashMap<SignedFormula, INode>();
	map.put(sfc.parseString("F(B->C)&(D|A)"), null);
	map.put(sfc.parseString("TC|D"), null);
	map.put(sfc.parseString("TB"), null);
	map.put(sfc.parseString("T(C->B)&(B|D)"), null);
	AnaliseNumeroAtomos ana = new AnaliseNumeroAtomos();
	ArrayList<SignedFormula> lista = ana.toList(map, listaPB);
	
	System.out.println("------------------lista:");
	ana.print(lista);
	System.out.println("------------------listaPB:");
	ana.print(listaPB);
	System.out.println("------------------lAtomosEstrategia:");
	HashMap<AtomicFormula, Integer> lAtomosEstrategia = ana.getHashAtomosEstrategia(lista);
	ana.print(lAtomosEstrategia);
	System.out.println("------------------hListaAtomicPB:");
	HashMap<SignedFormula, HashMap<AtomicFormula, Integer>> hListaAtomicPB = ana.getHashFromList(listaPB);
	ana.printHMapList(hListaAtomicPB);
	System.out.println("------------------CompareMapFrequenciasAtomos:");
	
	//CompareMapFrequenciasAtomos(HashMap<AtomicFormula, Integer> hPb, 
	//HashMap<AtomicFormula, Integer> hFormulasEstrategia, boolean ignoreEmptyAtoms)
	for (Map.Entry<SignedFormula, HashMap<AtomicFormula, Integer>> pairPB : hListaAtomicPB.entrySet()) {
		System.out.println(
				pairPB.getKey() + " ["+pairPB.getValue()+"] : " + 
				ana.CompareMapFrequenciasAtomos(pairPB.getValue(), lAtomosEstrategia, false, true)
				);
	}

	System.out.println("------------------sfEscolhida:");
	SignedFormula sfEscolhida = ana.getSFAnaliseAtomos(hListaAtomicPB, lAtomosEstrategia, true, true);
	System.out.println(sfEscolhida);
	
	System.out.println("------------------getSFAnaliseAtomos:");
	SignedFormula sfEscolhida2 = ana.getSFAnaliseAtomos(listaPB, map, true, true);
	System.out.println(sfEscolhida2);
}
*/