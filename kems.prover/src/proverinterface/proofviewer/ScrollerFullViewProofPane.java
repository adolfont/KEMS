/*
 * Created on 04/11/2005
 *
 */
package proverinterface.proofviewer;

import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

/**
 * A full view proof pane with a scroller.
 * 
 * @author Adolfo Gustavo Serra Seca Neto
 *  
 */
public class ScrollerFullViewProofPane extends JScrollPane {

    /**
	 * 
	 */
	private static final long serialVersionUID = 992197661928613668L;

	/**
     * Creates a full view proof pane with a scroller.
     * 
     * @param proofViewer
     */
    public ScrollerFullViewProofPane(FullViewProofPane fullViewProofPane) {
        super(fullViewProofPane);
        setPreferredSize(fullViewProofPane.getProofViewer().getArea());
        setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    }

}
