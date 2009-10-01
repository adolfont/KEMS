/*
 * Created on 11/11/2005
 *
 */
package main.proofTree;

//import org.jdom.Element;

/**
 * [CLASS_DESCRIPTION] 
 *
 * @author Adolfo Gustavo Serra Seca Neto
 *
 */
public interface INode {
    /**
     * @param next
     *            The next to set.
     */
    public abstract void setNext(INode next);

    /**
     * @param previous
     *            The previous to set.
     */
    public abstract void setPrevious(INode previous);

    /**
     *  
     */
    public abstract INode getPrevious();

    /**
     *  
     */
    public abstract INode getNext();

    /**
     * @return
     */
    public abstract Object getContent();

    public abstract State getState();

    public abstract IProofTree getBranch();

    public abstract void setBranch(IProofTree branch);

//    /**
//     * @return
//     */
//    public abstract Element asXMLElement();
}