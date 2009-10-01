/*
 * Created on 02/11/2005
 *  
 */
package proverinterface.proofviewer;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import logic.formulas.Formula;
import logic.signedFormulas.SignedFormula;
import logic.signedFormulas.SignedFormulaList;
import logic.valuation.AbstractValuation;
import main.proofTree.INode;
import main.proofTree.IProofTree;
import main.proofTree.Node;
import main.proofTree.SignedFormulaNode;
import main.proofTree.iterator.IProofTreeBackwardNodeIterator;
import main.proofTree.iterator.IProofTreeBasicIterator;
import main.proofTree.iterator.IProofTreeVeryBasicIterator;
import main.proofTree.origin.NamedOrigin;
import main.strategy.IClassicalProofTree;
import main.tableau.IProof;
import main.tableau.verifier.ExtendedProof;
import main.tableau.verifier.ExtendedProofTree;
import proverinterface.ProverInterface;

/**
 * A Panel that shows a full branch of a proof tree.
 * 
 * @author Adolfo Gustavo Serra Seca Neto
 * 
 */
public class InteractiveProofPane extends JPanel implements ActionListener,
		MouseListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2966437180162772814L;

	/** the parent proof viewer */
	private ProofViewer proofViewer;

	/** the current last proof tree node */
	private IClassicalProofTree lastNode;

	/** the panel preffered size */
	private Dimension area;

	/** all nodes in a branch */
	private LinkedList<IProofTree> branchNodes;

	/**
	 * If true, all the objects are removed and reincluded. This is necessary
	 * when there is some change in attributes.
	 */
	private boolean changed = true;

	private IProof proof;

	private Map<SignedFormulaNode,FormulaButton> formulasToButtons;

	private JFrame proofInfoFrame;

	// TODO testar se muda qdo muda a prova

	/**
	 * Creates a StructuredProofPane for a ProofViewer
	 * 
	 * @param proofViewer -
	 *            the proofViewer that provides the proof and parameters.
	 */
	public InteractiveProofPane(ProofViewer proofViewer) {
		setLayout(new GridLayout(0, 1));
		this.proofViewer = proofViewer;
		area = proofViewer.getArea();
		this.setPreferredSize(area);
		this.revalidate();

		this.addMouseListener(this);

		proofInfoFrame = new JFrame();
		proofInfoFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.Component#paint(java.awt.Graphics)
	 */
	public void paint(Graphics g) {
		this.setPreferredSize(area);
		// if some parameter that affects components has changed
		if (isChanged()) {
			formulasToButtons = new HashMap<SignedFormulaNode, FormulaButton>();
			setFont(proofViewer.getTextFont().deriveFont(
					proofViewer.getFontSize()));
			this.setPreferredSize(area);
			removeAll();
			showProofTree(g);
			setChanged(false);
		}
		super.paint(g);
		this.revalidate();
	}

	/**
	 * Sets the proof to be exhibited and repaints the panel
	 * 
	 * @param proof
	 */
	public void setProof(IProof proof) {
		this.proof = proof;
		createBranchNodeList(proof);

		changed = true;
		repaint();
		proofViewer.getFullViewProofPane().repaint();
	}

	public void setBranch(IClassicalProofTree branch) {
		createBranchNodeList(branch);

		changed = true;
		repaint();
		proofViewer.getFullViewProofPane().repaint();
	}

	/**
	 * @param proof
	 */
	private void createBranchNodeList(IProof proof) {
		branchNodes = new LinkedList<IProofTree>();
		lastNode = (IClassicalProofTree) proof.getProofTree();
		branchNodes.addLast(lastNode);

		// goes to the leftmost node
		while (lastNode.getLeft() != null) {
			lastNode = (IClassicalProofTree) lastNode.getLeft();
			branchNodes.addLast(lastNode);
		}
	}

	private void createBranchNodeList(IClassicalProofTree branch) {
		branchNodes = new LinkedList<IProofTree>();
		lastNode = branch;

		IProofTreeBackwardNodeIterator it = branch.getBackwardNodeIterator();

		while (it.hasPrevious()) {
			branchNodes.addFirst(it.previous());
		}
	}

	/**
	 * Shows a proof tree
	 * 
	 * @param g
	 */
	private void showProofTree(Graphics g) {
		try {
			if (lastNode != null) {
				showNodeAndChildren(g, lastNode);
			} else {
				removeAll();
				repaint();
			}
		} catch (Throwable e) {
			if (e instanceof OutOfMemoryError) {
				proofViewer.getProverInterface().showErrorMessage(
						ProverInterface.OUT_OF_MEMORY_MESSAGE);
			} else {
				proofViewer.getProverInterface().showErrorMessage(
						e.getMessage());

			}
			close();
		}
	}

	private void showNodeAndChildren(Graphics g, IClassicalProofTree current) {
		if (current != null) {

			// shows branch formulas
			JPanel rootNode = new JPanel(new GridLayout(0, 1));

			rootNode.add(createOpenInformationFrameButton(current));

			if (((IClassicalProofTree) proof.getProofTree())
					.getOpenCompletedBranch() != null) {
				rootNode.add(createOpenCompletedBranchButton(g));
			}

			Iterator<IProofTree> it = branchNodes.iterator();
			while (it.hasNext()) {
				current = (IClassicalProofTree) it.next();
				rootNode = showNode(g, current, rootNode);
			}

			add(rootNode);

		}

	}

	/**
	 * @return
	 */
	private Component createOpenCompletedBranchButton(Graphics g) {
		// button shown
		JButton openCompletedBranchButton = new JButton(
				"Open first completed open branch");
		openCompletedBranchButton.setFont(proofViewer.getFont());
		openCompletedBranchButton.setBorder(BorderFactory
				.createLineBorder(Color.red));

		openCompletedBranchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setBranch(((IClassicalProofTree) proof.getProofTree())
						.getOpenCompletedBranch());
			}
		});

		return openCompletedBranchButton;
	}

	/**
	 * @param current
	 */
	private JButton createOpenInformationFrameButton(IClassicalProofTree current) {
		String branchName = ((ExtendedProofTree) lastNode).getBranchId()
				.toString().equals("") ? "root"
				: ((ExtendedProofTree) lastNode).getBranchId().toString();

		// button shown
		JButton openInfoFrameButton = new JButton(
				"Show information about proof and current branch: "
						+ branchName);
		openInfoFrameButton.setFont(proofViewer.getFont());
		openInfoFrameButton
				.setBorder(BorderFactory.createLineBorder(Color.red));

		// frame
		JPanel proofInfoPanel = new JPanel(new GridLayout(0, 1));

		JLabel strategyIdentificationLabel = new JLabel("Strategy used: "
				+ ((ExtendedProof) proof).getProverConfiguration());
		strategyIdentificationLabel
				.setToolTipText("The strategy configuration used for this proof.");

		JLabel closedProofLabel = new JLabel(
				"Tableau is "
						+ (((IClassicalProofTree) proof.getProofTree())
								.isClosed() ? "closed" : "open"));
		closedProofLabel
				.setToolTipText("A tableau is closed when all its branches are closed.");
		NumberFormat nf = NumberFormat.getInstance(Locale.US);
		nf.setMinimumFractionDigits(3);
		String number = nf
				.format(((ExtendedProof) proof).getTimeSpent() / 1000.0);
		JLabel timeSpentLabel = new JLabel("Time spent: " + number + " s");
		JLabel verifiedLabel = new JLabel(
				((ExtendedProof) proof).getVerificationResult().booleanValue() ? "Verification successful"
						: "Verification failed!");
		verifiedLabel
				.setToolTipText("A proof verification confirms that all signed formulas "
						+ "in the tableau could be generated by applying rules on problem formulas.");
		JLabel usedQuantityLabel = new JLabel("Used: "
				+ ((ExtendedProof) proof).getUsedQuantity());
		JLabel nodesQuantityLabel = new JLabel("Total:"
				+ ((ExtendedProof) proof).getNodesQuantity());

		JPanel subProofInfoPanel = new JPanel(new GridLayout(0, 3));
		subProofInfoPanel.add(closedProofLabel);
		subProofInfoPanel.add(timeSpentLabel);
		subProofInfoPanel.add(verifiedLabel);
		subProofInfoPanel.add(usedQuantityLabel);
		subProofInfoPanel.add(nodesQuantityLabel);

		JPanel auxProofInfoPanel = new JPanel(new GridLayout(0, 1));
		auxProofInfoPanel.setBorder(BorderFactory.createTitledBorder("Proof"));
		auxProofInfoPanel.add(subProofInfoPanel);
		auxProofInfoPanel.add(strategyIdentificationLabel);
		proofInfoPanel.add(auxProofInfoPanel);

		JLabel branchLabel = new JLabel("Name: " + branchName);
		branchLabel
				.setToolTipText("A branch name is either \"Root\" or composed "
						+ "of \"l\"\'s (for left sub-branches) and \"r\"\'s (for right sub-branches)");
		JLabel closedLabel = new JLabel(
				(current.isClosed() ? "Closed" : "Open"));
		closedLabel
				.setToolTipText("A branch is closed when it has T(X) and F(X) for some formula X.");
		JLabel completedLabel = new JLabel(
				current.isClosed()?"":(
				(((IClassicalProofTree) proof.getProofTree()).isCompleted() ? "Completed" : "Not completed")));
// TODO CHANGE DONE IN 11/05/2009
		//		JLabel completedLabel = new JLabel(
//				current.isClosed()?"":(
//				(lastNode.isCompleted() ? "Completed" : "Not completed")));
		
//		System.out.println("COMPLETED:"+lastNode.isCompleted());
//		System.out.println(((IClassicalProofTree) proof.getProofTree()).isCompleted());
//		System.out.println(((IClassicalProofTree) proof.getProofTree()));
//		System.out.println("COB:"+((IClassicalProofTree) proof.getProofTree()).getOpenCompletedBranch());
		completedLabel
				.setToolTipText("A branch is completed when no further rule can be applied (except useless PB).");

		JPanel subBranchInfoPanel = new JPanel(new GridLayout(0, 3));
		subBranchInfoPanel.setBorder(BorderFactory
				.createTitledBorder("Current branch"));
		subBranchInfoPanel.add(branchLabel);
		subBranchInfoPanel.add(closedLabel);
		subBranchInfoPanel.add(completedLabel);
		proofInfoPanel.add(subBranchInfoPanel);

		proofInfoPanel.addMouseListener(proofViewer.getMouseListener());
		// branchLabel.setFont(this.getFont());

		JPanel branchPanel = new JPanel(new GridLayout(0, 1));
		branchPanel.add(proofInfoPanel);

		// shows a valuation if it is an open and completed branch
		JPanel valuationPanel = null;
		if (lastNode == ((IClassicalProofTree) proof.getProofTree())
				.getOpenCompletedBranch()
				&& proofViewer.getValuation() != null) {
			valuationPanel = createValuationPanel();
		}
		if (valuationPanel != null) {
			branchPanel.add(valuationPanel);

		}

		proofInfoFrame
				.setTitle("Information about current branch - Proof Viewer #"
						+ proofViewer.getInstanceNumber());
		proofInfoFrame.setContentPane(branchPanel);
		proofInfoFrame.pack();
		// proofInfoFrame.setSize(new Dimension(500, 130));
		// proofInfoFrame.setVisible(true);

		openInfoFrameButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				proofInfoFrame.setVisible(true);
			}
		});

		return openInfoFrameButton;

	}

	/**
	 * @return
	 */
	private JPanel createValuationPanel() {
		JPanel result = new JPanel(new GridLayout(0, 4));
		result.setBorder(BorderFactory
				.createTitledBorder("Counter-model Valuation"));
		AbstractValuation valuation = proofViewer.getValuation();

		// goes through the open completed branch
		IProofTreeVeryBasicIterator it = ((IClassicalProofTree) proof
				.getProofTree()).getOpenCompletedBranch().getTopDownIterator();
		SignedFormulaList sfl = new SignedFormulaList();
		while (it.hasNext()) {
			sfl.add((SignedFormula) ((SignedFormulaNode) it.next())
					.getContent());
		}

		// creates the valuation
		if (sfl.size() > 0) {
			valuation.create(sfl);

			List<Formula> l = new ArrayList<Formula>(valuation.keySet());
			Collections.sort(l, new Comparator<Formula>() {
				public int compare(Formula arg0, Formula arg1) {
					return arg0.toString().compareTo(arg1.toString());
				}
			});

			Iterator<Formula> itVal = l.iterator();
			while (itVal.hasNext()) {
				Formula f = (Formula) itVal.next();
				result.add(new JLabel("v(" + f + ")="
						+ (valuation.getValue(f) ? "1" : "0")));
			}
		}

		return result;
	}

	/**
	 * @param g
	 * @param current
	 * @param size
	 */
	private JPanel showNode(Graphics g, IProofTree current, JPanel thisNode) {

		boolean isFirst = true;
		IProofTreeBasicIterator it = current.getLocalIterator();
		while (it.hasNext()) {
			if (isFirst) {
			}
			g.setColor(proofViewer.getNodeColor());

			Node n = (Node) it.next();
			// String s = proofViewer.isOriginEnabled() ? n.toString()
			// : ((SignedFormulaNode) n).getContent().toString();

			SignedFormulaNode sfn = (SignedFormulaNode) n;

			// if this is the first node in the branch
			// but this is not the first branch
			if (isFirst && (current != proof.getProofTree())) {
				JPanel aPanel = new JPanel(new GridLayout(0, 2));
				if (isLeftBranch(current.getRoot())) {
					JComponent jb = createFormulaButton(sfn, true);
					jb.setFont(this.getFont());
					aPanel.add(jb);
					JComponent fb = createFormulaButton(
							(SignedFormulaNode) current.getParent().getRight()
									.getRoot(), false);
					fb.setBackground(proofViewer
							.getUnselectedFormulaButtonColor());
					fb.setFont(this.getFont());
					aPanel.add(fb);
					((JButton) fb).addActionListener(this);
				} else {
					JComponent fb = createFormulaButton(
							(SignedFormulaNode) current.getParent().getLeft()
									.getRoot(), false);
					fb.setBackground(proofViewer
							.getUnselectedFormulaButtonColor());
					fb.setFont(this.getFont());
					aPanel.add(fb);
					JComponent jb = createFormulaButton(sfn, true);
					jb.setFont(this.getFont());
					aPanel.add(jb);
					((JButton) fb).addActionListener(this);
				}
				thisNode.add(aPanel);
			} else {
				JComponent fb = createFormulaButton(sfn, true);
				fb.setFont(this.getFont());
				thisNode.add(fb);
			}
			isFirst = false;

		}
		return thisNode;

	}

	private boolean isLeftBranch(INode node) {
		return (node.getBranch() == node.getBranch().getParent().getLeft());
	}

	private JComponent createFormulaButton(SignedFormulaNode sfn, boolean option) {
		FormulaButton fb = new FormulaButton(FormulaButton.createStringArray(
				sfn, proofViewer.getMaxStringLength()), sfn);
		if (option) {
			fb.addMouseListener(this);
			formulasToButtons.put(sfn, fb);
			fb.setBackground(proofViewer.getSelectedFormulaButtonColor());
		}
		return fb;
	}

	public boolean isChanged() {
		return changed;
	}

	public void setChanged(boolean changed) {
		this.changed = changed;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() instanceof FormulaButton) {
			FormulaButton fb = (FormulaButton) e.getSource();
			SignedFormulaNode sfn = fb.getSignedFormulaNode();

			changePathAndRepaint(sfn);

		}

	}

	public void changePathAndRepaint(SignedFormulaNode sfn) {
		changePath(sfn);
		changed = true;
		repaint();
		proofViewer.getFullViewProofPane().repaint();
	}

	/**
	 * @param sfn
	 * @return
	 */
	private void changePath(SignedFormulaNode sfn) {
		Iterator<IProofTree> it = branchNodes.iterator();
		IClassicalProofTree current = (IClassicalProofTree) it.next();
		LinkedList<IProofTree> branchNodesNew = new LinkedList<IProofTree>();
		branchNodesNew.add(current);

		while (it.hasNext()) {
			current = (IClassicalProofTree) it.next();

			if (sfn == current.getParent().getRight().getRoot()) {
				current = (IClassicalProofTree) current.getParent().getRight();
				branchNodesNew.add(current);
				break;
			} else if (sfn == current.getParent().getLeft().getRoot()) {
				current = (IClassicalProofTree) current.getParent().getLeft();
				branchNodesNew.add(current);
				break;
			} else {
				branchNodesNew.add(current);
			}
		}

		// goes to the leftmost node
		while (current.getLeft() != null) {
			current = (IClassicalProofTree) current.getLeft();
			branchNodesNew.addLast(current);
		}

		branchNodes = branchNodesNew;
		lastNode = current;

	}

	/**
	 * Setter for the area property.
	 * 
	 * @param d -
	 *            a new Dimension
	 */
	public void setArea(Dimension d) {
		area = d;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
	 */
	public void mouseClicked(MouseEvent e) {
		clearAllHighlighted();
		if (e.getSource() instanceof FormulaButton) {
			highlightPathToFormula((FormulaButton) e.getSource());
		}
	}

	/**
	 * 
	 */
	private void clearAllHighlighted() {
		Iterator<FormulaButton> it = formulasToButtons.values().iterator();

		while (it.hasNext()) {
			FormulaButton fb = (FormulaButton) it.next();
			fb.setBackground(proofViewer.getSelectedFormulaButtonColor());
		}

	}

	/**
	 * @param content
	 */
	private void highlightPathToFormula(FormulaButton fb) {
		fb.setBackground(proofViewer.getHiglightedFormulaButtonColor());
		fb.repaint();

		if (!(fb.getSignedFormulaNode().getOrigin().equals(NamedOrigin.DEFINITION) || fb
				.getSignedFormulaNode().getOrigin().equals(NamedOrigin.PROBLEM))) {

			SignedFormulaNode main = (fb.getSignedFormulaNode().getOrigin())
					.getMain();
			List<SignedFormulaNode> auxiliaries = (fb.getSignedFormulaNode().getOrigin())
					.getAuxiliaries();
			if (main != null) {
				FormulaButton fb1 = (FormulaButton) formulasToButtons.get(main);
				highlightPathToFormula(fb1);
			}
			
			Iterator<SignedFormulaNode> auxIt = auxiliaries.iterator();
			while(auxIt.hasNext()){
				FormulaButton fb2 = (FormulaButton) formulasToButtons.get(auxIt.next());
				highlightPathToFormula(fb2);
			}

		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
	 */
	public void mousePressed(MouseEvent e) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
	 */
	public void mouseReleased(MouseEvent e) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
	 */
	public void mouseEntered(MouseEvent e) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
	 */
	public void mouseExited(MouseEvent e) {
	}

	/**
	 * @return
	 */
	public String getCurrentBranch() {
		return "" + ((ExtendedProofTree) lastNode).getBranchId();
	}

	/**
	 * 
	 */
	public void closeAll() {
		proofInfoFrame.dispose();

	}

	private void close() {
		if (proofInfoFrame != null) {
			closeAll();
		}

		lastNode = null;
		area = null;
		branchNodes = null;
		proof = null;
		formulasToButtons = null;

		proofViewer.closeWithError();

	}
}
