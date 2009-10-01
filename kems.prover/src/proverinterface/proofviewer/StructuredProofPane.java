/*
 * Created on 02/11/2005
 *  
 */
package proverinterface.proofviewer;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import main.proofTree.IProofTree;
import main.proofTree.Node;
import main.proofTree.SignedFormulaNode;
import main.proofTree.iterator.IProofTreeBasicIterator;
import main.tableau.IProof;
import main.tableau.verifier.ExtendedProofTree;

/**
 * A Panel that shows one node of a proof tree
 * 
 * @author Adolfo Gustavo Serra Seca Neto
 *  
 */
public class StructuredProofPane extends JPanel implements ActionListener {


    /**
	 * 
	 */
	private static final long serialVersionUID = -5785420405383514824L;

	/** the parent proof viewer */
    private ProofViewer proofViewer;

    /** the current proof tree node */
    private IProofTree currentNode;

    /** the panel preffered size */
    private Dimension area;

    /**
     * if true, all thw objects are removed and reincluded. This is necessary
     * when there is some change in attributes.
     */
    private boolean changed = true;

    // action button labels
    private static final String OPEN_LEFT = "Open left branch";

    private static final String OPEN_RIGHT = "Open right branch";

    private static final String BACK_TO_PARENT = "Back to parent";

    // TODO testar se muda qdo muda a prova

    /**
     * Creates a StructuredProofPane for a ProofViewer
     * 
     * @param proofViewer -
     *            the proofViewer that provides the proof and parameters.
     */
    public StructuredProofPane(ProofViewer proofViewer) {
        setLayout(new GridLayout(0, 1));
        this.proofViewer = proofViewer;
        area = proofViewer.getArea();
        this.setPreferredSize(area);
        this.revalidate();
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
        currentNode = proof.getProofTree();
        changed = true;
        repaint();
    }

    /**
     * Shows a proof tree
     * 
     * @param g
     */
    private void showProofTree(Graphics g) {
        if (currentNode != null) {
            showNodeAndChildren(g, currentNode);
        } else {
            removeAll();
            repaint();
        }
    }

    private void showNodeAndChildren(Graphics g, IProofTree current) {
        if (current != null) {

            JPanel rootNode = new JPanel(new GridLayout(0, 1));

            JPanel branchId = new JPanel();
            JLabel branchLabel = new JLabel("Node id:"
                    + ((ExtendedProofTree)current).getBranchId());
            branchLabel.setFont(this.getFont());
            branchId.add(branchLabel);
            branchId.addMouseListener(proofViewer.getMouseListener());
            rootNode.add(branchId);

            rootNode = showOnlyNode(g, current, rootNode);
            JPanel leftNode = new JPanel(new GridLayout(0, 1));
            JPanel rightNode = new JPanel(new GridLayout(0, 1));

            if (current.getLeft() != null) {
                IProofTree left = current.getLeft();
                JComponent fb =createCombo((SignedFormulaNode) left.getRoot());  
                fb.setFont(this.getFont());
                leftNode.add(fb);
                JButton jb = new JButton(OPEN_LEFT);
                jb.setFont(this.getFont());
                leftNode.add(jb);
                jb.addActionListener(this);
            }

            if (current.getRight() != null) {
                IProofTree right = current.getRight();
                JComponent fb =createCombo((SignedFormulaNode) right.getRoot());  
                fb.setFont(this.getFont());
                rightNode.add(fb);
                JButton jb = new JButton(OPEN_RIGHT);
                jb.setFont(this.getFont());
                rightNode.add(jb);
                jb.addActionListener(this);
            }

            JPanel end = new JPanel(new GridLayout(0, 1));
            JPanel leftAndRight = new JPanel(new GridLayout(0, 2));
            leftAndRight.add(leftNode);
            leftAndRight.add(rightNode);

            end.add(leftAndRight);

            if (current.getParent() != null) {
                JPanel moveUpPanel = new JPanel(new GridLayout(0, 1));
                JButton jb = new JButton(BACK_TO_PARENT);
                jb.setFont(this.getFont());
                jb.addActionListener(this);
                moveUpPanel.add(jb);
                end.add(moveUpPanel);
            }

            rootNode.add(end);
            add(rootNode);

        }

    }

    /**
     * @param g
     * @param current
     * @param size
     */
    private JPanel showOnlyNode(Graphics g, IProofTree current, JPanel thisNode) {

//        int index = 0;
        IProofTreeBasicIterator it = current.getLocalIterator();
        while (it.hasNext()) {
            g.setColor(proofViewer.getNodeColor());

            Node n = (Node) it.next();
//            String s = proofViewer.isOriginEnabled() ? n.toString()
//                    : ((SignedFormulaNode) n).getContent().toString();

            SignedFormulaNode sfn = (SignedFormulaNode) n;

            JComponent fb =createCombo(sfn);
            fb.setFont(this.getFont());
            thisNode.add(fb);

        }
        return thisNode;

    }

    private JComponent createCombo(SignedFormulaNode sfn) {

        //        JComboBox content = new JComboBox(createStringArray(sfn, proofViewer
        //                .getMaxStringLength()));
        //        content.setSelectedIndex(0);
        //        content.setFont(this.getFont());
        //
        //        content.setToolTipText(sfn.getOrigin().toString());
        //        content.setMaximumSize(new Dimension(20, 20));
        //        return content;
        return new FormulaButton(FormulaButton.createStringArray(sfn, proofViewer
                .getMaxStringLength()), sfn);
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

        if (e.getActionCommand().equals(OPEN_LEFT)) {
            if (currentNode.getLeft() != null) {
                currentNode = currentNode.getLeft();
                setChanged(true);
                repaint();
            }
        } else if (e.getActionCommand().equals(OPEN_RIGHT)) {
            if (currentNode.getRight() != null) {
                currentNode = currentNode.getRight();
                setChanged(true);
                repaint();
            }
        } else if (e.getActionCommand().equals(BACK_TO_PARENT)) {
            if (currentNode.getParent() != null) {
                currentNode = currentNode.getParent();
                setChanged(true);
                repaint();
            }

        }

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

}
