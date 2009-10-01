/*
 * Created on 24/09/2004
 *
 */
package main.proofTree;

//import org.jdom.Element;

/**
 * A simple node for testing.
 * 
 * @author Adolfo Gustavo Serra Seca Neto
 *  
 */
public class StringNode extends Node {

    String _content;

    public StringNode(String content) {
        _content = content;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return "String Node content: " + _content;
    }

    /*
     * (non-Javadoc)
     * 
     * @see proofTree.Node#getContent()
     */
    public Object getContent() {
        return _content;
    }

    /*
     * (non-Javadoc)
     * 
     * @see proofTree.Node#getState()
     */
    public State getState() {
        return StringState.ANALYSED;
    }

//    /*
//     * (non-Javadoc)
//     * 
//     * @see proofTree.Node#asXMLElement()
//     */
//    public Element asXMLElement() {
//        Element el = new Element("stringNode");
//        el.setText(_content);
//        return el;
//    }
}