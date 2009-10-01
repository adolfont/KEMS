/*
 * Created on 22/10/2004
 *
 */
package rules;

/** Class that represents the type of an actions suggested by a rule.
 * 
 * @author Adolfo Gustavo Serra Seca Neto
 *
 */
public class ActionType {
	String _name;

	public static final ActionType ADD_NODE =
		new ActionType("add node to current branch");

	public static final ActionType BRANCH_PB =
		new ActionType("branch with one formula and its opposite");

	private ActionType(String name) {
		this._name = name;
	}

}
