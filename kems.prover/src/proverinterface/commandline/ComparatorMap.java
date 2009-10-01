package proverinterface.commandline;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import logicalSystems.classicalLogic.ClassicalConnectives;
import logicalSystems.classicalLogic.ClassicalSigns;
import main.newstrategy.cpl.configurable.comparator.ComplexitySignedFormulaComparator;
import main.newstrategy.cpl.configurable.comparator.ConnectiveSignedFormulaComparator;
import main.newstrategy.cpl.configurable.comparator.ISignedFormulaComparator;
import main.newstrategy.cpl.configurable.comparator.InsertionOrderSignedFormulaComparator;
import main.newstrategy.cpl.configurable.comparator.NormalFormulaOrderSignedFormulaComparator;
import main.newstrategy.cpl.configurable.comparator.ReverseFormulaOrderSignedFormulaComparator;
import main.newstrategy.cpl.configurable.comparator.ReverseInsertionOrderSignedFormulaComparator;
import main.newstrategy.cpl.configurable.comparator.SignSignedFormulaComparator;

public class ComparatorMap {

	private Map<String, ISignedFormulaComparator> comparatorMap;

	public ComparatorMap() {
		comparatorMap = new HashMap<String, ISignedFormulaComparator>();
		comparatorMap.put(new InsertionOrderSignedFormulaComparator()
				.toString(), new InsertionOrderSignedFormulaComparator());
		comparatorMap
				.put(new ReverseInsertionOrderSignedFormulaComparator()
						.toString(),
						new ReverseInsertionOrderSignedFormulaComparator());
		comparatorMap
				.put("AndComparator", new ConnectiveSignedFormulaComparator(
						ClassicalConnectives.AND));
		comparatorMap
				.put("AndComparator", new ConnectiveSignedFormulaComparator(
						ClassicalConnectives.AND));
		comparatorMap.put("OrComparator",
				new ConnectiveSignedFormulaComparator(ClassicalConnectives.OR));
		comparatorMap.put("ImpliesComparator",
				new ConnectiveSignedFormulaComparator(
						ClassicalConnectives.IMPLIES));
		comparatorMap.put("BiimpliesComparator",
				new ConnectiveSignedFormulaComparator(
						ClassicalConnectives.BIIMPLIES));
		comparatorMap
				.put("XorComparator", new ConnectiveSignedFormulaComparator(
						ClassicalConnectives.XOR));
		comparatorMap.put("TrueComparator", new SignSignedFormulaComparator(
				ClassicalSigns.TRUE));
		comparatorMap.put("FalseComparator", new SignSignedFormulaComparator(
				ClassicalSigns.FALSE));
		comparatorMap.put("AscendingComparator",
				new ComplexitySignedFormulaComparator(
						ComplexitySignedFormulaComparator.ASCENDING));
		comparatorMap.put("DescendingComparator",
				new ComplexitySignedFormulaComparator(
						ComplexitySignedFormulaComparator.DESCENDING));
		comparatorMap.put("NormalFormulaComparator",
				new NormalFormulaOrderSignedFormulaComparator());
		comparatorMap.put("ReverseFormulaComparator",
				new ReverseFormulaOrderSignedFormulaComparator());
	}

	public ISignedFormulaComparator getComparator(String name) {
		return comparatorMap.get(name);
	}

	public Set<String> getComparators() {
		return comparatorMap.keySet();
	}

}
