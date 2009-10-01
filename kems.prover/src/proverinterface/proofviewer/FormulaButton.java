/*
 * Created on 07/11/2005
 *
 */
package proverinterface.proofviewer;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import main.proofTree.SignedFormulaNode;
import main.proofTree.origin.SignedFormulaNodeOrigin;

/**
 * A button with a signed formula that shows information about the formula when
 * clicked.
 * 
 * @author Adolfo Gustavo Serra Seca Neto
 * 
 */
public class FormulaButton extends JButton implements MouseListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4065206881854715376L;

	private JPopupMenu popup;

	private Vector<String> labels;

	private SignedFormulaNode signedFormulaNode;

	public FormulaButton(Vector<String> v, SignedFormulaNode sfn) {
		super((String) v.get(0));
		this.signedFormulaNode = sfn;
		labels = v;
		popup = new JPopupMenu((String) labels.get(0));
		popup.add(new JMenuItem(v.get(0).toString()));
		for (int i = 1; i < v.size(); i++) {
			popup.add(new JMenuItem(v.get(i).toString()));
		}
		this.addMouseListener(this);
	}

	public void mousePressed(MouseEvent e) {
		maybeShowPopup(e);
	}

	public void mouseReleased(MouseEvent e) {
		maybeShowPopup(e);
	}

	private void maybeShowPopup(MouseEvent e) {
		if (e.isPopupTrigger()) {
			popup.show(e.getComponent(), e.getX(), e.getY());
		}
	}

	public void mouseClicked(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public static Vector<String> createStringArray(SignedFormulaNode sfn, int maxLength) {
		Vector<String> result = new Vector<String>();
		result.add(formatStringHtml(sfn.getContent().toString(), maxLength));
		result.add(sfn.getOrigin().getName());
		if (sfn.getOrigin() instanceof SignedFormulaNodeOrigin && (sfn.getOrigin()).getMain() != null) {
			result.add(formatStringHtml(((SignedFormulaNodeOrigin) sfn.getOrigin()).toStringMain(),
					maxLength));
			if ((sfn.getOrigin()).getAuxiliaries().size() != 0) {
				Iterator<SignedFormulaNode> itAuxs = sfn.getOrigin().getAuxiliaries().iterator();
				int i = 1;
				while (itAuxs.hasNext()) {
					result.add(formatStringHtml(((SignedFormulaNodeOrigin) sfn.getOrigin())
							.toStringAuxiliary(itAuxs.next(), i++), maxLength));

				}
//				result.add(formatStringHtml(((SignedFormulaNodeOrigin) sfn.getOrigin())
//						.toStringAuxiliaries(), maxLength));
			}
		}

		return result;

	}

	public static String formatStringHtml(String s, int maxLength) {
		// dealing with <= and >
		return new String("<html><samp>"
				+ formatString(s, maxLength, "<br/>").replaceAll("&", "&amp;").replaceAll("<=", "&lt;=")
						.replaceAll("->", "-&gt;") + "</samp></html>");
	}

	public static String formatString(String s, int maxLength, String separator) {
		String result = "";
		if (s.length() <= maxLength) {
			return s;
		} else {
			for (int i = 0; i <= (s.length() / maxLength); i++) {
				// 0, maxLength, (maxLength*2), ...
				int begin = i * maxLength;
				// (1*maxLength),(2*maxLength),... or s.length()
				int end = Math.min(((i + 1) * maxLength), s.length());
				if (begin < end) {
					result += s.substring(begin, end) + separator;
				}
			}
			return result;

		}

	}

	public SignedFormulaNode getSignedFormulaNode() {
		return signedFormulaNode;
	}
}
