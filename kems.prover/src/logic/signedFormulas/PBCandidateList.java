/*
 * Created on 12/11/2003, 09:47:19 
 *
 */
package logic.signedFormulas;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import main.newstrategy.cpl.configurable.comparator.ISignedFormulaComparator;
import main.newstrategy.cpl.configurable.comparator.InsertionOrderSignedFormulaComparator;
import main.newstrategy.cpl.configurable.comparator.NormalFormulaOrderSignedFormulaComparator;
import main.newstrategy.cpl.configurable.comparator.ReverseInsertionOrderSignedFormulaComparator;

import org.apache.log4j.Logger;

/**
 * A class for representing lists of PB candidates, which are signed formulas..
 * 
 * @author Adolfo Neto
 * 
 */
public class PBCandidateList {

	/**
	 * logger
	 */
	public static Logger logger = Logger.getLogger(PBCandidateList.class);

	private List<SignedFormula> _sfl;

	private Map<SignedFormula, Long> _orderFormulaIndexMap;

	private Map<Long, SignedFormula> _orderIndexFormulaMap;

	private long _orderCounter;

	boolean _changed;

	public PBCandidateList() {
		_sfl = new LinkedList<SignedFormula>();
		_orderFormulaIndexMap = new HashMap<SignedFormula, Long>();
		_orderIndexFormulaMap = new HashMap<Long, SignedFormula>();
		_orderCounter = Long.MIN_VALUE;
		_changed = false;
	}

	public PBCandidateList(SignedFormula sf) {
		this();
		add(sf);
	}

	public void add(SignedFormula sf) {
		if (!_sfl.contains(sf)) {
			_sfl.add(sf);
			putInMaps(_orderFormulaIndexMap, _orderIndexFormulaMap, sf);
			_changed = true;
		}
	}

	public void addAll(PBCandidateList sfl) {
		Iterator<SignedFormula> it = sfl.iterator();
		while (it.hasNext()) {
			SignedFormula sf = (SignedFormula) it.next();
			add(sf);
		}
	}

	public boolean remove(SignedFormula sf) {
		if (!_sfl.contains(sf)) {
			logger.debug("Trying to remove a formula which is not in the PB candidate list");
			// logger.debug("Trying to remove a formula (" + sf +
			// ") which is not in the PB candidate list "
			// + _sfl + " - Acceptable in some strategies");
			return false;
		} else {
			_changed = true;
			return _sfl.remove(sf);
		}
	}

	private void putInMaps(Map<SignedFormula, Long> orderFormulaIndexMap,
			Map<Long, SignedFormula> orderIndexFormulaMap, SignedFormula sf) {
		Long next = nextOrderCounter();
		orderFormulaIndexMap.put(sf, next);
		orderIndexFormulaMap.put(next, sf);
	}

	private Long nextOrderCounter() {
		if (_orderCounter < Long.MAX_VALUE) {
			return new Long(_orderCounter++);
		} else {
			recreateMaps();
			if (_orderCounter == Long.MAX_VALUE) {
				throw new RuntimeException("Too many formulas in a PB Candidate List");
			} else {
				_orderCounter++;
				return new Long(_orderCounter++);
			}
		}
	}

	private void recreateMaps() {
		// TODO
		logger.error("Recreating PBCandidateList maps");

		Set<Long> indexes = _orderIndexFormulaMap.keySet();
		_orderCounter = Long.MIN_VALUE;

		Map<SignedFormula, Long> temporaryOrderFormulaIndexMap;

		Map<Long, SignedFormula> temporaryOrderIndexFormulaMap;

		temporaryOrderFormulaIndexMap = new HashMap<SignedFormula, Long>();
		temporaryOrderIndexFormulaMap = new HashMap<Long, SignedFormula>();

		Object[] indexesArray = indexes.toArray();
		// TODO SORT ASPECT
		Arrays.sort(indexesArray);

		for (int i = 0; i < indexesArray.length; i++) {
			SignedFormula sf = _orderIndexFormulaMap.get((Long) indexesArray[i]);

			putInMaps(temporaryOrderFormulaIndexMap, temporaryOrderIndexFormulaMap, sf);

		}

		_orderFormulaIndexMap = temporaryOrderFormulaIndexMap;
		_orderIndexFormulaMap = temporaryOrderIndexFormulaMap;
	}

	public void sort(ISignedFormulaComparator comparator) {

		if (!comparator.getComparatorDescriptor().equals(
				InsertionOrderSignedFormulaComparator.DESCRIPTOR)
				&& _changed) {
			if (comparator.getComparatorDescriptor().equals(
					NormalFormulaOrderSignedFormulaComparator.DESCRIPTOR)) {
				// string-order based sort???
				// TODO SORT ASPECT
				Collections.sort(_sfl);

			} else {
				if (comparator.getComparatorDescriptor().equals(
						ReverseInsertionOrderSignedFormulaComparator.DESCRIPTOR)) {
					// TODO SORT ASPECT
					reverseInsertionOrderSort();
				} else {
					// TODO SORT ASPECT
					Collections.sort(_sfl, comparator);
				}
			}
		}

	}

	private void reverseInsertionOrderSort() {
		Set<Long> keys = _orderIndexFormulaMap.keySet();
		List<Long> l = new ArrayList<Long>(keys);
		Collections.sort(l);
		LinkedList<SignedFormula> sflToReplace = new LinkedList<SignedFormula>();
		Iterator<Long> it = l.iterator();
		while (it.hasNext()) {
			SignedFormula sf = _orderIndexFormulaMap.get(it.next());
			if (_sfl.contains(sf)) {
				sflToReplace.add(0, sf);
			}
		}
		_sfl = sflToReplace;
	}

	public SignedFormula get(int index) {
		return _sfl.get(index);
	}

	public int size() {
		return _sfl.size();
	}

	public String toString() {
		return _sfl.toString();
	}

	public boolean contains(SignedFormula sf) {
		return _sfl.contains(sf);
	}

	public Iterator<SignedFormula> iterator() {
		return _sfl.iterator();
	}
}