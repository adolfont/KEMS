/*
 * Created on 27/01/2005
 *
 */
package main.strategy.memorySaver;

import logic.formulas.AtomicFormula;
import logic.formulas.CompositeFormula;
import logic.formulas.Formula;
import logic.formulas.FormulaList;
import logic.signedFormulas.PBCandidateList;
import logic.signedFormulas.SignedFormula;
import logic.signedFormulas.SignedFormulaList;
import main.proofTree.IProofTree;
import main.proofTree.iterator.IProofTreeBackwardNodeIterator;
import main.proofTree.iterator.IProofTreeBasicIterator;
import main.strategy.IClassicalProofTree;

/**
 * A singleton that finds references to formulas in proof trees, just like
 * FormulaReferenceClassicalProofTree does.
 * 
 * @author Adolfo Gustavo Serra Seca Neto
 *  
 */
public class ReferenceFinder {

    private static ReferenceFinder __INSTANCE = null;

    public static ReferenceFinder getInstance() {
        if (__INSTANCE == null) {
            __INSTANCE = new ReferenceFinder();
        }

        return __INSTANCE;
    }

    /**
     * @param cpt
     * @param aFormula
     * @return
     */
    public SignedFormulaList getLocalReferences(IClassicalProofTree cpt,
            Formula aFormula) {

        SignedFormulaList sfl = new SignedFormulaList();
        IProofTreeBasicIterator pti = cpt.getLocalIterator();

        while (pti.hasNext()) {
            SignedFormula sf = (SignedFormula) pti.next().getContent();

            if (aFormula.isSubformula(sf.getFormula())) {
                sfl.add(sf);
            }
        }

        return sfl;
    }

    //    public static boolean contains(SignedFormula sf, Formula f) {
    //
    //        return contains(sf.getFormula(), f);
    //    }
    //
    //    public static boolean contains(Formula f1, Formula f2) {
    //
    //        if (f1 == f2) {
    //            return true;
    //        } else if (f1 instanceof AtomicFormula) {
    //            return false;
    //        } else {
    //            CompositeFormula cf = (CompositeFormula) f1;
    //
    //            for (int i = 0; i < cf.getImmediateSubformulas().size(); i++) {
    //                if (contains((Formula) cf.getImmediateSubformulas().get(i), f2)) {
    //                    return true;
    //                }
    //            }
    //        }
    //
    //        return false;
    //    }

    /**
     * @param proofTree
     * @param bottomFormula
     * @param signedFormulaWithTOPorBOTTOM
     * @return
     */
    public FormulaList getSubformulaLocalReferences(
            IClassicalProofTree proofTree, Formula bottomFormula,
            SignedFormula signedFormulaWithTOPorBOTTOM) {
        return subformulasOfWith(signedFormulaWithTOPorBOTTOM.getFormula(),
                bottomFormula, new FormulaList());
    }

    public FormulaList subformulasOfWith(Formula f1, Formula f2, FormulaList fl) {
        if (f1 instanceof AtomicFormula) {
            return fl;
        } else {
            CompositeFormula cf = (CompositeFormula) f1;

            // search immediate subformulas first
            for (int i = 0; i < cf.getImmediateSubformulas().size(); i++) {
                if (cf.getImmediateSubformulas().get(i) == f2) {
                    fl.add(cf);
                    return fl;
                }
            }

            // searches deeper subformulas first
            for (int i = 0; i < cf.getImmediateSubformulas().size(); i++) {
                FormulaList result = subformulasOfWith((Formula) cf
                        .getImmediateSubformulas().get(i), f2, fl);
                if (result.size() > 0) {
                    return result;
                }
            }
        }

        return fl;
    }

    /**
     * @param proofTree
     * @param formula
     * @param mainCandidate
     * @return
     */
    public FormulaList getSubformulaParentReferences(
            IClassicalProofTree proofTree, Formula formula,
            SignedFormula mainCandidate) {
        return subformulasOfWith(mainCandidate.getFormula(), formula,
                new FormulaList());
    }

    public SignedFormula getSignedFormulaWith(PBCandidateList sfl,
            Formula f1, Formula f2) {
        for (int i = 0; i < sfl.size(); i++) {
            if (f1.isStrictSubformula(sfl.get(i).getFormula())
                    || f2.isSubformula(sfl.get(i).getFormula())) {
                return sfl.get(i);
            }
        }

        return null;
    }

    public SignedFormula getSignedFormulaWith(PBCandidateList sfl,
            Formula f1, int begin) {
        for (int i = begin; i < sfl.size(); i++) {
            if (f1.isStrictSubformula(sfl.get(i).getFormula())) {
                return sfl.get(i);
            }
        }

        return null;
    }

    /**
     * @param proofTree
     * @param formula
     * @return
     */
    public SignedFormulaList getParentReferences(IClassicalProofTree proofTree,
            Formula formula) {

        IProofTreeBackwardNodeIterator it = proofTree.getBackwardNodeIterator();
        SignedFormulaList sfl = new SignedFormulaList();
        IProofTree current;
        while (it.hasPrevious()) {
            current = it.previous();
            sfl.addAll(ReferenceFinder.getInstance().getLocalReferences(
                    (IClassicalProofTree) current, formula));
        }
        return sfl;

        //        IProofTree current = proofTree;
        //        SignedFormulaList sfl = new SignedFormulaList();
        //        do {
        //            sfl.addAll(ReferenceFinder.getLocalReferences((IClassicalProofTree)current,
        // formula));
        //            current = current.getParent();
        //        } while (current != null);
        //
        //        return sfl;
    }

}