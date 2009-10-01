/*
 * Created on 05/04/2006
 *
 */
package logicalSystems.mCi;

import logic.logicalSystem.ISignature;
import logicalSystems.mbc.MBCConnectives;
import logicalSystems.mbc.MBCRuleStructures;
import logicalSystems.mbc.MBCSigns;
import rules.structures.OnePremiseRuleList;

/**
 * Rule structures for the mCi logic.
 * 
 * @author Adolfo Gustavo Serra Seca Neto
 * 
 */
public class MCIRuleStructures extends MBCRuleStructures {

	/**
	 * @param signature
	 */
	public MCIRuleStructures(ISignature signature) {
		super(signature);
	}

	protected OnePremiseRuleList initializeOnePremiseRuleList() {
		onePremiseRules = super.initializeOnePremiseRuleList();

		addToOnePremiseRules(MBCSigns.TRUE, MBCConnectives.NOT,
				MCIRules.T_NOT_CONS);

		addToOnePremiseRules(MBCSigns.FALSE, MBCConnectives.CONSISTENCY,
				MCIRules.F_CONS);

		return onePremiseRules;
	}

}
