/*
 * Created on 15/11/2005
 *
 */
package main.tableau.verifier;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import logic.signedFormulas.SignedFormula;
import main.proofTree.SignedFormulaNode;
import main.proofTree.SignedFormulaNodeState;
import main.proofTree.origin.IOrigin;
import main.proofTree.origin.SignedFormulaNodeOrigin;
import main.strategy.IClassicalProofTree;

/**
 * [CLASS_DESCRIPTION]
 * 
 * @author Adolfo Gustavo Serra Seca Neto
 * 
 */
public class ExtendedNode extends SignedFormulaNode {

	private Boolean verified;

	private Boolean used;

	/**
	 * @param sfn
	 * @param ept
	 */
	public ExtendedNode(SignedFormulaNode node, IClassicalProofTree ept) {
		super((SignedFormula) node.getContent(), (SignedFormulaNodeState) node.getState(), null);

		if (node.getOrigin() instanceof SignedFormulaNodeOrigin) {

			List<SignedFormulaNode> auxiliaries = node.getOrigin().getAuxiliaries();
			Iterator<SignedFormulaNode> itAuxies = auxiliaries.iterator();
			List<SignedFormulaNode> newAuxies = new ArrayList<SignedFormulaNode>();
			while (itAuxies.hasNext()) {
				SignedFormulaNode sfn = itAuxies.next();
				if (sfn != null && sfn.getContent() != null)
					newAuxies.add(ept.getNode((SignedFormula) sfn.getContent()));
			}

			setOrigin(new SignedFormulaNodeOrigin(node.getOrigin().getRule(),

			// main
					node.getOrigin().getMain() != null ? ept.getNode((SignedFormula) node.getOrigin()
							.getMain().getContent()) : null,
					// auxies
					newAuxies));
		} else {
			setOrigin(node.getOrigin());
		}

		verified = null;
		used = null;
	}

	/**
	 * @param sf
	 * @param state
	 * @param origin
	 */
	public ExtendedNode(SignedFormula sf, SignedFormulaNodeState state, IOrigin origin) {
		super(sf, state, origin);
	}

	/**
	 * @param b
	 */
	public void setVerified(boolean b) {
		verified = b ? Boolean.TRUE : Boolean.FALSE;
	}

	public Boolean getVerified() {
		return verified;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see main.proofTree.SignedFormulaNode#toString()
	 */
	public String toString() {
		return super.toString() + " Verified: " + verified;
	}

	public void setOrigin(IOrigin o) {
		super.setOrigin(o);
	}

	/**
	 * @param b
	 */
	public void setUsed(boolean b) {
		this.used = b ? Boolean.TRUE : Boolean.FALSE;
	}

	public Boolean getUsed() {
		return used;
	}
}
