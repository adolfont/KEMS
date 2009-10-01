/*
 * Created on 12/11/2003, 09:47:19 
 *
 */
package logic.signedFormulas;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * A class for representing lists of signed formulas. Uses Adaptor design
 * pattern.
 * 
 * @author Adolfo Neto
 *  
 */
public class SignedFormulaList {

    private List<SignedFormula> _sfl;

    public SignedFormulaList() {
        _sfl = new LinkedList<SignedFormula>();
    }

    public SignedFormulaList(SignedFormula sf) {
        this();
        _sfl.add(sf);
    }

    public SignedFormulaList(List<SignedFormula> sfl) {
    	this();
        _sfl.addAll(sfl);
    }

    public void add(SignedFormula sf) {
        _sfl.add(sf);
    }

    public void addAll(SignedFormulaList sfl) {
        _sfl.addAll(sfl.getList());
    }
    
    public void addAll(Collection<SignedFormula> c) {
        Iterator<SignedFormula> i = c.iterator();
        SignedFormulaList sfl = new SignedFormulaList();
        
        while(i.hasNext()){
            SignedFormula sf = (SignedFormula) i.next();
            sfl.add(sf);
        }
        
        addAll(sfl);
    }
   

    public List<SignedFormula> getList() {
        return _sfl;
    }

    public SignedFormula get(int index) {
        return (SignedFormula) _sfl.get(index);
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

    public boolean remove(SignedFormula sf) {
        return _sfl.remove(sf);
    }

    public SignedFormula remove(int index) {
        return (SignedFormula) _sfl.remove(index);
    }

    public Iterator<SignedFormula> iterator() {
        return _sfl.iterator();
    }

    /**
     * @param sf
     * @param i
     */
    public void add(int i, SignedFormula sf) {
        _sfl.add(i, sf);
    }
    
//    public void sort (ISignedFormulaComparator comparator){
//		System.err.println("BEFORE SORT: "+ _sfl);
//
//    	if (comparator.getComparatorDescriptor().equals(NormalFormulaOrderSignedFormulaComparator.DESCRIPTOR)){
//            Collections.sort(_sfl);
//    	}
//    	else{
//        	if (comparator.getComparatorDescriptor().equals(ReverseInsertionOrderSignedFormulaComparator.DESCRIPTOR)){
//        		System.err.println(_sfl.size());
//        	}
//        	else{
//                Collections.sort(_sfl, comparator);
//        	}
//    	}
//		System.err.println("AFTER SORT:" +_sfl);
//
//    	
//    }

    /**
     * @return
     */
    public String show() {
        String result="";
        Iterator<SignedFormula> it = this.iterator();
        while (it.hasNext()){
            result += it.next()+ System.getProperty("line.separator");
        }
        
        return result;
    }

//	public void reverse() {
//		Collections.reverse(_sfl);
//	}
}