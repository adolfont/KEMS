package main.newstrategy.simple.ag;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import logic.formulas.AtomicFormula;
import logic.formulas.Formula;
import logic.signedFormulas.SignedFormula;
import logic.signedFormulas.SignedFormulaCreator;
import main.newstrategy.simple.ag.util.AnaliseNumeroAtomos;
import main.proofTree.INode;

public class mainGATESTE {

	private static SignedFormulaCreator sfc;
	private static String packageName = "satlfiinconsdef"; //sats5,satlfiinconsdef
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		sfc = new SignedFormulaCreator(packageName);
//		testeAnaliseDeAtomos();
//		testeAnaliseAtomos();
		
		TestGetAvaliacaoFrequencia();
	}
	
	private static void TestGetAvaliacaoFrequencia(){
		ArrayList<SignedFormula> listaPB = new ArrayList<SignedFormula>();
		listaPB.add(sfc.parseString("F(B->C)&(D|A)"));
		listaPB.add(sfc.parseString("T(C->B)&(B|D)"));
		
		Map<SignedFormula, INode> map = new HashMap<SignedFormula, INode>();
		map.put(sfc.parseString("F(B->C)&(D|A)"), null);
		map.put(sfc.parseString("TC|D"), null);
		map.put(sfc.parseString("TB"), null);
		map.put(sfc.parseString("T(C->B)&(B|D)"), null);
		
		AnaliseNumeroAtomos ana = new AnaliseNumeroAtomos();
		HashMap<AtomicFormula, Integer> hAtomosEstrategia = ana.getHashAtomosEstrategia(ana.toList(map, listaPB));
		
		System.out.println(
				ana.GetAvaliacaoIndividual(
					listaPB.get(0), 
					hAtomosEstrategia,
					true, true
				)
				);
		System.out.println(
				ana.GetAvaliacaoIndividual(
					listaPB.get(1), 
					hAtomosEstrategia,
					true, true
				)
				);
		
	}
	
	
	public static int GetAvaliacaoIndividual(
			SignedFormula signedFormulaPBAvaliar,
			HashMap<AtomicFormula, Integer> hAtomosEstrategia,
			boolean sumPbFrequency,
			boolean ignoreEmptyAtoms){
		
		//para conseguir 'HashMap<AtomicFormula, Integer> hAtomosEstrategia' a partir do
		//'Map<SignedFormula, INode> map':
		//AnaliseNumeroAtomos ana = new AnaliseNumeroAtomos();
		//HashMap<AtomicFormula, Integer> hAtomosEstrategia = ana.getHashAtomosEstrategia(ana.toList(map, listaPB));
		
		AnaliseNumeroAtomos ana = new AnaliseNumeroAtomos();
		return ana.CompareMapFrequenciasAtomos(
				ana.getHashFromFormula(signedFormulaPBAvaliar.getFormula()), 
						hAtomosEstrategia, sumPbFrequency,ignoreEmptyAtoms);
	}
	
	
	private void TesteNotCompareAgain(){
		ArrayList<SignedFormula> ListaFormulasJaSelecionadas = new ArrayList<SignedFormula>();
		AnaliseNumeroAtomos ana = new AnaliseNumeroAtomos();
		
		ArrayList<SignedFormula> listaPB = new ArrayList<SignedFormula>();
		listaPB.add(sfc.parseString("F(B->C)&(D|A)"));
		listaPB.add(sfc.parseString("T(C->B)&(B|D)"));
		
		Map<SignedFormula, INode> map = new HashMap<SignedFormula, INode>();
		map.put(sfc.parseString("F(B->C)&(D|A)"), null);
		map.put(sfc.parseString("TC|D"), null);
		map.put(sfc.parseString("TB"), null);
		map.put(sfc.parseString("T(C->B)&(B|D)"), null);
		
		SignedFormula rt = ana.getSFAnaliseAtomos(ListaFormulasJaSelecionadas, listaPB,map,false,true);
		
	}
	
	private static void testeAnaliseDeAtomos(){
		ArrayList<SignedFormula> ListaFormulasJaSelecionadas = new ArrayList<SignedFormula>();
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
		SignedFormula sfEscolhida = ana.getSFAnaliseAtomos(ListaFormulasJaSelecionadas,hListaAtomicPB, lAtomosEstrategia, true, true);
		System.out.println(sfEscolhida);
		
		System.out.println("------------------getSFAnaliseAtomos:");
		SignedFormula sfEscolhida2 = ana.getSFAnaliseAtomos(ListaFormulasJaSelecionadas,listaPB, map, true, true);
		System.out.println(sfEscolhida2);
	}
	
	
	private static void testeAnaliseAtomos(){
		System.out.println("----------------------------------");
		List<SignedFormula> listaPb = new ArrayList<SignedFormula>();
		List<SignedFormula> listaFormulasArvore = new ArrayList<SignedFormula>();
	
		listaFormulasArvore.add(sfc.parseString("F(B->C)&(D|A)"));
		listaFormulasArvore.add(sfc.parseString("TC|D"));
		listaFormulasArvore.add(sfc.parseString("TB"));
		listaFormulasArvore.add(sfc.parseString("T(C->B)&(B|D)"));
		
		listaPb.add(sfc.parseString("F(B->C)&(D|A)"));
		listaPb.add(sfc.parseString("T(C->B)&(B|D)"));
		
		listaFormulasArvore = trataListaFormulas(listaFormulasArvore, listaPb);
		//print(listaFormulasArvore);
		
		HashMap<AtomicFormula, Integer> hFA = getHashAtomosEstrategia(listaFormulasArvore);
		//print(hFA);
		
		HashMap<SignedFormula, HashMap<AtomicFormula, Integer>> hSFPB = getHashFromList(listaPb);
		//printHMapList(hSFPB);
		
		SignedFormula sfRT = getSFAnaliseAtomos(hSFPB, hFA, false, false);
		System.out.println(sfRT);
	}
	
	/**
	 * Método final
	 * @return Seleciona a SignedFormula com a maior frequência de átomos da base de conhecimentos  
	 * */
	public static SignedFormula getSFAnaliseAtomos(
			HashMap<SignedFormula, HashMap<AtomicFormula, Integer>> hListaAtomicPB, 
			HashMap<AtomicFormula, Integer> lAtomosEstrategia,
			boolean sumPbFrequency,
			boolean ignoreEmptyAtoms
			){
		SignedFormula rt = null;
		int analiseEscolhida = 0, tmpAnalise = 0;
		for (Map.Entry<SignedFormula, HashMap<AtomicFormula, Integer>> pairPB : hListaAtomicPB.entrySet()) {
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
	 * Compara as frequências de átomos de 2 hashmaps
	 * */
	public static int CompareMapFrequenciasAtomos(
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
	 * Remove fórmulas da lista PB da lista de fórmulas da Árvore.
	 * Usado para análise de frequência de átomos
	 * @param listaFormulasArvore Lista de SignedFormula da árvore orginal
	 * @param listaPB Lista de SignedFormula da lista PB
	 * @return lista de SignedFormula da árvore orginal sem objetos SignedFormula da lista PB
	 * @author Emerson Shigueo Sugimoto 20/12/2012
	 * */
	private static List<SignedFormula> trataListaFormulas(List<SignedFormula> listaFormulasArvore, List<SignedFormula> listaPB){
		List<SignedFormula> rt = new ArrayList<SignedFormula>();
		SignedFormula sf;
		for (int i = 0; i < listaFormulasArvore.size(); i++){
			sf = listaFormulasArvore.get(i);
			if (listaPB.contains(sf)) {continue;}
			rt.add(sf);
		}
		return rt;
	}
	
	/**
	 * Para Cada SignedFormula cria um map com as frequências das fórmulas atômicas
	 * */
	private static HashMap<SignedFormula, HashMap<AtomicFormula, Integer>> getHashFromList(
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
	 * Retorna uma lista de átomos das fórmulas da estratégia
	 * */
	private static HashMap<AtomicFormula, Integer> getHashAtomosEstrategia(List<SignedFormula> lista){
		if (lista==null || lista.size() <= 0) {return null;}
		HashMap<AtomicFormula, Integer> rt = new HashMap<AtomicFormula, Integer>();
		HashMap<AtomicFormula, Integer> tmp;
		for(int i = 0; i <lista.size(); i++){
			tmp = getHashFromFormula(lista.get(i).getFormula());
			if (tmp==null){continue;}
			addHash(tmp, rt);
		}
		return rt;
	}
	
	/**
	 * Retorna um Map com as frequências de uma fórmula
	 * */
	private static HashMap<AtomicFormula, Integer> getHashFromFormula(Formula f){
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
	
	private static void addHash(HashMap<AtomicFormula, Integer> hMap, HashMap<AtomicFormula, Integer> hMapFinal){
		for (Map.Entry<AtomicFormula, Integer> pair : hMap.entrySet()) {
			addHash(pair.getKey(), pair.getValue(), hMapFinal);
		}
	}
	private static void addHash(Formula f, int freq, HashMap<AtomicFormula, Integer> hMapFinal){
		if (hMapFinal.containsKey((AtomicFormula)f)) {
			hMapFinal.put((AtomicFormula)f, (hMapFinal.get((AtomicFormula)f)+freq));
		} else {
			hMapFinal.put((AtomicFormula)f, freq);
		}
	}
	
	
	public static void print(List<SignedFormula> lista){
		for(int i = 0; i < lista.size(); i++) {
			System.out.println(lista.get(i));
		}
	}
	
	public static void print(HashMap<AtomicFormula, Integer> hMap) {
		for (Map.Entry<AtomicFormula, Integer> pair : hMap.entrySet()) {
			System.out.println(pair.getKey() + " : " + pair.getValue());
		}
	}
	
	public static void printHMapList(HashMap<SignedFormula, HashMap<AtomicFormula, Integer>> hMap){
		for (Map.Entry<SignedFormula, HashMap<AtomicFormula, Integer>> pair : hMap.entrySet()) {
			System.out.println("-" + pair.getKey());
			print(pair.getValue());
		}
	}
}