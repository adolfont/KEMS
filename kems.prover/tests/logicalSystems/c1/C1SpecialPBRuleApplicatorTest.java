package logicalSystems.c1;

import static org.junit.Assert.assertTrue;

import java.util.Iterator;

import logic.signedFormulas.SignedFormula;
import logic.signedFormulas.SignedFormulaBuilder;
import logic.signedFormulas.SignedFormulaCreator;
import logic.signedFormulas.SignedFormulaList;
import main.newstrategy.c1.simple.C1SpecialPBRuleApplicator;
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


/// DEPRECATED
public class C1SpecialPBRuleApplicatorTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testTwoPremiseApplicator() {

		Method method = new Method(RuleStructureFactory
				.createRulesStructure("C1"));
		MockSimpleStrategy mockStrategy = new MockSimpleStrategy(method);
		C1SpecialPBRuleApplicator x = new C1SpecialPBRuleApplicator(mockStrategy,
				C1RuleStructures.SPECIAL_PB_RULES);
		SignedFormulaCreator sfc = new SignedFormulaCreator("sats5");
		SignedFormulaList sfl = new SignedFormulaList();


		 sfl.add(sfc.parseString("F !( (A8&B8)&!(A8&B8))"));
		 sfl.add(sfc.parseString("F !( (A7|B7)&!(A7|B7))"));
		 sfl.add(sfc.parseString("F !( (A9->B9)&!(A9->B9))"));

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
		// TODO F! A should be declared ANALYSED after F!A --> T A 
		
		// System.out.println(cpt);
		mockStrategy.setProofTree(cpt);
		mockStrategy.setCurrent(cpt);
		mockStrategy.setComparator(new InsertionOrderSignedFormulaComparator());
		// System.out.println(cpt.getPBCandidates());
		x.apply(cpt, sfb);
//		System.out.println(cpt);
//		System.out.println(cpt.getNumberOfNodes());
		assertTrue(cpt.getNumberOfNodes() ==10);
		
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
		System.out.println(cpt);
		System.out.println(cpt.getNumberOfNodes());
		assertTrue(cpt.getNumberOfNodes() ==5);

		cpt = (ClassicalProofTree) cpt.getLeft();
		x.apply(cpt, sfb);
		System.out.println(cpt);
		System.out.println(cpt.getNumberOfNodes());
		assertTrue(cpt.getNumberOfNodes() ==5);

		// System.out.println(cpt.getNumberOfNodes());
		assertTrue(cpt.getNumberOfNodes() > 10);

	}
}
