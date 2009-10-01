/*
 * Created on Oct 22, 2004
 *
 */
package main.strategy;

import logic.problem.Problem;
import logic.signedFormulas.SignedFormula;
import logic.signedFormulas.SignedFormulaBuilder;
import logicalSystems.classicalLogic.ClassicalConnectives;
import logicalSystems.classicalLogic.ClassicalSigns;
import main.proofTree.Node;
import main.proofTree.ProofTree;
import main.proofTree.SignedFormulaNode;
import main.proofTree.SignedFormulaNodeState;
import main.proofTree.origin.NamedOrigin;
import main.tableau.Method;

/**
 * Class that represents strategies. <br>
 * <b>Strategy pattern? </b>
 * 
 * @author Adolfo Gustavo Serra Seca Neto
 * 
 */
public abstract class Strategy {

    protected Method _method;
    
    private SignedFormula tTopFormula, fBottomFormula;

    /**
     * @param _method
     */
    public Strategy(Method _method) {
        super();
        this._method = _method;
    }

    public ProofTree close(Problem p, SignedFormulaBuilder sfb){
        return  execute(initialize(p, sfb),  sfb);
    }
    
    private ProofTree initialize(Problem p, SignedFormulaBuilder sfb){
    	
    	tTopFormula =  p
        .getSignedFormulaFactory().createSignedFormula(
                ClassicalSigns.TRUE,
                p.getFormulaFactory().createCompositeFormula(
                        ClassicalConnectives.TOP));
    	
    	fBottomFormula = p
        .getSignedFormulaFactory().createSignedFormula(
                ClassicalSigns.FALSE,
                p.getFormulaFactory().createCompositeFormula(
                        ClassicalConnectives.BOTTOM));
    	
        SignedFormulaNode root = new SignedFormulaNode(tTopFormula,
                SignedFormulaNodeState.FULFILLED, NamedOrigin.DEFINITION);
        ProofTree pt = createPTInstance(root);
        SignedFormulaNode second = new SignedFormulaNode(fBottomFormula,
                SignedFormulaNodeState.FULFILLED, NamedOrigin.DEFINITION);
        pt.addLast(second);
        fillWith(pt, p);
        return pt;
    }
    
    public abstract ProofTree createPTInstance(Node aNode);
    protected abstract ProofTree execute (ProofTree pt,  SignedFormulaBuilder sfb);

    public String getName() {
        return getClass().getName();
    }

    public Method getMethod() {
        return _method;
    }
    
    private void fillWith(ProofTree pt, Problem p) {
        for (int i = 0; i < p.getFormulas().size(); i++) {
            SignedFormulaNode n = new SignedFormulaNode(p.getFormulas().get(i),
                    SignedFormulaNodeState.NOT_ANALYSED, NamedOrigin.PROBLEM);
            pt.addLast(n);
        }
    }

	public SignedFormula getFBottomFormula() {
		return fBottomFormula;
	}

	public SignedFormula getTTopFormula() {
		return tTopFormula;
	}

    

}