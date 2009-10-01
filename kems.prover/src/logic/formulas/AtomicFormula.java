/*
 * Created on 15/10/2004
 *
 */
package logic.formulas;

import java.util.ArrayList;
import java.util.List;

//import util.HashCodeUtil;

/**
 * Class that represents an atomic formula.
 * 
 * @author Adolfo Gustavo Serra Seca Neto
 *  
 */
public class AtomicFormula extends Formula {

    private String _name;

    //hashCode aspect
    /**
     * Once an atomic formula is created it cannot be modified. Therefore the
     * result of hashCode() can be calculated in the constructor.
     */
    //private int _hashCode;

    /**
     * Creates an atomic formula.
     * 
     * @param _name -
     *                   name of the atom
     */
    public AtomicFormula(String _name) {
        super();
        this._name = _name;

//        // hashCode aspect
//        this._hashCode = calculateHashCode();
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public boolean equals(Object f) {
        if (f instanceof AtomicFormula) {
            return this.toString().equals(f.toString());
        } else
            return false;
    }

//    // hashCode aspect
//    /**
//     * @return the hashcode calculated for the formula.
//     */
//    private int calculateHashCode() {
//        int result = HashCodeUtil.SEED;
//        result = HashCodeUtil.hash(result, _name);
//        return result;
//    }
//
//    // hashCode aspect
//    /*
//     * (non-Javadoc)
//     * 
//     * @see java.lang.Object#hashCode()
//     */
//    public int hashCode() {
//        return _hashCode;
//    }

    /*
     * (non-Javadoc)
     * 
     * @see formulasNew.Formula#getSubformulas()
     */
    public List<Formula> getImmediateSubformulas() {
        return new ArrayList<Formula>();
    }

    /*
     * (non-Javadoc)
     * 
     * @see formulasNew.Formula#toString()
     */
    public String toString() {
        return _name;
    }

    /*
     * (non-Javadoc)
     * 
     * @see formulasNew.Formula#clone(formulasNew.FormulaFactory)
     */
    public Formula clone(FormulaFactory ff) {
        return ff.createAtomicFormula(this._name);
    }

}