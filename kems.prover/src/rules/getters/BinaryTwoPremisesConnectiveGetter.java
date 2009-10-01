/*
 * Created on 10/11/2004
 *
 */
package rules.getters;

import logic.formulas.Formula;
import logic.formulas.FormulaFactory;
import logic.signedFormulas.FormulaSign;
import logic.signedFormulas.SignedFormula;
import logic.signedFormulas.SignedFormulaFactory;
import logic.signedFormulas.SignedFormulaList;
import logicalSystems.classicalLogic.ClassicalSigns;
import rules.KERuleRole;

/**
 * @author Adolfo Gustavo Serra Seca Neto
 * 
 *  
 */
public class BinaryTwoPremisesConnectiveGetter implements KESignedFormulaGetter{
    FormulaSign _sign;

    KERuleRole _role;

    public static final BinaryTwoPremisesConnectiveGetter FALSE_OTHER = new BinaryTwoPremisesConnectiveGetter(
            ClassicalSigns.FALSE, KERuleRole.OTHER);

    public static final BinaryTwoPremisesConnectiveGetter TRUE_OTHER = new BinaryTwoPremisesConnectiveGetter(
            ClassicalSigns.TRUE, KERuleRole.OTHER);

    private BinaryTwoPremisesConnectiveGetter(FormulaSign sign, KERuleRole role) {
        _sign = sign;
        _role = role;
    };

//    /**
//     * @param sff
//     * @param sfMain
//     * @param sfAux
//     * @return
//     */
//    // TODO esse é para ser excluído
//    public SignedFormula getSignedFormula(SignedFormulaFactory sff,
//            SignedFormula sfMain, SignedFormula sfAux) {
//        
//        Formula left = (Formula) (KERuleRole.LEFT.getFormulas(sfMain.getFormula()).get(0));
//        Formula right = (Formula)(KERuleRole.RIGHT.getFormulas(sfMain.getFormula()).get(0));
//        
//        if (sfAux.getFormula() ==  left){
//            return sff.createSignedFormula(_sign, right);
//        }
//        else{
//            return sff.createSignedFormula(_sign, left);
//        }
//            
//    }

    
    public SignedFormula getSignedFormula(SignedFormulaFactory sff, FormulaFactory ff, 
            SignedFormulaList sfl ) {
    	SignedFormula sfMain = sfl.get(0), sfAux=sfl.get(1);
        Formula left = (Formula) (KERuleRole.LEFT.getFormulas(sfMain.getFormula()).get(0));
        Formula right = (Formula)(KERuleRole.RIGHT.getFormulas(sfMain.getFormula()).get(0));
        
        if (sfAux.getFormula() ==  left){
            return sff.createSignedFormula(_sign, right);
        }
        else{
            return sff.createSignedFormula(_sign, left);
        }
            
    }

//    public SignedFormula getSignedFormula(SignedFormulaFactory sff,
//    		SignedFormulaList sfl ) {
//    	SignedFormula sfMain = sfl.get(0), sfAux=sfl.get(1);
//        Formula left = (Formula) (KERuleRole.LEFT.getFormulas(sfMain.getFormula()).get(0));
//        Formula right = (Formula)(KERuleRole.RIGHT.getFormulas(sfMain.getFormula()).get(0));
//        
//        if (sfAux.getFormula() ==  left){
//            return sff.createSignedFormula(_sign, right);
//        }
//        else{
//            return sff.createSignedFormula(_sign, left);
//        }
//            
//    }
    
    

}