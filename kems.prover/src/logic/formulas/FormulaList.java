/* 
 * Created on 11/11/2004
 *
 */
package logic.formulas;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Class that represents a simple list of formulas.
 * 
 * @author Adolfo Gustavo Serra Seca Neto
 * 
 *  
 */
public class FormulaList {

    List<Formula> _formulas;

    /**
     * Constructs an empty formula list.
     */
    public FormulaList() {
        _formulas = new ArrayList<Formula>();
    }

    /** constructs a list with a formula
     * @param f - a formula
     */
    public FormulaList(Formula f) {
        _formulas = new ArrayList<Formula>();
        _formulas.add(f);
    }

    /** constructs a list with two formulas
     * @param f1 - a formula
     * @param f2 - another formula
     */
    public FormulaList(Formula f1, Formula f2) {
        _formulas = new ArrayList<Formula>();
        _formulas.add(f1);
        _formulas.add(f2);
    }

    /** returns the size of the list
     * @return the size of the list
     */
    public int size() {
        return _formulas.size();
    }

    /** gets teh formula of the index given in the list.
     * @param index
     * @return a formula
     */
    public Formula get(int index) {
        return (Formula) _formulas.get(index);
    }

    /**
     * @param o
     * @return
     */
    public boolean add(Formula f) {
        return _formulas.add(f);
    }

    /**
     * @param o
     * @return
     */
    public boolean contains(Formula o) {
        return _formulas.contains(o);
    }

    /**
     * @param fl
     */
    public void addAll(FormulaList fl) {
        _formulas.addAll(fl.getList());
    }

    private List<Formula> getList() {
        return _formulas;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return _formulas.toString();
    }
    
    /**
     * @return
     */
    public Iterator<Formula> iterator() {
        return _formulas.iterator();
    }

    /**
     * @param value
     */
    public void remove(Formula value) {
        _formulas.remove(value);
    }
}