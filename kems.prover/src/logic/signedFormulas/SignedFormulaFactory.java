/*
 * Created on 15/10/2004
 *
 */
package logic.signedFormulas;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import logic.formulas.Formula;
import logic.formulas.FormulaFactory;
import proverinterface.proofviewer.CloseSignedFormula;

/**
 * Factory of signed formulas.
 * 
 * @author Adolfo Gustavo Serra Seca Neto
 *  
 */
public class SignedFormulaFactory {

    Map<String,SignedFormula> _signedFormulas;

    SignedFormula _lastSignedFormulaAdded = null;

    public SignedFormulaFactory() {
        _signedFormulas = new TreeMap<String, SignedFormula>();
    }

    public SignedFormulaFactory(SignedFormulaFactory sff) {
        _signedFormulas = new TreeMap<String, SignedFormula>();

        _signedFormulas.putAll(sff.getSignedFormulas());

    }

    public Map<String,SignedFormula> getSignedFormulas() {
        return _signedFormulas;
    }

    public SignedFormula createSignedFormula(FormulaSign sign, Formula formula) {
        if (_signedFormulas.containsKey(SignedFormula.toString(sign, formula))) {
            return (SignedFormula) _signedFormulas.get(SignedFormula.toString(
                    sign, formula));
        }

        SignedFormula sf;
        _signedFormulas.put(SignedFormula.toString(sign, formula),
                sf = new SignedFormula(sign, formula));

        _lastSignedFormulaAdded = sf;

        //        formula.addSignedCounterpart(sf);

        return sf;
    }

    public SignedFormula cloneSignedFormula(SignedFormula sf) {
        if (this.getSignedFormulas().containsKey(sf.toString())) {
            return (SignedFormula) this.getSignedFormulas().get(sf.toString());
        } else
            return this.createSignedFormula(sf.getSign(), sf.getFormula());
    }

    public SignedFormula cloneSignedFormula(FormulaSign fs, Formula f) {
        if (this.getSignedFormulas().containsKey(SignedFormula.toString(fs, f))) {
            return (SignedFormula) this.getSignedFormulas().get(
                    SignedFormula.toString(fs, f));
        } else
            return this.createSignedFormula(fs, f);
    }

    public void cloneAll(SignedFormulaFactory sff, FormulaFactory ff) {
//ORIGINAL:        List<Formula> l = Arrays.asList(sff.getSignedFormulas().keySet().toArray());
    	Set<String> s = sff.getSignedFormulas().keySet();
    	List<String> l = new ArrayList<String>(s);
    	s=null;
        for (int i = 0; i < l.size(); i++) {
            if (!this.getSignedFormulas().containsKey(l.get(i))) {
                this.getSignedFormulas().put(
                        l.get(i).toString(),
                        this.cloneSignedFormula(((SignedFormula) (sff
                                .getSignedFormulas().get(l.get(i)))).getSign(),
                                ((SignedFormula) (sff.getSignedFormulas().get(l
                                        .get(i)))).getFormula().clone(ff)));
            }
        }
    }

    /**
     * Used by FormulaCreator
     * 
     * @return
     */
    public SignedFormula getLastSignedFormulaAdded() {
        return _lastSignedFormulaAdded;
    }

    public int getSize() {
        return _signedFormulas.entrySet().size();
    }

    public String toString() {
        return _signedFormulas.keySet().toString();
    }

    public SignedFormula createOppositeSignedFormula(SignedFormula sf) {
        return createSignedFormula(sf.getSign().getOpposite(), sf.getFormula());
    }

    /**
     * @param sign
     * @param formula
     * @return
     */
    public SignedFormula createCloseSignedFormula(FormulaSign sign, Formula formula) {
            if (_signedFormulas.containsKey(SignedFormula.toString(sign, formula))) {
                return (SignedFormula) _signedFormulas.get(SignedFormula.toString(
                        sign, formula));
            }

            SignedFormula sf;
            _signedFormulas.put(SignedFormula.toString(sign, formula),
                    sf = new CloseSignedFormula(sign, formula));

            _lastSignedFormulaAdded = sf;

            //        formula.addSignedCounterpart(sf);

            return sf;
    }

}