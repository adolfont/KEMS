package main.newstrategy.cpl.configurable.comparator;

import java.util.Comparator;

import logic.signedFormulas.SignedFormula;

/**
 * An interface for signed formula comparators
 * 
 * @author Adolfo Neto
 *
 */
public interface ISignedFormulaComparator extends Comparator<SignedFormula>{
	
	public String getComparatorDescriptor();

}
