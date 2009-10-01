/*
 * Created on 18/01/2005
 *  
 */
package logic.aspects;

import java.util.Collection;
import java.util.Iterator;

import logic.formulas.AtomicFormula;
import logic.formulas.CompositeFormula;
import logic.formulas.Formula;
import logic.formulas.FormulaFactory;
import logic.formulas.FormulaList;

/**
 * Aspect used for calculating parents on time. Only the formula factory of a 
 * a formula is recorded. Still incomplete.
 * 
 * @author adolfo
 *  
 */
public aspect FormulaParentsOnTimeAspect {

    
    declare precedence: FormulaParentsAspect, FormulaParentsAspectIntroductions, FormulaParentsOnTimeAspect;

    // TODO Try to substitute this aspect for the other two aspects.

    // assigns a formula factory to a formula
    
    FormulaFactory Formula._formulaFactory;

    public FormulaFactory Formula.getFormulaFactory() {
        return _formulaFactory;
    }

    pointcut createAtomicFormula(FormulaFactory ff):
        target (ff) &&
        execution (AtomicFormula FormulaFactory.createAtomicFormula(*));

    pointcut createCompositeFormula(FormulaFactory ff):
        target (ff) &&
        execution (Formula FormulaFactory.createCompositeFormula(..));

    AtomicFormula around(FormulaFactory ff): createAtomicFormula(ff){
        AtomicFormula af = proceed(ff);
        af._formulaFactory = ff;
        return af;
    }

    CompositeFormula around(FormulaFactory ff): createCompositeFormula(ff){
        CompositeFormula cf = proceed(ff);
        cf._formulaFactory = ff;
        return cf;
    }

    
    // gets immediate parents
    public FormulaList Formula.getImmediateParents() {
        FormulaList fl = new FormulaList();

        Collection<Formula> allCompositeFormulas = _formulaFactory
                .getCompositeFormulas().values();

        Iterator<Formula> i = allCompositeFormulas.iterator();

        while (i.hasNext()) {
            Formula f = (CompositeFormula) i.next();

            if (f.getImmediateSubformulas().contains(this)) {
                fl.add(f);
            }
        }

        return fl;
    }
    
    // gets even more distant parents
    public FormulaList Formula.getParents() {
        FormulaList fl = new FormulaList();

        Collection<Formula> allCompositeFormulas = _formulaFactory
                .getCompositeFormulas().values();

        Iterator<Formula> i = allCompositeFormulas.iterator();

        while (i.hasNext()) {
            Formula f = (CompositeFormula) i.next();

            if (f.getImmediateSubformulas().contains(this)) {
                fl.add(f);

                Iterator<Formula> it2 = f.getParents().iterator();
                while (it2.hasNext()) {
                    Formula f2 = (Formula) it2.next();
                    fl.add(f2);
                }

            }
        }

        return fl;
    }

}