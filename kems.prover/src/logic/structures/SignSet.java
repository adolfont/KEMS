/*
 * Created on 18/11/2004
 *
 */
package logic.structures;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import logic.signedFormulas.FormulaSign;

/**
 * @author adolfo
 *
 */
public class SignSet  {


    Set<FormulaSign> _s;
    
    public SignSet() {
        _s = new HashSet<FormulaSign>();
    }

    public int size() {
        return _s.size();
    }
    
    public boolean add(FormulaSign s) {
        return _s.add(s);
    }

    public boolean contains(FormulaSign s) {
        return _s.contains(s);
    }

    public String toString(){
        return _s.toString();
    }
    
    public FormulaSign get (int index){
        Iterator<FormulaSign> it = _s.iterator();
        while (it.hasNext()){
            return (FormulaSign)it.next();
        }
        return null;
    }

    public boolean remove(FormulaSign s) {
        return _s.remove(s);
    }


    /**
     * @param i
     * @return
     */
    public Iterator<FormulaSign> iterator() {
        return _s.iterator();
    }

}
