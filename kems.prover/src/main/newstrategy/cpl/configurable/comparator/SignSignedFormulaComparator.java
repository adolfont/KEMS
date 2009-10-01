/*
 * Created on 07/12/2005
 *
 */
package main.newstrategy.cpl.configurable.comparator;

import logic.formulas.AtomicFormula;
import logic.formulas.Formula;
import logic.signedFormulas.FormulaSign;
import logic.signedFormulas.SignedFormula;

/**
 * A signed formula comparator that gives preference to formulas whose sign
 * is the one specified 
 *
 * @author Adolfo Gustavo Serra Seca Neto
 *
 */
public class SignSignedFormulaComparator implements ISignedFormulaComparator{
    
    private FormulaSign sign;

    public SignSignedFormulaComparator(FormulaSign sign){
        this.sign = sign;
    }
    
    private SignSignedFormulaComparator(){}; 

    public int compare(SignedFormula sf0, SignedFormula sf1) {
        Formula f0 = sf0.getFormula();
        Formula f1 = sf1.getFormula();

        if (f0 instanceof AtomicFormula && f1 instanceof AtomicFormula) {
            return f0.toString().compareTo(f1.toString());
        } else {
            if (f0 instanceof AtomicFormula) {
                return -1;
            } else {
                if (f1 instanceof AtomicFormula) {
                    return 1;
                } else {
                    FormulaSign s0 = sf0.getSign();
                    FormulaSign s1 = sf1.getSign();

                    if (s0.equals(sign)) {
                        return -1;
                    } else {
                        if (s1.equals(sign)) {
                            return 1;
                        } else {
                            return 0;
                        }
                    }
                }
            }
        }

    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return "SignSignedFormulaComparator:"+sign;
    }

	public String getComparatorDescriptor() {
		return sign.toString();
	}

}
