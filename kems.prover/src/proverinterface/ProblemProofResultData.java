/*
 * Created on 23/11/2005
 *
 */
package proverinterface;

import proverinterface.runner.family.FamilyProblemBasicInfo;
import main.tableau.verifier.ExtendedProof;

/**
 * Records info about a problem proof. 
 *
 * @author Adolfo Gustavo Serra Seca Neto
 *
 */
public class ProblemProofResultData {
    
    private FamilyProblemBasicInfo problem;
    private ExtendedProof proof;
    
    public ProblemProofResultData(FamilyProblemBasicInfo problem, ExtendedProof ep){
        this.problem = problem;
        this.proof = ep;
    }
    
    public ExtendedProof getProof(){
        return proof;
    }
}
