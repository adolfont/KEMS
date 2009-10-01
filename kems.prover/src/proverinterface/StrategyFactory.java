/*
 * Created on 28/12/2004
 *
 */
package proverinterface;

import java.util.HashMap;
import java.util.Map;

import main.newstrategy.c1.simple.C1SimpleStrategy;
import main.newstrategy.cpl.simple.configurable.ConfigurableSimpleStrategy;
import main.newstrategy.mbc.simple.MBCSimpleStrategy;
import main.newstrategy.mbc.simple.optional.MBCSimpleWithOptionalRulesStrategy;
import main.newstrategy.mci.simple.MCISimpleStrategy;
import main.newstrategy.mci.simple.optional.MCISimpleWithOptionalRulesStrategy;
import main.newstrategy.memorysaver.MemorySaverStrategy;
import main.newstrategy.simple.SimpleStrategy;
import main.newstrategy.simple.backjumping.BackjumpingSimpleStrategy;
import main.newstrategy.simple.learning.LearningSimpleStrategy;
import main.newstrategy.simple.newlearning.NewLearningSimpleStrategy;
import util.StringUtil;

/**
 * @author adolfo
 * 
 */
public class StrategyFactory {

	private Map<String, String> strategyMap;

	/**
	 * 
	 */
	public StrategyFactory() {
		super();

		initializeStrategyMap();
	}

	private void initializeStrategyMap() {

		strategyMap = new HashMap<String, String>();

		// CPL strategies
		strategyMap.put(SimpleStrategy.class.getSimpleName(),
				SimpleStrategy.class.getName());
		strategyMap.put(MemorySaverStrategy.class.getSimpleName(),
				MemorySaverStrategy.class.getName());
		strategyMap.put(ConfigurableSimpleStrategy.class.getSimpleName(),
				ConfigurableSimpleStrategy.class.getName());
		strategyMap.put(BackjumpingSimpleStrategy.class.getSimpleName(),
				BackjumpingSimpleStrategy.class.getName());
		strategyMap.put(LearningSimpleStrategy.class.getSimpleName(),
				LearningSimpleStrategy.class.getName());
		strategyMap.put(NewLearningSimpleStrategy.class.getSimpleName(),
				NewLearningSimpleStrategy.class.getName());

		// mbC strategies
		strategyMap.put(MBCSimpleStrategy.class.getSimpleName(),
				MBCSimpleStrategy.class.getName());
		strategyMap.put(MBCSimpleWithOptionalRulesStrategy.class
				.getSimpleName(), MBCSimpleWithOptionalRulesStrategy.class
				.getName());
//		strategyMap.put(MBCConfigurableSimpleStrategy.class.getSimpleName(),
//				MBCConfigurableSimpleStrategy.class.getName());

		// mCi strategies
		strategyMap.put(MCISimpleStrategy.class.getSimpleName(),
				MCISimpleStrategy.class.getName());
		strategyMap.put(MCISimpleWithOptionalRulesStrategy.class
				.getSimpleName(), MCISimpleWithOptionalRulesStrategy.class
				.getName());
//		strategyMap.put(MCIConfigurableSimpleStrategy.class.getSimpleName(),
//				MCIConfigurableSimpleStrategy.class.getName());

		
		// mCi strategies
		strategyMap.put(C1SimpleStrategy.class.getSimpleName(),
				C1SimpleStrategy.class.getName());

	}

	public String getFullStrategyClassName(String strategyName) {
		if (strategyMap.get(strategyName) != null) {
			return strategyMap.get(strategyName);
		} else {
			throw new RuntimeException("Strategy " + strategyName
					+ " not found!" + showStrategiesAvailable());
		}
	}

	private String showStrategiesAvailable() {
		String result = StringUtil.LINE_SEPARATOR;
		result += "Strategies available: "+ strategyMap.keySet()+StringUtil.LINE_SEPARATOR;
		return result;
	}

//	/**
//	 * @param strategyName
//	 * @param method
//	 */
//	public Strategy createStrategy(String strategyName, Method method) {
//
//		if (strategyName.equals("SimpleStrategy")) {
//			return new main.strategy.simple.SimpleStrategy(method);
//		} else if (strategyName.equals("MemorySaverStrategy")) {
//			return new main.strategy.memorySaver.MemorySaverStrategy(method);
//			// } else if (strategyName.equals("OldSimpleStrategy")) {
//			// return new OldSimpleStrategy(method);
//		} else if (strategyName.equals("MemorySaverSimpleStrategy")) {
//			return new MemorySaverSimpleStrategy(method);
//		} else if (strategyName.equals("MBCSimpleStrategy")) {
//			return new main.strategy.simple.mbc.MBCSimpleStrategy(method);
//		} else {
//			return new main.strategy.simple.SimpleStrategy(method);
//		}
//
//	}

}