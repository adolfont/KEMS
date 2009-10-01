/*
 * Created on 07/12/2005
 *
 */
package main.newstrategy.cpl.configurable.comparator;

import logic.formulas.AtomicFormula;
import logic.formulas.CompositeFormula;
import logic.formulas.Connective;
import logic.formulas.Formula;
import logic.signedFormulas.SignedFormula;

/**
 * A signed formula comparator that gives preference to formulas whose main
 * connective is the one specified
 * 
 * @author Adolfo Gustavo Serra Seca Neto
 *  
 */
public class ConnectiveSignedFormulaComparator implements ISignedFormulaComparator {

    private Connective connective;

    public ConnectiveSignedFormulaComparator(Connective conn) {
        this.connective = conn;
    }

    private ConnectiveSignedFormulaComparator() {
    };

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
                    Connective c0 = ((CompositeFormula) f0).getConnective();
                    Connective c1 = ((CompositeFormula) f1).getConnective();

                    if (c0.equals(connective)) {
                        return -1;
                    } else {
                        if (c1.equals(connective)) {
                            return 1;
                        } else {
                            return 0;
                        }
                    }
                }
            }
        }

    }

    public String toString() {
        return "ConnectiveSignedFormulaComparator:" + connective;
    }

	public String getComparatorDescriptor() {
		return connective.toString();
	}

}
