/*
 * Created on 30/11/2004
 *
 */
package rules.structures;

import logic.formulas.Connective;
import logic.signedFormulas.FormulaSign;
import rules.NullRule;
import rules.Rule;

/**
 * List-map of rules with one premise.
 * 
 * @author adolfo
 *  
 */
public class OnePremiseRuleList extends RuleList {

	SignConnectiveRuleMap _onePremiseRules;

	/**
	 * Creates an empty one premise rule list
	 */
	public OnePremiseRuleList() {
		super();
		_onePremiseRules = new SignConnectiveRuleMap();
	}

	/**
	 * Adds a one premise rule to the list.
	 * 
	 * @param fs -
	 *            the sign that identifies of the rule
	 * @param conn -
	 *            the connective that identifies the rule
	 * @param r -
	 *            the rule
	 */
	public void add(FormulaSign fs, Connective conn, Rule r) {
		add(r);
		_onePremiseRules.put(fs, conn, r);
	}

	/**
	 * Gives the rule for a given sign, connective pair.
	 * 
	 * @param fs -
	 *            the sign
	 * @param conn -
	 *            the connective
	 * @return a rule for a given sign, connective pair
	 */
	public Rule get(FormulaSign fs, Connective conn) {
		return _onePremiseRules.get(fs, conn) != null ? _onePremiseRules.get(
				fs, conn) : NullRule.INSTANCE;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see rules.structures.RuleList#toString()
	 */
	public String toString() {

		return "OnePremiseRuleList <Rules: " + getRules().toString() + "Map: "
				+ _onePremiseRules + ">";
	}

}