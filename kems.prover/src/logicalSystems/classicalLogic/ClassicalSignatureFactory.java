/*
 * Created on 06/04/2005
 *
 */
package logicalSystems.classicalLogic;

import logic.formulas.ConnectiveCode;
import logic.logicalSystem.Signature;

/**
 * Factory of signatures for classical logic. It is a singleton.
 * 
 * @author Adolfo Gustavo Serra Seca Neto
 */
public class ClassicalSignatureFactory {

    public static ConnectiveCode TOP = new ConnectiveCode();

    public static ConnectiveCode BOTTOM = new ConnectiveCode();

    public static ConnectiveCode NOT = new ConnectiveCode();

    public static ConnectiveCode AND = new ConnectiveCode();

    public static ConnectiveCode OR = new ConnectiveCode();

    public static ConnectiveCode IMPLIES = new ConnectiveCode();

    public static ConnectiveCode XOR = new ConnectiveCode();

    public static ConnectiveCode BIIMPLIES = new ConnectiveCode();

    private static ClassicalSignatureFactory __instance = null;

    private Signature clausal, normal, normal_b, normal_b_x;

    private ClassicalSignatureFactory() {
    }

    public static ClassicalSignatureFactory getInstance() {
        if (__instance == null) {
            __instance = new ClassicalSignatureFactory();
        }

        return __instance;
    }

    public Signature getClausalSignature() {
        if (clausal == null) {
            clausal = new Signature();
            clausal
                    .add(ClassicalSignatureFactory.TOP,
                            ClassicalConnectives.TOP);
            clausal.add(ClassicalSignatureFactory.BOTTOM,
                    ClassicalConnectives.BOTTOM);
            clausal
                    .add(ClassicalSignatureFactory.NOT,
                            ClassicalConnectives.NOT);
            clausal
                    .add(ClassicalSignatureFactory.AND,
                            ClassicalConnectives.AND);
            clausal.add(ClassicalSignatureFactory.OR, ClassicalConnectives.OR);
        }
        return clausal;
    }

    public Signature getNormalSignature() {
        if (normal == null) {
            normal = new Signature(getClausalSignature());
            normal.add(ClassicalSignatureFactory.IMPLIES,
                    ClassicalConnectives.IMPLIES);
        }
        return normal;
    }

    public Signature getNormalBSignature() {
        if (normal_b == null) {

            normal_b = new Signature(getNormalSignature());
            normal_b.add(ClassicalSignatureFactory.BIIMPLIES,
                    ClassicalConnectives.BIIMPLIES);
        }

        return normal_b;
    }

    public Signature getNormalBXSignature() {
        if (normal_b_x == null) {

            normal_b_x = new Signature(getNormalBSignature());
            normal_b_x.add(ClassicalSignatureFactory.XOR,
                    ClassicalConnectives.XOR);
        }
        return normal_b_x;
    }

}
