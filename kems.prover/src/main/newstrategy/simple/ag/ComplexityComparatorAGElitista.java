package main.newstrategy.simple.ag;

import java.util.Collections;

import logic.signedFormulas.SignedFormula;

/**
 * A signed formula comparator that gives preference to formulas whose
 * complexity degree is higher.
 * 
 * BACKUP !
 * 
 * @author Emerson Shigueo Sugimoto
 */
class ComplexityComparatorAGElitista /*implements ISignedFormulaComparator*/ {

	private static final String DESCRIPTOR = "ccage";

	private ComplexityComparatorAGElitista() {}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	private int compare(SignedFormula sf0, SignedFormula sf1) {
		//System.out.println("Comparando sf0: " + sf0.toString() + "[" + sf0.getComplexity() + "]" +
		//		", sf1: " + sf1.toString() + "[" + sf1.getComplexity() + "] ==> " + rtTeste(sf0, sf1));
		if (sf0.getComplexity() == sf1.getComplexity()) {
//			System.out.println(
//					"Complexidades iguais... " + sf0.toString() + ", " + sf1.toString()
//					);
			//trata quando a complex. é igual
			
			//return 0;
			return Collections.reverseOrder().compare(sf0,sf1);
		}
		
//		System.out.println(
//				"#Complexidades diferentes... " + sf0.toString() + ", " + sf1.toString()
//				);
		
		return sf0.getComplexity() > sf1.getComplexity() ? -1 : 1;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
//	private String toString() {
//		return "ComplexityComparatorAGElitista";
//	}

	private String getComparatorDescriptor() {
		return DESCRIPTOR;
	}

}