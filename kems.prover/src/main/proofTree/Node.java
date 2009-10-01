/*
 * Created on 24/09/2004
 *
 */
package main.proofTree;

//import org.jdom.Element;

/**
 * @author Adolfo Gustavo Serra Seca Neto
 *  
 */
public abstract class Node implements INode {

    INode _previous, _next;
    IProofTree _branch;

    /**
     * @param next
     *            The next to set.
     */
    public void setNext(INode next) {
        _next = next;
    }

    /**
     * @param previous
     *            The previous to set.
     */
    public void setPrevious(INode previous) {
        _previous = previous;
    }

    /**
     *  
     */
    public INode getPrevious() {
        return _previous;
    }

    /**
     *  
     */
    public INode getNext() {
        return _next;
    }
    
    /**
     * @return
     */
    public abstract Object getContent();
    
    public abstract State getState();
    
    public IProofTree getBranch(){
        return _branch;
    }
    
    public void setBranch(IProofTree branch){
        _branch = branch;
    }

//    /**
//     * @return
//     */
//    public abstract Element asXMLElement();

}