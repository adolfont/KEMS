/*
 * Created on 29/11/2005
 *
 */
package main.newstrategy.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import logic.problem.Problem;
import main.newstrategy.ISimpleStrategy;
import main.proofTree.IProofTree;
import main.proofTree.SignedFormulaNode;
import main.proofTree.SignedFormulaNodeState;
import main.proofTree.iterator.IProofTreeBasicIterator;
import main.proofTree.origin.NamedOrigin;
import util.StringUtil;

/**
 * A class that saves a proof and loads it afterwards.
 * 
 * @author Adolfo Gustavo Serra Seca Neto
 *  
 */
public class ProofSaverLoader {
    public static final String BRANCH = "BRANCH ID: ";

    public static final String ID_SEPARATOR = "*";

    private String proofId;

    private FileWriter fileWriter;

    private String filename;

    private Problem problem;

    public ProofSaverLoader(Problem p) {
        this.problem = p;
        this.proofId = p.getName();
        try {
            filename = System.getProperty("user.dir")
                    + StringUtil.FILE_SEPARATOR
                    + proofId.substring(proofId
                            .lastIndexOf(StringUtil.FILE_SEPARATOR) == -1 ? 0
                            : proofId.lastIndexOf(StringUtil.FILE_SEPARATOR),
                            proofId.length()) + ".tmp";
            fileWriter = new FileWriter(filename);
            fileWriter = new FileWriter(filename, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    

    public void saveBranch(IProofTree branch) {

        try {

            IProofTreeBasicIterator it = branch.getLocalIterator();
            fileWriter.write(BRANCH + ID_SEPARATOR + getBranchId(branch)
                    + ID_SEPARATOR + StringUtil.LINE_SEPARATOR);
            while (it.hasNext()) {
                fileWriter.write(((SignedFormulaNode) it.next()).getContent()
                        .toString()
                        + StringUtil.LINE_SEPARATOR);

            }
            fileWriter.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * @param branch2
     * @return
     */
    private String getBranchId(IProofTree branch2) {
        if (branch2.getParent() == null) {
            return "";
        } else {
            if (branch2 == branch2.getParent().getLeft()) {
                return getBranchId(branch2.getParent()) + "l";
            } else
                return getBranchId(branch2.getParent()) + "r";
        }

    }

    public IProofTree loadBranch(ISimpleStrategy strategy, String id) {

        IProofTree branch = null;
        boolean foundBranch = false;

        FileReader file;
        try {
            file = new FileReader(filename);
            BufferedReader reader = new BufferedReader(file);

            while (reader.ready()) {
                String line = reader.readLine();
                if (line.startsWith(BRANCH)
                        && line.endsWith(ID_SEPARATOR + id + ID_SEPARATOR)) {
                    foundBranch = true;
                    break;
                }
            }

            if (!foundBranch) {
                return null;
            }

            while (reader.ready()) {
                String line = reader.readLine();
                if (line.startsWith(BRANCH)) {
                    break;
                } else {
                    SignedFormulaNode node = new SignedFormulaNode(problem
                            .getSignedFormulaCreator().parseString(line),
                            SignedFormulaNodeState.ANALYSED, NamedOrigin.EMPTY);
                    if (branch == null) {
                        branch = strategy.createPTInstance(node);
                    } else {
                        branch.addLast(node);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return branch;

    }
    
    public void deleteFile() {
        new File(filename).delete();
    }


}
