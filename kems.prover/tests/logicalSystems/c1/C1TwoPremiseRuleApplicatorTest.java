package logicalSystems.c1;

import static org.junit.Assert.assertTrue;

import java.util.Iterator;

import logic.signedFormulas.SignedFormula;
import logic.signedFormulas.SignedFormulaBuilder;
import logic.signedFormulas.SignedFormulaCreator;
import logic.signedFormulas.SignedFormulaList;
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

public class C1TwoPremiseRuleApplicatorTest {


	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testTwoPremiseApplicator(){
		
		Method method = new Method(RuleStructureFactory
				.createRulesStructure("C1"));
		MockSimpleStrategy mockStrategy = new MockSimpleStrategy(method);
        TwoPremiseRuleApplicator x = new TwoPremiseRuleApplicator(mockStrategy,
                C1RuleStructures.TWO_PREMISE_RULES);
        SignedFormulaCreator sfc = new SignedFormulaCreator("sats5");
        SignedFormulaList sfl = new SignedFormulaList();
        
        sfl.add(sfc.parseString("T A1"));
        sfl.add(sfc.parseString("T A1->B1"));
        sfl.add(sfc.parseString("F B2"));
        sfl.add(sfc.parseString("T A2->B2"));
        
        sfl.add(sfc.parseString("F A3&B3"));
        sfl.add(sfc.parseString("T A3"));
        sfl.add(sfc.parseString("F A4&B4"));
        sfl.add(sfc.parseString("T B4"));
        
        sfl.add(sfc.parseString("T A5|B5"));
        sfl.add(sfc.parseString("F A5"));
        sfl.add(sfc.parseString("T A6|B6"));
        sfl.add(sfc.parseString("F B6"));

        sfl.add(sfc.parseString("T !(A7&!A7)"));
        sfl.add(sfc.parseString("T !A7"));

//        sfl.add(sfc.parseString("F !( (A8&B8)&!(A8&B8))"));
//        sfl.add(sfc.parseString("T !(A8&!A8)"));

        
      SignedFormulaBuilder sfb = new SignedFormulaBuilder(sfc.getSignedFormulaFactory(),sfc.getFormulaFactory());
      
      Iterator<SignedFormula> it = sfl.iterator();
     
      ClassicalProofTree cpt = new FormulaReferenceClassicalProofTree(new 
      		SignedFormulaNode(it.next(),
              SignedFormulaNodeState.NOT_ANALYSED, NamedOrigin.PROBLEM));
      
      while(it.hasNext()){
    	cpt.addLast(new SignedFormulaNode(it.next(),SignedFormulaNodeState.NOT_ANALYSED,NamedOrigin.PROBLEM));  
      }
//      System.out.println(cpt);
      mockStrategy.setProofTree(cpt);
      mockStrategy.setCurrent(cpt);
//      System.out.println(cpt.getPBCandidates());
      x.applyAll(cpt, sfb);
      
//      System.out.println(cpt.getNumberOfNodes());
      assertTrue(cpt.getNumberOfNodes()==21);
      
//      System.out.println(cpt);
		
	}
}
