/*
 * Created on 04/03/2005
 *  
 */
package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.tree.DefaultMutableTreeNode;

import proofTree.ProofTree;
import proofTree.StringNode;
import proofTree.iterator.ProofTreeLocalIterator;
import facade.DynamicTree;

public class DynamicTreeDemo extends JPanel implements ActionListener {
    private int newNodeSuffix = 1;

    private static String ADD1_COMMAND = "add1";

    private static String ADD2_COMMAND = "add2";

    private static String ADD_COMMAND = "add";

    private static String REMOVE_COMMAND = "remove";

    private static String CLEAR_COMMAND = "clear";

    private static DefaultMutableTreeNode LEFT = new DefaultMutableTreeNode(
            "left");

    private static DefaultMutableTreeNode RIGHT = new DefaultMutableTreeNode(
            "right");

    private DynamicTree treePanel;

    // mine
    StringNode aNode1, aNode2;
    ProofTree leftright;

    public DynamicTreeDemo() {
        super(new BorderLayout());

        //Create the components.
        treePanel = new DynamicTree();
        populateTree(treePanel, null, createSampleProofTree());
        treePanel.expandAll(true);

        JButton add1Button = new JButton("Add1");
        add1Button.setActionCommand(ADD1_COMMAND);
        add1Button.addActionListener(this);

        JButton add2Button = new JButton("Add2");
        add2Button.setActionCommand(ADD2_COMMAND);
        add2Button.addActionListener(this);

        JButton addButton = new JButton("Add");
        addButton.setActionCommand(ADD_COMMAND);
        addButton.addActionListener(this);

        JButton removeButton = new JButton("Remove");
        removeButton.setActionCommand(REMOVE_COMMAND);
        removeButton.addActionListener(this);

        JButton clearButton = new JButton("Clear");
        clearButton.setActionCommand(CLEAR_COMMAND);
        clearButton.addActionListener(this);

        //Lay everything out.
        treePanel.setPreferredSize(new Dimension(300, 150));
        add(treePanel, BorderLayout.CENTER);

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(add1Button);
        panel.add(add2Button);
        panel.add(addButton);
        panel.add(removeButton);
        panel.add(clearButton);
        add(panel, BorderLayout.LINE_END);
    }

    ProofTree pt, rightleft, rightright, right;

    public ProofTree createSampleProofTree() {
        pt = new ProofTree(new StringNode("root 0"));

        pt.addLast(new StringNode("root 1"));
        pt.addLast(new StringNode("root 2"));
        ProofTree left = pt.addLeft(new StringNode("left 0"));
        left.addLast(new StringNode("left 1"));
        ProofTree leftleft = left.addLeft(aNode2 = new StringNode("left left 0"));
        leftright = left.addRight(new StringNode("left right 0"));
        right = pt.addRight(new StringNode("right 0"));
        right.addLast(new StringNode("right 1"));
        rightright = right.addRight(new StringNode("right right 0"));
        rightleft = right.addLeft(aNode1 = new StringNode("right left 0"));

        return pt;
    }

    public void populateTree(DynamicTree treePanel) {
        String p1Name = new String("Parent 1");
        String p2Name = new String("Parent 2");
        String c1Name = new String("Child 1");
        String c2Name = new String("Child 2");

        DefaultMutableTreeNode p1, p2;

        p1 = treePanel.addObject(null, p1Name);
        p2 = treePanel.addObject(null, p2Name);

        treePanel.addObject(p1, c1Name);
        treePanel.addObject(p1, c2Name);

        treePanel.addObject(p2, c1Name);
        treePanel.addObject(p2, c2Name);
    }

    public void populateTree(DynamicTree treePanel,
            DefaultMutableTreeNode current, ProofTree tree) {

        ProofTreeLocalIterator treeIterator = tree.getLocalIterator();

        while (treeIterator.hasNext()) {
            treePanel.addObject(current, treeIterator.next());
        }

        if (tree.getLeft() != null) {
            DefaultMutableTreeNode left = treePanel.addObject(current, LEFT);
            populateTree(treePanel, left, tree.getLeft());

        }
        if (tree.getRight() != null) {
            DefaultMutableTreeNode right = treePanel.addObject(current, RIGHT);
            populateTree(treePanel, right, tree.getRight());
        }

    }

    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        if (ADD1_COMMAND.equals(command)) {
            //Add button clicked
            //            System.out.println(treePanel.locateObjectNode(aNode1));
            //            System.out.println(treePanel.locateObjectNode(new
            // StringNode("xx")));
            //            System.out.println(treePanel.locateObjectNode(aNode2));
            
//             TODO PUT AS SIBLING 
            treePanel.addObject((DefaultMutableTreeNode)treePanel.locateObjectNode(aNode1).getParent(),
                    new StringNode("After right left 0"), true);
             
//             TODO PUT IN A BRANCH
            treePanel.addObject((DefaultMutableTreeNode)treePanel.locateObjectNode(leftright.getRoot()).getParent(),
                    new StringNode("Below left right"), true);

        } else if (ADD2_COMMAND.equals(command)) {
            
//            TODO CREATE CHILDREN
            DefaultMutableTreeNode left = treePanel.addObject((DefaultMutableTreeNode)treePanel.locateObjectNode(aNode2).getParent(),
                    new String("left"), true);
            DefaultMutableTreeNode right = treePanel.addObject((DefaultMutableTreeNode)treePanel.locateObjectNode(aNode2).getParent(),
                    new String("right"), true);
            treePanel.addObject(left,
                    new StringNode("T A"), true);
            treePanel.addObject(left,
                    new StringNode("T B"), true);
            treePanel.addObject(right,
                    new StringNode("F A"), true);
            
        } else if (ADD_COMMAND.equals(command)) {
            //Add button clicked
            treePanel.addObject("New Node " + newNodeSuffix++);

        } else if (REMOVE_COMMAND.equals(command)) {
            //Remove button clicked
            treePanel.removeCurrentNode();
        } else if (CLEAR_COMMAND.equals(command)) {
            //Clear button clicked.
            treePanel.clear();
        }
    }

    /**
     * Create the GUI and show it. For thread safety, this method should be
     * invoked from the event-dispatching thread.
     */
    private static void createAndShowGUI() {
        //Make sure we have nice window decorations.
        JFrame.setDefaultLookAndFeelDecorated(true);

        //Create and set up the window.
        JFrame frame = new JFrame("DynamicTreeDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the content pane.
        DynamicTreeDemo newContentPane = new DynamicTreeDemo();
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }

}

