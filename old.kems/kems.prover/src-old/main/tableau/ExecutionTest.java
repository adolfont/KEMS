/*
 * Created on 22/12/2004
 *
 */
package tableau;

import java.util.Date;

import junit.framework.TestCase;
import ruleStructures.RulesStructure;
import strategy.Strategy;
import strategy.simple.SimpleStrategy;
import aspects.Execution;
import aspects.RulesUsage;
import classicalLogic.ClassicalRules;

/**
 * @author adolfo
 *
 */
public class ExecutionTest extends TestCase {
    
    /**
     * 
     */
    public void testAll() {
        
        Execution e = new Execution ("kurumin", new Date());
        
        e.setTimeElapsed(0.0678);
        e.setFilename("/home/adolfo/xxx.prove");
        RulesStructure rs = new RulesStructure();
        Method m = new Method(rs);
        Strategy s = new AbstractSimpleStrategy(m);
        e.setStrategy(s);
        
        RulesUsage ru = new RulesUsage();
        ru.addToUsage(ClassicalRules.F_AND_LEFT);
        ru.addToUsage(ClassicalRules.F_AND_LEFT);

        ru.setUsage(ClassicalRules.F_BIIMPLIES_LEFT_FALSE, 10);

        
        e.setRulesUsage(ru);
        
//        System.out.println(XMLViewer.getXMLElementAsString(e.asXMLElement()));
        
}
    
}
