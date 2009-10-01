/*
 * Created on 15/10/2004
 *
 */
package logic.formulas;

/**
 * Represents a connective.
 * 
 * @author Adolfo Gustavo Serra Seca Neto
 *  
 */
public class Connective {

	Arity _arity;

	String _symbol;

	/**
	 * Returns the arity of the connective.
	 * 
	 * @return arity.
	 */
	public Arity getArity() {
		return _arity;
	}

	/**
	 * Returns the symbol of the connective.
	 * 
	 * @return the symbol.
	 */
	public String getSymbol() {
		return _symbol;
	}

	/**
	 * Creates a connective.
	 * 
	 * @param _symbol -
	 *            a symbol
	 * @param _arity -
	 *            an arity
	 */
	public Connective(String _symbol, Arity _arity) {
		this._symbol = _symbol;
		this._arity = _arity;
	}

	//	/* (non-Javadoc)
	//     * @see java.lang.Object#hashCode()
	//     */
	//    public int hashCode() {
	//        int result = 23;
	//        
	//        result += _arity.hashCode() + _symbol.hashCode();
	//        
	//        return result;
	//    }

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return getSymbol();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object obj) {
		//		if (_symbol.equals(((Connective)obj).getSymbol())){
		//		System.err.println(obj.hashCode() + " " + this.hashCode() );
		//		}
		//	return _symbol.equals(((Connective)obj).getSymbol()) ;
		return this == obj;
	}
}