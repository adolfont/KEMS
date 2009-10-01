/*
 * Created on 07/04/2005
 *
 */
package logicalSystems.c1;

import logic.formulas.Connective;
import logicalSystems.classicalLogic.ClassicalConnectives;
import logicalSystems.mbc.MBCConnectives;

/**
 * Connectives for mbC. The connectives are public static members for efficiency
 * reasons. The releaseMemory() method allows us to save some memory when
 * necessary.
 * 
 * @author Adolfo Gustavo Serra Seca Neto
 *  
 */
public final class C1Connectives {

    public static Connective NOT = ClassicalConnectives.NOT;

    public static Connective AND = ClassicalConnectives.AND;

    public static Connective OR = ClassicalConnectives.OR;

    public static Connective IMPLIES = ClassicalConnectives.IMPLIES;

    public static Connective CONSISTENCY = MBCConnectives.CONSISTENCY;
    
    // for future extensions
    public static final Connective XOR=null;
    public static final Connective BIIMPLIES=null;
    public static final Connective TOP=null;
    public static final Connective BOTTOM=null;

    public static void releaseMemory() {
        NOT = null;
        OR = null;
        AND = null;
        IMPLIES = null;
        CONSISTENCY = null;
    }
}
