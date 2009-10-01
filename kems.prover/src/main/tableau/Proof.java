/*
 * Created on Oct 22, 2004
 *
 */
package main.tableau;

import logic.problem.Problem;
import main.proofTree.IProofTree;
import main.proofTree.ProofTree;

//import org.jdom.Element;

/**
 * 
 * Class that represents proofs.
 * 
 * @author Adolfo Gustavo Serra Seca Neto
 *  
 */
public class Proof implements IProof{

    boolean closed;

    IProofTree proofTree;

    Problem problem;

//    Execution execution;

//    Element problemElement;

    String summary = "";

    /**
     * Creates a proof
     * 
     * @param closed -
     *            if the proof is closed or not
     * @param pt -
     *            the proof tree built for teh problem
     * @param p -
     *            the problem used to build the proof
     */
    public Proof(boolean _closed, ProofTree _pt, Problem p) {
        super();
        this.closed = _closed;
        this.proofTree = _pt;
        this.problem = p;

        // comentado em 23-06-2006
//        problemElement = problem.asXMLElement();

    }

    /**
     * Creates a Proof object
     * 
     * @param prover -
     *            a prover used to build the proof.
     * @param p -
     *            a problem
     */
    public Proof(Prover _prover, Problem p) {
        problem = p;
        
        // comentado em 23-06-2006
		// problemElement = problem.asXMLElement();

        Proof x = _prover.prove(p);
        closed = x.isClosed();
        proofTree = x.getProofTree();
    }

    /**
     * @return true if te proof is closed.
     */
    public boolean isClosed() {
        return closed;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    public String toString() {
        String result = showProblem() + System.getProperty("line.separator");
        result += showExecution() + System.getProperty("line.separator");
        result += proofTree.toString() + System.getProperty("line.separator");

        return result;
    }

    private String showExecution() {
        return "";
    }

    private String showProblem() {
        return problem.toString();
    }

    public String summary() {
        return summary;
    }

    /**
     * @return
     */
    public String toStringShort() {
        return proofTree.toStringShort();
    }

    /**
     * @return
     */
    public String showSize() {
        return ((ProofTree)proofTree).showSize();
    }

    /**
     * @param string
     */
    public void setSummary(String string) {
        summary = string;
    }

    /**
     * @param e
     */
//    public void setExecution(Execution e) {
//        execution = e;
//
//    }

    // comentado em 23-06-2006
//    public Element asXMLElement() {
//        Element proof = new Element("proof");
//
//        proof.addContent(problemElement);
//        if (execution != null) {
//            proof.addContent(execution.asXMLElement());
//        }
//        proof.addContent(proofTree.asXMLElement());
//
//        return proof;
//    }

    public IProofTree getProofTree() {
        return proofTree;
    }

    public Problem getProblem() {
        return problem;
    }

    /**
     * @param tree
     */
    public void setProofTree(IProofTree tree) {
        proofTree = tree;
        
    }
}