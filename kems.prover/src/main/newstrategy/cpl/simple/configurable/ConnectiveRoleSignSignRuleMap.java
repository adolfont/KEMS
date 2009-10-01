/*
 * Created on 05/12/2005
 *
 */
package main.newstrategy.cpl.simple.configurable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import logic.formulas.Connective;
import logic.signedFormulas.FormulaSign;
import rules.KERuleRole;
import rules.Rule;
import rules.TwoPremisesOneConclusionRule;

/**
 * A connective-role-sign-sign to rule map
 *
 * @author Adolfo Gustavo Serra Seca Neto
 *
 */
public class ConnectiveRoleSignSignRuleMap {

    private Map<ConnectiveRoleSignSignFour,TwoPremisesOneConclusionRule> _m = new HashMap<ConnectiveRoleSignSignFour, TwoPremisesOneConclusionRule>();

    private List<ConnectiveRoleSignSignFour> _pairs = new ArrayList<ConnectiveRoleSignSignFour>();

    /**
     *  
     */
    public ConnectiveRoleSignSignRuleMap() {
        super();
    }

    /**
     * @param conn
     * @param conn2
     * @param rulew
     */
    public void put(Connective conn, KERuleRole role, FormulaSign sign, FormulaSign sign2,
            TwoPremisesOneConclusionRule rulew) {
        _m.put(createConnectiveRoleSignFour(conn, role, sign, sign2), rulew);
    }

    /**
     * @param conn
     * @param conn2
     * @return
     */
    public Rule get(Connective conn, KERuleRole role, FormulaSign sign, FormulaSign sign2) {
        return (Rule) _m.get(createConnectiveRoleSignFour(conn, role, sign, sign2));
    }

    private ConnectiveRoleSignSignFour createConnectiveRoleSignFour(Connective conn,
            KERuleRole role, FormulaSign sign, FormulaSign sign2) {
        ConnectiveRoleSignSignFour ccp;
        for (int i = 0; i < _pairs.size(); i++) {
            ccp = (ConnectiveRoleSignSignFour) _pairs.get(i);
            if (ccp.getConnective1().equals(conn) && ccp.getRole().equals(role)
                    && ccp.getSign().equals(sign) && ccp.getSign2().equals(sign2)) {
                return ccp;
            }
        }

        ccp = new ConnectiveRoleSignSignFour(conn, role, sign, sign2);
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

    class ConnectiveRoleSignSignFour {
        
        public ConnectiveRoleSignSignFour(Connective _conn, KERuleRole _role, FormulaSign sign, FormulaSign sign2) {
            super();
            this._conn = _conn;
            this._role = _role;
            this._sign = sign;
            this.sign2 = sign2;
        }

        public Connective getConnective1() {
            return _conn;
        }

        public KERuleRole getRole() {
            return _role;
        }

        public FormulaSign getSign2() {
            return sign2;
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
        FormulaSign sign2;
        
        
    }
}
