/*
 * Created on 09/12/2005
 *
 */
package proverinterface;

import main.newstrategy.cpl.configurable.comparator.ISignedFormulaComparator;

/**
 * An object that contains all information used to configure a prover.
 * 
 * @author Adolfo Gustavo Serra Seca Neto
 * 
 */
public class ProverConfiguration implements Cloneable{

	private String strategyName;

	private String strategyFullClassName;

	private String firstParsingLibName;

	private String rulesStructureName;

	private int times;

	private long timeLimit;

	private boolean saveOrigin, discardClosedBranches, saveDiscardedBranches;

	// useBackjumping;

	private ISignedFormulaComparator signedFormulaComparator;

	private boolean twoPhasesParserOption;

	/**
	 * @param strategyName
	 */
	public ProverConfiguration(String strategyName) {
		this.strategyName = strategyName;
	}

	/**
	 * @param firstParsingLibName
	 */
	public void setFirstParsingLibName(String firstParsingLibName) {
		this.firstParsingLibName = firstParsingLibName;
	}

	/**
	 * @param rulesStructureName
	 */
	public void setRulesStructureName(String rulesStructureName) {
		this.rulesStructureName = rulesStructureName;
	}

	/**
	 * @param times
	 */
	public void setTimes(int times) {
		this.times = times;

	}

	/**
	 * @param saveOrigin
	 */
	public void setSaveOrigin(boolean saveOrigin) {
		this.saveOrigin = saveOrigin;
	}

	/**
	 * @param discardClosedBranches
	 */
	public void setDiscardClosedBranches(boolean discardClosedBranches) {
		this.discardClosedBranches = discardClosedBranches;
	}

	/**
	 * @param saveDiscardedBranches
	 */
	public void setSaveDiscardedBranches(boolean saveDiscardedBranches) {
		this.saveDiscardedBranches = saveDiscardedBranches;
	}

	/**
	 * @param signedFormulaComparator
	 */
	public void setSignedFormulaComparator(ISignedFormulaComparator signedFormulaComparator) {
		this.signedFormulaComparator = signedFormulaComparator;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return strategyName
				+ ","
				+ getRulesStructureName()
				+ ","
				+ (getSaveOrigin() ? "SO" : "NSO")
				+ ","
				+ (getDiscardClosedBranches() ? "D" : "ND")
				+ ","
				// + (getUseBackjumping() ? "BJ," : "")
				+ (getSaveDiscardedBranches() ? "SD" : "NSD") 
//				+ getFirstParsingLibName()  
				+ (getSignedFormulaComparator() == null ? ",noComparator" : ","
						+ getSignedFormulaComparator().toString());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object obj) {
		if (!(obj instanceof ProverConfiguration)) {
			return false;
		}

		ProverConfiguration other = (ProverConfiguration) obj;
		return strategyName.equals(other.getStrategyName())
				&& getRulesStructureName()
						.equals(other.getRulesStructureName())
				&& getSaveOrigin() == other.getSaveOrigin()
				&& getDiscardClosedBranches() == other
						.getDiscardClosedBranches()
				// && getUseBackjumping() == other.getUseBackjumping()
				&& getSaveDiscardedBranches() == other
						.getSaveDiscardedBranches()
				&& getSignedFormulaComparator() == other
						.getSignedFormulaComparator();

	}

	public boolean getDiscardClosedBranches() {
		return discardClosedBranches;
	}

	public String getFirstParsingLibName() {
		return firstParsingLibName;
	}

	public String getRulesStructureName() {
		return rulesStructureName;
	}

	public boolean getSaveDiscardedBranches() {
		return saveDiscardedBranches;
	}

	public boolean getSaveOrigin() {
		return saveOrigin;
	}

	public ISignedFormulaComparator getSignedFormulaComparator() {
		return signedFormulaComparator;
	}

	public String getStrategyName() {
		return strategyName;
	}

	public int getTimes() {
		return times;
	}

	// public boolean getUseBackjumping() {
	// return useBackjumping;
	// }
	//
	// /**
	// * @param useBackjumping
	// */
	// public void setUseBackjumping(boolean useBackjumping) {
	// this.useBackjumping = useBackjumping;
	//
	// }

	public long getTimeLimit() {
		return timeLimit;
	}

	public void setTimeLimit(long timeLimit) {
		this.timeLimit = timeLimit;
	}

	public String getStrategyFullClassName() {
		return strategyFullClassName;
	}

	public void setStrategyFullClassName(String strategyFullClassName) {
		this.strategyFullClassName = strategyFullClassName;
	}

	public boolean getTwoPhasesParserOption() {
		return twoPhasesParserOption;
	}

	public void setTwoPhasesParserOption(boolean option) {
		this.twoPhasesParserOption = option;
	}

	public void setStrategyName(String strategyName) {
		this.strategyName = strategyName;
	}
	
	public ProverConfiguration clone(){
		try {
			return (ProverConfiguration) super.clone();
		} catch (CloneNotSupportedException e) {
			return null;
		}
	}

}
