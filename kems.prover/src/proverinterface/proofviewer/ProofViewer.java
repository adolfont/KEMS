/*
 * Created on 02/11/2005
 *  
 */
package proverinterface.proofviewer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.plaf.ColorUIResource;

import proverinterface.ProverInterface;

import logic.valuation.AbstractValuation;
import main.tableau.IProof;

public class ProofViewer extends JFrame implements ActionListener,
		WindowListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3279784142505934174L;

//	private JFrame proofViewerFrame;

	private static int instanceCounter = 0;

	// private StructuredProofPane structuredProofPane;

	private InteractiveProofPane interactiveProofPane;

//	private ProofTree proofTree;

	private ParametersPanel parametersPanel = null;

//	private PictureMover proofMover = null;

	private boolean originEnabled = false;

	private JScrollPane scrollerFullViewPane,
	// scrollerStructuredProofPane,
			scrollerInteractiveProofPane;

	private Font textFont;

	private IProof proof;

	private int maxStringLength;

	private int fontSize;

	private Dimension area;

	private int circlesRadius = 4;

	private ProofViewerMouseListener mouseListener;

	private FullViewProofPane fullViewProofPane;

	private Dimension paneSize;

	private int dividerLocation;

	private AbstractValuation valuation;

	private int instanceNumber;

	private ProverInterface proverInterface;
	
	// basic design
	public ProofViewer(ProverInterface proverInterface) {
		instanceNumber = ++instanceCounter;
		
		this.proverInterface = proverInterface;


		initialize();

		// a panel that show only one node per time
		// structuredProofPane = new StructuredProofPane(this);
		// scrollerStructuredProofPane = new JScrollPane(structuredProofPane);
		// scrollerStructuredProofPane
		// .setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		// scrollerStructuredProofPane
		// .setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);

		// a panel that show a full branch
		interactiveProofPane = new InteractiveProofPane(this);
		scrollerInteractiveProofPane = new JScrollPane(interactiveProofPane);
		scrollerInteractiveProofPane
				.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollerInteractiveProofPane
				.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);

		// a panel with a scroller that shows full proofs - formulas are strings
		scrollerFullViewPane = new ScrollerFullViewProofPane(fullViewProofPane);
		JPanel fullView = new JPanel(new BorderLayout());
		// fullView.add(proofMover = new PictureMover(this),
		// BorderLayout.NORTH);
		fullView.add(scrollerFullViewPane, BorderLayout.CENTER);

		// a tabbed pane - the main panel of the proof viewer
		// JTabbedPane jTabbedPane = new JTabbedPane();
		// ImageIcon icon = null;
		//
		// // jTabbedPane.addTab("Not so much Interactive View", icon,
		// // scrollerStructuredProofPane, "Not so much Interactive View");
		// jTabbedPane.addTab("Interactive View", icon,
		// scrollerInteractiveProofPane, "Interactive View");
		// jTabbedPane.addTab("Full View", icon, fullView, "Full View");
		//
		// // jTabbedPane.addTab("Parameters", icon, sizeDefPanel, "Full view");
		// // foi para popup
		//
		// jTabbedPane.setMnemonicAt(0, KeyEvent.VK_1);
		// jTabbedPane.setMnemonicAt(1, KeyEvent.VK_2);
		// jTabbedPane.setMnemonicAt(2, KeyEvent.VK_3);
		// setContentPane(jTabbedPane);

		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
				scrollerInteractiveProofPane, fullView);
		splitPane.setOneTouchExpandable(true);
		splitPane.setDividerLocation(dividerLocation);

		// Provide minimum sizes for the two components in the split pane
		Dimension minimumSize = new Dimension(100, 50);
		scrollerInteractiveProofPane.setMinimumSize(minimumSize);
		fullView.setMinimumSize(minimumSize);

		setContentPane(splitPane);

	}

	/**
	 * Initialize attributes
	 */
	private void initialize() {
		fontSize = 10;
		textFont = new Font("Roman", Font.BOLD, fontSize);
		maxStringLength = 80;
		paneSize = new Dimension(300, 1600);
		area = new Dimension(400, 600);
		dividerLocation = 300;
		// a panel for parameters that is shown only when asked
		// TODO make structuredProofPane also use it
		// PESSIMA PROGRAMACAO: a ordem importa!!!
		mouseListener = new ProofViewerMouseListener(this);
		fullViewProofPane = new FullViewProofPane(this, 300, 600);
		mouseListener.setCheckBoxesInitialState();
		parametersPanel = new ParametersPanel(this);
	}

	public void showOn() {
		// Make sure we have nice window decorations.
		JFrame.setDefaultLookAndFeelDecorated(true);

		// Create and set up the window.
		// proofViewerFrame = new JFrame("KEMS Proof Viewer #"
		// + getInstanceNumber());
		// proofViewerFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		//
		// proofViewerFrame.setContentPane(this.getContentPane());
		// proofViewerFrame.addWindowListener(this);
		//
		// //Display the window.
		// proofViewerFrame.pack();
		// proofViewerFrame.setVisible(true);
		// proofViewerFrame.setLocation(10,10);

		setTitle("KEMS Proof Viewer #" + getInstanceNumber());
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		setContentPane(this.getContentPane());
		addWindowListener(this);

		// Display the window.
		pack();
		setVisible(true);
		setLocation(10, 10);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == parametersPanel.getUpdateButton()) {
			int fullProofX = (int) this.getSize().getWidth();
			int fullProofY = (int) this.getSize().getHeight();
			int xPane = (int) this.getPaneSize().getWidth();
			int yPane = (int) this.getPaneSize().getHeight();
			int maxLength = this.getMaxStringLength();
			int fontSize = this.getFontSize();
			try {
				fullProofX = Integer.parseInt(parametersPanel
						.getFullProofWidthTextField().getText());
				fullProofY = Integer.parseInt(parametersPanel
						.getFullProofHeightTextField().getText());
				xPane = Integer.parseInt(parametersPanel
						.getPaneWidthTextField().getText());
				yPane = Integer.parseInt(parametersPanel
						.getPaneHeightTextField().getText());
				maxLength = Integer.parseInt(parametersPanel
						.getMaxLengthTextField().getText());
				fontSize = Integer.parseInt(parametersPanel
						.getFontSizeTextField().getText());
				circlesRadius = Integer.parseInt(parametersPanel
						.getCirclesRadiusTextField().getText());

				fullProofX = fullProofX > 0 ? fullProofX : 1;
				fullProofY = fullProofY > 0 ? fullProofY : 1;
				maxLength = maxLength > 10 ? maxLength : 11;
				fontSize = fontSize > 0 ? fontSize : 1;
				circlesRadius = (circlesRadius > 2 && circlesRadius < 20) ? circlesRadius
						: 4;
			} catch (Exception exception) {
				// using default values
				System.err.println("Exception parsing text fields: "
						+ exception.getMessage());
			}
			updateSizeDisplay(xPane, yPane, fullProofX, fullProofY, maxLength,
					fontSize);
		}

		// its a move button - from the move panel
		// if (e.getSource() instanceof JButton
		// && e.getSource() != parametersPanel.getUpdateButton()) {
		//
		// if (e.getActionCommand().indexOf("left") != -1) {
		// scrollerFullViewPane.getHorizontalScrollBar().setValue(
		// scrollerFullViewPane.getHorizontalScrollBar()
		// .getValue()
		// - parametersPanel.getStep());
		//
		// }
		// if (e.getActionCommand().indexOf("right") != -1) {
		// scrollerFullViewPane.getHorizontalScrollBar().setValue(
		// scrollerFullViewPane.getHorizontalScrollBar()
		// .getValue()
		// + parametersPanel.getStep());
		//
		// }
		// if (e.getActionCommand().indexOf("up") != -1) {
		// scrollerFullViewPane.getVerticalScrollBar().setValue(
		// scrollerFullViewPane.getVerticalScrollBar().getValue()
		// - parametersPanel.getStep());
		//
		// }
		// if (e.getActionCommand().indexOf("down") != -1) {
		// scrollerFullViewPane.getVerticalScrollBar().setValue(
		// scrollerFullViewPane.getVerticalScrollBar().getValue()
		// + parametersPanel.getStep());
		//
		// }
		//
		// }
	}

	/**
	 * @param fullProofX
	 * @param fullProofY
	 */
	private void updateSizeDisplay(int xPane, int yPane, int fullProofX,
			int fullProofY, int maxLength, int fontSize) {

		interactiveProofPane.setChanged(true);
		interactiveProofPane.setArea(new Dimension(xPane, yPane));
		fullViewProofPane.setMyPreferredSize(new Dimension(fullProofX,
				fullProofY));

		this.setMaxStringLength(maxLength);
		this.setFontSize(fontSize);

		parametersPanel.getFullProofWidthTextField().setText(
				""
						+ (int) this.getFullViewProofPane()
								.getMyPreferredSize().getWidth());
		parametersPanel.getFullProofHeightTextField().setText(
				""
						+ (int) this.getFullViewProofPane()
								.getMyPreferredSize().getHeight());
		parametersPanel.getMaxLengthTextField().setText(
				"" + this.getMaxStringLength());
		parametersPanel.getCirclesRadiusTextField().setText(
				"" + this.getCirclesRadius());
		parametersPanel.getFontSizeTextField().setText("" + this.getFontSize());
		repaint();
		scrollerFullViewPane.repaint();
		// structuredProofPane.repaint();
		interactiveProofPane.repaint();
	}

	// getters and setters
	public Font getTextFont() {
		return textFont.deriveFont((float) getFontSize());
	}

	public Color getNodeColor() {
		return Color.BLACK;
	}

	public boolean isOriginEnabled() {
		return originEnabled;
	}

	public void setOriginEnabled(boolean originEnabled) {
		this.originEnabled = originEnabled;
	}

	public IProof getProof() {
		return proof;
	}

	public void setProof(IProof proof) {
		try{
		
		this.proof = proof;
		// structuredProofPane.setProof(proof);
		interactiveProofPane.setProof(proof);
		setTitle(getTitle() + ": A proof of " + proof.getProblem().getName());
		updateSizeDisplay((int) getPaneSize().getWidth(), (int) getPaneSize()
				.getHeight(), (int) fullViewProofPane.getMyPreferredSize()
				.getWidth(), (int) fullViewProofPane.getMyPreferredSize()
				.getHeight(), getMaxStringLength(), getFontSize());
		
		} catch(Throwable e){
			if (e instanceof OutOfMemoryError){
				proverInterface.showErrorMessage(ProverInterface.OUT_OF_MEMORY_MESSAGE);
			}
			else{
				proverInterface.showErrorMessage(e.getMessage());
			}
			closeWithError();
		}
	}

	public int getMaxStringLength() {
		return maxStringLength;
	}

	public void setMaxStringLength(int possibleMaxStringLength) {
		this.maxStringLength = possibleMaxStringLength;
	}

	public int getFontSize() {
		return fontSize;
	}

	public void setFontSize(int fontSize) {
		this.fontSize = fontSize;
	}

	public Dimension getArea() {
		return area;
	}

	public ParametersPanel getParametersPanel() {
		return parametersPanel;
	}

	public int getCirclesRadius() {
		return circlesRadius;
	}

	public void setCircleRadius(int radius) {
		circlesRadius = radius;
	}

	public Color getUnselectedFormulaButtonColor() {
		return Color.GRAY;
	}

	public Color getSelectedFormulaButtonColor() {
		return new ColorUIResource(238, 238, 238);
	}

	public Color getHiglightedFormulaButtonColor() {
		return Color.YELLOW;
	}

	/**
	 * @return
	 */
	public MouseListener getMouseListener() {
		return mouseListener;
	}

	/**
	 * @return
	 */
	public FullViewProofPane getFullViewProofPane() {
		return fullViewProofPane;
	}

	public Dimension getPaneSize() {
		return paneSize;
	}

	public void setPaneSize(Dimension paneSize) {
		this.paneSize = paneSize;
	}

	/**
	 * @return
	 */
	public String getHighlightedBranch() {
		return interactiveProofPane.getCurrentBranch();
	}

	/**
	 * @return
	 */
	public Color getHighlightedNodeColor() {
		return Color.red;
	}

	/**
	 * @return
	 */
	public AbstractValuation getValuation() {
		return valuation;
	}

	public void setValuation(AbstractValuation valuation) {
		this.valuation = valuation;
	}

	public int getInstanceNumber() {
		return instanceNumber;
	}

	public Font getFont() {
		return textFont.deriveFont((float) fontSize);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.WindowListener#windowOpened(java.awt.event.WindowEvent)
	 */
	public void windowOpened(WindowEvent e) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.WindowListener#windowClosing(java.awt.event.WindowEvent)
	 */
	public void windowClosing(WindowEvent e) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.WindowListener#windowClosed(java.awt.event.WindowEvent)
	 */
	public void windowClosed(WindowEvent e) {
		interactiveProofPane.closeAll();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.WindowListener#windowIconified(java.awt.event.WindowEvent)
	 */
	public void windowIconified(WindowEvent e) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.WindowListener#windowDeiconified(java.awt.event.WindowEvent)
	 */
	public void windowDeiconified(WindowEvent e) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.WindowListener#windowActivated(java.awt.event.WindowEvent)
	 */
	public void windowActivated(WindowEvent e) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.WindowListener#windowDeactivated(java.awt.event.WindowEvent)
	 */
	public void windowDeactivated(WindowEvent e) {
	}
	
	public void closeWithError(){
		this.setVisible(false);
		
		dispose();
	}

	public ProverInterface getProverInterface() {
		return proverInterface;
	}

}
