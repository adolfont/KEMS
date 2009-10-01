/*
 * Created on 21/10/2004
 *
 */
package main.proofTree;

/**
 * @author Adolfo Gustavo Serra Seca Neto
 *
 */
public class SignedFormulaNodeState implements State {
	
	public static final SignedFormulaNodeState ANALYSED = 
		new SignedFormulaNodeState ("analysed");  
	public static final SignedFormulaNodeState NOT_ANALYSED = 
		new SignedFormulaNodeState ("not analysed");  
	public static final SignedFormulaNodeState FULFILLED = 
		new SignedFormulaNodeState ("fulfilled");
		  
	String _value;
	
	private SignedFormulaNodeState (String value){
		this._value = value;
	}

	/* (non-Javadoc)
	 * @see proofTree.State#getValue()
	 */
	public Object getValue() {
		return _value;
	}
	
	/* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return _value;
    }

}
