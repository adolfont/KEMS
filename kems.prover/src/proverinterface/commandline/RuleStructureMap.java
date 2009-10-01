package proverinterface.commandline;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

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
import proverinterface.RuleStructureFactory;


public class RuleStructureMap {

	private Map<String, String> ruleStructureMap;

	public RuleStructureMap() {
		ruleStructureMap = new HashMap<String, String>();
		
		ruleStructureMap.put(SimpleStrategy.class.getSimpleName(),RuleStructureFactory.CPL_NORMAL_BX);
		ruleStructureMap.put(MemorySaverStrategy.class.getSimpleName(),RuleStructureFactory.CPL_NORMAL_BX);
		ruleStructureMap.put(BackjumpingSimpleStrategy.class.getSimpleName(),RuleStructureFactory.CPL_NORMAL_BX);
		ruleStructureMap.put(LearningSimpleStrategy.class.getSimpleName(),RuleStructureFactory.CPL_NORMAL_BX);
		ruleStructureMap.put(NewLearningSimpleStrategy.class.getSimpleName(),RuleStructureFactory.CPL_NORMAL_BX);

		ruleStructureMap.put(ConfigurableSimpleStrategy.class.getSimpleName(),RuleStructureFactory.CPL_CONFIGURABLE);

		ruleStructureMap.put(MBCSimpleStrategy.class.getSimpleName(),RuleStructureFactory.MBC);
		ruleStructureMap.put(MBCSimpleWithOptionalRulesStrategy.class.getSimpleName(),RuleStructureFactory.MBC);

		ruleStructureMap.put(MCISimpleStrategy.class.getSimpleName(),RuleStructureFactory.MCI);
		ruleStructureMap.put(MCISimpleWithOptionalRulesStrategy.class.getSimpleName(),RuleStructureFactory.MCI);
	}

	public String getRuleStructure(String name) {
		return ruleStructureMap.get(name);
	}

	public Set<String> getRuleStructures() {
		return ruleStructureMap.keySet();
	}


}
