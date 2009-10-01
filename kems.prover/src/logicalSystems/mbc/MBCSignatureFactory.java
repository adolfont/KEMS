/*
 * Created on 07/04/2005
 *
 */
package logicalSystems.mbc;

import logic.formulas.ConnectiveCode;
import logic.logicalSystem.ISignature;
import logic.logicalSystem.Signature;

/**
 * Factory of mbC signatures. It is a singleton.
 * 
 * @author Adolfo Gustavo Serra Seca Neto
 * 
 *  
 */
public class MBCSignatureFactory {

    public static final ConnectiveCode NOT = new ConnectiveCode();

    public static final ConnectiveCode AND = new ConnectiveCode();

    public static final ConnectiveCode OR = new ConnectiveCode();

    public static final ConnectiveCode IMPLIES = new ConnectiveCode();

    public static final ConnectiveCode CONSISTENCY = new ConnectiveCode();

    private static MBCSignatureFactory __instance = null;

    private MBCSignatureFactory() {
    }

    public static MBCSignatureFactory getInstance() {
        if (__instance == null) {
            __instance = new MBCSignatureFactory();
        }
        return __instance;
    }

    private ISignature mainSignature;

    /**
     * @return the basic signature of mbC 
     */
    public ISignature getMainSignature() {
        if (mainSignature == null) {
            mainSignature = new Signature();

            mainSignature.add(NOT, MBCConnectives.NOT);
            mainSignature.add(AND, MBCConnectives.AND);
            mainSignature.add(OR, MBCConnectives.OR);
            mainSignature.add(IMPLIES, MBCConnectives.IMPLIES);
            mainSignature.add(CONSISTENCY, MBCConnectives.CONSISTENCY);
        }

        return mainSignature;
    }
}
