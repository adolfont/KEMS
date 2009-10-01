/*
 * Created on 25/11/2004
 *
 */
package logic.structures;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import logic.formulas.Formula;
import logic.formulas.FormulaList;
import logic.signedFormulas.SignedFormula;
import logic.signedFormulas.SignedFormulaList;

/**
 * @author adolfo
 *  
 */
public class SignedFormula_FormulaMultimap {

	HashMap<SignedFormula, FormulaList> _map;

	/**
	 *  
	 */
	public SignedFormula_FormulaMultimap() {
		super();
		_map = new HashMap<SignedFormula, FormulaList>();
	}

	public FormulaList get(SignedFormula key) {
		return (FormulaList) _map.get(key);
	}

	public void put(SignedFormula key, Formula value) {
		FormulaList fl;
		if (_map.get(key) == null) {
			fl = new FormulaList(value);
		} else {
			fl = (FormulaList) _map.get(key);
			if (!fl.contains(value)) {
				fl.add(value);
			}
		}
		_map.put(key, fl);
	}
	
	public void remove(SignedFormula key, Formula value){
		FormulaList fl;
		if (_map.get(key) != null) {
			fl = (FormulaList) _map.get(key);
			if (fl.contains(value)) {
				fl.remove(value);
				_map.put(key,fl);
			}
		}
	}
	
	public void removeList(SignedFormula key){
		_map.remove(key);
	}

	/**
	 * @return
	 */
	public SignedFormulaList keySet() {
		Set<SignedFormula> s = _map.keySet();
		Iterator<SignedFormula> i = s.iterator();
		SignedFormulaList sfl = new SignedFormulaList();

		while (i.hasNext()) {
			SignedFormula sf = (SignedFormula) i.next();
			sfl.add(sf);
		}

		return sfl;
	}

//	/**
//	 * @return
//	 */
//	public FormulaList values() {
//		Iterator it = _map.values().iterator();
//		FormulaList fl = new FormulaList();
//		while (it.hasNext()) {
//			fl.add((Formula) it.next());
//		}
//		return fl;
//	}
	
	/* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return _map.toString();
    }
}