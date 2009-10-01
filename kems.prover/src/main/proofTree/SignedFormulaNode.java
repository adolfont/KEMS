/*
 * Created on 21/10/2004
 *
 */
package main.proofTree;

import logic.signedFormulas.SignedFormula;
import main.proofTree.origin.IOrigin;
import main.proofTree.origin.NamedOrigin;

//import org.jdom.Element;

/**
 * @author Adolfo Gustavo Serra Seca Neto
 *  
 */
public class SignedFormulaNode extends Node implements Cloneable {

    SignedFormula _content;

    SignedFormulaNodeState _state;

    IOrigin _origin = NamedOrigin.DEFINITION;

    public SignedFormulaNode(SignedFormula sf, SignedFormulaNodeState state,
            IOrigin origin) {
        this._content = sf;
        this._state = state;
        this._origin = origin;
    }

    /*
     * (non-Javadoc)
     * 
     * @see proofTree.Node#getContent()
     */
    public SignedFormula getContent() {
        return _content;
    }

    /*
     * (non-Javadoc)
     * 
     * @see proofTree.Node#getState()
     */
    public State getState() {
        return _state;
    }

    public void setState(SignedFormulaNodeState s) {
        _state = s;
    }

    public IOrigin getOrigin() {
        return _origin;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return getContent() + ((getOrigin() != null) ?
                (
//                        " [" + getState() + "]" +
                        "   Origin: [" + getOrigin().toString() + "]") : "");
    }

//    public Collection getXMLElements() {
//        List l = new ArrayList();
//
//        Element content = new Element("content");
//        content.setText(getContent().toString());
//        Element origin = new Element("origin");
//        origin.setText(getOrigin().toString());
//
//        l.add(content);
//        l.add(origin);
//
//        return l;
//
//    }

//    /*
//     * (non-Javadoc)
//     * 
//     * @see proofTree.Node#asXMLElement()
//     */
//    public Element asXMLElement() {
//        Element root = new Element("node");
//        root.addContent(getXMLElements());
//        return root;
//    }

    public void setOrigin(IOrigin _origin) {
        this._origin = _origin;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#clone()
     */
    public Object clone() throws CloneNotSupportedException {
        SignedFormulaNode sf = new SignedFormulaNode((SignedFormula) this
                .getContent(), (SignedFormulaNodeState) this.getState(), this
                .getOrigin());
        sf.setNext(this.getNext());
        sf.setBranch(this.getBranch());
        sf.setPrevious(this.getPrevious());
        return sf;
    }
}