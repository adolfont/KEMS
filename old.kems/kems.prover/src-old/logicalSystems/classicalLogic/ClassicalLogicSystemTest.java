/*
 * Created on 03/08/2005
 *
 */
package logicalSystems.classical;

import junit.framework.TestCase;
import logic.ILogicalSystem;
import logicalSystems.LogicalSystemFactory;
import classicalLogic.ClassicalConnectives;
import classicalLogic.ClassicalRules;
import classicalLogic.ClassicalSignatureFactory;

/**
 * Tests the ClassicalLogicSystem class.
 * 
 * @author Adolfo Gustavo Serra Seca Neto
 * 
 *
 */
public class ClassicalLogicSystemTest extends TestCase {
    
    private ILogicalSystem cls;
    
    /* (non-Javadoc)
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception {
        cls = LogicalSystemFactory.getInstance().getLogicalSystem(LogicalSystemFactory.CLASSICAL_LOGIC);
    }
    
    public void testClassicalLogicSystemAndGetConnectives() {
        assertNotNull (cls);
        
        assertNotNull (cls.getConnectives());
        assertTrue (cls.getConnectives().values().size()==8);
        assertTrue(cls.getConnectives().containsValue(ClassicalConnectives.AND));
        assertTrue(cls.getConnectives().containsValue(ClassicalConnectives.OR));
        assertTrue(cls.getConnectives().containsValue(ClassicalConnectives.NOT));
        assertTrue(cls.getConnectives().containsValue(ClassicalConnectives.IMPLIES));
        assertTrue(cls.getConnectives().containsValue(ClassicalConnectives.BIIMPLIES));
        assertTrue(cls.getConnectives().containsValue(ClassicalConnectives.XOR));
        assertTrue(cls.getConnectives().containsValue(ClassicalConnectives.TOP));
        assertTrue(cls.getConnectives().containsValue(ClassicalConnectives.BOTTOM));
    }
    
    public void testGetConnective() {
        assertEquals (cls.getConnective(ClassicalSignatureFactory.AND), ClassicalConnectives.AND);
    }
    
    // TODO more tests later

    public void testGetRules() {
        assertNotNull (cls.getRules());
        
        assertTrue (cls.getRules().size()==12);
    }
    
    
    public void testGetRulesStructure() {
        assertNotNull (cls.getRulesStructure());
    }
    
    public void testGetSignature() {
        assertNotNull (cls.getSignature());
        
        System.err.println (ClassicalConnectives.AND);
        System.err.println (ClassicalRules.T_AND);
        System.out.println (Runtime.getRuntime().freeMemory());
    }
}
