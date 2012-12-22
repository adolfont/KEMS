package main.newstrategy.simple.ag.util;

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
 * Análisa a frequência dos átomos das regras PB (PBCandidateList) e compara com
 * os átomos das demais fórmulas da base de conhecimentos
 * @author Emerson Shigueo Sugimoto
 * */
public class AnaliseNumeroAtomos {
	
	/**
	 * @listaPbIgnore: itens da lista PB devem ser ignorados
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
	
	public ArrayList<SignedFormula> toList(Map<SignedFormula, INode> map){
		return toList(map, null);
	}
	
	/**
	 * Converte em list as fórmulas de uma estratégia
	 * @listaPbIgnore: itens a serem ignorados por pertencem a lista PB
	 * */
	public ArrayList<SignedFormula> toList(ISimpleStrategy strategy, List<SignedFormula> listaPbIgnore){
		return toList(strategy.getProofTree().getSignedFormulasToNodes(), listaPbIgnore);
	}
	
	/**
	 * Método final
	 * @return Seleciona a SignedFormula com a maior frequência de átomos da base de conhecimentos  
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
	 * Método final
	 * @return Seleciona a SignedFormula com a maior frequência de átomos da base de conhecimentos  
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
	 * Método final
	 * @return Seleciona a SignedFormula com a maior frequência de átomos da base de conhecimentos  
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
	 * para conseguir 'HashMap<AtomicFormula, Integer> hAtomosEstrategia' a partir do
	 * 'Map<SignedFormula, INode> map':
	 * AnaliseNumeroAtomos ana = new AnaliseNumeroAtomos();
	 * HashMap<AtomicFormula, Integer> hAtomosEstrategia = ana.getHashAtomosEstrategia(ana.toList(map, listaPB));
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
	 * Compara as frequências de átomos de 2 hashmaps
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
	 * Retorna uma lista de átomos das fórmulas da estratégia
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
	 * Para Cada SignedFormula cria um map com as frequências das fórmulas atômicas
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
	 * Retorna um Map com as frequências de uma fórmula
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
	
	private void addHash(HashMap<AtomicFormula, Integer> hMap, HashMap<AtomicFormula, Integer> hMapFinal){
		for (Map.Entry<AtomicFormula, Integer> pair : hMap.entrySet()) {
			addHash(pair.getKey(), pair.getValue(), hMapFinal);
		}
	}
	private void addHash(Formula f, int freq, HashMap<AtomicFormula, Integer> hMapFinal){
		if (hMapFinal.containsKey((AtomicFormula)f)) {
			hMapFinal.put((AtomicFormula)f, (hMapFinal.get((AtomicFormula)f)+freq));
		} else {
			hMapFinal.put((AtomicFormula)f, freq);
		}
	}
	
	public void print(ArrayList<SignedFormula> lista){
		for(int i = 0; i < lista.size(); i++) {
			System.out.println(lista.get(i));
		}
	}
	
	public void print(HashMap<AtomicFormula, Integer> hMap) {
		for (Map.Entry<AtomicFormula, Integer> pair : hMap.entrySet()) {
			System.out.println(pair.getKey() + " : " + pair.getValue());
		}
	}
	
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