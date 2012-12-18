package main.newstrategy.simple.ag;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import logic.formulas.AtomicFormula;
import logic.formulas.CompositeFormula;
import logic.formulas.Formula;
import logic.signedFormulas.SignedFormula;
import logic.signedFormulas.SignedFormulaCreator;
import main.newstrategy.ISimpleStrategy;
import main.newstrategy.simple.ag.util.AnaliseNumeroAtomos;
import main.newstrategy.simple.ag.util.AnaliseNumeroAtomosSignedGrosseiro;
import main.proofTree.INode;

public class mainGATESTE {

	private static SignedFormulaCreator sfc;
	private static String packageName = "satlfiinconsdef"; //sats5,satlfiinconsdef
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		sfc = new SignedFormulaCreator(packageName);
		//testeAnaliseDeAtomos();
		testeAtomsSignedGrosseiro();
	}
	
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
	

	//----------------------------------------------
	
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

//	private static void Teste(Formula f){
//		System.out.println("f: " + f);
//		if (f.getImmediateSubformulas().size()==0) {
//			if (f instanceof AtomicFormula) {System.out.println("f atomic: " + f);}
//			return;
//		}
//		for (int i = 0; i < f.getImmediateSubformulas().size(); ++i) {
//			Teste(f.getImmediateSubformulas().get(i));
//		}
//	}
	
	
//	private static int getNumeroAtomosFirstNivel(SignedFormula sf){
//		System.out.println(sf.toString());
//		int rt = 0;
//		if (sf.getFormula().getImmediateSubformulas().size()==0) {return 0;}
//		for (int i = 0; i < sf.getFormula().getImmediateSubformulas().size(); ++i) {
//			System.out.println("f: " + sf.getFormula().getImmediateSubformulas().get(i));
//			if (sf.getFormula().getImmediateSubformulas().get(i) instanceof AtomicFormula){rt++;}
//		}
//		return rt;
//	}
	
	
}