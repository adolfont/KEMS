/*
 * Created on 22/12/2004
 *
 */
package tableau;

import junit.framework.TestCase;
import aspects.RulesUsage;
import classicalLogic.ClassicalRules;

/**
 * @author adolfo
 *  
 */
public class RulesUsageTest extends TestCase {

    public void testAll() {

        RulesUsage ru = new RulesUsage();

        ru.addToUsage(ClassicalRules.F_AND_LEFT);
        ru.addToUsage(ClassicalRules.F_AND_LEFT);

        ru.setUsage(ClassicalRules.F_BIIMPLIES_LEFT_FALSE, 10);

        assertTrue(10 == ru.getUsage(ClassicalRules.F_BIIMPLIES_LEFT_FALSE));
        assertTrue(2 == ru.getUsage(ClassicalRules.F_AND_LEFT));

    }

}