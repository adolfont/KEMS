/*
 * Created on 01/11/2005
 *
 */
package main.newstrategy.cpl.simple.configurable;

import logic.formulas.CompositeFormula;
import logic.formulas.Connective;
import logic.signedFormulas.FormulaSign;
import logic.signedFormulas.PBCandidateList;
import logic.signedFormulas.SignedFormula;
import logic.signedFormulas.SignedFormulaBuilder;
import logic.signedFormulas.SignedFormulaList;
import logicalSystems.classicalLogic.ClassicalSigns;
import main.newstrategy.ISimpleStrategy;
import main.newstrategy.cpl.configurable.comparator.ISignedFormulaComparator;
import main.newstrategy.simple.ag.util.AGConfiguration;
import main.proofTree.SignedFormulaNode;
import main.proofTree.SignedFormulaNodeState;
import main.proofTree.iterator.IProofTreeVeryBasicIterator;
import main.strategy.ClassicalProofTree;
import main.strategy.applicator.IRuleApplicator;
import rules.KERuleRole;
import rules.Rule;
import rules.TwoPremisesOneConclusionRule;
import rules.structures.ConnectiveRoleSignRuleList;

/**
 * A rule applicator that applies simple two-premise rules.
 * 
 * @author Adolfo Gustavo Serra Seca Neto
 * 
 */
public class ConfigurableTwoPremiseRuleApplicator implements IRuleApplicator {

	private ISimpleStrategy strategy;

	private String ruleListName;

	private String secondRuleListName;

	private PBCandidateList mainCandidates;

	private int counterMainCandidates;

	private ISignedFormulaComparator signedFormulaComparator;

	/**
	 * @param strategy
	 * @param sfb
	 */
	public ConfigurableTwoPremiseRuleApplicator(ISimpleStrategy strategy,
			String ruleListName, String secondRuleListName) {
		super();
		this.strategy = strategy;
		this.ruleListName = ruleListName;
		this.secondRuleListName = secondRuleListName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see main.strategy.applicator.IRuleApplicator#applyAll(main.strategy.ClassicalProofTree,
	 *      logic.signedFormulas.SignedFormulaBuilder)
	 */
	public boolean applyAll(ClassicalProofTree current, SignedFormulaBuilder sfb) {
		// faz o seguinte:
		// para cada main, procurar referências a um dos dois possíveis
		// auxiliary candidates
		// se encontrar, então aplicar
		boolean hasApplied = false;

		ConnectiveRoleSignRuleList twoPremiseRules = (ConnectiveRoleSignRuleList) strategy
				.getMethod().getRules().get(ruleListName);

		ConfigurableConnectiveRoleSignRuleList secondTwoPremiseRules = null;
		if (secondRuleListName != null) {
			secondTwoPremiseRules = (ConfigurableConnectiveRoleSignRuleList) strategy
					.getMethod().getRules().get(secondRuleListName);
		}

		SignedFormula mainCandidate;

		// TODO É ISSO MESMO?
		initializeMainCandidates(current, null);

		while ((mainCandidate = nextMainCandidate(strategy.getProofTree(), null)) != null) {

			Connective mainConnective = ((CompositeFormula) mainCandidate
					.getFormula()).getConnective();
			FormulaSign mainSign = mainCandidate.getSign();

			// only for mbc
			// if (mainConnective == MBCConnectives.CONSISTENCY) {
			// continue;
			// }
			// System.err.println(mainCandidate+ " "+mainConnective+ " "+
			// mainSign+ " "+twoPremiseRules.getRules());
			// System.err.println(mainCandidate+ " "+mainConnective+ " "+
			// mainSign+ " "+secondTwoPremiseRules.getRules());

			Rule left_rule = twoPremiseRules.get(mainConnective,
					KERuleRole.LEFT, mainSign);
			Rule right_rule = twoPremiseRules.get(mainConnective,
					KERuleRole.RIGHT, mainSign);

			// SignedFormulaList sfl = new SignedFormulaList();
			// verifies if left rule can be applied. If it can apply it.
			if (left_rule != null) {
				hasApplied = hasApplied
						|| tryToApplyTwoPremiseRule(strategy.getCurrent(), sfb,
								mainCandidate, left_rule);
			}
			// verifies if right rule can be applied. If it can apply it.
			if (right_rule != null) {
				hasApplied = hasApplied
						|| tryToApplyTwoPremiseRule(strategy.getCurrent(), sfb,
								mainCandidate, right_rule);
			}

			if (secondTwoPremiseRules != null && left_rule == null
					&& right_rule == null) {
				Rule left_rule_T = secondTwoPremiseRules.get(mainConnective,
						KERuleRole.LEFT, mainSign, ClassicalSigns.TRUE);
				Rule left_rule_F = secondTwoPremiseRules.get(mainConnective,
						KERuleRole.LEFT, mainSign, ClassicalSigns.FALSE);
				Rule right_rule_T = secondTwoPremiseRules.get(mainConnective,
						KERuleRole.RIGHT, mainSign, ClassicalSigns.TRUE);
				Rule right_rule_F = secondTwoPremiseRules.get(mainConnective,
						KERuleRole.RIGHT, mainSign, ClassicalSigns.FALSE);

				if (left_rule_T != null) {
					hasApplied = hasApplied
							|| tryToApplyTwoPremiseRule(strategy.getCurrent(),
									sfb, mainCandidate, left_rule_T);
				}
				if (left_rule_F != null) {
					hasApplied = hasApplied
							|| tryToApplyTwoPremiseRule(strategy.getCurrent(),
									sfb, mainCandidate, left_rule_F);
				}
				if (right_rule_T != null) {
					hasApplied = hasApplied
							|| tryToApplyTwoPremiseRule(strategy.getCurrent(),
									sfb, mainCandidate, right_rule_T);
				}
				if (right_rule_F != null) {
					hasApplied = hasApplied
							|| tryToApplyTwoPremiseRule(strategy.getCurrent(),
									sfb, mainCandidate, right_rule_F);
				}

				// System.err.println(left_rule_T);
				// System.err.println(left_rule_F);
				// System.err.println(right_rule_T);
				// System.err.println(right_rule_F);
			}

		}

		return hasApplied;
	}

	/**
	 * @param current
	 * @param object
	 */
	private void initializeMainCandidates(ClassicalProofTree proofTree,
			Object object) {

		// TODO SORT ASPECT
		// sorts PB candidates before aplying linear rules
		//EMERSON: Temporário Algoritmo Genético
		if (strategy.getAbordagensAG() == AGConfiguration.Abordagens.NotApplyAG) {
			proofTree.getPBCandidates().sort(signedFormulaComparator);
		}

		mainCandidates = proofTree.getPBCandidates();

		counterMainCandidates = 0;
	}

	protected SignedFormula nextMainCandidate(ClassicalProofTree proofTree,
			SignedFormula auxCandidate) {
		if (counterMainCandidates < mainCandidates.size()) {
			return mainCandidates.get(counterMainCandidates++);
		}

		return null;
	}

	// comentado em 22-06-2006
	// /**
	// * applies a two premise rule.
	// *
	// * @param proofTree
	// * @param sfb
	// * @param mainCandidate
	// * @param auxCandidate
	// * @param rule
	// */
	// private void applyTwoPremiseRule(ClassicalProofTree proofTree,
	// SignedFormulaBuilder sfb, SignedFormula mainCandidate,
	// SignedFormula auxCandidate, Rule rule) {
	//
	// TwoPremisesOneConclusionRule aRule = ((TwoPremisesOneConclusionRule)
	// rule);
	//
	// SignedFormulaList sfl = aRule.getAuxiliaryCandidates(sfb
	// .getSignedFormulaFactory(), sfb.getFormulaFactory(),
	// mainCandidate);
	//
	// System.err.println(aRule + " " + mainCandidate + " " + sfl);
	//
	// // TODO apenas considera uma conclusão
	// if (sfl.get(0) == auxCandidate) {
	// applyTwoPremiseRule(proofTree, sfb, mainCandidate, aRule, sfl,
	// auxCandidate);
	// }
	// // else{
	// // if (sfl.size()>1 && sfl.get(1) == auxCandidate){
	// // applyTwoPremiseRule(proofTree, sfb, mainCandidate, aRule, new
	// // SignedFormulaList(sfl.get(1)),
	// // auxCandidate);
	// // }
	// // }
	//
	// }

	/**
	 * @param proofTree
	 * @param sfb
	 * @param mainCandidate
	 * @param rule
	 * @return
	 */
	private boolean tryToApplyTwoPremiseRule(ClassicalProofTree proofTree,
			SignedFormulaBuilder sfb, SignedFormula mainCandidate, Rule rule) {
		boolean hasApplied = false;
		TwoPremisesOneConclusionRule aRule = ((TwoPremisesOneConclusionRule) rule);
		SignedFormulaList sfl = aRule.getAuxiliaryCandidates(sfb
				.getSignedFormulaFactory(), sfb.getFormulaFactory(),
				mainCandidate);
		// System.err.println(aRule+" " +sfl+ " " +mainCandidate);
		if (sfl != null) {
			SignedFormulaList result = getReferences(proofTree, sfl);
			// System.err.println("Result "+result);
			if (result.size() > 0) {
				SignedFormula auxCandidate = (SignedFormula) result.get(0);
				hasApplied = applyTwoPremiseRule(proofTree, sfb, mainCandidate,
						aRule, result, auxCandidate);
			}
		}
		return hasApplied;
	}

	/**
	 * Applies a two premise rulem given:
	 * 
	 * @param proofTree
	 * @param sfb
	 * @param mainCandidate
	 * @param rule
	 * @param aRule
	 * @param sfl
	 * @param auxCandidate
	 */
	private boolean applyTwoPremiseRule(ClassicalProofTree proofTree,
			SignedFormulaBuilder sfb, SignedFormula mainCandidate,
			TwoPremisesOneConclusionRule aRule, SignedFormulaList sfl,
			SignedFormula auxCandidate) {
		boolean hasApplied = false;
		sfl.add(0, mainCandidate);
		SignedFormulaList conclusion = (aRule.getPossibleConclusions(sfb
				.getSignedFormulaFactory(), sfb.getFormulaFactory(), sfl));

		// TODO supõe apenas uma conclusão
		if (proofTree.getNode(conclusion.get(0)) == null) {
			proofTree.addLast(new SignedFormulaNode((SignedFormula) conclusion
					.get(0), SignedFormulaNodeState.NOT_ANALYSED, strategy
					.createOrigin(aRule, proofTree.getNode(mainCandidate),
							proofTree.getNode(auxCandidate))));
			// System.err.println(proofTree.getBranchId()+ " " +
			// conclusion.get(0));
			// try {
			// System.in.read();
			// } catch (IOException e) {
			// e.printStackTrace();
			// }
			// removes main from the list of PB candidates
			strategy.getCurrent().removeFromPBCandidates(mainCandidate,
					SignedFormulaNodeState.ANALYSED);
			
			// TODO SORT aspect
			// sorts PB candidates after applying a two premise rule
			
			//EMERSON: Temporário Algoritmo Genético
			if (strategy.getAbordagensAG() == AGConfiguration.Abordagens.NotApplyAG) {
				strategy.getCurrent().getPBCandidates().sort(
						signedFormulaComparator);
			}

			hasApplied = true;
		}

		return hasApplied;
	}

	protected SignedFormulaList getReferences(ClassicalProofTree proofTree,
			SignedFormulaList sflInput) {

		// TODO do as in simple strategy? keep it in memory?
		// TODO do it top-down?

		SignedFormulaList sflResult = new SignedFormulaList();

		// iterates (bottom up) over the branch
		IProofTreeVeryBasicIterator it = proofTree.getTopDownIterator();

		while (it.hasNext()) {

			SignedFormulaNode sfn = (SignedFormulaNode) it.next();

			if (sflInput.contains((SignedFormula) sfn.getContent())) {
				sflResult.add((SignedFormula) sfn.getContent());
			}

		}
		return sflResult;
	}

	/**
	 * @param signedFormulaComparator
	 */
	public void setComparator(ISignedFormulaComparator signedFormulaComparator) {
		this.signedFormulaComparator = signedFormulaComparator;
	}

}
