package main.strategy.applicator;

import static org.junit.Assert.assertTrue;

import java.util.Iterator;

import logic.signedFormulas.SignedFormula;
import logic.signedFormulas.SignedFormulaBuilder;
import logic.signedFormulas.SignedFormulaCreator;
import logic.signedFormulas.SignedFormulaList;
import logicalSystems.c1.MockSimpleStrategy;
import logicalSystems.classicalLogic.ClassicalRuleStructures;
import main.newstrategy.cpl.configurable.comparator.InsertionOrderSignedFormulaComparator;
import main.proofTree.SignedFormulaNode;
import main.proofTree.SignedFormulaNodeState;
import main.proofTree.origin.NamedOrigin;
import main.strategy.ClassicalProofTree;
import main.strategy.simple.FormulaReferenceClassicalProofTree;
import main.tableau.Method;

import org.junit.Before;
import org.junit.Test;

import proverinterface.RuleStructureFactory;

public class PBRuleApplicatorTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testTwoPremisePBApplicator() {

		Method method = new Method(RuleStructureFactory
				.createRulesStructure(RuleStructureFactory.CPL_CONFIGURABLE));
		MockSimpleStrategy mockStrategy = new MockSimpleStrategy(method);
		PBRuleApplicator x = new PBRuleApplicator(mockStrategy,
				ClassicalRuleStructures.PB_RULE_LIST);
		SignedFormulaCreator sfc = new SignedFormulaCreator("sats5");
		SignedFormulaList sfl = new SignedFormulaList();

		sfl.add(sfc.parseString("T A1->B1"));
		sfl.add(sfc.parseString("T A2->B2"));

		sfl.add(sfc.parseString("F A3&B3"));
		sfl.add(sfc.parseString("F A4&B4"));

		sfl.add(sfc.parseString("T A5|B5"));
		sfl.add(sfc.parseString("T A6|B6"));


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
		// System.out.println(cpt);
		mockStrategy.setProofTree(cpt);
		mockStrategy.setCurrent(cpt);
		mockStrategy.setComparator(new InsertionOrderSignedFormulaComparator());
		
		//System.out.println(cpt.getPBCandidates());
		x.apply(cpt, sfb);
//		System.out.println(cpt);
	//	System.out.println(cpt.getNumberOfNodes());
		assertTrue(cpt.getNumberOfNodes() ==9);
		
		cpt = (ClassicalProofTree) cpt.getLeft();
		x.apply(cpt, sfb);
//		System.out.println(cpt);
//		System.out.println(cpt.getNumberOfNodes());
		assertTrue(cpt.getNumberOfNodes() ==5);

		cpt = (ClassicalProofTree) cpt.getLeft();
		x.apply(cpt, sfb);
//		System.out.println(cpt);
//		System.out.println(cpt.getNumberOfNodes());
		assertTrue(cpt.getNumberOfNodes() ==5);

		cpt = (ClassicalProofTree) cpt.getLeft();
		x.apply(cpt, sfb);
//		System.out.println(cpt);
//		System.out.println(cpt.getNumberOfNodes());
		assertTrue(cpt.getNumberOfNodes() ==5);

		cpt = (ClassicalProofTree) cpt.getLeft();
		x.apply(cpt, sfb);
//		System.out.println(cpt);
//		System.out.println(cpt.getNumberOfNodes());
		assertTrue(cpt.getNumberOfNodes() ==5);

		cpt = (ClassicalProofTree) cpt.getLeft();
		x.apply(cpt, sfb);
//		System.out.println(cpt);
//		System.out.println(cpt.getNumberOfNodes());
		assertTrue(cpt.getNumberOfNodes() ==5);

		cpt = (ClassicalProofTree) cpt.getLeft();
		x.apply(cpt, sfb);
//		System.out.println(cpt);
//		System.out.println(cpt.getNumberOfNodes());
		assertTrue(cpt.getNumberOfNodes() ==2);
	}
	
}
