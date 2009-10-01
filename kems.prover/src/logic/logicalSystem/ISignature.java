/*
 * Created on 02/08/2005
 *
 */
package logic.logicalSystem;

import java.util.Map;

import logic.formulas.Connective;
import logic.formulas.ConnectiveCode;

/**
 * Interface for signatures of logical systems.
 * 
 * @author Adolfo Gustavo Serra Seca Neto
 * 
 *  
 */
public interface ISignature {
    public abstract void add(ConnectiveCode code, Connective c);

    public abstract Map<ConnectiveCode,Connective> getConnectives();

    public abstract boolean contains(Connective conn);

    public abstract Connective getConnective(ConnectiveCode code);
}