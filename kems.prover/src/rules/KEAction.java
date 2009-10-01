/*
 * Created on 22/10/2004
 *
 */
package rules;

import rules.getters.KESignedFormulaGetter;

/**
 * @author Adolfo Gustavo Serra Seca Neto
 * 
 */
public class KEAction implements Action {

	ActionType _at;
	KESignedFormulaGetter _content;

	public KEAction(ActionType at, KESignedFormulaGetter content) {
		_at = at;
		_content = content;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see rulesNew.Action#getActionType()
	 */
	public ActionType getActionType() {
		return _at;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see rulesNew.Action#getContent()
	 */
	public KESignedFormulaGetter getContent() {
		return _content;
	}

}
