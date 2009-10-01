/*
 * Created on 28/09/2005
 *  
 */
package main.strategy.simple.mbc;

import java.util.Iterator;

import logic.formulas.AtomicFormula;
import logic.formulas.Formula;
import logic.problem.Problem;
import logic.signedFormulas.SignedFormula;
import logic.signedFormulas.SignedFormulaBuilder;
import logicalSystems.mbc.MBCConnectives;
import logicalSystems.mbc.MBCRules;
import main.proofTree.ProofTree;
import main.proofTree.SignedFormulaNode;
import main.proofTree.SignedFormulaNodeState;
import main.proofTree.iterator.IProofTreeVeryBasicIterator;
import main.proofTree.origin.Origin;
import main.strategy.AbstractStrategy;
import main.strategy.ClassicalProofTree;
import main.strategy.Strategy;
import main.strategy.simple.SimpleStrategy;
import rules.Rule;

/**
 * Aspect for features of Simple Strategy for mbC.
 * 
 * @author Adolfo Gustavo Serra Seca Neto
 *  
 */
public aspect MBCSimpleStrategyAspect {

	/**
	 * Advice for substituting default implementation that includes T TOP and F BOT
	 * in the Proof Tree
	 */
	ProofTree around(MBCSimpleStrategy s, Problem p): 
		execution (ProofTree Strategy+.initialize(..)) && 
								   args(p,..) && target(s){
		if (!isUsingSimplificationRules() && p.getFormulas().size() > 0) {

			SignedFormulaNode n = new SignedFormulaNode(p.getFormulas().get(0),
					SignedFormulaNodeState.NOT_ANALYSED, Origin.PROBLEM);
			ProofTree pt = s.createPTInstance(n);
			for (int i = 1; i < p.getFormulas().size(); i++) {
				n = new SignedFormulaNode(p.getFormulas().get(i),
						SignedFormulaNodeState.NOT_ANALYSED, Origin.PROBLEM);
				pt.addLast(n);
			}
			return pt;
		} else {
			return proceed(s, p);
		}
	}

	/**
	 * Advice for overring default implementation that removes T TOP and F BOT
	 * from PB candidates.
	 */
	void around(MBCSimpleStrategy s): 
		execution (void AbstractStrategy+.initialPBCandidatesActions(..))  
								   && target(s){
		return;
	}

	/**
	 * Advice for overring default implementation that applies rules with TOP and BOTTOM.
	 */
	boolean around(): 
		(execution (protected boolean AbstractStrategy+.applyTOPandBOTTOMrules(..)))
		&& target (MBCSimpleStrategy+){
		return false;
	}

	/**
	 * Advice for substituting method that applies two premise rules.
	 * Here we do not use substitution rules. 
	 */
	boolean around(MBCSimpleStrategy mbcss, ClassicalProofTree proofTree,
			SignedFormulaBuilder sfb): 
		(execution (protected boolean AbstractStrategy+.applyTwoPremiseRules*(..)))
		&& target (mbcss) && args (proofTree, sfb){

		return mbcss.applySimpleTwoPremiseRules(proofTree, sfb);
	}

	/**
	 * Advice for substituting auxiliary method for applying two premise rules.
	 */
	void around(MBCSimpleStrategy mbcss, ClassicalProofTree proofTree): 
		(execution (void SimpleStrategy.initializeMainCandidates(..)))
		&& target (mbcss) && args(proofTree,..){
		mbcss.setMainCandidates(proofTree.getPBCandidates());
		mbcss.setCounterMainCandidates(0);
	}

	//	Rule around(MBCSimpleStrategy mbcss):
	//		(execution (Rule AbstractStrategy+.choosePBRule(..)))
	//		&& target (mbcss) {
	//
	//		Rule r = proceed(mbcss);
	//		
	//// if (r == MBCRules.T_NOT_1){
	//// return NullRule.INSTANCE;
	//// }
	//		return r;
	//	}

	/**
	 * Advice for substituting method created specifically to be overriden in this
	 * way. TODO Could this be done in another way? 
	 * This implementation verifies if T_NOT_1 can be used as a PB motivation rule.
	 * 
	 */
	boolean around(AbstractStrategy strategy, Rule r, SignedFormula sf,
			SignedFormulaBuilder sfb):
		target(strategy) && args(r, sf, sfb) && 
		(execution (boolean AbstractStrategy+.canApply(..))){

		if (r == MBCRules.T_NOT_1) {
//			FormulaList fl = ((MBCSimpleStrategy) strategy).getSubformulaParentReferences(strategy
//					.getCurrent(), sfb.getFormulaFactory()
//					.createCompositeFormula(MBCConnectives.CONSISTENCY, sf
//							.getFormula()));
			
			Formula f = sfb.getFormulaFactory()
			.createCompositeFormula(MBCConnectives.CONSISTENCY, (Formula) sf
					.getFormula().getImmediateSubformulas().get(0));
//			System.err.println("Searching for "+ f);
			IProofTreeVeryBasicIterator it = strategy.getCurrent().getTopDownIterator();
			while(it.hasNext()){
				SignedFormula sf2 = (SignedFormula) it.next().getContent();
//				System.err.println(sf2);
//				if (includes(sf2.getFormula(), f) != f.isChild(sf2.getFormula())){
//				System.err.println(includes(sf2.getFormula(), f));
//				System.err.println(f.isChild(sf2.getFormula()));
//				System.err.println(f + " " + sf2.getFormula());
//				}
//				
				if (includes(sf2.getFormula(), f)){
//					System.err.println(sf2 +" Contains " +f);
					return true;
				}
			}
			
			
//			System.err.println(fl);
			return false;
		}
		return proceed(strategy, r, sf, sfb);
	}
	
	/** Verifies if a formula contains other formula as subformula
	 * @param container - a formula
	 * @param contained - another formula
	 * @return true if contained is a subformula of contained.
	 */
	private boolean includes (Formula container, Formula contained){
		if (container == contained){
			return true;
		}else{
			if (container == null || (container instanceof AtomicFormula)){
				return false;
			}
			else{
				Iterator it = container.getImmediateSubformulas().iterator();
				while (it.hasNext()){
					if (includes((Formula) it.next(), contained)){
						return true;
					}
				}
			}
			
		}
		return false;
	}

	// TODO check whenever a proof is started if it is using an MBC Rules
	// Structure
	// with simplification rules and set a variable that is accessed here
	private boolean isUsingSimplificationRules() {
		return false;
	}
}
