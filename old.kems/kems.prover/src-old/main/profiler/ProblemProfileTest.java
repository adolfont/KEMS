/*
 * Created on 15/03/2005
 *
 */
package profiler;

import java.util.Date;

import junit.framework.TestCase;
import problem.ProblemType;
import aspects.Execution;

/**
 * @author Adolfo Gustavo Serra Seca Neto
 * 
 *
 */
public class ProblemProfileTest extends TestCase {
    
    public void testProblemProfile() {
        
        IsolatedProblemProfile pp = new IsolatedProblemProfile("php");
        
        pp.setTimeElapsed(123.45);
        pp.setNumberOfExecutions(25);
        pp.setExecution(new Execution("Unix", new Date()));
        
        assertTrue(123.45== pp.getTimeElapsed());
        assertTrue(25== pp.getNumberOfExecutions() );
        assertEquals("php", pp.getName());
        
        FamilyProblemProfile fp = new FamilyProblemProfile(4);
        
        fp.setTimeElapsed(123.45);
        fp.setNumberOfExecutions(25);
        fp.setExecution(new Execution("Unix", new Date()));
        
        assertTrue(4==fp.getInstanceNumber());
        
        ProblemFamilyProfile pfp = new ProblemFamilyProfile ("php", 1,5,2,10, ProblemType.NORMAL);
        
        assertEquals("php_01", pfp.getProblemName(1));

        pfp = new ProblemFamilyProfile ("php", 1,5,2,100, ProblemType.CLAUSAL);
        
        assertEquals("php_n_001", pfp.getProblemName(1));
        assertTrue (pfp.getProblem(0) instanceof FamilyProblemProfile);
    }

}
