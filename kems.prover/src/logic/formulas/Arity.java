/*
 * Created on 15/10/2004
 *
 */
package logic.formulas;

/** Represent the arity of a connective.
 * 
 * @author Adolfo Gustavo Serra Seca Neto
 *
 */
public class Arity {
    
    public static Arity UNARY = new Arity();
    public static Arity BINARY = new Arity();
    public static Arity ZEROARY = new Arity();
    public static Arity NARY = new Arity();
    
    private Arity() {
    }

}
