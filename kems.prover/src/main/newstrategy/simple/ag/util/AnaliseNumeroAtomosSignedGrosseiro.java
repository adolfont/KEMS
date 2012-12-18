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
 * Análisa as possíveis inconsistências dos átomos das regras PB (PBCandidateList) e compara com
 * os átomos das demais fórmulas da estratégia
 * @author Emerson Shigueo Sugimoto
 * */
public class AnaliseNumeroAtomosSignedGrosseiro {
	/**
	 * Método final
	 * @return Seleciona a SignedFormula com a maior frequência de átomos da base de conhecimentos  
	 * */
	public SignedFormula GetSignedFormulaInconsitenciasSignedGrosseiro(
			ArrayList<SignedFormula> listaPB,
			ISimpleStrategy strategy){
		return GetSignedFormulaInconsitenciasSignedGrosseiro(
				getHashFromListSignedGrosseiro(listaPB), 
				getHashAtomosEstrategiaSignedGrosseiro(toList(strategy, listaPB)));
	}
	
	/**
	 * Método final
	 * @return Seleciona a SignedFormula com a maior frequência de átomos da base de conhecimentos  
	 * */
	public SignedFormula GetSignedFormulaInconsitenciasSignedGrosseiro(
			List<SignedFormula> listaPB,
			Map<SignedFormula, INode> map){
		return GetSignedFormulaInconsitenciasSignedGrosseiro(
				getHashFromListSignedGrosseiro(listaPB), 
				getHashAtomosEstrategiaSignedGrosseiro(toList(map, listaPB)));
	}
	
	public SignedFormula GetSignedFormulaInconsitenciasSignedGrosseiro(
			HashMap<SignedFormula, HashMap<String, Integer>> hAtomosPB,
			HashMap<String, Integer> hAtomosEstrategia
			){
		SignedFormula rt = null;
		int inconsitenciasRt = 0, inconsistenciasTmp = 0;
		for (Map.Entry<SignedFormula, HashMap<String, Integer>> pairPB : hAtomosPB.entrySet()) {
			inconsistenciasTmp = VerificaNumeroInconsitenciasSignedGrosseiro(pairPB.getValue(), hAtomosEstrategia);
			if (rt == null) {
				rt = pairPB.getKey();
				inconsitenciasRt = inconsistenciasTmp;
			} else if (inconsitenciasRt < inconsistenciasTmp){
				rt = pairPB.getKey();
				inconsitenciasRt = inconsistenciasTmp;
			}
		}
		return rt;
	} 
	
	public int VerificaNumeroInconsitenciasSignedGrosseiro(
			HashMap<String, Integer> hPb, 
			HashMap<String, Integer> hAtomosEstrategia){
		int rt = 0;
		String signPB, atomPb, signBusca;
		for (Map.Entry<String, Integer> pairPb : hPb.entrySet()) {
			signPB = pairPb.getKey().substring(0, pairPb.getKey().indexOf(" "));
			atomPb = pairPb.getKey().substring(pairPb.getKey().indexOf(" ")+1);
			
			signBusca = signPB.equals("T") ? "F" : "T";
			if (hAtomosEstrategia.containsKey(signBusca + " " + atomPb)){
				rt++;
			}
		}
		return rt;
	}
	
	//----------------------
	/**
	 * Para Cada SignedFormula cria um map com as frequências das fórmulas atômicas
	 * */
	public HashMap<SignedFormula, HashMap<String, Integer>> getHashFromListSignedGrosseiro(
			List<SignedFormula> lista){
		if (lista==null || lista.size() <= 0) {return null;}
		HashMap<SignedFormula, HashMap<String, Integer>> rt = new HashMap<SignedFormula, HashMap<String, Integer>>();
		HashMap<String, Integer> tmp = null;
		for(int i = 0; i < lista.size(); i++){
			if (rt.containsKey(lista.get(i))) {continue;}
			tmp = getHashFromFormulaSignedGrosseiro(lista.get(i).getFormula(), lista.get(i).getSign().toString());
			rt.put(lista.get(i), tmp);
		}
		return rt;
	}
	//--------------------------
	public HashMap<String, Integer> getHashAtomosEstrategiaSignedGrosseiro(ArrayList<SignedFormula> lista){
		if (lista==null || lista.size() <= 0) {return null;}
		HashMap<String, Integer> rt = new HashMap<String, Integer>();
		HashMap<String, Integer> tmp;
		for(int i = 0; i <lista.size(); i++){
			tmp = getHashFromFormulaSignedGrosseiro(lista.get(i).getFormula(), lista.get(i).getSign().toString());
			if (tmp==null){continue;}
			addHashSignedGrosseiro(tmp, rt);
		}
		return rt;
	}
	//-----------------------
	public HashMap<String, Integer> getHashFromFormulaSignedGrosseiro(Formula f, String sign){
		HashMap<String, Integer> rt = new HashMap<String, Integer>();
		if (f instanceof AtomicFormula) {
			rt.put(sign + " " + f.toString(), 1);
			return rt;
		}
		if (f.getImmediateSubformulas().size()==0) {return null;}
		HashMap<String, Integer> tmp = new HashMap<String, Integer>();
		for(int i = 0; i < f.getImmediateSubformulas().size(); i++){
			tmp = getHashFromFormulaSignedGrosseiro(f.getImmediateSubformulas().get(i), sign);
			if (tmp == null || tmp.size() <= 0) {continue;}
			addHashSignedGrosseiro(tmp, rt);
		}
		return rt;
	}

	private void addHashSignedGrosseiro(HashMap<String, Integer> hMap, HashMap<String, Integer> hMapFinal){
		for (Map.Entry<String, Integer> pair : hMap.entrySet()) {
			addHashSignedGrosseiro(pair.getKey(), pair.getValue(), hMapFinal);
		}
	}
	private void addHashSignedGrosseiro(String s, int freq, HashMap<String, Integer> hMapFinal){
		if (hMapFinal.containsKey(s)) {
			hMapFinal.put(s, (hMapFinal.get(s)+freq));
		} else {
			hMapFinal.put(s, freq);
		}
	}
	//----------------------------------------------
	/**
	 * @param listaPbIgnore: itens a serem ignorados por pertencem a lista PB
	 * */
	public ArrayList<SignedFormula> toList(Map<SignedFormula, INode> map, List<SignedFormula> listaPbIgnore){
		ArrayList<SignedFormula> rt = new ArrayList<SignedFormula>();
		for (Map.Entry<SignedFormula, INode> pair : map.entrySet()) {
			if (rt.contains(pair.getKey())) continue;
			if (listaPbIgnore!=null){
				if (listaPbIgnore.contains(pair.getKey())){continue;}
			}
			rt.add(pair.getKey());
		}
		return rt;
	}
	/**
	 * Converte em list as fórmulas de uma estratégia
	 * @listaPbIgnore: itens a serem ignorados por pertencem a lista PB
	 * */
	public ArrayList<SignedFormula> toList(ISimpleStrategy strategy, ArrayList<SignedFormula> listaPbIgnore){
		return toList(strategy.getProofTree().getSignedFormulasToNodes(), listaPbIgnore);
	}
}
/*
------------------------------------------
Testa a Classe
------------------------------------------
private static void testeAtomsSignedGrosseiro(){
	AnaliseNumeroAtomosSignedGrosseiro anasg = new AnaliseNumeroAtomosSignedGrosseiro();
	
	ArrayList<SignedFormula> listaPB = new ArrayList<SignedFormula>();
	listaPB.add(sfc.parseString("F(B->C)&(D|A)"));
	listaPB.add(sfc.parseString("T(C->B)&(B|D)"));
	
	Map<SignedFormula, INode> map = new HashMap<SignedFormula, INode>();
	map.put(sfc.parseString("F(B->C)&(D|A)"), null);
	map.put(sfc.parseString("TC|D"), null);
	map.put(sfc.parseString("TB"), null);
	map.put(sfc.parseString("T(C->B)&(B|D)"), null);
	
	ArrayList<SignedFormula> listaFormulasEstrategia = anasg.toList(map, listaPB);
	System.out.println("------------------listaFormulasEstrategia:");
	new AnaliseNumeroAtomos().print(listaFormulasEstrategia);
	System.out.println("------------------listaPB:");
	new AnaliseNumeroAtomos().print(listaPB);
	
	System.out.println("------------------hAtomosEstrategia:");
	HashMap<String, Integer> hAtomosEstrategia = anasg.getHashAtomosEstrategiaSignedGrosseiro(listaFormulasEstrategia);
	for (Map.Entry<String, Integer> pair : hAtomosEstrategia.entrySet()) {
		System.out.println(pair.getKey() + " : " + pair.getValue());
	}
	System.out.println("------------------hAtomosPB:");
	HashMap<SignedFormula, HashMap<String, Integer>> hAtomosPB = anasg.getHashFromListSignedGrosseiro(listaPB);
	for (Map.Entry<SignedFormula, HashMap<String, Integer>> pairPB : hAtomosPB.entrySet()) {
		System.out.println(
				pairPB.getKey() + " ["+pairPB.getValue()+"]" 
		);
	}
	
	System.out.println("---------------------------------VerificaNumeroInconsitencias");
	for (Map.Entry<SignedFormula, HashMap<String, Integer>> pairPB : hAtomosPB.entrySet()) {
		System.out.println(" - " + pairPB.getKey() + " : " +
				anasg.VerificaNumeroInconsitenciasSignedGrosseiro(pairPB.getValue(), hAtomosEstrategia)
		);
	}
	System.out.println("---------------------------------GetSignedFormulaInconsitenciasSignedGrosseiro:");
	System.out.println(anasg.GetSignedFormulaInconsitenciasSignedGrosseiro(hAtomosPB, hAtomosEstrategia));
	
	System.out.println("----- GetSignedFormulaInconsitenciasSignedGrosseiro 2 :");
	System.out.println(anasg.GetSignedFormulaInconsitenciasSignedGrosseiro(listaPB, map));
}
*/
