/*
 * Created on 15/10/2004
 *
 */
package logic.signedFormulas;

/**
 * Sign of a signed formula.
 * 
 * @author Adolfo Gustavo Serra Seca Neto
 * 
 */
public class FormulaSign {
    private int _value;
    
    private FormulaSign opposite;

    /**
     * @param value
     */
    public FormulaSign(int value) {
        _value = value;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return _value == 0 ? "F" : "T";
    }

    
    public boolean equals(FormulaSign fs) {
        return (_value == fs.getValue());
    }

    private int getValue() {
        return _value;
    }
    public FormulaSign getOpposite() {
        return opposite;
    }
    public void setOpposite(FormulaSign opposite) {
        this.opposite = opposite;
    }
    
    @Override
    public int hashCode() {
    	return _value + 1;
    }
}
