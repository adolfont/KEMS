/*
 * Created on 29/09/2005
 *
 */
package main.strategy;

import java.util.Iterator;
import java.util.LinkedList;

import logic.formulas.CompositeFormula;
import logic.formulas.Connective;
import logic.formulas.Formula;
import logic.formulas.FormulaList;
import logic.signedFormulas.FormulaSign;
import logic.signedFormulas.PBCandidateList;
import logic.signedFormulas.SignedFormula;
import logic.signedFormulas.SignedFormulaBuilder;
import logic.signedFormulas.SignedFormulaList;
import logicalSystems.classicalLogic.ClassicalConnectives;
import logicalSystems.classicalLogic.ClassicalRules;
import logicalSystems.classicalLogic.ClassicalSigns;
import main.proofTree.ProofTree;
import main.proofTree.SignedFormulaNode;
import main.proofTree.SignedFormulaNodeState;
import main.proofTree.iterator.ProofTreeGlobalIterator;
import main.proofTree.origin.Origin;
import main.strategy.memorySaver.ReferenceFinder;
import main.tableau.Method;
import rules.KERuleRole;
import rules.NullRule;
import rules.Rule;
import rules.TwoPremisesOneConclusionRule;
import rules.structures.ConnectiveRoleSignRuleList;
import rules.structures.OnePremiseRuleList;
import rules.structures.PBRuleList;
import rules.structures.TopBottomRoleRuleList;
import sun.reflect.ReflectionFactory.GetReflectionFactoryAction;
import main.exceptions.KEMSException;

/**
 * Class that contains features common to several strategies.
 * 
 * @author Adolfo Gustavo Serra Seca Neto
 *  
 */
public abstract class AbstractStrategy extends Strategy {

	/**
	 * Creates an AbstractSimpleStrategy
	 * 
	 * @param m
	 */
	public AbstractStrategy(Method m) {
		super(m);
	}

	/**
	 * current proof tree
	 */
	private ClassicalProofTree _current;

	/**
	 * @return current proof tree
	 */
	public ClassicalProofTree getCurrent() {
		return _current;
	}

	/**
	 * sets current proof tree
	 * 
	 * @param value
	 */
	public void setCurrent(ClassicalProofTree value) {
		_current = value;
	}

	/**
	 * returns main proof tree
	 */
	private ClassicalProofTree _proofTree;

	/**
	 * @return main proof tree
	 */
	public ClassicalProofTree getProofTree() {
		return _proofTree;
	}

	/**
	 * sets main proof tree
	 * 
	 * @param value
	 */
	public void setProofTree(ClassicalProofTree value) {
		_proofTree = value;
	}

	/**
	 * Crates an origin object
	 * 
	 * @param rule
	 * @param main
	 * @param auxiliary
	 * @return the origin object
	 */
	protected abstract Origin createOrigin(Rule rule, SignedFormulaNode main,
			SignedFormulaNode auxiliary);

	/**
	 * @return true if it is the right branch of a node whose left branch is
	 *         open
	 */
	protected abstract boolean isRightBranchOfOpenLeftBranch();

	/**  Executes first actions regarding PBCandidates
	 * @param thisProofTree
	 */
	protected final void initialPBCandidatesActions(
			ClassicalProofTree thisProofTree) {
		thisProofTree.removeFromPBCandidates(0);
		thisProofTree.removeFromPBCandidates(0);
	}

	/**
	 * possibly erases left branch of the current proof tree
	 */
	abstract protected void maybeEraseLeft();

	/* (non-Javadoc)
	 * @see main.strategy.Strategy#execute(main.proofTree.ProofTree, logic.signedFormulas.SignedFormulaBuilder)
	 */
	protected final ProofTree execute(ProofTree cpt, SignedFormulaBuilder sfb) {
		ClassicalProofTree pt = (ClassicalProofTree) cpt;

		setProofTree(pt);

		// the close method has already loaded the problem into a proof tree!
		// if it is already closed, finish now!
		if (getProofTree().isClosed())
			return pt;

		initialPBCandidatesActions(getProofTree());

		LinkedList openBranches = new LinkedList();
		openBranches.addFirst(pt);

		boolean hasApplied;
		while (!openBranches.isEmpty() && !getProofTree().isClosed()) {

			setCurrent((ClassicalProofTree) openBranches.removeFirst());

			// if this branch is the right of some branch
			if (isRightBranchOfOpenLeftBranch()) {
				break;
				//                System.err.println("Did not close because left " +_current
				//                        .getParent().getLeft().getBranchNumber() + " is open");
			}

			maybeEraseLeft();

			// keep applying rules until it is not possible to apply
			do {
				hasApplied = applyLinearRules(sfb);
				if (getCurrent().isClosed()) {
					//                    System.err.println("Closed " +_current
					//                            .getBranchNumber() + " !");
					break;
				} else {
					hasApplied = applyPBRule(openBranches, sfb);
				}

			} while (hasApplied);

		}

		return getProofTree();
	}

	/**
	 * applies the PB rule
	 * 
	 * @param s
	 * @param sfb
	 * @return
	 */
	protected final boolean applyPBRule(LinkedList s, SignedFormulaBuilder sfb) {

		boolean hasApplied = false;

		PBCandidateList candidates = getCurrent().getPBCandidates();

		if (candidates.size() > 0) {
			return applyPBOnce(s, sfb, candidates);
		}

		return hasApplied;
	}

	/**
	 * Applies as much linear rules as possible in the current branch.
	 * 
	 * @param sfb -
	 *            the formula builder
	 * @return true if has applied any rule
	 */
	protected final boolean applyLinearRules(SignedFormulaBuilder sfb) {
		boolean hasApplied;
		do {
			hasApplied = applyOnePremiseRules(getCurrent(), sfb);
			if (getCurrent().isClosed()) {
				break;
			}

			hasApplied = applyTOPandBOTTOMrules(getCurrent(), sfb)
					|| hasApplied;
			if (getCurrent().isClosed()) {
				break;
			}

			// first faster
			hasApplied = applyTwoPremiseRulesWithMap(
			//             hasApplied = applyTwoPremiseRulesWithoutMapCurrentDownThenUp(
					// hasApplied = applyTwoPremiseRulesWithoutMapBottomUp(
					getCurrent(), sfb) || hasApplied;
			if (getCurrent().isClosed()) {
				break;
			}

		} while (hasApplied);
		return hasApplied;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see main.strategy.simple.AbstractSimpleStrategy#applyOnePremiseRules(main.strategy.ClassicalProofTree,
	 *      logic.signedFormulas.SignedFormulaBuilder)
	 */
	protected final boolean applyOnePremiseRules(ClassicalProofTree proofTree,
			SignedFormulaBuilder sfb) {
		boolean hasApplied = false;

		int i = 0;

		// notice that I am using a non-recommended "i--"

		while (i < proofTree.getPBCandidates().size() && !proofTree.isClosed()) {

			// for each signed formula not used, if it accepts a one premise
			// rule, apply the rule and remove it form the list of candidates.

			SignedFormula sf = (SignedFormula) proofTree.getPBCandidates().get(
					i);

			hasApplied = chooseAndApplyOnePremiseRule(proofTree, sfb, sf);
			if (hasApplied) {
				i--;
			}

			i++;
		}

		return hasApplied;
	}

	/**
	 * chooses and applies a one premise rule.
	 * 
	 * @param proofTree
	 * @param sfb
	 * @param hasApplied
	 * @param sf
	 * @return true if the rule has been applied
	 */
	protected boolean chooseAndApplyOnePremiseRule(
			ClassicalProofTree proofTree, SignedFormulaBuilder sfb,
			SignedFormula sf) {
		boolean hasApplied = false;
		Rule r = chooseOnePremiseRule(proofTree, sf);

		if (r != NullRule.INSTANCE) {
			hasApplied = true;

			SignedFormulaList sfl = r.getPossibleConclusions(sfb
					.getSignedFormulaFactory(), sfb.getFormulaFactory(),
					new SignedFormulaList(sf));
			
			proofTree.removeFromPBCandidates(sf,
					SignedFormulaNodeState.ANALYSED);

			for (int j = 0; j < sfl.size(); j++) {
				proofTree.addLast(new SignedFormulaNode(sfl.get(j),
						SignedFormulaNodeState.NOT_ANALYSED, createOrigin(r,
								proofTree.getNode(sf), null)));
			}
		}
		return hasApplied;
	}

	private final Rule chooseOnePremiseRule(ClassicalProofTree cpt,
			SignedFormula sf) {

		OnePremiseRuleList onePremiseRules = (OnePremiseRuleList) getMethod()
				.getRules().get("onePremiseRules");

		if (sf.getFormula() instanceof CompositeFormula) {
			return onePremiseRules.get(sf.getSign(), ((CompositeFormula) sf
					.getFormula()).getConnective());
		}

		return NullRule.INSTANCE;

	}

	/**
	 * gets the conclusion of a rule.
	 * 
	 * @param r -
	 *            the rule
	 * @param sfb -
	 *            the formula builder
	 * @param aFormula -
	 *            the signed formula
	 * @return the conclusion
	 */
	protected final SignedFormula getSignedFormula(Rule r,
			SignedFormulaBuilder sfb, SignedFormula aFormula) {
		SignedFormulaList sfl = r.getPossibleConclusions(sfb
				.getSignedFormulaFactory(), sfb.getFormulaFactory(),
				new SignedFormulaList(aFormula));
		if (sfl.size() > 0) {
			return sfl.get(0);
		} else
			throw new KEMSException("Rule applied but no conclusion: " + r);
	}

	/**
	 * @param s
	 * @param sfb
	 * @param PBRules
	 * @param candidates
	 * @return
	 */
	protected boolean applyPBOnce(LinkedList s, SignedFormulaBuilder sfb,
			PBCandidateList candidates) {
		PBRuleList PBRules = (PBRuleList) getMethod().getRules().get("PBRules");
		

		boolean foundRule=false;
		Iterator it = candidates.iterator();
		SignedFormula candidateChosen = null;;
		Rule ruleChosen = NullRule.INSTANCE;
		
		// loks for a rule that can be applied
		while (it.hasNext()){
			candidateChosen = (SignedFormula) it.next();
			ruleChosen = choosePBRule(PBRules, candidateChosen);
			if (canApply(ruleChosen, candidateChosen, sfb)){
				foundRule = true;
				break;
			}
		}
		
		if (!foundRule || ruleChosen == NullRule.INSTANCE) {
			return false;
//			throw new TPException("No PB rule found for "
//					+ candidateChosen.toString() + ".");
		}

		// System.err.println(candidateChosen);
		TwoPremisesOneConclusionRule r = (TwoPremisesOneConclusionRule) ruleChosen;
//		System.err.println("TPRule "+r);

		SignedFormula aux = r.getAuxiliaryCandidates(
				sfb.getSignedFormulaFactory(), sfb.getFormulaFactory(),
				candidateChosen).get(0);
//		System.err.println("cand "+aux);

		SignedFormula auxOpposite = (aux.getSign().equals(ClassicalSigns.TRUE)) ? sfb
				.createSignedFormula(ClassicalSigns.FALSE, aux.getFormula())
				: sfb
						.createSignedFormula(ClassicalSigns.TRUE, aux
								.getFormula());

//				System.err.println("cand opp "+auxOpposite);
		// TODO verificar se conclusão já está na ProofTree
		SignedFormulaList sfl = new SignedFormulaList(candidateChosen);
		sfl.add(aux);
		SignedFormula conclusion = r.getPossibleConclusions(
				sfb.getSignedFormulaFactory(), sfb.getFormulaFactory(), sfl)
				.get(0);
//		System.err.println("conc "+conclusion + " to "+sfl);
		getCurrent().removeFromPBCandidates(candidateChosen,
				SignedFormulaNodeState.ANALYSED);
//		System.err.println("remove candidate chosen for PB "+candidateChosen);
//		System.err.println("current PB: "+ getCurrent().getPBCandidates());

		ClassicalProofTree right = (ClassicalProofTree) getCurrent().addRight(
				new SignedFormulaNode(auxOpposite,
						SignedFormulaNodeState.NOT_ANALYSED, createOrigin(
								ClassicalRules.PB, getCurrent().getNode(
										candidateChosen), null)));

		Rule complementary = PBRules.getComplementary(r);
//		System.err.println("complementary rule: " + complementary);
//		System.err.println("current PB: "+ getCurrent().getPBCandidates());
		
		if (complementary != null) {
			SignedFormulaList sflComplementary = new SignedFormulaList(
					candidateChosen);
			sflComplementary.add(auxOpposite);
			SignedFormula complementaryConclusion = complementary
					.getPossibleConclusions(sfb.getSignedFormulaFactory(),
							sfb.getFormulaFactory(), sflComplementary).get(0);

			right.addLast(new SignedFormulaNode(complementaryConclusion,
					SignedFormulaNodeState.NOT_ANALYSED, createOrigin(
							complementary, getCurrent()
									.getNode(candidateChosen), right
									.getNode(auxOpposite))));
		}

//		System.err.println("current PB: "+ getCurrent().getPBCandidates());
		ClassicalProofTree left = (ClassicalProofTree) getCurrent().addLeft(
				new SignedFormulaNode(aux, SignedFormulaNodeState.NOT_ANALYSED,
						createOrigin(ClassicalRules.PB, getCurrent().getNode(
								candidateChosen), null)));
//		System.err.println("Adds to left: "+ aux);
//		System.err.println("left PB: "+ left.getPBCandidates());
		left.addLast(new SignedFormulaNode(conclusion,
				SignedFormulaNodeState.NOT_ANALYSED, createOrigin(r,
						getCurrent().getNode(candidateChosen), left
								.getNode(aux))));
//		System.err.println("Adds to left: "+ conclusion);
//		System.err.println("left PB: "+ left.getPBCandidates());
		s.addFirst(right);
		setCurrent(left);

		return true;
	}

	/**
	 * @param ruleChosen
	 * @return
	 */
	private boolean canApply(Rule ruleChosen, SignedFormula candidateChosen,
			SignedFormulaBuilder sfb) {
		return true;
	}

	/**
	 * @param PBRules
	 * @param candidateChosen
	 * @return
	 */
	private Rule choosePBRule(PBRuleList PBRules, SignedFormula candidateChosen) {
		return PBRules.get(candidateChosen.getSign(),
				((CompositeFormula) candidateChosen.getFormula())
						.getConnective());
	}

	/**
	 * Applies rules with TOP or BOTTOM unary connectives.
	 * 
	 * @param proofTree
	 * @param sfb -
	 *            the formula builder
	 * @return true if some rule was applied.
	 */
	protected boolean applyTOPandBOTTOMrules(ClassicalProofTree proofTree,
			SignedFormulaBuilder sfb) {

		Formula topFormula = sfb
				.createCompositeFormula(ClassicalConnectives.TOP);
		Formula bottomFormula = sfb
				.createCompositeFormula(ClassicalConnectives.BOTTOM);
		TopBottomRoleRuleList topAndBottomRules = (TopBottomRoleRuleList) getMethod()
				.getRules().get("topAndBottomRulesNew");

		SignedFormula sfTB = nextSignedFormulaWithTOPorBOTTOM(proofTree,
				topFormula, bottomFormula);
		//		SignedFormulaList formulasWithTOPorBOTTOM = calculate(proofTree,
		//				topFormula, bottomFormula);

		boolean hasApplied = false;

		while (sfTB != null && !proofTree.isClosed()) {
			//			while (formulasWithTOPorBOTTOM.size() > 0 &&
			// !proofTree.isClosed()) {
			SignedFormula aSignedFormulaWithTOPorBOTTOM = sfTB;
			FormulaList fl = getSubformulaLocalReferences(proofTree,
					topFormula, aSignedFormulaWithTOPorBOTTOM);
			fl.addAll(getSubformulaLocalReferences(proofTree, bottomFormula,
					aSignedFormulaWithTOPorBOTTOM));
			CompositeFormula cf1 = (CompositeFormula) fl.get(0);

			CompositeFormula cf1_left = null, cf1_right = null;
			if (cf1.getImmediateSubformulas().get(0) instanceof CompositeFormula) {
				cf1_left = (CompositeFormula) cf1.getImmediateSubformulas()
						.get(0);
			}
			if (cf1.getImmediateSubformulas().size() > 1
					&& cf1.getImmediateSubformulas().get(1) instanceof CompositeFormula) {
				cf1_right = (CompositeFormula) cf1.getImmediateSubformulas()
						.get(1);
			}

			Rule r = null;
			SignedFormula sf = null;

			if (cf1_left == topFormula || cf1_left == bottomFormula) {
				r = topAndBottomRules.get(cf1_left.getConnective(), cf1
						.getConnective(), KERuleRole.LEFT);
				sf = getSignedFormula(r, sfb, aSignedFormulaWithTOPorBOTTOM);
			} else {
				if (cf1_right == topFormula || cf1_right == bottomFormula) {
					r = topAndBottomRules.get(cf1_right.getConnective(), cf1
							.getConnective(), KERuleRole.RIGHT);
					sf = getSignedFormula(r, sfb, aSignedFormulaWithTOPorBOTTOM);
				}
			}

			//			formulasWithTOPorBOTTOM.remove(aSignedFormulaWithTOPorBOTTOM);
			proofTree.removeFromPBCandidates(aSignedFormulaWithTOPorBOTTOM,
					SignedFormulaNodeState.ANALYSED);
			proofTree.addLast(new SignedFormulaNode(sf,
					SignedFormulaNodeState.NOT_ANALYSED, createOrigin(r,
							proofTree.getNode(aSignedFormulaWithTOPorBOTTOM),
							null)));

			hasApplied = true;

			updateFormulasWithTOPorBOTTOM(sf, topFormula, bottomFormula);

			//			if (topFormula.isStrictSubformula(sf.getFormula())
			//					|| bottomFormula.isStrictSubformula(sf.getFormula())) {
			//				if (!formulasWithTOPorBOTTOM.contains(sf)) {
			//					formulasWithTOPorBOTTOM.add(0, sf);
			//				}
			//			}
			
			sfTB = nextSignedFormulaWithTOPorBOTTOM(proofTree,
					topFormula, bottomFormula);
		}

		resetFormulasWithTOPorBottom();
		return hasApplied;

	}

	/**
	 *  
	 */
	protected abstract void resetFormulasWithTOPorBottom();

	/**
	 * @param sf
	 * @param topFormula
	 * @param bottomFormula
	 */
	protected abstract void updateFormulasWithTOPorBOTTOM(SignedFormula sf,
			Formula topFormula, Formula bottomFormula);

	/**
	 * @param proofTree
	 * @param topFormula
	 * @param bottomFormula
	 * @return
	 */
	protected abstract SignedFormula nextSignedFormulaWithTOPorBOTTOM(
			ClassicalProofTree proofTree, Formula topFormula,
			Formula bottomFormula);

	/**
	 * @param proofTree
	 * @param bottomFormula
	 * @param signedFormulaWithTOPorBOTTOM
	 * @return
	 */
	protected abstract FormulaList getSubformulaLocalReferences(
			ClassicalProofTree proofTree, Formula bottomFormula,
			SignedFormula signedFormulaWithTOPorBOTTOM);

	//////////////////////

	protected abstract SignedFormula nextMainCandidate(
			ClassicalProofTree proofTree, SignedFormula auxCandidate);

	protected abstract void initializeMainCandidates(
			ClassicalProofTree proofTree, SignedFormula auxCandidate);

	protected abstract int getPositionMainCandidates();

	protected abstract void decrementPositionMainCandidates();

	/**
	 * Applies as much two premise rules as possible.
	 * 
	 * @param proofTree -
	 *            the current proof tree
	 * @param sfb -
	 *            the formula builder
	 * @return true if any rule has been applied
	 */
	protected final boolean applyTwoPremiseRulesWithMap(
			ClassicalProofTree proofTree, SignedFormulaBuilder sfb) {

		boolean hasApplied = false;
		// RuleList twoPremiseRules = getMethod()._rules
		// .get("twoPremiseRulesNew");

		ConnectiveRoleSignRuleList twoPremiseRulesII = (ConnectiveRoleSignRuleList) getMethod()
				.getRules().get("twoPremiseRulesNewII");

		Formula topFormula = sfb
				.createCompositeFormula(ClassicalConnectives.TOP);
		Formula bottomFormula = sfb
				.createCompositeFormula(ClassicalConnectives.BOTTOM);

		SignedFormula T_TOP = sfb.createSignedFormula(ClassicalSigns.TRUE,
				topFormula);
		SignedFormula F_BOT = sfb.createSignedFormula(ClassicalSigns.FALSE,
				bottomFormula);

		ProofTreeGlobalIterator it = (ProofTreeGlobalIterator) proofTree
				.getGlobalIterator(), firstInTheBranch = (ProofTreeGlobalIterator) proofTree
				.getGlobalIterator();

		while (!proofTree.isClosed()) {

			SignedFormulaNode sfn = null;
			if (it.hasNextLeft()) {
				sfn = (SignedFormulaNode) it.nextRight();
			} else if (firstInTheBranch.hasPrevious()) {
				sfn = (SignedFormulaNode) firstInTheBranch.previous();
			} else
				break;

			// if it is T_TOP or F_BOT, forget

			if (((SignedFormula) sfn.getContent() == T_TOP)
					|| ((SignedFormula) sfn.getContent() == F_BOT)) {
				continue;
			}

			SignedFormula auxCandidate = (SignedFormula) sfn.getContent();
			// SignedFormulaList mainCandidates = intersect(proofTree
			// .getPBCandidates(),
			// ReferenceFinder.getParentReferences(proofTree,
			// auxCandidate.getFormula()));
			// mainCandidates.remove(auxCandidate);

			// for (int candidate = 0; candidate < mainCandidates.size();
			// candidate++) {
			SignedFormula mainCandidate;

			initializeMainCandidates(proofTree, auxCandidate);

			while ((mainCandidate = nextMainCandidate(proofTree, auxCandidate)) != null) {

				SignedFormulaList sflToApply = new SignedFormulaList(
						mainCandidate);
				sflToApply.add(auxCandidate);

				FormulaList fl = ReferenceFinder.getInstance().getSubformulaParentReferences(
						proofTree, auxCandidate.getFormula(), mainCandidate);

				if (fl.size() >= 1) {
					CompositeFormula f = (CompositeFormula) fl.get(0);
					Connective conn = f.getConnective();

					Formula f_left = (Formula) f.getImmediateSubformulas().get(
							0);
					Formula f_right = null;
					if (f.getImmediateSubformulas().size() > 1) {
						f_right = (Formula) f.getImmediateSubformulas().get(1);
					}
					KERuleRole role = (auxCandidate.getFormula() == f_left) ? KERuleRole.LEFT
							: ((f_right != null) && (auxCandidate.getFormula() == f_right)) ? KERuleRole.RIGHT
									: null;
					FormulaSign sign = auxCandidate.getSign();

					TwoPremisesOneConclusionRule r1 = (TwoPremisesOneConclusionRule) twoPremiseRulesII
							.get(conn, role, sign);

					SignedFormulaList sflResult = r1.getPossibleConclusions(sfb
							.getSignedFormulaFactory(),
							sfb.getFormulaFactory(), sflToApply, f);
					if (sflResult != null) {
						SignedFormula conclusion = sflResult.get(0);

//						SignedFormulaNode n;
						proofTree.addLast(new SignedFormulaNode(conclusion,
								SignedFormulaNodeState.NOT_ANALYSED,
								createOrigin(r1, proofTree
										.getNode(mainCandidate), proofTree
										.getNode(auxCandidate))));

						decrementPositionMainCandidates();
						proofTree.removeFromPBCandidates(mainCandidate,
								SignedFormulaNodeState.ANALYSED);

						hasApplied = true;
					}

				}

			}
		}

		return hasApplied;
	}

}
