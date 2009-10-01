/*
 * Created on 24/11/2005
 *
 */
package main.strategy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * [CLASS_DESCRIPTION] 
 *
 * @author Adolfo Gustavo Serra Seca Neto
 *
 */
public class MyHashMap implements Map {
    
    private List keys;
    private List values;
    
    public MyHashMap(){
        keys = new ArrayList();
        values = new ArrayList();
    }

    /* (non-Javadoc)
     * @see java.util.Map#size()
     */
    public int size() {
        return keys.size();
    }

    /* (non-Javadoc)
     * @see java.util.Map#isEmpty()
     */
    public boolean isEmpty() {
        return keys.isEmpty();
    }

    /* (non-Javadoc)
     * @see java.util.Map#containsKey(java.lang.Object)
     */
    public boolean containsKey(Object key) {
        return keys.contains(key);
    }

    /* (non-Javadoc)
     * @see java.util.Map#containsValue(java.lang.Object)
     */
    public boolean containsValue(Object value) {
        return values.contains(value);
    }

    /* (non-Javadoc)
     * @see java.util.Map#get(java.lang.Object)
     */
    public Object get(Object key) {
        Iterator it = keys.iterator();
        int i =0;
        while (it.hasNext()){
            Object iteratingKey = it.next();
            if (iteratingKey.equals(key)){
                return values.get(i);
            }
            i++;
        }
        
        return null;
    }

    /* (non-Javadoc)
     * @see java.util.Map#put(java.lang.Object, java.lang.Object)
     */
    public Object put(Object arg0, Object arg1) {
        Iterator it = keys.iterator();
        int i =0;
        while (it.hasNext()){
            Object iteratingKey = it.next();
            if (iteratingKey.equals(arg0)){
                values.set(i,arg1);
                return values.get(i);
            }
            i++;
        }
        
        keys.add(arg0);
        values.add(arg1);

        return arg1;
    }

    /* (non-Javadoc)
     * @see java.util.Map#remove(java.lang.Object)
     */
    public Object remove(Object key) {
        return null;
    }

    /* (non-Javadoc)
     * @see java.util.Map#putAll(java.util.Map)
     */
    public void putAll(Map arg0) {
    }

    /* (non-Javadoc)
     * @see java.util.Map#clear()
     */
    public void clear() {
    }

    /* (non-Javadoc)
     * @see java.util.Map#keySet()
     */
    public Set keySet() {
        return new HashSet(keys);
    }

    /* (non-Javadoc)
     * @see java.util.Map#values()
     */
    public Collection values() {
        return values;
    }

    /* (non-Javadoc)
     * @see java.util.Map#entrySet()
     */
    public Set entrySet() {
        return null;
    }

}
