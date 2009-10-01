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
import logic.signedFormulas.SignedFormula;
import logic.signedFormulas.SignedFormulaList;

/**
 * @author adolfo
 * 
 */
public class FormulaSignedFormulaMultiMap {

	Map<Formula, SignedFormulaList> _map;

	/**
	 * 
	 */
	public FormulaSignedFormulaMultiMap() {
		super();
		_map = new HashMap<Formula, SignedFormulaList>();
	}

	// /* (non-Javadoc)
	// * @see java.util.Map#size()
	// */
	// public int size() {
	// return 0;
	// }
	//
	// /* (non-Javadoc)
	// * @see java.util.Map#clear()
	// */
	// public void clear() {
	//
	// }

	// /* (non-Javadoc)
	// * @see java.util.Map#isEmpty()
	// */
	// public boolean isEmpty() {
	// return false;
	// }
	//
	// /* (non-Javadoc)
	// * @see java.util.Map#containsKey(java.lang.Object)
	// */
	// public boolean containsKey(Object key) {
	// return false;
	// }

	// /* (non-Javadoc)
	// * @see java.util.Map#containsValue(java.lang.Object)
	// */
	// public boolean containsValue(Object value) {
	// return false;
	// }
	//
	// /* (non-Javadoc)
	// * @see java.util.Map#values()
	// */
	// public Collection values() {
	// return null;
	// }

	// /* (non-Javadoc)
	// * @see java.util.Map#putAll(java.util.Map)
	// */
	// public void putAll(Map t) {
	//
	// }
	//
	// /* (non-Javadoc)
	// * @see java.util.Map#entrySet()
	// */
	// public Set entrySet() {
	// return null;
	// }
	//
	// /* (non-Javadoc)
	// * @see java.util.Map#keySet()
	// */
	// public Set keySet() {
	// return null;
	// }

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Map#get(java.lang.Object)
	 */
	public SignedFormulaList get(Formula key) {
		return (SignedFormulaList) _map.get(key);
	}

	// /* (non-Javadoc)
	// * @see java.util.Map#remove(java.lang.Object)
	// */
	// public Object remove(Object key) {
	// return null;
	// }

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Map#put(java.lang.Object, java.lang.Object)
	 */
	public void put(Formula key, SignedFormula value) {
		SignedFormulaList sfl;
		if (_map.get(key) == null) {
			sfl = new SignedFormulaList(value);
		} else {
			sfl = (SignedFormulaList) _map.get(key);
			if (!sfl.contains(value)) {
				sfl.add(value);
			}
		}
		_map.put(key, sfl);
	}

	public void remove(Formula key, SignedFormula value) {
		if (_map.get(key) != null) {
			SignedFormulaList sfl = (SignedFormulaList) _map.get(key);
			if (sfl.contains(value)) {
				sfl.remove(value);
				_map.put(key, sfl);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
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
			SignedFormulaList values = (SignedFormulaList) _map.get(key);
			if (values.contains(sf)) {
				values.remove(sf);

				if (values.size() == 0) {
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