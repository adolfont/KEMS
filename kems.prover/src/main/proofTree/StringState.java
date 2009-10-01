/*
 * Created on 21/10/2004
 *
 */
package main.proofTree;

/**
 * @author Adolfo Gustavo Serra Seca Neto
 *
 */
public class StringState implements State{
	
	public static final StringState ANALYSED = new StringState("analysed");
	
	String _value;
	private StringState(String value){
		this._value = value;
	}

	/* (non-Javadoc)
	 * @see proofTree.State#getValue()
	 */
	public Object getValue() {
		return ANALYSED;
	}


}
