/*
 * Created on 05/10/2005
 *
 */
package main.proofTree.iterator;

import junit.framework.TestCase;
import logic.signedFormulas.SignedFormula;
import main.proofTree.INode;
import main.proofTree.IProofTree;
import main.proofTree.ProofTree;
import main.proofTree.StringNode;

/**
 * [CLASS_DESSCRIPTION]
 * 
 * @author Adolfo Gustavo Serra Seca Neto
 *  
 */
public class ProofTreeGlobalIteratorTest extends TestCase {

	class MyProofTree extends ProofTree {

		public MyProofTree(StringNode aNode) {
			super(aNode);

		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see main.proofTree.ProofTree#setOtherStructures(main.proofTree.ProofTree,
		 *      main.proofTree.Node)
		 */
		protected void setOtherStructures(IProofTree pt, INode aNode) {
			// TODO Auto-generated method stub

		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see main.proofTree.ProofTree#makeInstance(main.proofTree.Node)
		 */
		protected IProofTree makeInstance(INode aNode) {
			return new MyProofTree((StringNode) aNode);
		}

		protected void removeOtherReferencesInChildren(SignedFormula sf) {
			// TODO Auto-generated method stub
			
		}

	}

	MyProofTree pt;
	MyProofTree left_right;
	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		pt = new MyProofTree(new StringNode("RAIZ 0"));
		pt.addLast(new StringNode("Raiz 1"));
		pt.addLast(new StringNode("Raiz 2"));
		
		MyProofTree left = (MyProofTree) pt.addLeft(new StringNode("Left 0"));
		left.addLast(new StringNode("Left 1"));
		left.addLast(new StringNode("Left 2"));
		
		MyProofTree right = (MyProofTree) pt.addRight(new StringNode("Right 0"));
		right.addLast(new StringNode("Right 1"));
		right.addLast(new StringNode("Right 2"));

		left_right = (MyProofTree) right.addLeft(new StringNode("LR 0"));
		left_right.addLast(new StringNode("LR 1"));
		left_right.addLast(new StringNode("LR 2"));

		
	}
	
	public void testProofTreeBackwardGlobalIterator(){
		IProofTreeVeryBasicIterator it = left_right.getTopDownIterator();
		
		// TODO arrumar
		System.err.println("FIRST TEST");
		
		while(it.hasNext()){
			System.err.println(it.next());
		}
	}
	

	public void testProofTreeGlobalIterator() {
		
		IProofTree current = left_right;
		
		IProofTreeBasicIterator it = current.getLocalIterator();
		
		boolean continua = true;
		while(continua){
			if (it.hasNext()){
				System.err.println(it.next());
			}else{
				current = current.getParent();
				
				if (current == null){
					continua = false;
				}
				else{
					it = current.getLocalIterator();
				}
				
			}
		}
		
	}

	public void testProofTreeTopDownGlobalIterator() {
	    System.out.println("TopDownIterator");
//	    System.out.println(pt.toStringShort());
		
		ProofTree current = left_right;
//	    System.out.println(current.toStringShort());
		
		IProofTreeVeryBasicIterator it = current.getTopDownIterator();
		
		while(it.hasNext()){
				System.err.println(it.next());
		}
		
	}

}
