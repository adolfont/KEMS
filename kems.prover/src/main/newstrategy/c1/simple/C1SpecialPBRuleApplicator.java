/*
 * Created on 28/10/2005
 *
 */
package main.newstrategy.c1.simple;

import logic.formulas.CompositeFormula;
import logic.signedFormulas.PBCandidateList;
import logic.signedFormulas.SignedFormula;
import logic.signedFormulas.SignedFormulaBuilder;
import logic.signedFormulas.SignedFormulaList;
import logicalSystems.classicalLogic.ClassicalRules;
import main.ag.strategy.util.AGConfiguration;
import main.newstrategy.CPLPBRuleChooser;
import main.newstrategy.IPBRuleChooser;
import main.newstrategy.ISimpleStrategy;
import main.proofTree.SignedFormulaNode;
import main.proofTree.SignedFormulaNodeState;
import main.strategy.ClassicalProofTree;
import main.strategy.applicator.IProofTransformation;
import rules.NullRule;
import rules.Rule;
import rules.TwoPremisesOneConclusionRule;
import rules.structures.PBRuleList;


// DEPRECATED AND NOT COMPLETED
/**
 * Applies PB with C1's F_CONS_ANY rules 
 * 
 * @author Adolfo Gustavo Serra Seca Neto
 * 
 */
public class C1SpecialPBRuleApplicator implements IProofTransformation {

	private ISimpleStrategy strategy;

	private String ruleListName;

	private IPBRuleChooser pbRuleChooser;

	/**
	 * @param strategy
	 * @param sfb
	 */
	public C1SpecialPBRuleApplicator(ISimpleStrategy strategy, String ruleListName) {
		super();
		this.strategy = strategy;
		this.ruleListName = ruleListName;
		this.pbRuleChooser = new CPLPBRuleChooser();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see main.strategy.applicator.IProofTransformation#apply(main.strategy.ClassicalProofTree,
	 *      logic.signedFormulas.SignedFormulaBuilder)
	 */
	public boolean apply(ClassicalProofTree current, SignedFormulaBuilder sfb) {
		boolean hasApplied = false;

		PBCandidateList candidates = current.getPBCandidates();
		
		// TODO SORT ASPECT
//		System.out.println("PB SORT");
		//EMERSON: Temporário Algoritmo Genético
		if (strategy.getAbordagensAG() == AGConfiguration.Abordagens.NotApplyAG) {
			candidates.sort(strategy.getComparator());
		}

		if (candidates.size() > 0) {
			return applyPBOnce(current, sfb, candidates);
		}

		return hasApplied;
	}

	protected boolean applyPBOnce(ClassicalProofTree current,
			SignedFormulaBuilder sfb, PBCandidateList candidates) {
		PBRuleList PBRules = (PBRuleList) strategy.getMethod().getRules().get(
				ruleListName);
		boolean foundRule = false;
		SignedFormula candidateChosen = null;
		Rule ruleChosen = NullRule.INSTANCE;

		SignedFormula aux = null;

		// O QUE FAZER QUANDO A POSSIVEL CONCLUSAO JA ESTA NA CURRENT PT NODE???
		TwoPremisesOneConclusionRule r = null;

		// SignedFormula aux = r.getAuxiliaryCandidates(
		// sfb.getSignedFormulaFactory(), sfb.getFormulaFactory(),
		// candidateChosen).get(0);
		// System.err.println("cand "+aux);

		SignedFormula auxOpposite = null, conclusion = null;

		int candidateIndex = 0;
		// looks for a rule that can be applied
		while (candidateIndex < candidates.size()) {
			candidateChosen = candidates.get(candidateIndex);
//			 System.err.println(candidateChosen);
			candidateIndex++;
//			System.err.println("rule chosen:"+ruleChosen.toString());
			ruleChosen = choosePBRule(PBRules, candidateChosen);

			if (ruleChosen != NullRule.INSTANCE) {

				aux = ((TwoPremisesOneConclusionRule) ruleChosen)
						.getAuxiliaryCandidates(sfb.getSignedFormulaFactory(),
								sfb.getFormulaFactory(), candidateChosen)
						.get(0);
				if ((strategy.getCurrent().contains(aux) || strategy
						.getCurrent().contains(
								sfb.createOppositeSignedFormula(aux)))) {
					continue;
				}

				if (pbRuleChooser.canApply(ruleChosen, candidateChosen, sfb)) {

					r = (TwoPremisesOneConclusionRule) ruleChosen;

					// SignedFormula aux = r.getAuxiliaryCandidates(
					// sfb.getSignedFormulaFactory(), sfb.getFormulaFactory(),
					// candidateChosen).get(0);
					// System.err.println("cand "+aux);

					auxOpposite = sfb.createOppositeSignedFormula(aux);

					// System.err.println("cand opp "+auxOpposite);
					// Verifica se conclusï¿½o jï¿½ estï¿½ na ProofTree - se jï¿½
					// estiver, nï¿½o aplica PB
					SignedFormulaList sfl = new SignedFormulaList(
							candidateChosen);
					sfl.add(aux);
					conclusion = r.getPossibleConclusions(
							sfb.getSignedFormulaFactory(),
							sfb.getFormulaFactory(), sfl).get(0);
					// System.err.println("conc " + conclusion + " to " + sfl);
					// System.err.println((strategy.getCurrent().getNode(conclusion)==null)?"can
					// apply":"cannot apply");
					// System.err.println(strategy.getCurrent().getPBCandidates());
					if (strategy.getCurrent().getNode(conclusion) == null) {
						foundRule = true;
						break;
					}
				} else {
					// strategy.getCurrent().removeFromPBCandidates(
					// candidateChosen);
				}
			}

		}

		if (!foundRule || ruleChosen == NullRule.INSTANCE) {
			return false;
			// throw new TPException("No PB rule found for "
			// + candidateChosen.toString() + ".");
		}

		// versï¿½o antes de verificar se estava na proof tree
		// TwoPremisesOneConclusionRule r = (TwoPremisesOneConclusionRule)
		// ruleChosen;
		//
		// // SignedFormula aux = r.getAuxiliaryCandidates(
		// // sfb.getSignedFormulaFactory(), sfb.getFormulaFactory(),
		// // candidateChosen).get(0);
		// // System.err.println("cand "+aux);
		//
		// SignedFormula auxOpposite = sfb.createOppositeSignedFormula(aux);
		//
		// // System.err.println("cand opp "+auxOpposite);
		// // TODO verificar se conclusï¿½o jï¿½ estï¿½ na ProofTree
		// SignedFormulaList sfl = new SignedFormulaList(candidateChosen);
		// sfl.add(aux);
		// SignedFormula conclusion = r.getPossibleConclusions(
		// sfb.getSignedFormulaFactory(), sfb.getFormulaFactory(), sfl)
		// .get(0);
		// System.err.println("conc "+conclusion + " to "+sfl);
		// System.err.println(strategy.getCurrent().getNode(conclusion));
		// if (strategy.getCurrent().getNode(conclusion)!=null){
		//		    
		// }
		// System.err.println ("Removeu de PB candidates: "+ candidateChosen);
		// System.err.println("current PB: "+ getCurrent().getPBCandidates());

		ClassicalProofTree right = (ClassicalProofTree) current

		.addRight(new SignedFormulaNode(auxOpposite,
				SignedFormulaNodeState.NOT_ANALYSED, strategy
				// TODO informar a regra (r) que motivou o uso de PB
						.createOrigin(ClassicalRules.PB, current
								.getNode(candidateChosen), null)));

		Rule complementary = PBRules.getComplementary(r);
		// System.err.println("complementary rule: " + complementary);
		// System.err.println("current PB: "+ getCurrent().getPBCandidates());

		if (complementary != null) {
			SignedFormulaList sflComplementary = new SignedFormulaList(
					candidateChosen);
			sflComplementary.add(auxOpposite);
			SignedFormula complementaryConclusion = complementary
					.getPossibleConclusions(sfb.getSignedFormulaFactory(),
							sfb.getFormulaFactory(), sflComplementary).get(0);

			right.addLast(new SignedFormulaNode(complementaryConclusion,
					SignedFormulaNodeState.NOT_ANALYSED, strategy.createOrigin(
							complementary, current.getNode(candidateChosen),
							right.getNode(auxOpposite))));
		}

		// System.err.println("current PB: "+ getCurrent().getPBCandidates());
		ClassicalProofTree left = (ClassicalProofTree) current
				.addLeft(new SignedFormulaNode(aux,
						SignedFormulaNodeState.NOT_ANALYSED, strategy
								.createOrigin(ClassicalRules.PB, current
										.getNode(candidateChosen), null)));
		// System.err.println("Adds to left: "+ aux);
		// System.err.println("left PB: "+ left.getPBCandidates());
		left.addLast(new SignedFormulaNode(conclusion,
				SignedFormulaNodeState.NOT_ANALYSED, strategy.createOrigin(r,
						current.getNode(candidateChosen), left.getNode(aux))));
		// System.err.println("Adds to left: "+ conclusion);
		// System.err.println("left PB: "+ left.getPBCandidates());
		strategy.getOpenBranches().addFirst(right);

		left.removeFromPBCandidates(candidateChosen,
				SignedFormulaNodeState.ANALYSED);
		right.removeFromPBCandidates(candidateChosen,
				SignedFormulaNodeState.ANALYSED);

		strategy.setCurrent(left);

		return true;
	}

	private Rule choosePBRule(PBRuleList PBRules, SignedFormula candidateChosen) {
		return PBRules.get(candidateChosen.getSign(),
				((CompositeFormula) candidateChosen.getFormula())
						.getConnective());
	}

	public void setPbRuleChooser(IPBRuleChooser pbRuleChooser) {
		this.pbRuleChooser = pbRuleChooser;
	}
}
