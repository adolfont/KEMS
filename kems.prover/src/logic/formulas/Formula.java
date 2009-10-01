package logic.formulas;

import java.util.List;


/**
 * Abstract class that represents a formula.
 * 
 * @author Adolfo Gustavo Serra Seca Neto
 *  
 */
public abstract class Formula {

    /**
     *  
     */
    protected Formula() {
    }

    /**
     * verifies if a formula is a strict subformula of this formula
     * 
     * @param f -
     *            a formula
     * @return true if f is a strict subformula of this formula.
     */
    public boolean isStrictSubformula(Formula f) {

        if (f.getImmediateSubformulas().contains(this)) {
            return true;
        }
        for (int i = 0; i < f.getImmediateSubformulas().size(); i++) {
            if (this.isSubformula((Formula) f.getImmediateSubformulas().get(i))) {
                return true;
            }
        }
        return false;
    }

    /**
     * verifies if a formula is a subformula of this formula
     * 
     * @param f -
     *            a formula
     * @return true if f is a subformula of this formula.
     */
    public boolean isSubformula(Formula f) {
        return this.equals(f) || this.isStrictSubformula(f);
    }

    /**
     * gets immediate subformulas of this formula.
     * 
     * @return list of immediate subformulas or empty list if it is an atomic
     *         formula.
     */
    public abstract List<Formula> getImmediateSubformulas();

    public abstract String toString();

    /**
     * clones this formula in a formula factory.
     * 
     * @param ff -
     *            a formula factory
     * @return the cloned formula
     */
    public abstract Formula clone(FormulaFactory ff);
    
    @Override
    public int hashCode() {
    	return this.toString().hashCode();
    }

}