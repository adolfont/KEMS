/*
 * Created on 25/11/2004
 *
 */
package logic.structures;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import logic.formulas.Formula;
import logic.formulas.FormulaList;
import logic.signedFormulas.SignedFormula;

/**
 * Maps a formula and a signed formula to the list of subformulas where that
 * formula appears in the signed formula.
 * 
 * @author adolfo
 *  
 */
public class FormulaSubformulaReferencesMultiMap {

    Map<Formula,SignedFormula_FormulaMultimap> _map;

    /**
     *  
     */
    public FormulaSubformulaReferencesMultiMap() {
        super();
        _map = new HashMap<Formula, SignedFormula_FormulaMultimap>();
    }

    /**
     * @param f3
     * @param sf1
     * @param f1
     */
    public void put(Formula f3, SignedFormula sf1, Formula f1) {
        SignedFormula_FormulaMultimap m;
        if (_map.get(f3) == null) {
            m = new SignedFormula_FormulaMultimap();
        } else {
            m = (SignedFormula_FormulaMultimap) _map.get(f3);
        }
        m.put(sf1, f1);
        _map.put(f3, m);
    }
    
    public void remove(Formula f3, SignedFormula sf1, Formula f1){
        if (_map.get(f3) != null) {
            SignedFormula_FormulaMultimap map = (SignedFormula_FormulaMultimap) _map.get(f3);
            map.remove(sf1, f1);
            _map.put(f3, map);
        }
    }
    
    /**
     * @param f3
     * @param sf1
     * @return
     */
    public FormulaList get(Formula f3, SignedFormula sf1) {
        if (_map.get(f3) == null) {
            return null;
        } else {
            SignedFormula_FormulaMultimap m = (SignedFormula_FormulaMultimap) _map.get(f3);
            return (FormulaList) m.get(sf1);
        }
    }

//    /**
//     * @param f3
//     * @return
//     */
//    public FormulaList get(Formula f3) {
//        if (_map.get(f3) == null) {
//            return null;
//        } else {
//            SignedFormula_FormulaMultimap m = (SignedFormula_FormulaMultimap) _map.get(f3);
//            return (FormulaList) m.values();
//        }
//    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return _map.toString();
    }

	public void removeAll(SignedFormula sf) {

		Set<Formula> removed = new HashSet<Formula>();
		Iterator<Formula> it = _map.keySet().iterator();

		while (it.hasNext()) {
			Formula key = (Formula) it.next();
			SignedFormula_FormulaMultimap multimap = (SignedFormula_FormulaMultimap) _map.get(key);
			if (multimap.keySet().contains(sf)) {
				multimap.removeList(sf);

				if (multimap.keySet().size() == 0) {
					removed.add(key);
				}
			}
		}
		
		it = removed.iterator();
		while (it.hasNext()){
			_map.remove(it.next());
		}
	}
    
}