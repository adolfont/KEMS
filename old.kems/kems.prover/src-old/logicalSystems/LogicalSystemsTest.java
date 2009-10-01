/*
 * Created on 03/08/2005
 *
 */
package logicalSystems;

import junit.framework.TestCase;
import logic.ILogicalSystem;

/**
 * @author Adolfo Gustavo Serra Seca Neto
 * 
 *
 */
public class LogicalSystemsTest extends TestCase {
    ILogicalSystem cpl = LogicalSystemFactory.getInstance().getLogicalSystem(LogicalSystemFactory.CLASSICAL_LOGIC);
    
    /* (non-Javadoc)
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();
    }

}
