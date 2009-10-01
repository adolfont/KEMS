package main.newstrategy.cpl.configurable.comparator;

import util.NotImplementedException;
import logic.signedFormulas.SignedFormula;



/**
 * A formula order-based comparator
 * 
 * @author Adolfo Gustavo Serra Seca Neto
 * 
 */
public class NormalFormulaOrderSignedFormulaComparator implements ISignedFormulaComparator{
	
	public static final String DESCRIPTOR = "nfo"; 
	
	public NormalFormulaOrderSignedFormulaComparator() {
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
		return "NormalFormulaOrderComparator";
	}

	public String getComparatorDescriptor() {
		return DESCRIPTOR;
	}



	
	

}
