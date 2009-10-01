/*
 * Created on 03/03/2005
 *
 */
package aspects;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.TextField;
import java.awt.event.TextEvent;
import java.awt.event.TextListener;
import java.util.Enumeration;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import proofTree.ProofTree;
import proofTree.StringNode;
import proofTree.iterator.ProofTreeLocalIterator;

/**
 * Example tree built out of DefaultMutableTreeNodes. 1999 Marty Hall,
 * http://www.apl.jhu.edu/~hall/java/
 */

public class SimpleTree extends JFrame implements TextListener {
    public static void main(String[] args) {
        new SimpleTree();
    }

    JTree tree;

    ProofTree pt, rightleft;

    JScrollPane jp;

    public SimpleTree() {
        super("Creating a Simple JTree");
        WindowUtilities.setNativeLookAndFeel();
        addWindowListener(new ExitListener());
        Container content = getContentPane();

        pt = new ProofTree(new StringNode("root 0"));

        pt.addLast(new StringNode("root 1"));
        pt.addLast(new StringNode("root 2"));
        ProofTree left = pt.addLeft(new StringNode("left 0"));
        left.addLast(new StringNode("left 1"));
        ProofTree leftleft = left.addLeft(new StringNode("left left 0"));
        ProofTree leftright = left.addRight(new StringNode("left right 0"));
        ProofTree right = pt.addRight(new StringNode("right 0"));
        right.addLast(new StringNode("right 1"));
        ProofTree rightright = right.addRight(new StringNode("right right 0"));
        rightleft = right.addLeft(new StringNode("right left 0"));

        System.out.println(pt);

        DefaultMutableTreeNode root = processProofTree(pt, "root");

        //        Object[] hierarchy = { "root", "A", "B", "C",
        //                new Object[] { "left", "D", "E", "E" },
        //                new Object[] { "right", new Object[] { "left", "F" }, "G" } };
        //        DefaultMutableTreeNode root = processHierarchy(hierarchy);
        tree = new JTree(root);

        content.add(jp = new JScrollPane(tree), BorderLayout.CENTER);
        setSize(275, 300);
        setVisible(true);
        //        expandAll(tree, true);

        TextField tf;
        content.add(tf = new TextField(), BorderLayout.SOUTH);

        tf.addTextListener(this);

        // TODO usar a posição para achar o nó certo e colocar no lugar

    }

    /**
     * Small routine that will make node out of the first entry in the array,
     * then make nodes out of subsequent entries and make them child nodes of
     * the first one. The process is repeated recursively for entries that are
     * arrays.
     */

    private DefaultMutableTreeNode processHierarchy(Object[] hierarchy) {
        DefaultMutableTreeNode node = new DefaultMutableTreeNode(hierarchy[0]);
        DefaultMutableTreeNode child;
        for (int i = 1; i < hierarchy.length; i++) {
            Object nodeSpecifier = hierarchy[i];
            if (nodeSpecifier instanceof Object[]) // Ie node with children
                child = processHierarchy((Object[]) nodeSpecifier);
            else
                child = new DefaultMutableTreeNode(nodeSpecifier); // Ie Leaf
            node.add(child);
        }
        return (node);
    }

    private DefaultMutableTreeNode processProofTree(ProofTree tree, String label) {
        ProofTreeLocalIterator treeIterator = tree.getLocalIterator();

        if (treeIterator.hasNext()) {
            DefaultMutableTreeNode child;
            DefaultMutableTreeNode node = new DefaultMutableTreeNode(label);
            while (treeIterator.hasNext()) {
                Object nodeSpecifier = treeIterator.next();
                child = new DefaultMutableTreeNode(nodeSpecifier); // Ie Leaf
                node.add(child);
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

    //  If expand is true, expands all nodes in the tree.
    // Otherwise, collapses all nodes in the tree.
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
                TreeNode n = (TreeNode) e.nextElement();
                if (n.equals(snode) ) {
                    DefaultMutableTreeNode aNode = (DefaultMutableTreeNode) n;
                    aNode.add(new DefaultMutableTreeNode(snode));
                }
                TreePath path = parent.pathByAddingChild(n);
                put(tree, path, branch, snode);
            }
        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see java.awt.event.TextListener#textValueChanged(java.awt.event.TextEvent)
     */
    public void textValueChanged(TextEvent e) {
        StringNode sn = new StringNode("right left 1");
        rightleft.addLast(sn);
        put(tree, pt, sn);
        expandAll(tree, true);
        tree.repaint();

    }

}