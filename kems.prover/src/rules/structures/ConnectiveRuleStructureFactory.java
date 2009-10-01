/*
 * Created on 06/04/2005
 *
 */
package rules.structures;

import java.util.HashMap;
import java.util.Map;

import logic.formulas.Connective;

/**
 * A map that associates a structure of rules to a connective
 * 
 * @author Adolfo Gustavo Serra Seca Neto
 * 
 *  
 */
public class ConnectiveRuleStructureFactory {

    private Map<Connective, ConnectiveRuleStructure> crss = new HashMap<Connective, ConnectiveRuleStructure>();

    public ConnectiveRuleStructure createCRS(Connective conn) {

        if (crss.containsKey(conn)) {
            return (ConnectiveRuleStructure) crss.get(conn);
        } else {
            ConnectiveRuleStructure newCRS = new ConnectiveRuleStructure(conn);
            crss.put(conn, newCRS);
            return newCRS;
        }
    }

}
