package logicalSystems.mbc;

import java.util.Iterator;

import logic.signedFormulas.SignedFormula;
import logic.signedFormulas.SignedFormulaBuilder;
import logic.signedFormulas.SignedFormulaCreator;
import logic.signedFormulas.SignedFormulaList;
import logicalSystems.classicalLogic.ClassicalConnectives;
import logicalSystems.classicalLogic.ClassicalSigns;
import main.newstrategy.mbc.simple.TwoPremiseRuleApplicator;
import main.proofTree.SignedFormulaNode;
import main.proofTree.SignedFormulaNodeState;
import main.proofTree.origin.NamedOrigin;
import main.strategy.ClassicalProofTree;
import main.strategy.simple.FormulaReferenceClassicalProofTree;
import main.tableau.Method;

import org.junit.Before;
import org.junit.Test;

import proverinterface.RuleStructureFactory;

public class MBCTwoPremiseRuleApplicatorTest {

	private SignedFormula tTopFormula;

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testTwoPremiseApplicator() {

		Method method = new Method(RuleStructureFactory
				.createRulesStructure("C1"));
		MBCMockSimpleStrategy mockStrategy = new MBCMockSimpleStrategy(method);
		TwoPremiseRuleApplicator x = new TwoPremiseRuleApplicator(mockStrategy,
				MBCRuleStructures.TWO_PREMISE_RULES);
		SignedFormulaCreator sfc = new SignedFormulaCreator("sats5");
		SignedFormulaList sfl = new SignedFormulaList();
		sfl.add(sfc.parseString("T A1"));
		sfl.add(sfc.parseString("T A1->B1"));
		sfl.add(sfc.parseString("F B2"));
		sfl.add(sfc.parseString("T A2->B2"));

		tTopFormula = sfc.getSignedFormulaFactory().createSignedFormula(
				ClassicalSigns.TRUE,
				sfc.getFormulaFactory().createCompositeFormula(
						ClassicalConnectives.TOP));

		ClassicalProofTree cpt = new FormulaReferenceClassicalProofTree(
				new SignedFormulaNode(tTopFormula,
						SignedFormulaNodeState.FULFILLED, NamedOrigin.DEFINITION));

		SignedFormulaBuilder sfb = new SignedFormulaBuilder(sfc
				.getSignedFormulaFactory(), sfc.getFormulaFactory());

		Iterator<SignedFormula> it = sfl.iterator();

		while (it.hasNext()) {
			cpt.addLast(new SignedFormulaNode(it.next(),
					SignedFormulaNodeState.NOT_ANALYSED, NamedOrigin.PROBLEM));
		}
		System.out.println(cpt);
		mockStrategy.setProofTree(cpt);
		mockStrategy.setCurrent(cpt);
		System.out.println(cpt.getPBCandidates());
		x.applyAll(cpt, sfb);

		System.out.println(cpt.getNumberOfNodes());
		// assertTrue(cpt.getNumberOfNodes()==14);

		System.out.println(cpt);

	}
}
