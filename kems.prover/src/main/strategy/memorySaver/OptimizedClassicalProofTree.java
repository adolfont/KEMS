/*
 * Created on 07/04/2005
 *
 */
package main.strategy.memorySaver;

import logic.signedFormulas.FormulaSign;
import logic.signedFormulas.SignedFormula;
import main.proofTree.INode;
import main.proofTree.IProofTree;
import main.proofTree.SignedFormulaNode;
import main.strategy.ClassicalProofTree;
import main.strategy.ICloseableProofTree;

/**
 * @author Adolfo Gustavo Serra Seca Neto
 * 
 * 
 */
public class OptimizedClassicalProofTree extends ClassicalProofTree implements
        ICloseableProofTree {

    // TODO MUITO RUIM A FORMA DE ESTENDER PROOF TREE e suas subclasses!
    // TEM QUE REIMPLEMENTAR ADDLEFT, ADDRIGHT, CONSTRUTOR

    boolean _locallyClosed, _closedByBranching;

    public OptimizedClassicalProofTree(SignedFormulaNode aNode) {
        super(aNode);
        _locallyClosed = false;
        _closedByBranching = false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see strategy.ClassicalProofTree#makeInstance(proofTree.Node)
     */
    protected IProofTree makeInstance(INode aNode) {
        return new OptimizedClassicalProofTree((SignedFormulaNode) aNode);
    }

    // COMENTADO EM 07-07-2006
//    public void save() {
//
//        IProofTreeBasicIterator it = this.getLocalIterator();
//
//        System.err.println("***");
//
//        while (it.hasNext()) {
//            System.err.println(it.next());
//        }
//
//    }

    // ROUBADO DE FRCPT
    // comentado em 22-06-2006
//    private void addStructures(ClassicalProofTree tree) {
//
//        List l = new ArrayList(tree.getFsmm().keySet());
//        for (int i = 0; i < l.size(); i++) {
//            SignSet ss = tree.getFsmm().get((Formula) l.get(i));
//
//            Iterator it = ss.iterator();
//
//            while (it.hasNext()) {
//                this.getFsmm().put((Formula) l.get(i), (FormulaSign) it.next());
//
//            }
//        }
//
//        l = new ArrayList(tree.getSignedFormulasToNodes().keySet());
//        for (int i = 0; i < l.size(); i++)
//            this.getSignedFormulasToNodes().put(l.get(i),
//                    tree.getSignedFormulasToNodes().get(l.get(i)));
//
//    }

    protected void updateMultimap(SignedFormulaNode aNode) {
        SignedFormula sf = (SignedFormula) aNode.getContent();
        getFsmm().put(sf.getFormula(), sf.getSign());
        if (getFsmm().get(sf.getFormula()).size() == 2) {
            setClosingReason(sf);
            _locallyClosed = true;
        } else {
            if (getFsmm().get(sf.getFormula()).size() == 1) {
                FormulaSign fs = getFsmm().get(sf.getFormula()).get(0);
                // System.err.println(fs + " " + sf);

                ClassicalProofTree current = (ClassicalProofTree) this
                        .getParent();
                while (current != null) {

                    // System.err.println(current.getFsmm().get(sf.getFormula()));
                    // System.err.println(current==null);

                    if (current.getFsmm().get(sf.getFormula()) != null
                            && ((current.getFsmm().get(sf.getFormula())).size() > 0)
                            && (((FormulaSign) current.getFsmm().get(
                                    sf.getFormula()).get(0)) != fs)) {
                        setClosingReason(sf);
                        _locallyClosed = true;
                        return;
                    }
                    current = (ClassicalProofTree) current.getParent();
                }
            }

        }
    }

    public boolean isLocallyClosed() {
        return _locallyClosed;
    }

    /**
     * @param b
     */
    protected void setLocallyClosed(boolean b) {
        _locallyClosed = b;
    }

    public boolean isClosedByBranching() {
        _closedByBranching = (getLeft() != null
                && ((ICloseableProofTree) getLeft()).isClosed() && ((ICloseableProofTree) getRight())
                .isClosed());
        return _closedByBranching;
    }

    public boolean getClosedByBranching() {
        return _closedByBranching;
    }

    public boolean isClosed() {
        return isLocallyClosed() || isClosedByBranching();
    }

}
