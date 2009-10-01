/*
 * Created on 21/09/2005
 */
package rules;


/**
 * @author Adolfo Gustavo Serra Seca Neto
 */
public abstract class OneConclusionRule extends Rule {
	
	private KEAction _conclusion;
	
	public KEAction getConclusion(){
		return _conclusion;
	}
	
	public OneConclusionRule(String name, KEAction conclusion){
		super(name);
		_conclusion = conclusion;
	}


}
