/*
 * Created on 07/04/2005
 *
 */
package logicalSystems.c1;

import logic.formulas.ConnectiveCode;
import logic.logicalSystem.ISignature;
import logic.logicalSystem.Signature;

/**
 * Factory of C1 signatures. It is a singleton.
 * 
 * @author Adolfo Gustavo Serra Seca Neto
 * 
 *  
 */
public class C1SignatureFactory {

    public static final ConnectiveCode NOT = new ConnectiveCode();

    public static final ConnectiveCode AND = new ConnectiveCode();

    public static final ConnectiveCode OR = new ConnectiveCode();

    public static final ConnectiveCode IMPLIES = new ConnectiveCode();

//    public static final ConnectiveCode CONSISTENCY = new ConnectiveCode();

    private static C1SignatureFactory __instance = null;

    private C1SignatureFactory() {
    }

    public static C1SignatureFactory getInstance() {
        if (__instance == null) {
            __instance = new C1SignatureFactory();
        }
        return __instance;
    }

    private ISignature mainSignature;

    /**
     * @return the basic signature of C1 
     */
    public ISignature getMainSignature() {
        if (mainSignature == null) {
            mainSignature = new Signature();

            mainSignature.add(NOT, C1Connectives.NOT);
            mainSignature.add(AND, C1Connectives.AND);
            mainSignature.add(OR, C1Connectives.OR);
            mainSignature.add(IMPLIES, C1Connectives.IMPLIES);
//            mainSignature.add(CONSISTENCY, C1Connectives.CONSISTENCY);
        }

        return mainSignature;
    }
}
