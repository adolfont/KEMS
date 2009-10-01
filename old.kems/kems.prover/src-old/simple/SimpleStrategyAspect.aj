/*
 * Created on 30/09/2005
 *  
 */
package main.strategy.simple;

import main.strategy.AbstractStrategy; 
import logic.signedFormulas.SignedFormulaBuilder;
import main.strategy.ClassicalProofTree;

/**
 * Aspect for variation of SimpleStrategy features.
 * 
 * @author Adolfo Gustavo Serra Seca Neto
 *  
 */
public aspect SimpleStrategyAspect {
	
	private static final char WITH_MAP='M';
	private static final char WITHOUT_MAP_BOTTOM_UP='B';
//	private static final char WITHOUT_MAP_TOP_DOWN='T';
	private char option=WITH_MAP;

	boolean around(SimpleStrategy strategy, 
			ClassicalProofTree cpt, SignedFormulaBuilder sfb): target (strategy) && args(cpt, sfb) &&
	call(protected boolean AbstractStrategy.applyTwoPremiseRulesWithMap(..)){
		if (option == WITH_MAP){
			return proceed(strategy, cpt, sfb);
		}else{
			if (option == WITHOUT_MAP_BOTTOM_UP){
				return strategy.applyTwoPremiseRulesWithoutMapBottomUp(cpt, sfb);
			}
			else{
				if (option == WITHOUT_MAP_BOTTOM_UP){
					return strategy.applyTwoPremiseRulesWithoutMapCurrentDownThenUp(cpt, sfb);
			}
		}
			
		return proceed(strategy, cpt, sfb);
			
	}

   }
	
}	
