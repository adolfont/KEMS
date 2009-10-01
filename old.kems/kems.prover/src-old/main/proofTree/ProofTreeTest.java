/*
 * Created on 24/09/2004
 *
 */
package proofTree;

import main.proofTree.origin.Origin;
import junit.framework.TestCase;
import proofTree.iterator.ProofTreeAdvancedIterator;
import proofTree.iterator.ProofTreeBasicIterator;
import signedFormulasNew.SignedFormula;
import signedFormulasNew.SignedFormulaCreator;
import signedFormulasNew.SignedFormulaList;
import strategy.simple.FormulaReferenceClassicalProofTree;
import formulasNew.Formula;
import formulasNew.FormulaList;

/**
 * @author Adolfo Gustavo Serra Seca Neto
 *  
 */
public class ProofTreeTest extends TestCase {

	public void testCreation() {

		ProofTree pt, left, right;
		Node n;
		pt = new ProofTree(n = new StringNode("Predicado"));
		assertEquals(n.getBranch(), pt);

		pt.addLast(new StringNode("estavam"));
		pt.addLast(new StringNode("indo"));

		left = pt.addLeft(n = new StringNode("Sujeito"));
		assertEquals(n.getBranch(), left);
		left.addLast(n = new StringNode("Os"));
		assertEquals(n.getBranch(), left);
		left.addLast(n = new StringNode("garotos"));
		assertEquals(n.getBranch(), left);

		right = pt.addRight(n = new StringNode("Objeto indireto"));
		assertEquals(n.getBranch(), right);
		right.addLast(n = new StringNode("para"));
		assertEquals(n.getBranch(), right);
		right.addLast(n = new StringNode("casa"));
		assertEquals(n.getBranch(), right);

		testeLocalIterator(pt);

		IProofTreeAdvancedIterator pt_git = pt.getGlobalIterator();

		testeGlobalLeft(pt_git);

		pt_git = testeGlobalRight(pt);

		testeGlobalPrevious(pt_git);

		//        System.out.println("GLOBAL PARA CIMA");
		//        System.out.println (pt_git.current());
		//        while(pt_git.hasPrevious()){
		//            pt_git.previous();
		//            System.out.println (pt_git.current());
		//        }
	}

	/**
	 * @param pt_git
	 */
	private void testeGlobalPrevious(IProofTreeAdvancedIterator pt_git) {
		assertTrue(pt_git.hasPrevious());
		assertEquals(pt_git.current().getContent(), "casa");
		assertEquals(pt_git.previous().getContent(), "para");
		assertEquals(pt_git.previous().getContent(), "Objeto indireto");
		assertEquals(pt_git.previous().getContent(), "indo");
		assertEquals(pt_git.previous().getContent(), "estavam");
		assertEquals(pt_git.previous().getContent(), "Predicado");
		assertFalse(pt_git.hasPrevious());
	}

	/**
	 * @param pt
	 * @return
	 */
	private IProofTreeAdvancedIterator testeGlobalRight(ProofTree pt) {
		IProofTreeAdvancedIterator pt_git;
		pt_git = pt.getGlobalIterator();
		assertTrue(pt_git.hasNextRight());
		assertEquals(pt_git.nextRight().getContent(), "Predicado");
		assertEquals(pt_git.nextRight().getContent(), "estavam");
		assertEquals(pt_git.nextRight().getContent(), "indo");
		assertEquals(pt_git.nextRight().getContent(), "Objeto indireto");
		assertEquals(pt_git.nextRight().getContent(), "para");
		assertEquals(pt_git.nextRight().getContent(), "casa");
		assertFalse(pt_git.hasNextRight());
		return pt_git;
	}

	/**
	 * @param pt_git
	 */
	private void testeGlobalLeft(IProofTreeAdvancedIterator pt_git) {
		assertTrue(pt_git.hasNextLeft());
		assertEquals(pt_git.nextLeft().getContent(), "Predicado");
		assertEquals(pt_git.nextLeft().getContent(), "estavam");
		assertEquals(pt_git.nextLeft().getContent(), "indo");
		assertEquals(pt_git.nextLeft().getContent(), "Sujeito");
		assertEquals(pt_git.nextLeft().getContent(), "Os");
		assertEquals(pt_git.nextLeft().getContent(), "garotos");
		assertFalse(pt_git.hasNextLeft());
	}

	/**
	 * @param pt
	 */
	private void testeLocalIterator(ProofTree pt) {
		IProofTreeBasicIterator pt_it = pt.getLocalIterator();
		assertTrue(pt_it.hasNext());
		assertEquals(pt_it.next().getContent(), "Predicado");
		assertTrue(pt_it.hasNext());
		assertEquals(pt_it.next().getContent(), "estavam");
		assertTrue(pt_it.hasNext());
		assertEquals(pt_it.next().getContent(), "indo");
		assertFalse(pt_it.hasNext());
		assertEquals(pt_it.current().getContent(), "indo");
		assertTrue(pt_it.hasPrevious());
		assertEquals(pt_it.previous().getContent(), "estavam");
		assertTrue(pt_it.hasPrevious());
		assertEquals(pt_it.previous().getContent(), "Predicado");
		assertFalse(pt_it.hasPrevious());
	}

	public void testStringNodeAndState() {
		StringNode sn = new StringNode("AAA");
		assertEquals(sn.getState(), StringState.ANALYSED);

	}

	public void testSignedFormulaNodeStateAndOrigin() {
		SignedFormulaCreator sfc = new SignedFormulaCreator();

		SignedFormulaNode sfn =
			new SignedFormulaNode(
				sfc.parseString("F A->B"),
				SignedFormulaNodeState.NOT_ANALYSED,
				Origin.PROBLEM);

	  assertEquals (sfn.getContent(), sfc.parseString("F A->B"));
	  assertEquals (sfn.getState(), SignedFormulaNodeState.NOT_ANALYSED);
	  assertEquals( sfn.getOrigin(), Origin.PROBLEM);
	  
	}
	
	public void testFormulaReferenceClassicalProofTree() {
	    
		SignedFormulaCreator sfc = new SignedFormulaCreator();

		SignedFormula sf1, sf2, sf4, sf5, sf6;
		SignedFormulaNode sfn =
			new SignedFormulaNode(
				sf1 = sfc.parseString("F A->B"),
				SignedFormulaNodeState.NOT_ANALYSED,
				Origin.PROBLEM);

		FormulaReferenceClassicalProofTree pt = new FormulaReferenceClassicalProofTree(sfn);
		
		assertFalse(pt.isClosed());
		SignedFormulaList sfl = pt.getLocalReferences(sf1.getFormula());
		
		assertEquals(sf1, sfl.get(0));

		sfn =
			new SignedFormulaNode(
				sf2 = sfc.parseString("T X&(A|B)->(!C&A&!C)"),
				SignedFormulaNodeState.NOT_ANALYSED,
				Origin.PROBLEM);

		pt.addLast(sfn);
		Formula a;
		sfl = pt.getLocalReferences(a= (Formula)sf1.getFormula().getImmediateSubformulas().get(0));
		assertTrue(sfl.contains(sf1));
		assertTrue(sfl.contains(sf2));
//		System.err.println(pt.getLocalReferences(a));
		
		FormulaList map = pt.getSubformulaLocalReferences(a,sf1);
//		System.err.println(map);
		assertTrue(map.contains(sf1.getFormula()));
		map = pt.getSubformulaLocalReferences(a,sf2);
//		System.err.println(sf2);
//		System.err.println(map);
		
		SignedFormula sf3 = sfc.parseString("T A|B");
		assertTrue(map.contains(sf3.getFormula()));
		sf3 = sfc.parseString("T !C&A&!C");
		assertTrue(map.contains(sf3.getFormula()));
		
		// testing addLeft and getLocalReferences as well as getParentReferences
		
		sfn =
			new SignedFormulaNode(
				sf4 = sfc.parseString("T A"),
				SignedFormulaNodeState.NOT_ANALYSED,
				Origin.PROBLEM);
		FormulaReferenceClassicalProofTree pt_left = (FormulaReferenceClassicalProofTree)pt.addLeft(sfn);
		sfn =
			new SignedFormulaNode(
				sf5 = sfc.parseString("F V->(A->B)"),
				SignedFormulaNodeState.NOT_ANALYSED,
				Origin.PROBLEM);
		pt_left.addLast(sfn);
		sfl = pt_left.getLocalReferences(sf4.getFormula());
		assertTrue(sfl.contains(sf4));
		assertTrue(sfl.contains(sf5));
		assertEquals(2, sfl.size());
//		System.err.println(sfl);
		sfl = pt_left.getParentReferences(sf4.getFormula());
//		System.err.println(sfl);
		assertTrue(sfl.contains(sf1));
		assertTrue(sfl.contains(sf2));
		assertTrue(sfl.contains(sf4));
		assertTrue(sfl.contains(sf5));

		// testing addRight and getLocalReferences as well as getParentReferences
		sfn =
			new SignedFormulaNode(
				sf6 = sfc.parseString("T G->(A->B)&(A&B)"),
				SignedFormulaNodeState.NOT_ANALYSED,
				Origin.PROBLEM);
		FormulaReferenceClassicalProofTree pt_right = (FormulaReferenceClassicalProofTree)pt.addRight(sfn);
		sfl = pt_right.getLocalReferences(sf4.getFormula());
		assertTrue(sfl.contains(sf6));
		assertEquals(1, sfl.size());
//		System.err.println(sfl);
		sfl = pt_right.getParentReferences(sf4.getFormula());
		assertTrue(sfl.contains(sf6));
		assertTrue(sfl.contains(sf1));
		assertTrue(sfl.contains(sf2));
//		System.err.println(sfl);
        
		map = pt_right.getSubformulaParentReferences(a,sf6);
		assertEquals(2, map.size());
//		System.out.println(map);

		map = pt_right.getSubformulaParentReferences(a,sf2);
		assertEquals(2, map.size());
//		System.out.println(map);
    }
	
	

}