/*
 * Created on 25/11/2005
 *
 */
package proverinterface.runner.family;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import proverinterface.runner.CommonResultsFrameTableLine;

import logic.problem.Problem;
import main.proofTree.ProofTree;
import main.tableau.verifier.ExtendedProof;

/**
 * A table line for proof results
 *
 * @author Adolfo Gustavo Serra Seca Neto
 *
 */
public class FamilyProblemProofResultsFrameTableLine extends CommonResultsFrameTableLine{
    
    /* (non-Javadoc)
     * @see proverconfigurator.CommonResultsFrameTableLine#getColumnNames()
     */
    public String[] getColumnNames() {
        String[] columnNames =super.getColumnNames();
        
        List<String> l = new ArrayList<String>(Arrays.asList(columnNames));
        l.add(new String("Prover configuration"));
        l.add(new String("Time spent (in s)"));
        l.add(new String("Closed?"));
        l.add(new String("Verified?"));
        l.add(new String("Problem size"));
        l.add(new String("Proof size"));
        l.add(new String("Nodes"));
        l.add(new String("Used nodes"));
        l.add(new String("% used nodes"));
        l.add(new String("Proof tree height"));
        
        return getStringArray(l);
    }

    
    public FamilyProblemProofResultsFrameTableLine(FamilyProblemBasicInfo problem,
            ExtendedProof ep, Problem p) {
        super(problem);
        NumberFormat nf = NumberFormat.getInstance(Locale.US);
        getValues().add(ep.getProverConfiguration().toString());
        getValues().add(nf.format(ep.getTimeSpent() / 1000.0));
        getValues().add(new Boolean(ep.isClosed()).toString());
        getValues().add(ep.getVerificationResult().toString());
        getValues().add(Long.toString(p.getSignedFormulaFactory().getComplexity()).toString());
        getValues().add(Long.toString(ep.getSize()));
        getValues().add(Integer.toString(ep.getNodesQuantity()));
        getValues().add(ep.getUsedQuantity()!=null?Integer.toString(ep.getUsedQuantity().intValue()):"n/a");
        getValues().add(ep.getUsedQuantity()!=null?nf.format(100.0 * ep.getUsedQuantity().intValue()
                / ep.getNodesQuantity())
                + "%":"n/a");
        getValues().add(Integer.toString(((ProofTree)ep.getProofTree()).getHeight()));

    }


}
