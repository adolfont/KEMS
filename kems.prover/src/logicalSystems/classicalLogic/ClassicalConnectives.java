/*
 * Created on 05/04/2005
 *
 */
package logicalSystems.classicalLogic;

import logic.formulas.Arity;
import logic.formulas.Connective;
import util.Messages;

/**
 * All classical logic connectives are constants oif this class.
 * 
 * @author Adolfo Gustavo Serra Seca Neto
 *  
 */
public class ClassicalConnectives {

    // TODO In the future maybe this is going to be unbearable -> to save memory
    // we should create objects on demand
    public static final Connective TOP = new Connective(Messages
            .getString("ClassicalConnectives.True"), Arity.ZEROARY); //$NON-NLS-1$

    public static final Connective BOTTOM = new Connective(Messages
            .getString("ClassicalConnectives.False"), Arity.ZEROARY); //$NON-NLS-1$

    public static final Connective NOT = new Connective(Messages
            .getString("ClassicalConnectives.Not"), Arity.UNARY); //$NON-NLS-1$

    public static final Connective AND = new Connective(Messages
            .getString("ClassicalConnectives.And"), Arity.BINARY); //$NON-NLS-1$

    public static final Connective OR = new Connective(Messages
            .getString("ClassicalConnectives.Or"), Arity.BINARY); //$NON-NLS-1$

    public static final Connective XOR = new Connective(Messages
            .getString("ClassicalConnectives.Xor"), Arity.BINARY); //$NON-NLS-1$

    public static final Connective IMPLIES = new Connective(Messages
            .getString("ClassicalConnectives.Implies"), Arity.BINARY); //$NON-NLS-1$

    public static final Connective BIIMPLIES = new Connective(Messages
            .getString("ClassicalConnectives.Biimplies"), Arity.BINARY); //$NON-NLS-1$

    public static final Connective ANDN = new Connective(Messages
            .getString("ClassicalConnectives.And_Nary"), Arity.NARY); //$NON-NLS-1$

    public static final Connective ORN = new Connective(Messages
            .getString("ClassicalConnectives.Or_Nary"), Arity.NARY); //$NON-NLS-1$

}
