/*
 * Created on 18/11/2004
 *
 */
package logic.structures;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import logic.formulas.Formula;
import logic.signedFormulas.FormulaSign;
import logic.signedFormulas.SignedFormula;

/**
 * Multi map of formulas into sets of signs (thinking of multivalued logics).
 * 
 * @author adolfo
 * 
 */
public class FormulaSignMultimap {

    Map<Formula,SignSet> _m;

    /**
     * 
     */
    public FormulaSignMultimap() {

        _m = new HashMap<Formula,SignSet>();

    }

    /**
     * @param key
     * @param sign
     * @return previous value associated with specified key, or null if there
     *               was no mapping for key. A null return can also indicate that the
     *               map previously associated null with the specified key.
     */
    public SignSet put(Formula key, FormulaSign sign) {

        return (SignSet) _m.put(key, addSignToSet(key, sign));
    }

    /**
     * @param key
     * @param sign
     * @return
     */
    private SignSet addSignToSet(Formula key, FormulaSign sign) {
        SignSet s;
        if (_m.get(key) == null) {
            s = new SignSet();
        } else
            s = (SignSet) _m.get(key);

        s.add(sign);
        return s;
    }

    public SignSet get(Formula key) {
        return (SignSet) _m.get(key);
    }

    public void remove(Formula key) {
        _m.remove(key);
    }
    
    public void remove(SignedFormula sf){
    	SignSet s = get(sf.getFormula());
    	s.remove(sf.getSign());
    	if (s.size()==0){
    		remove(sf.getFormula());
    	}
    }

    /**
     * @return
     */
    public Set<Formula> keySet() {
        return _m.keySet();
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    public String toString() {
        String result = "";
        Iterator<Formula> it = _m.keySet().iterator();
        while (it.hasNext()) {
            Object next = it.next();
            result += next + " " + _m.get(next)
                    + System.getProperty("line.separator");
        }
        return result;
    }
}