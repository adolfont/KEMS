/*
 * Created on 04/02/2005
 *
 */
package proofTree;

import junit.framework.TestCase;

/**
 * @author Adolfo Gustavo Serra Seca neto
 *
 */
public class ProofTreeHeightTest extends TestCase {
    
    public void testHeight() {
        
        // TODO One cannot add the same node to different places!
        
        StringNode root = new StringNode ("root - 1");
        ProofTree pt = new ProofTree(root);
        ProofTree main = pt;
        
        StringNode other = new StringNode ("root - 2");
        pt.addLast(other);

        other = new StringNode ("left - 1");
        ProofTree left = pt.addLeft(other);
        
        other = new StringNode ("right - 1");
        ProofTree right = pt.addRight(other);
        
        other = new StringNode ("left - 2");
        left.addLast(other);

        other = new StringNode ("left - 3");
        left.addLast(other);
        
        other = new StringNode ("left - left - 1");
        left.addLeft(other);

        other = new StringNode ("left - right - 1");
        left.addRight(other);

        other = new StringNode ("right - 2");
        right.addLast(other);
        
        System.err.println(main);
        

        
        
        
    }

}
