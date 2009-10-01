/*
 * Created on 05/04/2005
 *
 */
package logic.logicalSystem;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import logic.formulas.Connective;
import logic.formulas.ConnectiveCode;

/**
 * A signature is a collection of connectives.
 * 
 * @author Adolfo Gustavo Serra Seca Neto
 * 
 *  
 */
public class Signature implements ISignature {

    private Map<ConnectiveCode,Connective> connectives = new HashMap<ConnectiveCode, Connective>();

    /**
     * Creates an empty signature
     */
    public Signature() {
    }

    /**
     * Creates a signature from another one
     * 
     * @param sign
     */
    public Signature(ISignature sign) {
        // copies the connectives
        Iterator<ConnectiveCode> i = sign.getConnectives().keySet().iterator();

        while (i.hasNext()) {
            ConnectiveCode code = (ConnectiveCode) i.next();
            connectives.put(code, sign.getConnective(code));
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see logic.ISignature#add(formulasNew.ConnectiveCode,
     *           formulasNew.Connective)
     */
    public void add(ConnectiveCode code, Connective c) {
        connectives.put(code, c);
    }

    /*
     * (non-Javadoc)
     * 
     * @see logic.ISignature#getConnectives()
     */
    public Map<ConnectiveCode,Connective> getConnectives() {
        return connectives;
    }

    /*
     * (non-Javadoc)
     * 
     * @see logic.ISignature#contains(formulasNew.Connective)
     */
    public boolean contains(Connective conn) {
        return connectives.values().contains(conn);
    }

    /*
     * (non-Javadoc)
     * 
     * @see logic.ISignature#getConnective(formulasNew.ConnectiveCode)
     */
    public Connective getConnective(ConnectiveCode code) {
        return (Connective) connectives.get(code);
    }

}
