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
import rules.OnePremiseOneConclusionRule;
import rules.Rule;

/**
 * @author adolfo
 *  
 */
public class ConnectiveConnectiveRuleMap {

    Map<ConnectivesPair,OnePremiseOneConclusionRule > _m = new HashMap<ConnectivesPair,OnePremiseOneConclusionRule >();

    List<ConnectivesPair> _pairs = new ArrayList<ConnectivesPair>();

    /**
     *  
     */
    public ConnectiveConnectiveRuleMap() {
        super();
    }

    /**
     * @param conn
     * @param conn2
     * @param rulew
     */
    public void put(Connective conn, Connective conn2,
            OnePremiseOneConclusionRule rulew) {
        _m.put(createConnectivesPair(conn, conn2), rulew);
    }

    /**
     * @param conn
     * @param conn2
     * @return
     */
    public Rule get(Connective conn, Connective conn2) {
        return (Rule) _m.get(createConnectivesPair(conn, conn2));
    }

    private ConnectivesPair createConnectivesPair(Connective conn,
            Connective conn2) {
        ConnectivesPair ccp;
        for (int i = 0; i < _pairs.size(); i++) {
            ccp = (ConnectivesPair) _pairs.get(i);
            if (ccp.getConnective1().equals(conn)
                    && ccp.getConnective2().equals(conn2)) {
                return ccp;
            }
        }

        ccp = new ConnectivesPair(conn, conn2);
        _pairs.add(ccp);
        return ccp;
    }

    class ConnectivesPair {
        public ConnectivesPair(Connective _conn, Connective _conn2) {
            super();
            this._conn = _conn;
            this._conn2 = _conn2;
        }

        public Connective getConnective1() {
            return _conn;
        }

        public Connective getConnective2() {
            return _conn2;
        }

        Connective _conn, _conn2;
    }
}