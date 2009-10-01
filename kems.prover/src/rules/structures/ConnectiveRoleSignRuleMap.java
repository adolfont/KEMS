/*
 * Created on 30/11/2004
 *
 */
package rules.structures;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import logic.formulas.Connective;
import logic.signedFormulas.FormulaSign;
import rules.KERuleRole;
import rules.OneConclusionRule;
import rules.Rule;

/**
 * @author adolfo
 *  
 */
public class ConnectiveRoleSignRuleMap {

    Map<ConnectiveRoleSignTrio,OneConclusionRule> _m = new HashMap<ConnectiveRoleSignTrio,OneConclusionRule>();

    List<ConnectiveRoleSignTrio> _pairs = new ArrayList<ConnectiveRoleSignTrio>();

    /**
     *  
     */
    public ConnectiveRoleSignRuleMap() {
        super();
    }

    /**
     * @param conn
     * @param conn2
     * @param rulew
     */
    public void put(Connective conn, KERuleRole role, FormulaSign sign,
            OneConclusionRule rulew) {
        _m.put(createConnectiveRoleSignTrio(conn, role, sign), rulew);
    }

    /**
     * @param conn
     * @param conn2
     * @return
     */
    public Rule get(Connective conn, KERuleRole role, FormulaSign sign) {
        return (Rule) _m.get(createConnectiveRoleSignTrio(conn, role, sign));
    }

    private ConnectiveRoleSignTrio createConnectiveRoleSignTrio(Connective conn,
            KERuleRole role, FormulaSign sign) {
        ConnectiveRoleSignTrio ccp;
        for (int i = 0; i < _pairs.size(); i++) {
            ccp = (ConnectiveRoleSignTrio) _pairs.get(i);
            if (ccp.getConnective1().equals(conn) && ccp.getRole().equals(role)
                    && ccp.getSign().equals(sign)) {
                return ccp;
            }
        }

        ccp = new ConnectiveRoleSignTrio(conn, role, sign);
        _pairs.add(ccp);
        return ccp;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return _m.toString();
    }

    class ConnectiveRoleSignTrio {
        public ConnectiveRoleSignTrio(Connective _conn, KERuleRole _role, FormulaSign sign) {
            super();
            this._conn = _conn;
            this._role = _role;
            this._sign = sign;
        }

        public Connective getConnective1() {
            return _conn;
        }

        public KERuleRole getRole() {
            return _role;
        }

        /*
         * (non-Javadoc)
         * 
         * @see java.lang.Object#toString()
         */
        public String toString() {
            return "(" + _conn.toString() + "," + _role + ")";
        }

        Connective _conn;

        KERuleRole _role;
        /**
         * @return Returns the _sign.
         */
        public FormulaSign getSign() {
            return _sign;
        }
        FormulaSign _sign;
    }
}