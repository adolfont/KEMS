/*
 * Created on 30/11/2005
 *
 */
package main.proofTree.origin;

import java.util.Iterator;
import java.util.List;

import logic.signedFormulas.SignedFormula;
import logic.signedFormulas.SignedFormulaList;
import main.proofTree.SignedFormulaNode;
import rules.IRule;

/**
 * An origin for signed formula nodes
 * 
 * @author Adolfo Gustavo Serra Seca Neto
 * 
 */
public class SignedFormulaNodeOrigin implements IOrigin {

	private IRule _rule;
	private SignedFormulaNode main;
	private SignedFormulaList sfl;
	private List<SignedFormulaNode> _sfnList;

	public SignedFormulaNodeOrigin(IRule _rule, SignedFormulaNode main,
			List<SignedFormulaNode> sfnList) {
		this._rule = _rule;
		_sfnList = sfnList;
		this.main = main;
		sfl = new SignedFormulaList((SignedFormula) main.getContent());
		Iterator<SignedFormulaNode> it = _sfnList.iterator();
		while (it.hasNext()) {
			SignedFormulaNode sfn = it.next();
//			if (sfn != null && sfn.getContent() != null)
				sfl.add(sfn.getContent());
		}
	}

	public SignedFormulaNode getMain() {
		return main;
	}

	public SignedFormulaList getSignedFormulaList() {
		return sfl;
	}

	public IRule getRule() {
		return _rule;
	}

	public String getName() {
		return "Rule: " + _rule.toString();
	}

	public List<SignedFormulaNode> getAuxiliaries() {
		return _sfnList;
	}

	public String toString() {
		return "Rule: " + _rule.toString() + toStringMain() + toStringAuxiliaries();
	}

	public String toStringMain() {
		return " Main: " + main.getContent();
	}

	public String toStringAuxiliaries() {
		String result = "";
		Iterator<SignedFormulaNode> it = _sfnList.iterator();
		int i = 1;
		while (it.hasNext()) {
			result += " " + toStringAuxiliary(it.next(), i++);
		}
		return result;
	}

	public String toStringAuxiliary(SignedFormulaNode node, int i) {
		return "Auxiliary #" + i + ": " + node.getContent();
	}

}
