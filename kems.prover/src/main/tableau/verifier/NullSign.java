/*
 * Created on 16/11/2005
 *
 */
package main.tableau.verifier;

import logic.signedFormulas.FormulaSign;

/**
 * A class to represent a null sign
 * 
 * @author Adolfo Gustavo Serra Seca Neto
 *  
 */
public class NullSign extends FormulaSign {

    private static NullSign __instance;

    /**
     * @param value
     */
    private NullSign(int value) {
        super(value);
    }

    public static NullSign getInstance() {
        if (__instance == null) {
            __instance = new NullSign(-1);
        }
        return __instance;
    }

    /*
     * (non-Javadoc)
     * 
     * @see logic.signedFormulas.FormulaSign#toString()
     */
    public String toString() {
        return "NULL_SIGN";
    }

}
