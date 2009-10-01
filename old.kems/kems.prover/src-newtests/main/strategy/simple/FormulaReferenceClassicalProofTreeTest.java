package main.strategy.simple;

import junit.framework.TestCase;
import logic.signedFormulas.SignedFormula;
import logic.signedFormulas.SignedFormulaCreator;
import main.proofTree.SignedFormulaNode;
import main.proofTree.SignedFormulaNodeState;
import main.proofTree.origin.Origin;

public class FormulaReferenceClassicalProofTreeTest extends TestCase {
	public void ExTestCreation() throws Exception {
		
		SignedFormulaCreator sfc = new SignedFormulaCreator("sats5");
		SignedFormula sf1 = sfc.parseString("T A->B");
		SignedFormulaNode sfn1 = new SignedFormulaNode(sf1, SignedFormulaNodeState.NOT_ANALYSED, Origin.PROBLEM);
		SignedFormula sf2 = sfc.parseString("T B");
		SignedFormulaNode sfn2 = new SignedFormulaNode(sf2, SignedFormulaNodeState.NOT_ANALYSED, Origin.PROBLEM);
		SignedFormula sf3 = sfc.parseString("T C->D");
		SignedFormulaNode sfn3 = new SignedFormulaNode(sf3, SignedFormulaNodeState.NOT_ANALYSED, Origin.PROBLEM);
		SignedFormula sf4 = sfc.parseString("F C->D");
		SignedFormulaNode sfn4 = new SignedFormulaNode(sf4, SignedFormulaNodeState.NOT_ANALYSED, Origin.PROBLEM);
		FormulaReferenceClassicalProofTree pt = new FormulaReferenceClassicalProofTree(sfn1);
		pt.addLast(sfn2);
		pt.addLast(sfn3);
		pt.addLast(sfn4);
//		pt.removeSignedFormula(sf1);
//		pt.removeSignedFormula(sf2);
		pt.removeSignedFormula(sf3);
		pt.removeSignedFormula(sf4);
		
		System.err.println(pt.getFsmm());
		System.err.println(pt.getPBCandidates());
		System.err.println(pt.toStringShort());
		System.err.println(pt.getNodeSequence());
		System.err.println(pt.getNode(sf3));
		System.err.println(pt.getNode(sf4));
		
		System.err.println(pt.getAllLocalReferences());
		System.err.println(pt.getAllSubformulaLocalReferences());
		
	}
	
	public void testCreation() throws Exception {
		
		SignedFormulaCreator sfc = new SignedFormulaCreator("sats5");
		SignedFormula sf1 = sfc.parseString("T A->(A&C)->B");
		SignedFormulaNode sfn1 = new SignedFormulaNode(sf1, SignedFormulaNodeState.NOT_ANALYSED, Origin.PROBLEM);
		SignedFormula sf2 = sfc.parseString("T B");
		SignedFormulaNode sfn2 = new SignedFormulaNode(sf2, SignedFormulaNodeState.NOT_ANALYSED, Origin.PROBLEM);
		SignedFormula sf3 = sfc.parseString("T C->(D|E)");
		SignedFormulaNode sfn3 = new SignedFormulaNode(sf3, SignedFormulaNodeState.NOT_ANALYSED, Origin.PROBLEM);
		SignedFormula sf4 = sfc.parseString("F (C|E)->D");
		SignedFormulaNode sfn4 = new SignedFormulaNode(sf4, SignedFormulaNodeState.NOT_ANALYSED, Origin.PROBLEM);
		FormulaReferenceClassicalProofTree pt = new FormulaReferenceClassicalProofTree(sfn1);
		pt.addLast(sfn2);
		pt.addLast(sfn3);
		pt.addLast(sfn4);
//		pt.removeSignedFormula(sf1);
//		pt.removeSignedFormula(sf2);
//		pt.removeSignedFormula(sf3);
//		pt.removeSignedFormula(sf4);
		
		System.err.println(pt.getFsmm());
		System.err.println(pt.getPBCandidates());
		System.err.println(pt.toStringShort());
		System.err.println(pt.getNodeSequence());
		System.err.println(pt.getNode(sf3));
		System.err.println(pt.getNode(sf4));
		
		System.err.println(pt.getAllLocalReferences());
		System.err.println(pt.getAllSubformulaLocalReferences());
		
	}


}
