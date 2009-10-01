/*
 * Created on 15/11/2005
 *
 */
package main.tableau.verifier;

import proverinterface.ProverConfiguration;
import logic.problem.Problem;
import main.proofTree.IProofTree;
import main.tableau.IProof;

/**
 * A proof with more information
 * 
 * @author Adolfo Gustavo Serra Seca Neto
 *  
 */
public class ExtendedProof implements IProof {
    

    private Boolean verificationResult;

    private IProof originalProof;

    private ExtendedProofTree extendedProofTree;

    private long timeSpent;

    private int nodesQuantity;

    private Integer usedQuantity;
    
    private ProverConfiguration proverConfiguration;

    public ExtendedProof(IProof originalProof) {
        this.originalProof = originalProof;
        verificationResult = null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see main.tableau.IProof#isClosed()
     */
    public boolean isClosed() {
        return originalProof.isClosed();
    }

    /*
     * (non-Javadoc)
     * 
     * @see main.tableau.IProof#getProofTree()
     */
    public IProofTree getProofTree() {
        return extendedProofTree;
    }

    /*
     * (non-Javadoc)
     * 
     * @see main.tableau.IProof#getProblem()
     */
    public Problem getProblem() {
        return originalProof.getProblem();
    }

    public void setVerificationResult(boolean value) {
        verificationResult = value ? Boolean.TRUE : Boolean.FALSE;
    }

    public Boolean getVerificationResult() {
        return verificationResult;
    }

    /**
     * @param tree
     */
    public void setExtendedProofTree(ExtendedProofTree tree) {
        this.extendedProofTree = tree;

    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return "Extended Proof - Verified: " + verificationResult
                + System.getProperty("line.separator")
                + extendedProofTree.toString();
    }

    /**
     * @param l
     */
    public void setTimeSpent(long l) {
        timeSpent = l;
    }

    public long getTimeSpent() {
        return timeSpent;
    }

    public int getNodesQuantity() {
        return nodesQuantity;
    }

    public Integer getUsedQuantity() {
        return usedQuantity;
    }

    public void setNodesQuantity(int nodesQuantity) {
        this.nodesQuantity = nodesQuantity;
    }

    public void setUsedQuantity(Integer usedQuantity) {
        this.usedQuantity = usedQuantity;
    }
    
    public int getSize() {
        return extendedProofTree.getSize();
    }

    public ProverConfiguration getProverConfiguration() {
        return proverConfiguration;
    }
    public void setProverConfiguration(ProverConfiguration proverConfiguration) {
        this.proverConfiguration = proverConfiguration;
    }
}
