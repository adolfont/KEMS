package main.newstrategy.simple.ag.comparator;

import logic.signedFormulas.SignedFormula;
import main.newstrategy.cpl.configurable.comparator.ISignedFormulaComparator;

/**
 * A signed formula comparator that gives preference to formulas whose
 * complexity degree is higher.
 * 
 * @author Emerson Shigueo Sugimoto
 */
public class ComplexityComparatorAGElitista implements ISignedFormulaComparator {

	public static final String DESCRIPTOR = "ccage";

	public ComplexityComparatorAGElitista() {}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	public int compare(SignedFormula sf0, SignedFormula sf1) {
		//System.out.println("Comparando sf0: " + sf0.toString() + "[" + sf0.getComplexity() + "]" +
		//		", sf1: " + sf1.toString() + "[" + sf1.getComplexity() + "] ==> " + rtTeste(sf0, sf1));
		if (sf0.getComplexity() == sf1.getComplexity()) {return 0;}
		return sf0.getComplexity() > sf1.getComplexity() ? -1 : 1;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return "ComplexityComparatorAGElitista";
	}

	public String getComparatorDescriptor() {
		return DESCRIPTOR;
	}

}