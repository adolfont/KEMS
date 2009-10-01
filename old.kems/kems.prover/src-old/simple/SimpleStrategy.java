/*
 * Created on Oct 22, 2004
 *
 */
package main.strategy.simple;

import logic.formulas.Formula;
import logic.formulas.FormulaList;
import logic.signedFormulas.PBCandidatesSignedFormulaList;
import logic.signedFormulas.SignedFormula;
import logic.signedFormulas.SignedFormulaBuilder;
import logic.signedFormulas.SignedFormulaList;
import logicalSystems.classicalLogic.ClassicalConnectives;
import logicalSystems.classicalLogic.ClassicalSigns;
import main.proofTree.Node;
import main.proofTree.ProofTree;
import main.proofTree.SignedFormulaNode;
import main.proofTree.SignedFormulaNodeState;
import main.proofTree.iterator.ProofTreeGlobalIterator;
import main.proofTree.origin.Origin;
import main.proofTree.origin.SignedFormulaNodeOrigin;
import main.strategy.ClassicalProofTree;
import main.tableau.Method;
import rules.Rule;
import rules.TwoPremisesOneConclusionRule;
import rules.structures.RuleList;

/**
 * Class that represents a simple strategy for KE Tableaus.
 * 
 * Uses FormulaReferenceClassicalProofTree objects that keep references of
 * formulas and subformulas.
 * 
 * @author Adolfo Gustavo Serra Seca Neto
 *  
 */
public class SimpleStrategy extends AbstractSimpleStrategy {

	private SignedFormulaList formulasWithTOPorBOTTOM = null;

	/**
	 * Creates a simple strategy for a proof method
	 * 
	 * @param m
	 *            a method
	 */
	public SimpleStrategy(Method m) {
		super(m);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see main.strategy.AbstractStrategy#createOrigin(rules.Rule,
	 *      main.proofTree.SignedFormulaNode, main.proofTree.SignedFormulaNode)
	 */
	public Origin createOrigin(Rule rule, SignedFormulaNode main,
			SignedFormulaNode auxiliary) {
		return new SignedFormulaNodeOrigin(rule, main, auxiliary);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see main.strategy.Strategy#createPTInstance(main.proofTree.Node)
	 */
	public ProofTree createPTInstance(Node aNode) {
		return new FormulaReferenceClassicalProofTree((SignedFormulaNode) aNode);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see main.strategy.AbstractStrategy#isRightBranchOfOpenLeftBranch()
	 */
	public boolean isRightBranchOfOpenLeftBranch() {
		return (getThisCurrent().getParent() != null)
				&& (getThisCurrent() == getThisCurrent().getParent().getRight())
				&& (!((FormulaReferenceClassicalProofTree) getThisCurrent()
						.getParent().getLeft()).isClosed());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see main.strategy.AbstractStrategy#maybeEraseLeft()
	 */
	protected void maybeEraseLeft() {
	}

	protected FormulaList getSubformulaLocalReferences(
			ClassicalProofTree proofTree, Formula topFormula,
			SignedFormula aSignedFormula) {
		return ((FormulaReferenceClassicalProofTree) proofTree)
				.getSubformulaLocalReferences(topFormula, aSignedFormula);
	}

	/** returns all references 
	 * @param proofTree
	 * @param f
	 * @return
	 */
	public SignedFormulaList getParentReferences(ClassicalProofTree proofTree,
			Formula f) {
		return ((FormulaReferenceClassicalProofTree) proofTree)
				.getParentReferences(f);
	}

	public FormulaList getSubformulaParentReferences(
			ClassicalProofTree proofTree, Formula f, SignedFormula sf) {
		return ((FormulaReferenceClassicalProofTree) proofTree)
				.getSubformulaParentReferences(f, sf);
	}

//	public FormulaList getSubformulaParentReferences(
//			ClassicalProofTree proofTree, Formula f) {
//		return ((FormulaReferenceClassicalProofTree) proofTree)
//				.getSubformulaParentReferences(f);
//	}

	/**
	 * @param cpt
	 * @param topFormula
	 * @param bottomFormula
	 * @return
	 */
	private SignedFormulaList calculate(ClassicalProofTree cpt,
			Formula topFormula, Formula bottomFormula) {
		SignedFormulaList sfl = ((FormulaReferenceClassicalProofTree) cpt)
				.getLocalReferences(topFormula);
		sfl.addAll(((FormulaReferenceClassicalProofTree) cpt)
				.getLocalReferences(bottomFormula));
		sfl = intersect(cpt.getPBCandidates(), sfl);
		return sfl;
	}

	/**
	 * @param candidates
	 * @param sfl
	 * @return
	 */
	private SignedFormulaList intersect(PBCandidatesSignedFormulaList candidates,
			SignedFormulaList sfl) {
		SignedFormulaList result = new SignedFormulaList();

		for (int i = 0; i < candidates.size(); i++) {
			if (sfl.contains(candidates.get(i))) {
				result.add(candidates.get(i));
			}
		}

		return result;

	}

	/**
	 * Applies as much two premise rules as possible.
	 * 
	 * @param proofTree -
	 *            the current proof tree
	 * @param sfb -
	 *            the formula builder
	 * @return true if any rule has been applied
	 */
	// TODO não está funcionando.
	protected boolean applyTwoPremiseRulesWithoutMapBottomUp(
			ClassicalProofTree proofTree, SignedFormulaBuilder sfb) {

		boolean hasApplied = false;

		RuleList twoPremiseRules = getMethod().getRules().get(
				"twoPremiseRulesNewII");

		ProofTreeGlobalIterator it = (ProofTreeGlobalIterator) proofTree
				.getGlobalIterator();

		Formula topFormula = sfb
				.createCompositeFormula(ClassicalConnectives.TOP);
		Formula bottomFormula = sfb
				.createCompositeFormula(ClassicalConnectives.BOTTOM);

		SignedFormula T_TOP = sfb.createSignedFormula(ClassicalSigns.TRUE,
				topFormula);
		SignedFormula F_BOT = sfb.createSignedFormula(ClassicalSigns.FALSE,
				bottomFormula);

		// AQUI
		// TODO Deixar de ir de baixo para cima. Criar novo iterador.
		// goes to the last
		while (it.hasNextLeft()) {
			it.nextLeft();
		}
		// ProofTreeBasicIterator it = fcpt.getGlobalIterator();
		// TODO deixar de olhar o F BOT
		while (it.hasPrevious() && !proofTree.isClosed()) {
			SignedFormulaNode sfn = (SignedFormulaNode) it.current();
			it.previous();

			if (((SignedFormula) sfn.getContent() == T_TOP)
					|| ((SignedFormula) sfn.getContent() == F_BOT)) {
				continue;
			}

			// System.err.println(" FAss do nó " + sfn.getContent());
			SignedFormula auxCandidate = (SignedFormula) sfn.getContent();
			// System.err.println(" Ref à fórmula na árvore "
			// + fcpt.getParentReferences(auxCandidate.getFormula()));
			SignedFormulaList cand = intersect(proofTree.getPBCandidates(),
					getParentReferences(proofTree, auxCandidate.getFormula()));
			cand.remove(auxCandidate);
			// System.err.println(" Ref cand à fórmula na árvore " + cand);

			for (int candidate = 0; candidate < cand.size(); candidate++) {
				SignedFormula mainCandidate = cand.get(candidate);

				// TODO não funciona porque pode ser um conectivo interior
				// Connective mainConnective = ((CompositeFormula) mainCandidate
				// .getFormula()).getConnective();
				// System.err.print(mainConnective + " \n");

				for (int i = 0; i < twoPremiseRules.size(); i++) {
					TwoPremisesOneConclusionRule r = (TwoPremisesOneConclusionRule) twoPremiseRules
							.get(i);

					// System.err.print(r + " ");

					SignedFormulaList sflToApply = new SignedFormulaList(
							mainCandidate);
					sflToApply.add(auxCandidate);
					// System.err.print(sflToApply);

					// TODO não funciona porque pode ser subfórmula
					// KERuleRole auxRole = findRole(auxCandidate,
					// mainCandidate);

					// System.err.print(auxRole + " ");

					SignedFormulaList sflResult = r.getPossibleConclusions(sfb
							.getSignedFormulaFactory(),
							sfb.getFormulaFactory(), sflToApply);
					// System.err.println(" result " + sflResult);
					if (sflResult != null) {
						SignedFormula conclusion = sflResult.get(0);

						// coloca a nova na proof tree
//						SignedFormulaNode n;
						proofTree.addLast(new SignedFormulaNode(conclusion,
								SignedFormulaNodeState.NOT_ANALYSED,
								createOrigin(r, proofTree
										.getNode(mainCandidate), proofTree
										.getNode(auxCandidate))));

						// System.err.println ("Nodeadded " +n + " at " + fcpt);

						proofTree.removeFromPBCandidates(mainCandidate,
								SignedFormulaNodeState.ANALYSED);

						hasApplied = true;
					}

				}

			}

		}

		return hasApplied;
	}

	/**
	 * Applies as much two premise rules as possible.
	 * 
	 * @param proofTree -
	 *            the current proof tree
	 * @param sfb -
	 *            the formula builder
	 * @return true if any rule has been applied
	 */
	protected boolean applyTwoPremiseRulesWithoutMapCurrentDownThenUp(
			ClassicalProofTree proofTree, SignedFormulaBuilder sfb) {

		// TODO REMOVE
//		ConnectiveRoleSignRuleList twoPremiseRulesII = (ConnectiveRoleSignRuleList) getMethod()
//				.getRules().get("twoPremiseRulesNewII");

		boolean hasApplied = false;
		RuleList twoPremiseRules = getMethod().getRules().get(
				"twoPremiseRulesNewII");

		ProofTreeGlobalIterator it = (ProofTreeGlobalIterator) proofTree
				.getGlobalIterator(), firstInTheBranch = (ProofTreeGlobalIterator) proofTree
				.getGlobalIterator();

		Formula topFormula = sfb
				.createCompositeFormula(ClassicalConnectives.TOP);
		Formula bottomFormula = sfb
				.createCompositeFormula(ClassicalConnectives.BOTTOM);

		SignedFormula T_TOP = sfb.createSignedFormula(ClassicalSigns.TRUE,
				topFormula);
		SignedFormula F_BOT = sfb.createSignedFormula(ClassicalSigns.FALSE,
				bottomFormula);

		while (!proofTree.isClosed()) {

			SignedFormulaNode sfn = null;
			if (it.hasNextLeft()) {
				sfn = (SignedFormulaNode) it.nextRight();
			} else if (firstInTheBranch.hasPrevious()) {
				sfn = (SignedFormulaNode) firstInTheBranch.previous();
			} else
				break;

			if (((SignedFormula) sfn.getContent() == T_TOP)
					|| ((SignedFormula) sfn.getContent() == F_BOT)) {
				continue;
			}

			SignedFormula auxCandidate = (SignedFormula) sfn.getContent();
			SignedFormulaList mainCandidates = intersect(proofTree
					.getPBCandidates(), getParentReferences(proofTree,
					auxCandidate.getFormula()));
			mainCandidates.remove(auxCandidate);

			for (int candidate = 0; candidate < mainCandidates.size(); candidate++) {
				SignedFormula mainCandidate = mainCandidates.get(candidate);

				for (int i = 0; i < twoPremiseRules.size(); i++) {
					TwoPremisesOneConclusionRule r = (TwoPremisesOneConclusionRule) twoPremiseRules
							.get(i);

					SignedFormulaList sflToApply = new SignedFormulaList(
							mainCandidate);
					sflToApply.add(auxCandidate);

					// FormulaList fl = proofTree.getSubformulaParentReferences(
					// auxCandidate.getFormula(), mainCandidate);
					// if (fl.size() > 1) {
					// // System.err.println (mainCandidate + " " +
					// // auxCandidate );
					// CompositeFormula f = (CompositeFormula) fl.get(0);
					// Connective conn = f.getConnective();
					// // System.err.println(fl + " " + conn);
					//                    
					// Formula f_left = (Formula) f.getImmediateSubformulas()
					// .get(0);
					// Formula f_right = null;
					// if (f.getImmediateSubformulas().size() > 1) {
					// f_right = (Formula) f.getImmediateSubformulas()
					// .get(1);
					// }
					// KERuleRole role = (auxCandidate.getFormula() == f_left) ?
					// KERuleRole.LEFT
					// : ((f_right != null) && (auxCandidate
					// .getFormula() == f_right)) ? KERuleRole.RIGHT
					// : null;
					//                    
					// // System.err.println(role);
					// FormulaSign sign = auxCandidate.getSign();
					//                    
					// TwoPremisesOneConclusionRule r1 =
					// (TwoPremisesOneConclusionRule) twoPremiseRulesII
					// .get(conn, role, sign);
					// // System.err.println(r1);
					//                    
					// SignedFormulaList sflResultX = r1
					// .getPossibleConclusions(sfb
					// .getSignedFormulaFactory(), sfb
					// .getFormulaFactory(), sflToApply, f);
					//
					// // repeteco - início
					// if (sflResultX != null) {
					// SignedFormula conclusion = sflResultX.get(0);
					//
					// SignedFormulaNode n;
					// proofTree.addLast(n = new SignedFormulaNode(conclusion,
					// SignedFormulaNodeState.NOT_ANALYSED,
					// createOrigin(r1, proofTree.getNode(mainCandidate),
					// proofTree.getNode(auxCandidate))));
					//
					// proofTree.removeFromPBCandidates(mainCandidate,
					// SignedFormulaNodeState.ANALYSED);
					//
					// hasApplied = true;
					// }
					// // repeteco - fim
					// }

					SignedFormulaList sflResult = r.getPossibleConclusions(sfb
							.getSignedFormulaFactory(),
							sfb.getFormulaFactory(), sflToApply);
					if (sflResult != null) {
						SignedFormula conclusion = sflResult.get(0);

						proofTree.addLast(new SignedFormulaNode(conclusion,
								SignedFormulaNodeState.NOT_ANALYSED,
								createOrigin(r, proofTree
										.getNode(mainCandidate), proofTree
										.getNode(auxCandidate))));

						proofTree.removeFromPBCandidates(mainCandidate,
								SignedFormulaNodeState.ANALYSED);

						hasApplied = true;
					}

				}
			}
		}

		return hasApplied;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see main.strategy.AbstractStrategy#updateFormulasWithTOPorBOTTOM(logic.signedFormulas.SignedFormula)
	 */
	protected void updateFormulasWithTOPorBOTTOM(SignedFormula sf,
			Formula topFormula, Formula bottomFormula) {
		if (topFormula.isStrictSubformula(sf.getFormula())
				|| bottomFormula.isStrictSubformula(sf.getFormula())) {
			if (!formulasWithTOPorBOTTOM.contains(sf)) {
				formulasWithTOPorBOTTOM.add(0, sf);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see main.strategy.AbstractStrategy#nextSignedFormulaWithTOPorBOTTOM(main.strategy.ClassicalProofTree,
	 *      logic.formulas.Formula, logic.formulas.Formula)
	 */
	protected SignedFormula nextSignedFormulaWithTOPorBOTTOM(
			ClassicalProofTree proofTree, Formula topFormula,
			Formula bottomFormula) {

		if (formulasWithTOPorBOTTOM == null) {
			formulasWithTOPorBOTTOM = calculate(proofTree, topFormula,
					bottomFormula);
		}

		if (formulasWithTOPorBOTTOM.size() > 0) {
			return formulasWithTOPorBOTTOM.remove(0);
		}
		return null;
	}

	protected void resetFormulasWithTOPorBottom() {
		formulasWithTOPorBOTTOM = null;
	}

	protected SignedFormulaList mainCandidates = null;

	protected int counterMainCandidates;

	/*
	 * (non-Javadoc)
	 * 
	 * @see main.strategy.AbstractStrategy#nextMainCandidate(main.strategy.ClassicalProofTree,
	 *      logic.signedFormulas.SignedFormula)
	 */
	protected SignedFormula nextMainCandidate(ClassicalProofTree proofTree,
			SignedFormula auxCandidate) {
		if (counterMainCandidates < mainCandidates.size()) {
			return mainCandidates.get(counterMainCandidates++);
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see main.strategy.AbstractStrategy#initializeMainCandidates()
	 */
	protected void initializeMainCandidates(ClassicalProofTree proofTree,
			SignedFormula auxCandidate) {

		mainCandidates = intersect(proofTree.getPBCandidates(),
				getParentReferences(proofTree, auxCandidate.getFormula()));
		mainCandidates.remove(auxCandidate);
		
		counterMainCandidates = 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see main.strategy.AbstractStrategy#getPositionMainCandidates()
	 */
	protected int getPositionMainCandidates() {
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see main.strategy.AbstractStrategy#decrementPositionMainCandidates()
	 */
	protected void decrementPositionMainCandidates() {
	}

}
