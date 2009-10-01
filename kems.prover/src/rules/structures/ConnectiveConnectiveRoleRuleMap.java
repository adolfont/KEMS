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
import rules.KERuleRole;
import rules.OnePremiseOneConclusionRule;
import rules.Rule;

/**
 * @author adolfo
 *  
 */
public class ConnectiveConnectiveRoleRuleMap {

    Map<ConnectivesRoleTrio,OnePremiseOneConclusionRule> _m = new HashMap<ConnectivesRoleTrio,OnePremiseOneConclusionRule>();

    List<ConnectivesRoleTrio> _pairs = new ArrayList<ConnectivesRoleTrio>();

    /**
     *  
     */
    public ConnectiveConnectiveRoleRuleMap() {
        super();
    }

    /**
     * @param conn
     * @param conn2
     * @param rulew
     */
    public void put(Connective conn, Connective conn2, KERuleRole role,
            OnePremiseOneConclusionRule rulew) {
        //        System.out.println(rulew);
        _m.put(createConnectivesRoleTrio(conn, conn2, role),
                rulew);
        //        System.out.println(result);
    }

    /**
     * @param conn
     * @param conn2
     * @return
     */
    public Rule get(Connective conn, Connective conn2, KERuleRole role) {
        return (Rule) _m.get(createConnectivesRoleTrio(conn, conn2, role));
    }

    private ConnectivesRoleTrio createConnectivesRoleTrio(Connective conn,
            Connective conn2, KERuleRole role) {
        ConnectivesRoleTrio ccp;
        for (int i = 0; i < _pairs.size(); i++) {
            ccp = (ConnectivesRoleTrio) _pairs.get(i);
            if (ccp.getConnective1().equals(conn)
                    && ccp.getConnective2().equals(conn2)
                    && ccp.getRole().equals(role)) {
                return ccp;
            }
        }

        ccp = new ConnectivesRoleTrio(conn, conn2, role);
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

    class ConnectivesRoleTrio {
        public ConnectivesRoleTrio(Connective _conn, Connective _conn2,
                KERuleRole _role) {
            super();
            this._conn = _conn;
            this._conn2 = _conn2;
            this._role = _role;
        }

        public Connective getConnective1() {
            return _conn;
        }

        public Connective getConnective2() {
            return _conn2;
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
            return "(" + _conn.toString() + "," + _conn2.toString() + ","
                    + _role + ")";
        }

        Connective _conn, _conn2;

        KERuleRole _role;
    }
}