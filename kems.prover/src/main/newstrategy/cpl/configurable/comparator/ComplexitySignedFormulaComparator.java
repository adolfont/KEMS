/*
 * Created on 07/12/2005
 *
 */
package main.newstrategy.cpl.configurable.comparator;

import logic.signedFormulas.SignedFormula;

/**
 * A complexity-based comparator
 * 
 * @author Adolfo Gustavo Serra Seca Neto
 * 
 */
public class ComplexitySignedFormulaComparator implements ISignedFormulaComparator {

	public static final int ASCENDING = 1;

	public static final int DESCENDING = -1;

	private int order;

	public ComplexitySignedFormulaComparator(int order) {
		this.order = order;
	}

	private ComplexitySignedFormulaComparator() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	public int compare(SignedFormula sf0, SignedFormula sf1) {
		if (sf0.getComplexity() < sf1.getComplexity()) {
			return -1 * order;
		} else {
			if (sf0.getComplexity() > sf1.getComplexity()) {
				return 1 * order;
			}

		}

		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		String result = "ComplexityComparator";

		if (order > 0) {
			result += ":increasingOrder";
		} else {
			if (order < 0) {
				result += ":decreasingOrder";
			} else {
				result += ":noOrdering";
			}
		}
		return result;
	}

	public String getComparatorDescriptor() {
		String result;
		if (order > 0) {
			result = "inc";
		} else {
			if (order < 0) {
				result = "dec";
			} else {
				result = "no_ord";
			}
		}
		
		return result;
	}

}
