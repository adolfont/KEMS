/*
 * Created on 05/04/2005
 *
 */
package logic.logicalSystem;

import junit.framework.TestCase;
import classicalLogic.ClassicalConnectives;
import classicalLogic.ClassicalRuleStructures;
import classicalLogic.ClassicalSignatures;

/**
 * @author Adolfo Gustavo Serra Seca Neto
 * 
 *  
 */
public class LogicTest extends TestCase {

    ClassicalSignatures cs;

    Signature signature;

    LogicalSystemSample ls_cpl_n;
    
    ConfigurableClassicalRuleStructures rs_cpl;

    public void setUp() {
        cs = new ClassicalSignatures();
        signature = cs.getNormalBXSignature();
        rs_cpl = new ConfigurableClassicalRuleStructures(signature);
        
        ls_cpl_n = new LogicalSystemSample(cs.getNormalBXSignature(), new RulesStructureBuilderSample());;
    }

    public void testConstructor() {
        assertNotNull(ls_cpl_n);
    }

    public void testGetSetSignature() {
        assertNotNull(ls_cpl_n.getSignature());
        
        assertTrue (ls_cpl_n.getSignature() instanceof ISignature);
        
        assert(ls_cpl_n.getSignature().contains(ClassicalConnectives.AND));
    }
    
    public void testGetSetRulesStructure() {
        
        assertNotNull (ls_cpl_n.getRulesStructure());
    }

}
