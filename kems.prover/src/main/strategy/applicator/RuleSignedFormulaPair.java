package main.strategy.applicator;

import logic.signedFormulas.SignedFormula;
import rules.Rule;

public class RuleSignedFormulaPair {
	
	private Rule rule;
	private SignedFormula sformula;
	
	
	public RuleSignedFormulaPair(Rule rule, SignedFormula sformula) {
		super();
		this.rule = rule;
		this.sformula = sformula;
	}


	public Rule getRule() {
		return rule;
	}


	public void setRule(Rule rule) {
		this.rule = rule;
	}


	public SignedFormula getSignedformula() {
		return sformula;
	}


	public void setSformula(SignedFormula sformula) {
		this.sformula = sformula;
	}
	

}
