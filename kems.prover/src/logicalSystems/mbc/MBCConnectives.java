/*
 * Created on 07/04/2005
 *
 */
package logicalSystems.mbc;

import logic.formulas.Arity;
import logic.formulas.Connective;
import logicalSystems.classicalLogic.ClassicalConnectives;
import util.Messages;

/**
 * Connectives for mbC. The connectives are public static members for efficiency
 * reasons. The releaseMemory() method allows us to save some memory when
 * necessary.
 * 
 * @author Adolfo Gustavo Serra Seca Neto
 *  
 */
public final class MBCConnectives {

	// Não podem ser diferentes senão dá problema nas regras iguais
//    public static Connective NOT = new Connective(
//            Messages.getString("MBCConnectives.Not"), Arity.UNARY); //$NON-NLS-1$
//
//    public static Connective AND = new Connective(
//            Messages.getString("MBCConnectives.And"), Arity.BINARY); //$NON-NLS-1$
//
//    public static Connective OR = new Connective(
//            Messages.getString("MBCConnectives.Or"), Arity.BINARY); //$NON-NLS-1$new Connective(Messages.getString("MBCConnectives.Or"), Arity.BINARY); //$NON-NLS-1$
//
//    public static Connective IMPLIES = new Connective(Messages
//            .getString("MBCConnectives.Implies"), Arity.BINARY); //$NON-NLS-1$

    public static Connective NOT = ClassicalConnectives.NOT;

    public static Connective AND = ClassicalConnectives.AND;

    public static Connective OR = ClassicalConnectives.OR;

    public static Connective IMPLIES = ClassicalConnectives.IMPLIES;

    public static Connective CONSISTENCY = new Connective(Messages
            .getString("MBCConnectives.Consistency"), Arity.UNARY); //$NON-NLS-1$
    
    // for future extensions
    public static final Connective XOR=ClassicalConnectives.XOR;
    public static final Connective BIIMPLIES=ClassicalConnectives.BIIMPLIES;
    public static final Connective TOP=ClassicalConnectives.TOP;
    public static final Connective BOTTOM=ClassicalConnectives.BOTTOM;

    public static void releaseMemory() {
        NOT = null;
        OR = null;
        AND = null;
        IMPLIES = null;
        CONSISTENCY = null;
    }
}
