/*
 * Created on 02/11/2005
 *  
 */
package proverinterface.proofviewer;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import logicalSystems.classicalLogic.ClassicalRules;
import main.proofTree.IProofTree;
import main.proofTree.Node;
import main.proofTree.SignedFormulaNode;
import main.proofTree.iterator.IProofTreeBasicIterator;
import main.strategy.IClassicalProofTree;
import main.tableau.IProof;
import main.tableau.verifier.ExtendedNode;
import main.tableau.verifier.ExtendedProofTree;

import org.apache.log4j.Logger;

/**
 * A Panel that shows a full proof.
 * 
 * @author Adolfo Gustavo Serra Seca Neto
 * 
 */
public class FullViewProofPane extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2461887511439383128L;

	/**
	 * logger
	 */
	public static Logger logger = Logger.getLogger(FullViewProofPane.class);

	/**
	 * The parent <code>proofViewer</code>
	 */
	private ProofViewer proofViewer;

	private String highlightedBranch;

	private Dimension myPreferredSize;

	private boolean showCircles = true;

	private boolean showNumbers = false;

	private boolean showMarkUsed = true;

	private int spaceBetweenLines = 2;

	/**
	 * Creates a FullViewProofPane for a ProofViewer
	 * 
	 * @param proofViewer
	 */
	public FullViewProofPane(ProofViewer proofViewer, int x, int y) {
		setBackground(Color.white);
		setLayout(null);
		this.proofViewer = proofViewer;
		this.addMouseListener(proofViewer.getMouseListener());
		myPreferredSize = new Dimension(x, y);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	protected void paintComponent(Graphics g) {

		super.paintComponent(g);

		g.setFont(proofViewer.getTextFont());

//		System.out.println("SHOW PROOF TREE...");
		showProofTree(g, proofViewer.getProof());

		// calculateSize(proofViewer.getProof());
		// this.setPreferredSize(this.proofViewer.getSize());
		this.setPreferredSize(myPreferredSize);
		// this.setSize(myPreferredSize);
		// this.setSize(preferredSize);

		// Let the scroll pane know to update itself
		// and its scrollbars.
		this.revalidate();
	}

	/**
	 * Shows a proof tree
	 * 
	 * @param g
	 * @param proof
	 */
	private void showProofTree(Graphics g, IProof proof) {

		if (proofViewer.getProof() == null) {
			logger.error("The proof object is null");
		} else {
			IProofTree current = proofViewer.getProof().getProofTree();

			// formulaRectangles = new ArrayList();
			formulaIndex = 1;

			highlightedBranch = proofViewer.getHighlightedBranch();
			showNodeAndChildren(g, current, this.getMyBounds(), null);
		}

	}

	private void setBranchColor(Graphics g, IProofTree current) {
		String s = ((ExtendedProofTree) current).getBranchId() + "";
		int size = s.length();

		if (highlightedBranch.length() >= size
				&& highlightedBranch.substring(0, size).equals(s)) {
			g.setColor(proofViewer.getHighlightedNodeColor());
		} else {
			g.setColor(proofViewer.getNodeColor());
		}
	}

	/**
	 * Shows a node and its children
	 * 
	 * @param g
	 * @param current
	 * @param bounds
	 * @param point
	 */
	private void showNodeAndChildren(Graphics g, IProofTree current,
			Rectangle bounds, Point point) {
		if (current != null) {
			Point placeWhereFinished = showOnlyNode(g, current, bounds, point);

			if (current.getLeft() != null) {
				showLeftBranch(g, current, bounds, placeWhereFinished);
			}

			if (current.getRight() != null) {
				showRightBranch(g, current, bounds, placeWhereFinished);
			}

			if (current.getLeft() == null && current.getRight() == null
					&& !((IClassicalProofTree) current).isClosed()) {
				showOpenCompletedMark(g, current, placeWhereFinished);
			}

		}

	}

	private void showRightBranch(Graphics g, IProofTree current,
			Rectangle bounds, Point placeWhereFinished) {
		int x = (int) placeWhereFinished.getX();
		int y = (int) placeWhereFinished.getY();
		int width = ((int) (bounds.getMaxX())) - x;
		int height = ((int) (bounds.getMaxY())) - y;

		setBranchColor(g, current.getRight());

		showNodeAndChildren(g, current.getRight(), new Rectangle(x, y, width,
				height), placeWhereFinished);
	}

	private void showLeftBranch(Graphics g, IProofTree current,
			Rectangle bounds, Point placeWhereFinished) {
		int x = (int) bounds.getX();
		int y = (int) placeWhereFinished.getY();
		int width = ((int) placeWhereFinished.getX()) - x;
		int height = ((int) (bounds.getMaxY())) - y;

		setBranchColor(g, current.getLeft());

		showNodeAndChildren(g, current.getLeft(), new Rectangle(x, y, width,
				height), placeWhereFinished);
	}

	private void showOpenCompletedMark(Graphics g, IProofTree current,
			Point placeWhereFinished) {
		// show a mark for an open (or open and completed) branch
		if (showCircles) {
			drawOpenCompletedSign(placeWhereFinished.x, placeWhereFinished.y,
					current == ((IClassicalProofTree) proofViewer.getProof()
							.getProofTree()).getOpenCompletedBranch(), g);
		} else {
			drawOpenCompletedSignInStringMode(placeWhereFinished.x,
					placeWhereFinished.y,
					current == ((IClassicalProofTree) proofViewer.getProof()
							.getProofTree()).getOpenCompletedBranch(), g);
		}
	}

	/**
	 * Shows only a node
	 * 
	 * @param g
	 * @param current
	 * @param size
	 */
	private Point showOnlyNode(Graphics g, IProofTree current,
			Rectangle bounds, Point point) {

		double x = bounds.getX() + (bounds.getWidth() / 2);
		double y = bounds.getY();
		double drawingY = 0;

		// draws a line to the previous node
		if (point != null) {
			g.drawLine((int) x, (int) y + spaceBetweenLines,
					(int) point.getX(), (int) point.getY() + spaceBetweenLines);
		}

		int index = 0;
		IProofTreeBasicIterator it = current.getLocalIterator();
		while (it.hasNext()) {

			if (current.getParent() == null) {
				g.setColor(proofViewer.getHighlightedNodeColor());
			}

			Node n = (Node) it.next();
			String s = proofViewer.isOriginEnabled() ? n.toString()
					: ((SignedFormulaNode) n).getContent().toString();

			ExtendedNode en = (ExtendedNode) n;

			if (showNumbers && en.getOrigin().getRule() != ClassicalRules.CLOSE) {
				s = formulaIndex++ + " " + s;
			}

			if (showMarkUsed && en.getUsed() == Boolean.TRUE) {
				s = "* " + s;
			}

			List<Double> result = null;
			if (showCircles) {
				result = drawCircle(drawingY, index, x, y, en, g);
			} else {
				result = drawString(s, drawingY, x, y, spaceBetweenLines,
						index, g);
			}
			drawingY = result.get(0).doubleValue();
			index = result.get(1).intValue();
		}
		return new Point((int) x, (int) drawingY);

	}

	private void drawOpenCompletedSign(double x, double y, boolean isCompleted,
			Graphics g) {
		double drawingY = y + proofViewer.getCirclesRadius();

		int x1 = (int) x - (proofViewer.getCirclesRadius() / 2);
		int y1 = (int) drawingY - (proofViewer.getCirclesRadius() / 2);

		if (isCompleted) {
			g.fillRect(x1, y1, proofViewer.getCirclesRadius(), proofViewer
					.getCirclesRadius());
		} else {
			g.drawRect(x1, y1, proofViewer.getCirclesRadius(), proofViewer
					.getCirclesRadius());
		}

	}

	private void drawOpenCompletedSignInStringMode(int x, int y,
			boolean isCompleted, Graphics g) {
		int textHeight = g.getFontMetrics().getHeight();

		double drawingY = y + ((textHeight + spaceBetweenLines) * 1);

		int x1 = (int) x - (textHeight / 2);
		int y1 = (int) drawingY - (textHeight / 2);

		if (isCompleted) {
			g.fillRect(x1, y1, (textHeight/2), (textHeight/2));
		} else {
			g.drawRect(x1, y1, (textHeight/2), (textHeight/2));
		}
	}

	private List<Double> drawCircle(double drawingY, int index, double x,
			double y, ExtendedNode en, Graphics g) {
		drawingY = y + (++index) * proofViewer.getCirclesRadius();

		int x1 = (int) x - (proofViewer.getCirclesRadius() / 2);
		int y1 = (int) drawingY - (proofViewer.getCirclesRadius() / 2);

		if (en.getOrigin().getRule() != ClassicalRules.CLOSE) {
			if ((showMarkUsed && en.getUsed() == Boolean.TRUE) || !showMarkUsed) {
				g.fillOval(x1, y1, proofViewer.getCirclesRadius(), proofViewer
						.getCirclesRadius());
			} else {
				g.drawOval(x1, y1, proofViewer.getCirclesRadius(), proofViewer
						.getCirclesRadius());
			}
		}

		List<Double> result = new ArrayList<Double>();
		result.add(new Double(drawingY));
		result.add(new Double(index));
		return result;
	}

	// breaks a long formula according in parts with
	// proofViewer.getMaxStringLength() characters
	// puts the formula centered
	private List<Double> drawString(String s, double drawingY, double x,
			double y, int spaceBetweenLines, int index, Graphics g) {

		int maxLength = proofViewer.getMaxStringLength();
		int textHeight = g.getFontMetrics().getHeight();

		if (s.length() <= maxLength) {
			drawingY = y + ((textHeight + spaceBetweenLines) * (++index));
			drawStringInAGraphic(g, s, (int) (x - g.getFontMetrics()
					.stringWidth(s) / 2), (int) drawingY);

		} else {
			for (int i = 0; i <= (s.length() / maxLength); i++) {
				drawingY = y + ((textHeight + spaceBetweenLines) * (++index));
				// 0, maxLength, (maxLength*2), ...
				int begin = i * maxLength;
				// (1*maxLength),(2*maxLength),... or s.length()
				int end = Math.min(((i + 1) * maxLength), s.length());
				if (begin < end) {
					drawStringInAGraphic(g, s.substring(begin, end),
							(int) (x - g.getFontMetrics().stringWidth(
									s.substring(begin, end)) / 2),
							(int) drawingY);
				}
			}

		}

		List<Double> result = new ArrayList<Double>();
		result.add(new Double(drawingY));
		result.add(new Double(index));
		return result;

	}

	public void setShowCircles(boolean value) {
		if (showCircles != value) {
			showCircles = value;
			repaint();
		}
	}

	public void setShowNumbers(boolean value) {
		if (showNumbers != value) {
			showNumbers = value;
			repaint();
		}
	}

	public void setMarkUsed(boolean value) {
		if (showMarkUsed != value) {
			showMarkUsed = value;
			repaint();
		}
	}

	private int formulaIndex = 0;

	private void drawStringInAGraphic(Graphics g, String s, int x, int y) {
		g.drawString(s, x, y);
	}

	public ProofViewer getProofViewer() {
		return proofViewer;
	}

	/**
	 * @return
	 */
	public boolean isShowingCircles() {
		return showCircles;
	}

	public boolean isShowingNumbers() {
		return showNumbers;
	}

	public boolean isMarkingUsed() {
		return showMarkUsed;
	}

	public Dimension getMyPreferredSize() {
		return myPreferredSize;
	}

	public void setMyPreferredSize(Dimension preferredSize) {
		this.myPreferredSize = preferredSize;
	}

	public Rectangle getMyBounds() {
		return new Rectangle(0, 0, (int) myPreferredSize.getWidth(),
				(int) myPreferredSize.getHeight());
	}
}
