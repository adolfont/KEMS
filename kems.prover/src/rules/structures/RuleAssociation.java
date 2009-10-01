/*
 * Created on 05/04/2005
 *
 */
package rules.structures;

import logic.formulas.Connective;
import logic.signedFormulas.FormulaSign;
import rules.KERuleRole;
import rules.Rule;

/** Associates a rule with (possibly) a sign, a main connective, (possibly) an auxiliary connective
 * and (possibly) a role.
 * @author Adolfo Gustavo Serra Seca Neto
 * 
 *
 */
public class RuleAssociation {
    
//    private Rule rule;
    private FormulaSign sign;
    private Connective mainConnective, auxiliaryConnective;
    private KERuleRole role;
   
    
    
    /**
     * @param rule
     * @param sign
     * @param mainConnective
     */
    public RuleAssociation(Rule rule, FormulaSign sign,
            Connective mainConnective) {
        super();
//        this.rule = rule;
        this.sign = sign;
        this.mainConnective = mainConnective;
    }
    
    /**
     * @param rule
     * @param mainConnective
     * @param sign
     */
    public RuleAssociation(Rule rule, Connective mainConnective,
            FormulaSign sign) {
        super();
//        this.rule = rule;
        this.mainConnective = mainConnective;
        this.sign = sign;
    }
    
    /**
     * @param rule
     * @param sign
     * @param mainConnective
     * @param role
     */
    public RuleAssociation(Rule rule, FormulaSign sign,
            Connective mainConnective, KERuleRole role) {
        super();
//        this.rule = rule;
        this.sign = sign;
        this.mainConnective = mainConnective;
        this.role = role;
    }
    /**
     * @return Returns the auxiliaryConnective.
     */
    public Connective getAuxiliaryConnective() {
        return auxiliaryConnective;
    }
    /**
     * @return Returns the mainConnective.
     */
    public Connective getMainConnective() {
        return mainConnective;
    }
    /**
     * @return Returns the sign.
     */
    public FormulaSign getSign() {
        return sign;
    }
    /**
     * @return Returns the role.
     */
    public KERuleRole getRole() {
        return role;
    }
}
