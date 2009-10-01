/*
 * Created on 03/08/2005
 *
 */
package logicalSystems;

import junit.framework.TestCase;

/** 
 * Tests for LogicalSystemFactory class.
 * 
 * @author Adolfo Gustavo Serra Seca Neto
 * 
 */
public class LogicalSystemFactoryTest extends TestCase {
    
    public void testGetInstance() {
        assertNotNull (LogicalSystemFactory.getInstance());
    }
    
    public void testGetLogicalSystem(){
        assertNotNull (LogicalSystemFactory.getInstance().getLogicalSystem(LogicalSystemFactory.CLASSICAL_LOGIC));
    }

}
