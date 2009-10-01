package logicalSystems.c1;

import static org.junit.Assert.assertTrue;

import java.util.Iterator;

import logic.signedFormulas.SignedFormula;
import logic.signedFormulas.SignedFormulaBuilder;
import logic.signedFormulas.SignedFormulaCreator;
import logic.signedFormulas.SignedFormulaList;
import logicalSystems.classicalLogic.ClassicalConnectives;
import logicalSystems.classicalLogic.ClassicalSigns;
import main.newstrategy.c1.simple.C1ThreePremiseRuleApplicator;
import main.proofTree.SignedFormulaNode;
import main.proofTree.SignedFormulaNodeState;
import main.proofTree.origin.NamedOrigin;
import main.strategy.simple.FormulaReferenceClassicalProofTree;
import main.tableau.Method;

import org.junit.Before;
import org.junit.Test;

import proverinterface.RuleStructureFactory;

public class C1ThreePremiseRuleApplicatorTest {

	C1ThreePremiseRuleApplicator tpra;
	SignedFormulaCreator sfc;
	SignedFormulaList sfl;
	private SignedFormula tTopFormula;
	private FormulaReferenceClassicalProofTree cpt;
	private SignedFormulaBuilder sfb;
	private MockSimpleStrategy mockStrategy;

	@Before
	public final void setUp() {
		
		sfc = new SignedFormulaCreator("sats5");
		sfl = new SignedFormulaList();

		Method method = new Method(RuleStructureFactory
				.createRulesStructure("C1"));
		mockStrategy = new MockSimpleStrategy(method);
		
		tTopFormula = sfc.getSignedFormulaFactory().createSignedFormula(
				ClassicalSigns.TRUE,
				sfc.getFormulaFactory().createCompositeFormula(
						ClassicalConnectives.TOP));
		cpt = new FormulaReferenceClassicalProofTree(new SignedFormulaNode(
				tTopFormula, SignedFormulaNodeState.FULFILLED,
				NamedOrigin.DEFINITION));
		
		mockStrategy.setProofTree(cpt);
		mockStrategy.setCurrent(cpt);
		
		tpra = new C1ThreePremiseRuleApplicator(mockStrategy,
				C1RuleStructures.THREE_PREMISE_RULES);

		sfb = new SignedFormulaBuilder(sfc.getSignedFormulaFactory(), sfc
				.getFormulaFactory());
	}

	@Test
	public final void testThreePremiseRuleApplicator() {
		// test for T_NOT_AND_LEFT
		sfl.add(sfc.parseString("T!(A1&B1)"));
		sfl.add(sfc.parseString("T(A1&B1)"));
		sfl.add(sfc.parseString("T!(A1&!A1)"));

		// test for T_NOT_AND_RIGHT
		sfl.add(sfc.parseString("T!(A2&B2)"));
		sfl.add(sfc.parseString("T(A2&B2)"));
		sfl.add(sfc.parseString("T!(B2&!B2)"));

		// test for T_NOT_OR_LEFT
		sfl.add(sfc.parseString("T!(A3|B3)"));
		sfl.add(sfc.parseString("T(A3|B3)"));
		sfl.add(sfc.parseString("T!(A3&!A3)"));

		// test for T_NOT_OR_RIGHT
		sfl.add(sfc.parseString("T!(A4|B4)"));
		sfl.add(sfc.parseString("T(A4|B4)"));
		sfl.add(sfc.parseString("T!(B4&!B4)"));

		// test for T_NOT_IMPLIES_LEFT
		sfl.add(sfc.parseString("T!(A5->B5)"));
		sfl.add(sfc.parseString("T(A5->B5)"));
		sfl.add(sfc.parseString("T!(A5&!A5)"));

		// test for T_NOT_IMPLIES_RIGHT
		sfl.add(sfc.parseString("T!(A6->B6)"));
		sfl.add(sfc.parseString("T(A6->B6)"));
		sfl.add(sfc.parseString("T!(B6&!B6)"));

		Iterator<SignedFormula> it = sfl.iterator();

		while (it.hasNext()) {
			SignedFormula sf=it.next();
			cpt.addLast(new SignedFormulaNode(sf,
					SignedFormulaNodeState.NOT_ANALYSED, NamedOrigin.PROBLEM));
		}


		tpra.applyAll(cpt, sfb);
		
//		System.out.println("Final cpt:"+cpt);
//		System.out.println("Final cpt size:"+cpt.getNumberOfNodes());
		assertTrue(cpt.getNumberOfNodes()==25);
	}
}
