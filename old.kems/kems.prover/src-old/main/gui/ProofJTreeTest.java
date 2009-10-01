/*
 * Created on 04/03/2005
 *
 */
package gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import proofTree.Node;
import proofTree.ProofTree;
import proofTree.StringNode;
import proofTree.iterator.ProofTreeLocalIterator;
import aspects.ExitListener;

/**
 * @author Adolfo Gustavo Serra Seca neto
 *  
 */
public class ProofJTreeTest implements ActionListener {

    ProofTree pt, rightleft, rightright, right;

    JTree tree;
    DefaultTreeModel treeModel;
    DefaultMutableTreeNode root;

    JFrame f;

    public static void main(String args[]) {
        ProofJTreeTest ptjt = new ProofJTreeTest();

        ptjt.testProofJTree();
    }

    public void testProofJTree() {

        f = new JFrame("Proof JTree test");
        f.addWindowListener(new ExitListener());

        JButton jb1, jb2;
        f.getContentPane().add(jb2 = new JButton("ADD NODE 1"),
                BorderLayout.NORTH);
        f.getContentPane().add(new JScrollPane(createProofJTree()),
                BorderLayout.CENTER);
        f.getContentPane().add(jb1 = new JButton("ADD NODE 2"),
                BorderLayout.SOUTH);

        
        f.setSize(275, 300);
        f.setVisible(true);

        jb1.addActionListener(this);
        jb2.addActionListener(this);

    }

    public JTree createProofJTree() {
        treeModel = new DefaultTreeModel(root = new DefaultMutableTreeNode("root"));
        
        processProofTree(createSampleProofTree(), root);
        this.tree = new JTree(root);
        this.tree.setRootVisible(false);

        return tree;
    }

    public DefaultMutableTreeNode processProofTree(ProofTree tree, DefaultMutableTreeNode root) {
        ProofTreeLocalIterator treeIterator = tree.getLocalIterator();

        if (treeIterator.hasNext()) {
            DefaultMutableTreeNode child;
            DefaultMutableTreeNode node = root;

            while (treeIterator.hasNext()) {
                Node nodeSpecifier = treeIterator.next();
                child = new DefaultMutableTreeNode(nodeSpecifier); // Ie Leaf
//                node.add(child);
                
                treeModel.insertNodeInto(child, node, 
                        node.getChildCount());

            }

            if (tree.getLeft() != null) {
                node.add(processProofTree(tree.getLeft(), "left"));
            }
            if (tree.getRight() != null) {
                node.add(processProofTree(tree.getRight(), "right"));
            }
            return (node);
        } else
            return null;

    }

    
    public DefaultMutableTreeNode processProofTree(ProofTree tree, String label) {
        ProofTreeLocalIterator treeIterator = tree.getLocalIterator();

        if (treeIterator.hasNext()) {
            DefaultMutableTreeNode child;
            DefaultMutableTreeNode node = new DefaultMutableTreeNode(label);

            while (treeIterator.hasNext()) {
                Node nodeSpecifier = treeIterator.next();
                child = new DefaultMutableTreeNode(nodeSpecifier); // Ie Leaf
//                node.add(child);
                
                treeModel.insertNodeInto(child, node, 
                        node.getChildCount());

            }

            if (tree.getLeft() != null) {
                node.add(processProofTree(tree.getLeft(), "left"));
            }
            if (tree.getRight() != null) {
                node.add(processProofTree(tree.getRight(), "right"));
            }
            return (node);
        } else
            return null;

    }

    public ProofTree createSampleProofTree() {
        pt = new ProofTree(new StringNode("root 0"));

        pt.addLast(new StringNode("root 1"));
        pt.addLast(new StringNode("root 2"));
        ProofTree left = pt.addLeft(new StringNode("left 0"));
        left.addLast(new StringNode("left 1"));
        ProofTree leftleft = left.addLeft(new StringNode("left left 0"));
        ProofTree leftright = left.addRight(new StringNode("left right 0"));
        right = pt.addRight(new StringNode("right 0"));
        right.addLast(new StringNode("right 1"));
        rightright = right.addRight(new StringNode("right right 0"));
        rightleft = right.addLeft(new StringNode("right left 0"));

        return pt;
    }

    public void put(JTree tree, ProofTree branch, StringNode node) {
        TreeNode root = (TreeNode) tree.getModel().getRoot();

        // Traverse tree from root
        put(tree, new TreePath(root), branch, node);
    }

    private void put(JTree tree, TreePath parent, ProofTree branch,
            StringNode snode) {
        // Traverse children
        TreeNode node = (TreeNode) parent.getLastPathComponent();
        if (node.getChildCount() >= 0) {
            for (Enumeration e = node.children(); e.hasMoreElements();) {
                DefaultMutableTreeNode n = (DefaultMutableTreeNode) e
                        .nextElement();
                System.out.println(n.getUserObject());
                
                if ((n.getUserObject() instanceof Node)
                        && (((Node) n.getUserObject()).getBranch() == branch)) {
                    System.out.println("achou!");

                    DefaultMutableTreeNode nodeParent = (DefaultMutableTreeNode) n
                            .getParent();
                    
                    DefaultMutableTreeNode childNode =new DefaultMutableTreeNode(snode); 
//                    nodeParent.add(childNode);
                    
                    treeModel.insertNodeInto(childNode, nodeParent, nodeParent
                            .getChildCount());

                    //Make sure the user can see the lovely new node.
                    tree.scrollPathToVisible(new TreePath(childNode.getPath()));

                    System.out.println("Adicionou " + snode.hashCode() + " a "
                            + nodeParent.getUserObject().hashCode());
                    return;
                }
                TreePath path = parent.pathByAddingChild(n);
                put(tree, path, branch, snode);
            }
        }

    }

    //  If expand is true, expands all nodes in the tree.
    // Otherwise, collapses all nodes in the tree.
    public void expandAll(JTree tree, boolean expand) {
        TreeNode root = (TreeNode) tree.getModel().getRoot();

        // Traverse tree from root
        expandAll(tree, new TreePath(root), expand);
    }

    private void expandAll(JTree tree, TreePath parent, boolean expand) {
        // Traverse children
        TreeNode node = (TreeNode) parent.getLastPathComponent();
        if (node.getChildCount() >= 0) {
            for (Enumeration e = node.children(); e.hasMoreElements();) {
                TreeNode n = (TreeNode) e.nextElement();
                TreePath path = parent.pathByAddingChild(n);
                expandAll(tree, path, expand);
            }
        }

        // Expansion or collapse must be done bottom-up
        if (expand) {
            tree.expandPath(parent);
        } else {
            tree.collapsePath(parent);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    public void actionPerformed(ActionEvent e) {
        System.out.println(((JButton) e.getSource()).getText());
        if (((JButton) e.getSource()).getText().equals("ADD NODE 1")) {
            StringNode sn = new StringNode("NEW "+Math.random());
            rightleft.addLast(sn);
            put(tree, rightleft, sn);
            expandAll(tree, true);
            tree.repaint();
            f.repaint();
        } else {
            StringNode sn = new StringNode("NEW 2");
            right.addLast(sn);
            put(tree, right, sn);
            expandAll(tree, true);
            tree.repaint();
            f.repaint();
        }

    }

}