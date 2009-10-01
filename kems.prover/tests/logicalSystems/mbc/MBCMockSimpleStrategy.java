/*
 * Created on 01/11/2005
 *
 */
package logicalSystems.mbc;

import main.newstrategy.mbc.simple.MBCSimpleStrategy;
import main.strategy.ClassicalProofTree;
import main.tableau.Method;


public class MBCMockSimpleStrategy extends MBCSimpleStrategy {


	public MBCMockSimpleStrategy(Method method) {
		super(method);
	}

	public void setProofTree(ClassicalProofTree cpt) {
		super.setProofTree(cpt);
	}
 
}
