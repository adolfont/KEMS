package logicalSystems.c1;

import static org.junit.Assert.assertTrue;

import java.util.Iterator;

import logic.signedFormulas.SignedFormula;
import logic.signedFormulas.SignedFormulaBuilder;
import logic.signedFormulas.SignedFormulaCreator;
import logic.signedFormulas.SignedFormulaList;
import main.newstrategy.c1.simple.C1_T_NOT_ANY_PBRuleApplicator;
import main.newstrategy.cpl.configurable.comparator.InsertionOrderSignedFormulaComparator;
import main.proofTree.SignedFormulaNode;
import main.proofTree.SignedFormulaNodeState;
import main.proofTree.origin.NamedOrigin;
import main.strategy.ClassicalProofTree;
import main.strategy.applicator.PBRuleApplicator;
import main.strategy.simple.FormulaReferenceClassicalProofTree;
import main.tableau.Method;

import org.junit.Before;
import org.junit.Test;

import proverinterface.RuleStructureFactory;

public class C1_PBRuleApplicatorTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testTwoPremisePBApplicator() {

		Method method = new Method(RuleStructureFactory.createRulesStructure(RuleStructureFactory.C1));
		MockSimpleStrategy mockStrategy = new MockSimpleStrategy(method);
		PBRuleApplicator x = new PBRuleApplicator(mockStrategy, C1RuleStructures.PB_RULES);
		SignedFormulaCreator sfc = new SignedFormulaCreator("sats5");
		SignedFormulaList sfl = new SignedFormulaList();

		// sfl.add(sfc.parseString("T A1"));
		sfl.add(sfc.parseString("T A1->B1"));
		// sfl.add(sfc.parseString("F B2"));
		sfl.add(sfc.parseString("T A2->B2"));

		sfl.add(sfc.parseString("F A3&B3"));
		// sfl.add(sfc.parseString("T A3"));
		sfl.add(sfc.parseString("F A4&B4"));
		// sfl.add(sfc.parseString("T B4"));

		sfl.add(sfc.parseString("T A5|B5"));
		// sfl.add(sfc.parseString("F A5"));
		sfl.add(sfc.parseString("T A6|B6"));
		// sfl.add(sfc.parseString("F B6"));
		sfl.add(sfc.parseString("T XXX"));

		SignedFormulaBuilder sfb = new SignedFormulaBuilder(sfc.getSignedFormulaFactory(), sfc
				.getFormulaFactory());

		Iterator<SignedFormula> it = sfl.iterator();

		ClassicalProofTree cpt = new FormulaReferenceClassicalProofTree(new SignedFormulaNode(
				it.next(), SignedFormulaNodeState.NOT_ANALYSED, NamedOrigin.PROBLEM));

		while (it.hasNext()) {
			cpt.addLast(new SignedFormulaNode(it.next(), SignedFormulaNodeState.NOT_ANALYSED,
					NamedOrigin.PROBLEM));
		}
		// System.out.println(cpt);
		mockStrategy.setProofTree(cpt);
		mockStrategy.setCurrent(cpt);
		mockStrategy.setComparator(new InsertionOrderSignedFormulaComparator());
		// System.out.println("HERE: "+cpt.getPBCandidates());
		x.apply(cpt, sfb);
		// System.out.println(cpt);
		// System.out.println(cpt.getNumberOfNodes());
		assertTrue(cpt.getNumberOfNodes() == 10);

		cpt = (ClassicalProofTree) cpt.getLeft();
		x.apply(cpt, sfb);
		// System.out.println(cpt);
		// System.out.println(cpt.getNumberOfNodes());
		assertTrue(cpt.getNumberOfNodes() == 5);

		cpt = (ClassicalProofTree) cpt.getLeft();
		x.apply(cpt, sfb);
		// System.out.println(cpt);
		// System.out.println(cpt.getNumberOfNodes());
		assertTrue(cpt.getNumberOfNodes() == 5);

		cpt = (ClassicalProofTree) cpt.getLeft();
		x.apply(cpt, sfb);
		// System.out.println(cpt);
		// System.out.println(cpt.getNumberOfNodes());
		assertTrue(cpt.getNumberOfNodes() == 5);

		cpt = (ClassicalProofTree) cpt.getLeft();
		x.apply(cpt, sfb);
		// System.out.println(cpt);
		// System.out.println(cpt.getNumberOfNodes());
		assertTrue(cpt.getNumberOfNodes() == 5);

		cpt = (ClassicalProofTree) cpt.getLeft();
		x.apply(cpt, sfb);
		// System.out.println(cpt);
		// System.out.println(cpt.getNumberOfNodes());
		assertTrue(cpt.getNumberOfNodes() == 5);

		cpt = (ClassicalProofTree) cpt.getLeft();
		x.apply(cpt, sfb);
		// System.out.println(cpt);
		// System.out.println(cpt.getNumberOfNodes());
		assertTrue(cpt.getNumberOfNodes() == 2);
	}

	@Test
	public void testFalseThreePremisePBApplicator() {

		Method method = new Method(RuleStructureFactory.createRulesStructure("C1"));
		MockSimpleStrategy mockStrategy = new MockSimpleStrategy(method);
		C1_T_NOT_ANY_PBRuleApplicator pbApplctr = new C1_T_NOT_ANY_PBRuleApplicator(mockStrategy,
				C1RuleStructures.SPECIAL_PB_RULES);
		SignedFormulaCreator sfc = new SignedFormulaCreator("sats5");
		SignedFormulaList sfl = new SignedFormulaList();

		sfl.add(sfc.parseString("T !(A1->B1)"));
		sfl.add(sfc.parseString("T !(A2&B2)"));
		sfl.add(sfc.parseString("T !(A3|B3)"));

		SignedFormulaBuilder sfb = new SignedFormulaBuilder(sfc.getSignedFormulaFactory(), sfc
				.getFormulaFactory());

		Iterator<SignedFormula> it = sfl.iterator();

		ClassicalProofTree cpt = new FormulaReferenceClassicalProofTree(new SignedFormulaNode(
				it.next(), SignedFormulaNodeState.NOT_ANALYSED, NamedOrigin.PROBLEM));

		while (it.hasNext()) {
			cpt.addLast(new SignedFormulaNode(it.next(), SignedFormulaNodeState.NOT_ANALYSED,
					NamedOrigin.PROBLEM));
		}
		// System.out.println(cpt);
		mockStrategy.setProofTree(cpt);
		mockStrategy.setCurrent(cpt);
		mockStrategy.setComparator(new InsertionOrderSignedFormulaComparator());
		// System.out.println(cpt.getPBCandidates());
		pbApplctr.apply(cpt, sfb);
		// System.out.println(cpt);
		// System.out.println(cpt.getNumberOfNodes());
		assertTrue(cpt.getNumberOfNodes() == 6);

		cpt = (ClassicalProofTree) cpt.getLeft();
		// System.out.println(cpt);
		// System.out.println(cpt.getPBCandidates());
		pbApplctr.apply(cpt, sfb);
		// System.out.println(cpt);
		// System.out.println(cpt.getNumberOfNodes());
		assertTrue(cpt.getNumberOfNodes() == 5);

		cpt = (ClassicalProofTree) cpt.getLeft();
		// System.out.println(cpt);
		// System.out.println(cpt.getPBCandidates());
		pbApplctr.apply(cpt, sfb);
		// System.out.println(cpt);
		// System.out.println(cpt.getNumberOfNodes());
		assertTrue(cpt.getNumberOfNodes() == 5);

		cpt = (ClassicalProofTree) cpt.getLeft();
		// System.out.println(cpt);
		// System.out.println(cpt.getPBCandidates());
		pbApplctr.apply(cpt, sfb);
		// System.out.println(cpt);
		// System.out.println(cpt.getNumberOfNodes());
		assertTrue(cpt.getNumberOfNodes() == 2);

	}
}
