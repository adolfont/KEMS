/*
 * Created on 19/11/2004
 *
 */
package rules.structures;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import logic.formulas.Connective;
import logic.signedFormulas.FormulaSign;
import rules.IRule;
import rules.Rule;

/**
 * @author adolfo
 *  
 */
public class SignConnectiveRuleMap {

    Map<SignConnectivePair,IRule> _m = new HashMap<SignConnectivePair, IRule>();

    List<SignConnectivePair> _pairs = new ArrayList<SignConnectivePair>();

    public Rule get(FormulaSign fs, Connective conn) {
        return (Rule) _m.get(createSignConnectivePair(fs, conn));
    }

    private SignConnectivePair createSignConnectivePair(FormulaSign fs,
            Connective conn) {
        SignConnectivePair scp;
        for (int i = 0; i < _pairs.size(); i++) {
            scp = (SignConnectivePair) _pairs.get(i);
            if (scp.getConnective().equals(conn) && scp.getSign().equals(fs)) {
                return scp;
            }
        }

        scp = new SignConnectivePair(fs, conn);
        _pairs.add(scp);
        return scp;
    }

    public void put(FormulaSign fs, Connective conn, Rule r) {
        _m.put(createSignConnectivePair(fs, conn), r);
    }
    
    /* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return _m.toString();
	}
}


class SignConnectivePair {
    public SignConnectivePair(FormulaSign _fs, Connective _conn) {
        super();
        this._fs = _fs;
        this._conn = _conn;
    }

    public Connective getConnective() {
        return _conn;
    }

    public FormulaSign getSign() {
        return _fs;
    }

    FormulaSign _fs;

    Connective _conn;
    
    /* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return "< "+_fs+","+_conn+" >";
	}
}