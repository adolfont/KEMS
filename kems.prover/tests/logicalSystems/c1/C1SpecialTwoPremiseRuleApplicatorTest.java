package logicalSystems.c1;

import static org.junit.Assert.assertTrue;

import java.util.Iterator;

import logic.signedFormulas.SignedFormula;
import logic.signedFormulas.SignedFormulaBuilder;
import logic.signedFormulas.SignedFormulaCreator;
import logic.signedFormulas.SignedFormulaList;
import main.newstrategy.c1.simple.C1SpecialTwoPremiseRuleApplicator;
import main.proofTree.SignedFormulaNode;
import main.proofTree.SignedFormulaNodeState;
import main.proofTree.origin.NamedOrigin;
import main.strategy.ClassicalProofTree;
import main.strategy.simple.FormulaReferenceClassicalProofTree;
import main.tableau.Method;

import org.junit.Before;
import org.junit.Test;

import proverinterface.RuleStructureFactory;


/// DEPRECATED
public class C1SpecialTwoPremiseRuleApplicatorTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testTwoPremiseApplicator() {

		Method method = new Method(RuleStructureFactory
				.createRulesStructure("C1"));
		MockSimpleStrategy mockStrategy = new MockSimpleStrategy(method);
		C1SpecialTwoPremiseRuleApplicator x = new C1SpecialTwoPremiseRuleApplicator(
				mockStrategy, C1RuleStructures.SPECIAL_TWO_PREMISE_RULES);
		SignedFormulaCreator sfc = new SignedFormulaCreator("sats5");
		SignedFormulaList sfl = new SignedFormulaList();

		sfl.add(sfc.parseString("F !( (A8&B8)&!(A8&B8))"));
		sfl.add(sfc.parseString("T !(A8&!A8)"));
		sfl.add(sfc.parseString("F !( (A9&B9)&!(A9&B9))"));
		sfl.add(sfc.parseString("T !(B9&!B9)"));
		sfl.add(sfc.parseString("F !( (A18|B18)&!(A18|B18))"));
		sfl.add(sfc.parseString("T !(A18&!A18)"));
		sfl.add(sfc.parseString("F !( (A19|B19)&!(A19|B19))"));
		sfl.add(sfc.parseString("T !(B19&!B19)"));
		sfl.add(sfc.parseString("F !( (A118->B118)&!(A118->B118))"));
		sfl.add(sfc.parseString("T !(A118&!A118)"));
		sfl.add(sfc.parseString("F !( (A119->B119)&!(A119->B119))"));
		sfl.add(sfc.parseString("T !(B119&!B119)"));

		SignedFormulaBuilder sfb = new SignedFormulaBuilder(sfc
				.getSignedFormulaFactory(), sfc.getFormulaFactory());

		Iterator<SignedFormula> it = sfl.iterator();

		ClassicalProofTree cpt = new FormulaReferenceClassicalProofTree(
				new SignedFormulaNode(it.next(),
						SignedFormulaNodeState.NOT_ANALYSED, NamedOrigin.PROBLEM));

		while (it.hasNext()) {
			cpt.addLast(new SignedFormulaNode(it.next(),
					SignedFormulaNodeState.NOT_ANALYSED, NamedOrigin.PROBLEM));
		}
//		System.out.println(cpt);
		mockStrategy.setProofTree(cpt);
		mockStrategy.setCurrent(cpt);
//		System.out.println(cpt.getPBCandidates());
		x.applyAll(cpt, sfb);

//		System.out.println(cpt.getNumberOfNodes());
		assertTrue(cpt.getNumberOfNodes() == 18);

//		System.out.println(cpt);

	}
}
