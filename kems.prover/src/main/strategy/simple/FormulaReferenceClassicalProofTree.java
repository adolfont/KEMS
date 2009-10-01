/*
 * Created on 25/11/2004
 *
 */
package main.strategy.simple;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import logic.formulas.Arity;
import logic.formulas.CompositeFormula;
import logic.formulas.Formula;
import logic.formulas.FormulaList;
import logic.signedFormulas.FormulaSign;
import logic.signedFormulas.SignedFormula;
import logic.signedFormulas.SignedFormulaList;
import logic.structures.FormulaSignedFormulaMultiMap;
import logic.structures.FormulaSubformulaReferencesMultiMap;
import logic.structures.SignSet;
import main.proofTree.INode;
import main.proofTree.IProofTree;
import main.proofTree.SignedFormulaNode;
import main.proofTree.SignedFormulaNodeState;
import main.proofTree.origin.NamedOrigin;
import main.strategy.ClassicalProofTree;
import main.strategy.IClassicalProofTree;

/**
 * Class that represents proof trees where each formula that is part of a node
 * in the proof tree is mapped to the signed formulas that appear in a given
 * branch of the proof tree.
 * 
 * @author adolfo
 * 
 */
public class FormulaReferenceClassicalProofTree extends ClassicalProofTree {

	FormulaSignedFormulaMultiMap _localReferences;

	// TIREI EM 26/01/2005 - n�o vi necessidade
	// FormulaSignedFormulaMultiMap _localReferencesInCandidates;

	FormulaSubformulaReferencesMultiMap _subformulaLocalReferences;

	// FormulaSubformulaReferencesMultiMap _subformulaLocalReferencesInCandidates;

	private boolean _closed = false;

	/**
	 * @param aNode
	 */
	public FormulaReferenceClassicalProofTree(SignedFormulaNode aNode) {
		super(aNode);
		_localReferences = new FormulaSignedFormulaMultiMap();
		// TIREI EM 26/01/2005 - n�o vi necessidade
		// _localReferencesInCandidates = new FormulaSignedFormulaMultiMap();
		_subformulaLocalReferences = new FormulaSubformulaReferencesMultiMap();
		// _subformulaLocalReferencesInCandidates = new
		// FormulaSubformulaReferencesMultiMap();
		updateReferences(aNode);
	}

	/**
	 * @param aNode
	 */
	private void updateReferences(SignedFormulaNode aNode) {
		updateLocalReferences((SignedFormula) aNode.getContent(), _localReferences,
				_subformulaLocalReferences);
		// TIREI EM 26/01/2005 - n�o vi necessidade
		// updateLocalReferences((SignedFormula) aNode.getContent(),
		// _localReferencesInCandidates,
		// _subformulaLocalReferencesInCandidates);
	}

	protected void updateMultimap(SignedFormulaNode aNode) {
		SignedFormula sf = (SignedFormula) aNode.getContent();
		getFsmm().put(sf.getFormula(), sf.getSign());
		if (getFsmm().get(sf.getFormula()).size() == 2) {
			setClosingReason(sf);
			_closed = true;
			tryToCloseParent();
		}
	}

	public void tryToCloseParent() {
		// System.err.println (this.getBranchNumber()+ " fechou. Subindo para
		// talvez fechar");

		if ((_parent != null) && (this == _parent.getRight())
				&& !(((FormulaReferenceClassicalProofTree) _parent)._closed)) {
			((FormulaReferenceClassicalProofTree) _parent)._closed = true;
			// System.err.println (" Fechou "+ _parent.getBranchNumber());

			FormulaReferenceClassicalProofTree _previous = (FormulaReferenceClassicalProofTree) _parent;
			FormulaReferenceClassicalProofTree _current = ((FormulaReferenceClassicalProofTree) _parent
					.getParent());
			while (_current != null && !_current._closed) {
				// System.err.print(_current.getBranchNumber() + " ");
				if (_previous == (FormulaReferenceClassicalProofTree) _current.getRight()) {
					((FormulaReferenceClassicalProofTree) _current)._closed = true;
					_previous = _current;
					_current = (FormulaReferenceClassicalProofTree) _current.getParent();
				} else {
					// System.err.println (" N�o fechou "+
					// _current.getBranchNumber());
					break;
				}
			}

		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see strategy.ClassicalProofTree#isClosed()
	 */
	public boolean isClosed() {
		// TODO REPETIDO
		// if (!_closed){
		// _closed= (getLeft() != null
		// && ((FormulaReferenceClassicalProofTree) getLeft()).isClosed() &&
		// ((FormulaReferenceClassicalProofTree) getRight())
		// .isClosed());
		// }
		return _closed;
		// return super.isLocallyClosed()
		// || (getLeft() != null
		// && ((ClassicalProofTree) getLeft()).isClosed() &&
		// ((ClassicalProofTree) getRight())
		// .isClosed());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see proofTree.ClassicalProofTree#addLast(proofTree.Node)
	 */
	public void addLast(INode aNode) {
		// System.err.println("ADDED NODE "+aNode);
		super.addLast(aNode);
		updateReferences((SignedFormulaNode) aNode);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see strategy.ClassicalProofTree#makeInstance(proofTree.Node)
	 */
	protected IProofTree makeInstance(INode aNode) {
		return new FormulaReferenceClassicalProofTree((SignedFormulaNode) aNode);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see strategy.ClassicalProofTree#setOtherStructures(proofTree.ProofTree)
	 */
	protected void setOtherStructures(IProofTree pt, INode aNode) {
		// TODO TESTAR PARA VALER
		super.setOtherStructures(pt, aNode);
		((FormulaReferenceClassicalProofTree) pt).addStructures(this);
		((FormulaReferenceClassicalProofTree) pt).updateStructures(aNode);
		((FormulaReferenceClassicalProofTree) pt).updateReferences((SignedFormulaNode) aNode);
	}

	/**
	 * @param tree
	 */
	private void addStructures(FormulaReferenceClassicalProofTree tree) {

		List<Formula> l = new ArrayList<Formula>(tree.getFsmm().keySet());
		for (int i = 0; i < l.size(); i++) {
			SignSet ss = tree.getFsmm().get((Formula) l.get(i));

			Iterator<FormulaSign> it = ss.iterator();

			while (it.hasNext()) {
				this.getFsmm().put((Formula) l.get(i), (FormulaSign) it.next());

			}
		}

		List<SignedFormula> l2;
		l2 = new ArrayList<SignedFormula>(tree.getSignedFormulasToNodes().keySet());
		for (int i = 0; i < l2.size(); i++)
			this.getSignedFormulasToNodes()
					.put(l2.get(i), tree.getSignedFormulasToNodes().get(l2.get(i)));

	}

	// /**
	// * @param formula
	// */
	// private void updateLocalReferences(SignedFormula formula) {
	// _localReferences.put(formula.getFormula(), formula);
	// putAllSubformulasinLocalReferences(formula.getFormula(), formula);
	// }

	private void updateLocalReferences(SignedFormula signedFormula,
			FormulaSignedFormulaMultiMap references,
			FormulaSubformulaReferencesMultiMap subformulaReferences) {
		references.put(signedFormula.getFormula(), signedFormula);
		putAllSubformulasInLocalReferences(signedFormula.getFormula(), signedFormula, references,
				subformulaReferences);
	}

	// /**
	// * @param formula
	// * @param sf
	// */
	// private void putAllSubformulasinLocalReferences(Formula formula,
	// SignedFormula sf) {
	// if (formula.getImmediateSubformulas().size() != 0) {
	// for (int i = 0; i < formula.getImmediateSubformulas().size(); i++) {
	// //
	// _subformulaLocalReferences.put((Formula) formula
	// .getImmediateSubformulas().get(i), sf, formula);
	// _localReferences.put((Formula) formula
	// .getImmediateSubformulas().get(i), sf);
	// putAllSubformulasinLocalReferences((Formula) formula
	// .getImmediateSubformulas().get(i), sf);
	// }
	// }
	// }

	private void putAllSubformulasInLocalReferences(Formula formula, SignedFormula sf,
			FormulaSignedFormulaMultiMap references,
			FormulaSubformulaReferencesMultiMap subformulaReferences) {
		if (formula.getImmediateSubformulas().size() != 0) {
			for (int i = 0; i < formula.getImmediateSubformulas().size(); i++) {
				subformulaReferences.put((Formula) formula.getImmediateSubformulas().get(i), sf, formula);
				references.put((Formula) formula.getImmediateSubformulas().get(i), sf);
				putAllSubformulasInLocalReferences((Formula) formula.getImmediateSubformulas().get(i), sf,
						references, subformulaReferences);
			}
		}
	}

	/**
	 * @param formula
	 * @return all references to a formula in the current proof tree node
	 */
	public SignedFormulaList getLocalReferences(Formula formula) {

		if (_localReferences.get(formula) != null)
			return (SignedFormulaList) _localReferences.get(formula);
		else
			return new SignedFormulaList();
	}

	/**
	 * @param a
	 * @param sf
	 * @return all references to a formula in a subformula in the current proof
	 *         tree node
	 */
	public FormulaList getSubformulaLocalReferences(Formula a, SignedFormula sf) {
		if (_subformulaLocalReferences.get(a, sf) != null)
			return _subformulaLocalReferences.get(a, sf);
		else
			return new FormulaList();
	}

	/**
	 * Returns all references to a formula in the proof tree, from the current
	 * branch up.
	 * 
	 * @param formula
	 * @return
	 */
	public SignedFormulaList getParentReferences(Formula formula) {

		IProofTree current = this;
		SignedFormulaList sfl = new SignedFormulaList();
		do {
			sfl.addAll(((FormulaReferenceClassicalProofTree) current).getLocalReferences(formula));
			current = current.getParent();
		} while (current != null);

		return sfl;
	}

	/**
	 * @param formula
	 * @param sf
	 * @return all references to a formula in a signed formula in the current
	 *         branch or up.
	 */
	public FormulaList getSubformulaParentReferences(Formula formula, SignedFormula sf) {

		IProofTree current = this;
		FormulaList fl = new FormulaList();
		do {
			fl.addAll(((FormulaReferenceClassicalProofTree) current).getSubformulaLocalReferences(
					formula, sf));
			current = current.getParent();
		} while (current != null);

		return fl;
	}

	// /**
	// * @param f
	// * @return
	// */
	// public FormulaList getSubformulaParentReferences(Formula f) {
	// ProofTree current = this;
	// FormulaList fl = new FormulaList();
	// do {
	// fl.addAll(((FormulaReferenceClassicalProofTree) current)
	// .getSubformulaLocalReferences(f));
	// current = current.getParent();
	// } while (current != null);
	//
	// return fl;
	// }

	// /**
	// * @param a
	// * @param sf
	// * @return
	// */
	// public FormulaList getSubformulaLocalReferences(Formula a) {
	// if (_subformulaLocalReferences.get(a) != null)
	// return _subformulaLocalReferences.get(a);
	// else
	// return new FormulaList();
	// }

	// TIREI EM 26/01/2005 - n�o vi necessidade
	// /**
	// * @param topFormula
	// * @return
	// */
	// public SignedFormulaList getLocalReferencesInCandidates(Formula formula)
	// {
	// if (_localReferencesInCandidates.get(formula) != null)
	// return (SignedFormulaList) _localReferencesInCandidates
	// .get(formula);
	// else
	// return new SignedFormulaList();
	// }

	INode lastBJNode = null;

	/**
	 * @param node
	 */
	public void addBackjumpingFormula(SignedFormula sf) {
		SignedFormulaNode aNode = new SignedFormulaNode(sf, SignedFormulaNodeState.NOT_ANALYSED,
				NamedOrigin.EMPTY);

		// tries to do almost the same things that addLast does

		if (lastBJNode == null) {
			lastBJNode = getRoot();
		}

		// colocando como segundo em diante
		INode oldLastBJNodeNext = lastBJNode.getNext();
		aNode.setBranch(this);
		lastBJNode.setNext(aNode);
		aNode.setNext(oldLastBJNodeNext);
		// setLast(aNode);
		// setCurrent(getLast());
		lastBJNode = aNode;
		getSignedFormulasToNodes().put(sf, aNode);

		// colocando como �ltimo
		// aNode.setBranch(this);
		// getLast().setNext(aNode);
		// setLast(aNode);
		// setCurrent(getLast());
		// getSignedFormulasToNodes().put(sf, aNode);

		// TODO should be different for LFIs?
		// puts PB candidates in the right child
		if (sf.getFormula() instanceof CompositeFormula
				&& ((CompositeFormula) sf.getFormula()).getConnective().getArity().equals(Arity.BINARY)) {
			if (getRight() != null) {
				((IClassicalProofTree) getRight()).getPBCandidates().add(sf);
			}
		}
		updateReferences((SignedFormulaNode) aNode);

	}

	public void addLastSemAdicionar(SignedFormulaNode aNode) {
		updateStructures(aNode);
		updateReferences((SignedFormulaNode) aNode);
	}

	public void removeFromAllStructures(SignedFormula sformula) {
		getFsmm().remove(sformula.getFormula());
		getPBCandidates().remove(sformula);

		if (getNode(sformula).getPrevious() != null) {
			getNode(sformula).getPrevious().setNext(getNode(sformula).getNext());
		} else {
			setRoot(getNode(sformula).getNext());
		}
		getSignedFormulasToNodes().remove(sformula);

	}

	public void remove(SignedFormulaNode node) {
		SignedFormula sformula = ((SignedFormula) node.getContent());
		getFsmm().remove(sformula.getFormula());
		// logger.debug("Removing "+node);
		getPBCandidates().remove(sformula);

		if (node.getPrevious() != null) {
			node.getPrevious().setNext(node.getNext());
		} else {
			setRoot(node.getNext());
		}
		getSignedFormulasToNodes().remove(sformula);

	}

	public void removeReferencesInStructures(SignedFormulaNode node) {
		SignedFormula sformula = ((SignedFormula) node.getContent());
		getFsmm().remove(sformula.getFormula());

		getPBCandidates().remove(sformula);
		getSignedFormulasToNodes().remove(sformula);

		SignedFormula sf = (SignedFormula) node.getContent();
		_localReferences.remove(sf.getFormula(), sf);
		removeAllSubformulasInLocalReferences(sf.getFormula(), sf, _localReferences,
				_subformulaLocalReferences);
	}

	private void removeAllSubformulasInLocalReferences(Formula formula, SignedFormula sf,
			FormulaSignedFormulaMultiMap references,
			FormulaSubformulaReferencesMultiMap subformulaReferences) {
		if (formula.getImmediateSubformulas().size() != 0) {
			for (int i = 0; i < formula.getImmediateSubformulas().size(); i++) {
				subformulaReferences
						.remove((Formula) formula.getImmediateSubformulas().get(i), sf, formula);
				references.remove((Formula) formula.getImmediateSubformulas().get(i), sf);
				removeAllSubformulasInLocalReferences((Formula) formula.getImmediateSubformulas().get(i),
						sf, references, subformulaReferences);
			}
		}
	}

	/**
	 * @return
	 */
	public String showReferences() {
		System.err.println("sftonodes:" + getSignedFormulasToNodes());
		System.err.println("localrefe:" + _localReferences);
		System.err.println("subflocalrefe:" + _subformulaLocalReferences);
		// System.err.println("subfolocalrefincand:"
		// + _subformulaLocalReferencesInCandidates);
		return "";
	}

	/**
	 * @param b
	 */
	public void setClosed(boolean b) {
		_closed = b;
	}

	public FormulaSignedFormulaMultiMap getAllLocalReferences() {
		return _localReferences;
	}

	public FormulaSubformulaReferencesMultiMap getAllSubformulaLocalReferences() {
		return _subformulaLocalReferences;
	}

	protected void removeOtherReferencesInChildren(SignedFormula sf) {
		super.removeOtherReferencesInChildren(sf);
		_localReferences.removeAll(sf);
		_subformulaLocalReferences.removeAll(sf);

	}

	public void removeLeft() {
		// if it has children, first remove its children (recursively)
		if (getLeft().getLeft() != null) {
			// System.err.println(getLeft().getRoot());
			((FormulaReferenceClassicalProofTree) getLeft()).removeLeft();
			// System.err.println(getLeft().getRoot());
			((FormulaReferenceClassicalProofTree) getLeft()).removeRight();
		}

		INode current = getLeft().getRoot();

		// remove all except root signed formula node
		while (current.getNext() != null) {
			current = current.getNext();
			// System.err.println("REMOVING "+(SignedFormula)((SignedFormulaNode)current).getContent());
			((FormulaReferenceClassicalProofTree) getLeft())
					.removeSignedFormula((SignedFormula) ((SignedFormulaNode) current).getContent());
		}

		// removes root

		((FormulaReferenceClassicalProofTree) getLeft())
				.removeSignedFormula((SignedFormula) ((SignedFormulaNode) getLeft().getRoot()).getContent());

		// System.err.println(getLeft().getRoot());
		// System.err.println(getRight().getRoot());

		// recoverInPbCandidatesHereAndAbove((SignedFormulaNode)
		// getLeft().getRoot())

	}

	public void removeRight() {
		if (getRight().getLeft() != null) {
			((FormulaReferenceClassicalProofTree) getRight()).removeLeft();
			((FormulaReferenceClassicalProofTree) getRight()).removeRight();
		}

		INode current = getRight().getRoot();

		// remove all except root signed formula node
		while (current.getNext() != null) {
			current = current.getNext();
			// System.err.println("REMOVING "+(SignedFormula)((SignedFormulaNode)current).getContent());
			((FormulaReferenceClassicalProofTree) getRight())
					.removeSignedFormula((SignedFormula) ((SignedFormulaNode) current).getContent());
		}

		// removes root
		((FormulaReferenceClassicalProofTree) getRight())
				.removeSignedFormula((SignedFormula) ((SignedFormulaNode) getRight().getRoot())
						.getContent());

	}
}