/*
 * Created on 26/10/2005
 *
 */
package logic.valuation;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import logic.formulas.Formula;
import logic.signedFormulas.SignedFormula;
import logic.signedFormulas.SignedFormulaList;
import logicalSystems.classicalLogic.ClassicalSigns;

/**
 * Abstract class that represents common features from valuations for CPL, C1
 * and mbC.
 * 
 * @author Adolfo Gustavo Serra Seca Neto
 *  
 */
public abstract class AbstractValuation implements IValuation {

    /** the valuation map */
    private Map<Formula,Boolean> valuationMap;

    /**
     * Constructs a valuation
     */
    public AbstractValuation() {
        valuationMap = new HashMap<Formula,Boolean>();
    }

    /*
     * (non-Javadoc)
     * 
     * @see logic.valuation.IValuation#create(logic.signedFormulas.SignedFormulaList)
     */
    public IValuation create(SignedFormulaList signedFormulas) {
        Iterator<SignedFormula> it = signedFormulas.iterator();

        while (it.hasNext()) {
            SignedFormula sf = (SignedFormula) it.next();

            if (satisfiesCriteria(sf)) {
                include(sf.getFormula(), sf.getSign().equals(
                        ClassicalSigns.TRUE));
            }
        }

        return this;
    }

    /**
     * Verifies if a signed formula satisfies the criteria to be includes in the
     * valuation map.
     * 
     * @param sf -
     *            a signed formula
     * @return true if it satisfies
     */
    protected abstract boolean satisfiesCriteria(SignedFormula sf);

    /**
     * Includes a formula in the valuation map.
     * 
     * @param formula
     * @param b
     */
    protected void include(Formula formula, boolean b) {
        valuationMap.put(formula, new Boolean(b));
    }

    /*
     * (non-Javadoc)
     * 
     * @see logic.valuation.IValuation#getValue(logic.formulas.Formula)
     */
    public boolean getValue(Formula formula) {
        return ((Boolean) valuationMap.get(formula)).booleanValue();
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    public String toString() {
        String result = "[";
        Iterator<Formula> it = valuationMap.keySet().iterator();

        while (it.hasNext()) {
            Formula f = (Formula) it.next();
            result += f.toString() + "=" + getValue(f);
            if (it.hasNext()) {
                result += ",";
            }
        }

        return result + "]";
    }
    
    public Set<Formula> keySet(){
        return valuationMap.keySet();
    }

}
