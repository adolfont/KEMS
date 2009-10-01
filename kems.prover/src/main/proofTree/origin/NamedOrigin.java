/*
 * Created on 21/10/2004
 *
 */
package main.proofTree.origin;

import java.util.ArrayList;
import java.util.List;

import main.proofTree.SignedFormulaNode;
import rules.IRule;

/**
 * Classs that represents the origins of SignedFormulaNodes.
 * 
 * @author Adolfo Gustavo Serra Seca Neto
 * 
 */
public class NamedOrigin implements IOrigin {

	private String message;

	public static final NamedOrigin PROBLEM = new NamedOrigin("Problem");

	public static final NamedOrigin DEFINITION = new NamedOrigin("Definition");

	public static final NamedOrigin EMPTY = new NamedOrigin("No origin recorded");

	public static final NamedOrigin UNKNOWN = new NamedOrigin("Unknown");

	public static final NamedOrigin BACKJUMPING = new NamedOrigin("Moved by backjumping");

	public static final NamedOrigin LEARNING = new NamedOrigin("Introduced by learning");

	public static List<SignedFormulaNode> EMPTY_SFN_LIST;

	private NamedOrigin(String message) {
		this.message = message;
	}

	protected NamedOrigin() {
	};

	public String getName() {
		return message;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return message;
	}

	public IRule getRule() {
		return null;
	}

	public SignedFormulaNode getMain() {
		return null;
	}

	public List<SignedFormulaNode> getAuxiliaries() {
		if (EMPTY_SFN_LIST == null)
			EMPTY_SFN_LIST = new ArrayList<SignedFormulaNode>();
		return EMPTY_SFN_LIST;
	}
}