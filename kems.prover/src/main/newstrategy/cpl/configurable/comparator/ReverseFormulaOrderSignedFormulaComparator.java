package main.newstrategy.cpl.configurable.comparator;

import java.util.Collections;

import logic.signedFormulas.SignedFormula;


/**
 * A formula order-based comparator
 * 
 * @author Adolfo Gustavo Serra Seca Neto
 * 
 */
public class ReverseFormulaOrderSignedFormulaComparator implements ISignedFormulaComparator{
	
	public ReverseFormulaOrderSignedFormulaComparator() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	public int compare(SignedFormula o1, SignedFormula o2) {
		return Collections.reverseOrder().compare(o1,o2);
	}

	
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return "ReverseFormulaOrderComparator";
	}

	public String getComparatorDescriptor() {
		return "rfo";
	}



	
	

}
