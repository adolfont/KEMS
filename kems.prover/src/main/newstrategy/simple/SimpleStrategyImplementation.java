/*
 * Created on 27/10/2005
 *
 */
package main.newstrategy.simple;

import java.util.Iterator;
import java.util.LinkedList;

import main.ag.strategy.util.AGConfiguration;
import main.newstrategy.ISimpleStrategy;
import main.proofTree.IProofTree;
import main.proofTree.ProofTree;
import main.strategy.ClassicalProofTree;
import main.strategy.IClassicalProofTree;
import main.strategy.applicator.IProofTransformation;
import main.strategy.applicator.IRuleApplicator;

import org.apache.log4j.Logger;

/**
 * Implmentation of basic methods for simple strategy.
 * 
 * @author Adolfo Gustavo Serra Seca Neto
 * 
 */
public class SimpleStrategyImplementation {

	/**
	 * logger
	 */
	public static Logger logger = Logger.getLogger(SimpleStrategyImplementation.class);

	/**
	 * Executes a simple strategy.
	 * 
	 * @param strategy
	 * @return the proof tree obtained
	 */
	public ProofTree execute(ISimpleStrategy strategy) {

		// the close method has already loaded the problem into a proof tree!
		// if it is already closed, finish now!
		if (strategy.getProofTree().isClosed())
			return strategy.getProofTree();

		initialPBCandidatesActions(strategy.getProofTree(), strategy);

		// initializes stack of open branches
		LinkedList<IProofTree> l = new LinkedList<IProofTree>();
		l.addFirst(strategy.getProofTree());
		strategy.setOpenBranches(l);

		boolean hasApplied = false;
		while (!strategy.getOpenBranches().isEmpty() && !strategy.getProofTree().isClosed()) {

			strategy.setCurrent((ClassicalProofTree) strategy.getOpenBranches().removeFirst());

			// if this branch is the right of some branch
			if (isRightBranch(strategy.getCurrent())) {
				if (isLeftBrotherOpen(strategy.getCurrent())) {
					break;
				}
			}

			// keep applying rules until it is not possible to apply
			do {

				// if (logger.isDebugEnabled()){
				// logger.debug(strategy.getCurrent());
				// }

				hasApplied = applyLinearRules(strategy);

				if (strategy.getCurrent().isClosed()) {
					break;
				} else {
					hasApplied = applyPBRule(strategy);
				}

			} while (hasApplied);

			if (!strategy.getCurrent().isClosed()) {
				strategy.getCurrent().setCompleted(true);
				strategy.getProofTree().setOpenCompletedBranch(strategy.getCurrent());
				break;
			} else {

				if (!strategy.getProofTree().isClosed()) {
					strategy.finishBranch(strategy.getCurrent());
				}
			}

		}

		return strategy.getProofTree();
	}

	// comentado em 22-06-2006
	// private void addClosingSignedFormula(IClassicalProofTree frcpt,
	// ISimpleStrategy strategy, SignedFormula sf) {
	// frcpt
	// .addLast(new SignedFormulaNode(
	// strategy.getSignedFormulaBuilder().createSignedFormula(
	// ClassicalSigns.TRUE,
	// strategy.getSignedFormulaBuilder()
	// .createCompositeFormula(
	// ClassicalConnectives.BOTTOM)),
	// SignedFormulaNodeState.FULFILLED,
	// strategy
	// .createOrigin(
	// ClassicalRules.CLOSE,
	// frcpt.getNode(sf),
	// frcpt
	// .getNode(strategy
	// .getSignedFormulaBuilder()
	// .createSignedFormula(
	// sf.getSign() == ClassicalSigns.TRUE ? ClassicalSigns.FALSE
	// : ClassicalSigns.TRUE,
	// sf.getFormula())))));
	//
	// }

	/**
	 * @param current
	 * @return true if this is the right branch of another branch
	 */
	//EMERSON: Temporário Algoritmo Genético
	public boolean isRightBranch(IClassicalProofTree current) {
		return (current.getParent() != null) && (current == current.getParent().getRight());
	}
	
	//EMERSON: Temporário Algoritmo Genético
	public boolean isLeftBrotherOpen(IClassicalProofTree current) {
		return (!((IClassicalProofTree) current.getParent().getLeft()).isClosed());
	}

	/**
	 * Perform initial actions with PB candidates
	 * 
	 * @param pt
	 */
	//EMERSON: Temporário Algoritmo Genético
	public void initialPBCandidatesActions(ClassicalProofTree pt, ISimpleStrategy strategy) {
		pt.removeFromPBCandidates(strategy.getTTopFormula());
		pt.removeFromPBCandidates(strategy.getFBottomFormula());
	}

	/**
	 * Applies as much linear rules as possible
	 * 
	 * @param current
	 * @param sfb
	 * @param list
	 * @return
	 */
	//EMERSON: Temporário Algoritmo Genético
	public boolean applyLinearRules(ISimpleStrategy strategy) {
		boolean hasApplied = false;

		boolean hasAppliedInALoop;

		// System.out.println("LINEAR SORT");
		// TODO SORT ASPECT
		// sorts before all linear rules are applied in a given branch
		// strategy.getCurrent().getPBCandidates().sort(strategy.getComparator());
		//
		do {
			hasAppliedInALoop = false;
			Iterator<IRuleApplicator> it = strategy.getRuleApplicators().iterator();

			while (it.hasNext()) {
				IRuleApplicator ra = (IRuleApplicator) it.next();
				// System.out.println(ra);

				// TODO SORT ASPECT
				// sorts before every linear rule applicator
				//EMERSON: Temporário Algoritmo Genético
				if (strategy.getAbordagensAG() == AGConfiguration.Abordagens.NotApplyAG) {
					strategy.getCurrent().getPBCandidates().sort(strategy.getComparator());
				}

				boolean hasAppliedThis = ra.applyAll(strategy.getCurrent(), strategy
						.getSignedFormulaBuilder());
				if (hasAppliedThis) {
					hasApplied = true;
					hasAppliedInALoop = true;
				}
				if (strategy.getCurrent().isClosed()) {
					return hasApplied;
				}

			}
		} while (hasAppliedInALoop);
		return hasApplied;
	}
	
	//EMERSON: Temporário Algoritmo Genético
//	private int _numeroBifurcacoes=0;
//	public int getNumeroBifurcacoes(){return _numeroBifurcacoes;}
//	public void setNumeroBifurcacoes(int numeroBifurcacoes){_numeroBifurcacoes = numeroBifurcacoes;}
	
	/**
	 * @param openBranches
	 * @param sfb
	 * @return
	 */
	//EMERSON: Temporário Algoritmo Genético
	public boolean applyPBRule(ISimpleStrategy strategy) {

		boolean hasApplied;

		Iterator<IProofTransformation> it = strategy.getProofTransformations().iterator();
		hasApplied = false;
		while (it.hasNext()) {
			IProofTransformation pt = (IProofTransformation) it.next();

			boolean hasAppliedThis = pt.apply(strategy.getCurrent(), strategy.getSignedFormulaBuilder());
			if (hasAppliedThis) {
				//setNumeroBifurcacoes(getNumeroBifurcacoes()+1);
				hasApplied = true;
				break;
			}
			if (strategy.getCurrent().isClosed()) {
				break;
			}

		}
		return hasApplied;

	}

}
