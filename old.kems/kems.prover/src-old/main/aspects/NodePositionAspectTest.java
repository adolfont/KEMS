package aspects;
import junit.framework.TestCase;
import proofTree.ProofTree;
import proofTree.StringNode;
/*
 * Created on 03/03/2005
 *
 */

/**
 * @author Adolfo Gustavo Serra Seca neto
 *
 */
public class NodePositionAspectTest extends TestCase {
    
    public void testNodePosition() {
        
        
        ProofTree pt = new ProofTree(new StringNode("root 0"));
        
        pt.addLast(new StringNode("root 1"));
        pt.addLast(new StringNode("root 2"));
        ProofTree left = pt.addLeft(new StringNode("left 0"));
        left.addLast(new StringNode("left 1"));
        ProofTree leftleft = left.addLeft(new StringNode("left left 0"));
        ProofTree leftright = left.addRight(new StringNode("left right 0"));
        ProofTree right = pt.addRight(new StringNode("right 0"));
        right.addLast(new StringNode("right 1"));
        ProofTree rightright = right.addRight(new StringNode("right right 0"));
        ProofTree rightleft = right.addLeft(new StringNode("right left 0"));
        
        System.out.println(pt);
        
    }

}
