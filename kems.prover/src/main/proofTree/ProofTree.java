/*
 * Created on 24/09/2004
 *
 */
package main.proofTree;

import java.util.ArrayList;
import java.util.List;

import logic.signedFormulas.SignedFormula;
import main.proofTree.iterator.IProofTreeAdvancedIterator;
import main.proofTree.iterator.IProofTreeBackwardNodeIterator;
import main.proofTree.iterator.IProofTreeBasicIterator;
import main.proofTree.iterator.IProofTreeVeryBasicIterator;
import main.proofTree.iterator.ProofTreeBackwardLocalIterator;
import main.proofTree.iterator.ProofTreeBackwardNodeIterator;
import main.proofTree.iterator.ProofTreeGlobalIterator;
import main.proofTree.iterator.ProofTreeLocalIterator;
import main.proofTree.iterator.ProofTreeTopDownGlobalIterator;

// import org.jdom.Element;

/**
 * Class that represents proof trees that are built by a tableau method when
 * constructing a tableau proof.
 * 
 * @author Adolfo Gustavo Serra Seca Neto
 * 
 */
public abstract class ProofTree implements IProofTree {
	// basic attributes of a proof tree
	protected IProofTree _parent, // parent proof tree (can be
			// null)
			_left, // left proof tree (can be null)
			_right, // right proof tree (can be null)
			_currentPT; // current proof tree (can be null)

	protected INode _root, _last, _current;

	protected ProofTree() {
	};

	/**
	 * Creates a proof tree with an initial node.
	 */
	public ProofTree(INode aNode) {
		_root = aNode;
		aNode.setBranch(this);
		_last = _root;
		_current = _root;
		_currentPT = this;
	}

	/**
	 * Adds a node as the last node
	 */
	public void addLast(INode aNode) {
		getLast().setNext(aNode);
		aNode.setPrevious(getLast());
		aNode.setBranch(this);
		// aNode.setNext(null);
		setLast(aNode);
		setCurrent(getLast());
	}

	/**
	 * Creates a new ProofTree with the node and puts it as the left subtree of
	 * the current tree.
	 * 
	 * @param aNode
	 * @return
	 */
	public final IProofTree addLeft(INode aNode) {
		_left = makeInstance(aNode);
		setReferences(aNode, (ProofTree) _left);
		setOtherStructures(_left, aNode);
		return _left;
	}

	/**
	 * Creates a new ProofTree with the node and puts it as the right subtree of
	 * the current tree.
	 * 
	 * @param aNode
	 * @return
	 */
	public final IProofTree addRight(INode aNode) {
		_right = makeInstance(aNode);
		setReferences(aNode, (ProofTree) _right);
		setOtherStructures(_right, aNode);

		return _right;
	}

	public final void removeSignedFormula(SignedFormula sf) {
		if (_root.getContent() == sf) {
			INode newRoot = _root.getNext();
			if (newRoot != null) {
				newRoot.setPrevious(null);
			}
			if (_current == _root) {
				_current = newRoot;
			}
			if (_last == _root) {
				_last = newRoot;
			}
			_root = newRoot;
			removeOtherReferencesInChildren(sf);

		} else {

			INode currentInSearch = _root.getNext();

			while (currentInSearch != null) {
				if (currentInSearch.getContent() == sf) {

					INode oneAfter = currentInSearch.getNext();
					currentInSearch.getPrevious().setNext(oneAfter);
					if (oneAfter != null) {
						oneAfter.setPrevious(currentInSearch.getPrevious());

						if (_current == currentInSearch) {
							_current = oneAfter;
						}
						if (_last == currentInSearch) {
							_last = oneAfter;
						}
					} else {
						if (_current == currentInSearch) {
							_current = currentInSearch.getPrevious();
						}
						if (_last == currentInSearch) {
							_last = currentInSearch.getPrevious();
						}
					}

					removeOtherReferencesInChildren(sf);

					break;

				} else {
					currentInSearch = currentInSearch.getNext();
				}
			}

		}

	}

	/** IMPLEMENTADO VAZIO EM SUBCLASSES *** */
	protected abstract void removeOtherReferencesInChildren(SignedFormula sf);

	public List<INode> getNodeSequence() {
		List<INode> l = new ArrayList<INode>();

		l.add(_root);
		INode current = _root;

		while (current.getNext() != null) {
			l.add(current.getNext());
			if (current.getNext().getPrevious() != current) {
				System.err.println("ERRO");
			}
			current = current.getNext();
		}

		return l;
	}

	/**
	 * @param aNode
	 * @param right
	 */
	private final void setReferences(INode aNode, IProofTree newTree) {
		aNode.setBranch(newTree);
		((ProofTree) newTree).setParent(this);
		_currentPT = newTree;
	}

	/**
	 * @param aNode
	 * @return an instance of a subclass of ProofTree
	 */
	protected abstract IProofTree makeInstance(INode aNode);

	/**
	 * sets other strcutures of a subclass of ProofTree
	 * 
	 * @param pt
	 * @param aNode
	 */
	protected abstract void setOtherStructures(IProofTree pt, INode aNode);

	/**
	 * Returns the root of the tree.
	 */
	public INode getRoot() {
		return _root;
	}

	protected void setRoot(INode newRoot) {
		_root = newRoot;
	}

	/**
	 * Puts pt as parent of this proof tree.
	 */
	protected void setParent(IProofTree pt) {
		_parent = pt;
	}

	/**
	 * @return the parent of this proof tree or null.
	 */
	public IProofTree getParent() {
		return _parent;
	}

	/**
	 * @return the left subtree of this proof tree or null.
	 */
	public IProofTree getLeft() {
		return _left;
	}

	/**
	 * @return the right subtree of this proof tree or null.
	 */
	public IProofTree getRight() {
		return _right;
	}

	/**
	 * @return the last node added.
	 */
	public INode getLast() {
		return _last;
	}

	/**
	 * @param _currentpt
	 *            The _currentPT to set.
	 */
	public void setCurrentProofTree(IProofTree _currentpt) {
		_currentPT = _currentpt;
	}

	/**
	 * @param other
	 *            proof tree
	 */
	public void setLeft(IProofTree other) {
		_left = other;
		((ProofTree) other)._parent = this;
	}

	/**
	 * @param other
	 *            proof tree
	 */
	public void setRight(IProofTree other) {
		_right = other;
		((ProofTree) other)._parent = this;
	}

	/**
	 * @return current node
	 */
	public INode getCurrent() {
		return _current;
	}

	/**
	 * @return local iterator (no other branches)
	 */
	public IProofTreeBasicIterator getLocalIterator() {
		return new ProofTreeLocalIterator(this);
	}

	/**
	 * @return backward local iterator
	 */
	public IProofTreeBasicIterator getBackwardLocalIterator() {
		return new ProofTreeBackwardLocalIterator(this);
	}

	/**
	 * @return backward global iterator
	 */
	public IProofTreeVeryBasicIterator getTopDownIterator() {
		return new ProofTreeTopDownGlobalIterator(this);
	}

	/**
	 * @return global iterator (other branches can be seen)
	 */
	public IProofTreeAdvancedIterator getGlobalIterator() {
		return new ProofTreeGlobalIterator(this);
	}

	/**
	 * @return global iterator (other branches can be seen)
	 */
	public IProofTreeBackwardNodeIterator getBackwardNodeIterator() {
		return new ProofTreeBackwardNodeIterator(this);
	}

	public int getNumberOfNodes() {
		int result = 0;
		INode current = _root;

		do {
			result = result + 1;
			current = current.getNext();
		} while (current != null);

		if (_left != null) {
			result = result + _left.getNumberOfNodes()
					+ _right.getNumberOfNodes();
		}

		return result;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return this.branchToString("", 0);
	}

	private String branchToString(String prefix, int spaces) {

		String result = "";

		result = spaces(spaces) + result + this.getStatus();
		INode current = getRoot();

		do {
			result = result + spaces(spaces) + prefix
					+ (spaces > 0 ? spaces + ":" : "") + current.toString()
					+ System.getProperty("line.separator");
			current = current.getNext();
		} while (current != null);

		if (getLeft() != null) {
			result = result
					+ ((ProofTree) getLeft()).branchToString("l", spaces + 1)
					+ ((ProofTree) getRight()).branchToString("r", spaces + 1);
		}

		return result;

	}

	/**
	 * @return the status of the proof tree (when applicable)
	 */
	protected String getStatus() {
		return "a branch\n";
	}

	/**
	 * @return a short version of the string that shows the proof tree
	 */
	public String toStringShort() {
		return this.branchToStringShort("", 0);
	}

	private String branchToStringShort(String prefix, int spaces) {

		String result = "";

		result = spaces(spaces) + result + this.getStatus();
		INode current = getRoot();

		do {
			result = result + spaces(spaces) + prefix
					+ (spaces > 0 ? spaces + ":" : "")
					+ current.getContent().toString()
					+ System.getProperty("line.separator");
			current = current.getNext();
		} while (current != null);

		if (_left != null) {
			result = result
					+ ((ProofTree) _left).branchToStringShort("l", spaces + 1)
					+ ((ProofTree) _right).branchToStringShort("r", spaces + 1);
		}

		return result;

	}

	/**
	 * @param i
	 * @return a number of spaces
	 */
	private String spaces(int i) {
		String result = "";

		for (int j = 0; j < i; j++) {
			result = result + " ";
		}
		return result;
	}

	// // XML aspect
	// public Element asXMLElement() {
	//
	// Element root = new Element("proofTree");
	//
	// Element branches = new Element("branches");
	// branches.setText(Integer.toString(getBranches()));
	// root.addContent(branches);
	//
	// Element height = new Element("height");
	// height.setText(Integer.toString(((ProofTree) this).getHeight()));
	// root.addContent(height);
	//
	// Element nodes = new Element("nodes");
	// nodes.setText(Integer.toString(getNumberOfNodes()));
	// root.addContent(nodes);
	//
	// Element status = new Element("status");
	// status.setText(getStatus());
	// root.addContent(status);
	//
	// root.addContent(getNodesAsXMLElements());
	//
	// return root;
	//
	// }
	//
	// private Element getNodesAsXMLElements() {
	//
	// INode current = _root;
	//
	// Element tree = new Element("tree");
	//
	// do {
	// tree.addContent(current.asXMLElement());
	// current = current.getNext();
	// } while (current != null);
	//
	// if (_left != null) {
	//
	// Element left = new Element("left");
	// left.addContent(((ProofTree) _left).getNodesAsXMLElements());
	// tree.addContent(left);
	// Element right = new Element("right");
	// right.addContent(((ProofTree) _right).getNodesAsXMLElements());
	// tree.addContent(right);
	//
	// }
	//
	// return tree;
	// }

	/**
	 * @param last
	 */
	protected void setCurrent(INode node) {
		_current = node;

	}

	/**
	 * @param node
	 */
	protected void setLast(INode node) {
		_last = node;

	}

}