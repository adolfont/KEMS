package main.newstrategy.cpl.configurable.comparator;

import util.NotImplementedException;
import logic.signedFormulas.SignedFormula;



/**
 * An order-based comparator
 * 
 * @author Adolfo Gustavo Serra Seca Neto
 * 
 */
public class ReverseInsertionOrderSignedFormulaComparator implements ISignedFormulaComparator{
	public static final String DESCRIPTOR = "rev"; 
	
	public ReverseInsertionOrderSignedFormulaComparator() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	public int compare(SignedFormula o1, SignedFormula o2) {
		throw new NotImplementedException();
	}

	
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return "ReverseInsertionOrderComparator";
	}

	public String getComparatorDescriptor() {
		return DESCRIPTOR;
	}



	
	

}
