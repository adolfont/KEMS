/*
 * Created on 27/10/2005
 *
 */
package main.newstrategy;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import logic.problem.Problem;
import logic.signedFormulas.SignedFormula;
import logic.signedFormulas.SignedFormulaBuilder;
import logicalSystems.classicalLogic.ClassicalConnectives;
import logicalSystems.classicalLogic.ClassicalRuleStructures;
import logicalSystems.classicalLogic.ClassicalSigns;
import main.newstrategy.cpl.configurable.comparator.ISignedFormulaComparator;
import main.newstrategy.simple.SimpleStrategyImplementation;
import main.newstrategy.util.ProofSaverLoader;
import main.proofTree.IProofTree;
import main.proofTree.ProofTree;
import main.proofTree.SignedFormulaNode;
import main.proofTree.SignedFormulaNodeState;
import main.proofTree.origin.IOrigin;
import main.proofTree.origin.NamedOrigin;
import main.proofTree.origin.SignedFormulaNodeOrigin;
import main.strategy.ClassicalProofTree;
import main.strategy.NullClosedProofTree;
import main.strategy.applicator.IProofTransformation;
import main.strategy.applicator.IRuleApplicator;
import main.strategy.applicator.PBRuleApplicator;
import main.tableau.Method;

import org.apache.log4j.Logger;

import rules.Rule;

/**
 * A simple strategy for classical propositional logic.
 * 
 * @author Adolfo Gustavo Serra Seca Neto
 * 
 */
public abstract class AbstractSimpleStrategy implements ISimpleStrategy {

	/**
	 * logger
	 */
	public static Logger logger = Logger.getLogger(AbstractSimpleStrategy.class);

	/** a tableau method that provides rule structures */
	private Method method;

	/** a list of rule applicators */
	private List<IRuleApplicator> ruleApplicators;

	/** a list of proof transformations */
	private List<IProofTransformation> proofTransformations;

	/** a stack of open branches */
	private LinkedList<IProofTree> openBranches;

	/** the current branch of the proof tree */
	private ClassicalProofTree current;

	/** the root branch of the proof tree */
	private ClassicalProofTree proofTree;

	/**
	 * the signed formula builder provided by the problem and used during the
	 * proof process
	 */
	private SignedFormulaBuilder sfb;

	/**
	 * a comparator that, if set, sorts formulas before applying the PB rule.
	 */
	private ISignedFormulaComparator comparator;

	// Os atributos abaixo nao deveriam estar aqui!!!
	// TODO REFATORAR

	/** if true, saves s-formula origins in s-formula nodes */
	private boolean saveOrigin;

	/** if true, discards closed proof tree nodes */
	private boolean discardClosedBranches;

	/** if not null, contains a proof tree node saver */
	private ProofSaverLoader proofSaver;

	/** if true, saves discarded proof tree nodes */
	private boolean saveDiscardedBranches;

	private SignedFormula tTopFormula, fBottomFormula;

	/**
	 * Empty constructor to be used by subclasses.
	 */
	protected AbstractSimpleStrategy() {
		saveOrigin = true;
		discardClosedBranches = false;
		saveDiscardedBranches = true;
	}

	/**
	 * Creates a simple strategy for a method.
	 * 
	 * @param method
	 *          a proof method.
	 */
	public AbstractSimpleStrategy(Method method) {
		this();
		this.method = method;

		// initialize proof transformations
		proofTransformations = new ArrayList<IProofTransformation>();
		// add to proof transformations the PB rule applicator
		proofTransformations.add(new PBRuleApplicator(this, ClassicalRuleStructures.PB_RULE_LIST));

		ruleApplicators = new ArrayList<IRuleApplicator>();
	}

	/**
	 * Adds a rule applicator to this strategy rule applicator list
	 * 
	 * @param applicator
	 */
	protected void addRuleApplicator(IRuleApplicator applicator) {
		ruleApplicators.add(applicator);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see main.newstrategy.IStrategy#close(logic.problem.Problem)
	 */
	public ProofTree close(Problem p) {

		// TODO ASPECT save branches
		if (saveDiscardedBranches) {
			proofSaver = new ProofSaverLoader(p);
		}

		// if (logger.isDebugEnabled()){
		// logger.debug(p);
		// }

		sfb = new SignedFormulaBuilder(p.getSignedFormulaFactory(), p.getFormulaFactory());
		proofTree = (ClassicalProofTree) createProofTree(p, sfb);

		// if (logger.isDebugEnabled()){
		// logger.debug(proofTree);
		// }

		return new SimpleStrategyImplementation().execute(this);
	}

	/**
	 * @param p
	 * @param sfb
	 * @return
	 */
	//EMERSON: Temporário Algoritmo Genético
	public IProofTree createProofTree(Problem p, SignedFormulaBuilder sfb) {

		tTopFormula = p.getSignedFormulaFactory().createSignedFormula(ClassicalSigns.TRUE,
				p.getFormulaFactory().createCompositeFormula(ClassicalConnectives.TOP));

		fBottomFormula = p.getSignedFormulaFactory().createSignedFormula(ClassicalSigns.FALSE,
				p.getFormulaFactory().createCompositeFormula(ClassicalConnectives.BOTTOM));

		SignedFormulaNode root = new SignedFormulaNode(tTopFormula, SignedFormulaNodeState.FULFILLED,
				NamedOrigin.DEFINITION);
		IProofTree pt = createPTInstance(root);
		SignedFormulaNode second = new SignedFormulaNode(fBottomFormula,
				SignedFormulaNodeState.FULFILLED, NamedOrigin.DEFINITION);
		pt.addLast(second);
		fillWith(pt, p);
		return pt;
	}

	private void fillWith(IProofTree pt, Problem p) {
		for (int i = 0; i < p.getFormulas().size(); i++) {
			// System.err.println("ADDING " + p.getFormulas().get(i));
			SignedFormulaNode n = new SignedFormulaNode(p.getFormulas().get(i),
					SignedFormulaNodeState.NOT_ANALYSED, NamedOrigin.PROBLEM);
			pt.addLast(n);
			// System.err.println("ADDED "
			// + p.getFormulas().get(i)
			// + "?"
			// + ((FormulaReferenceClassicalProofTree) pt).contains(p
			// .getFormulas().get(i)));
		}
	}

	/**
	 * @param root
	 * @return
	 */
	public abstract IProofTree createPTInstance(SignedFormulaNode root);

	/**
	 * @return this strategy rule applicator list
	 */
	public List<IRuleApplicator> getRuleApplicators() {
		return ruleApplicators;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see main.newstrategy.IStrategy#getMethod()
	 */
	public Method getMethod() {
		return method;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see main.newstrategy.IStrategy#getOpenBranches()
	 */
	public LinkedList<IProofTree> getOpenBranches() {
		return openBranches;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * main.newstrategy.IStrategy#setCurrent(main.strategy.ClassicalProofTree)
	 */
	public void setCurrent(ClassicalProofTree newCurrent) {
		this.current = newCurrent;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see main.newstrategy.ISimpleStrategy#setOpenBranches(java.util.LinkedList)
	 */
	public void setOpenBranches(LinkedList<IProofTree> openBranches) {
		this.openBranches = openBranches;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see main.newstrategy.IStrategy#getProofTransformations()
	 */
	public List<IProofTransformation> getProofTransformations() {
		return proofTransformations;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see main.newstrategy.ISimpleStrategy#getCurrent()
	 */
	public ClassicalProofTree getCurrent() {
		return current;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see main.newstrategy.IStrategy#getProofTree()
	 */
	public ClassicalProofTree getProofTree() {
		return proofTree;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see main.newstrategy.IStrategy#getSignedFormulaBuilder()
	 */
	public SignedFormulaBuilder getSignedFormulaBuilder() {
		return sfb;
	}

	/**
	 * @param method
	 */
	protected void setMethod(Method method) {
		this.method = method;
	}

	/**
	 * @param proofTransformations
	 */
	protected void setProofTransformations(List<IProofTransformation> proofTransformations) {
		this.proofTransformations = proofTransformations;
	}

	/**
	 * @param ruleApplicators
	 */
	protected void setRuleApplicators(List<IRuleApplicator> ruleApplicators) {
		this.ruleApplicators = ruleApplicators;
	}

	/**
	 * @return
	 */
	public boolean isDiscardClosedBranches() {
		// ASPECT discard branches

		return discardClosedBranches;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see main.newstrategy.ISimpleStrategy#setDiscardClosedBranches(boolean)
	 */
	public void setDiscardClosedBranches(boolean discardClosedBranches) {
		// ASPECT discard branches
		this.discardClosedBranches = discardClosedBranches;
	}

	/**
	 * @return
	 */
	public boolean isSaveOrigin() {
		return saveOrigin;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see main.newstrategy.ISimpleStrategy#setSaveOrigin(boolean)
	 */
	public void setSaveOrigin(boolean saveOrigin) {
		this.saveOrigin = saveOrigin;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * main.newstrategy.ISimpleStrategy#finishBranch(main.proofTree.IProofTree)
	 */
	public void finishBranch(IProofTree branch) {

		if (isDiscardClosedBranches()) {
			if (isSaveDiscardedBranches()) {
				proofSaver.saveBranch(branch);
			}
			if (branch.getParent() != null) {
				if (branch.getParent().getLeft() == branch) {
					branch.getParent().setLeft(NullClosedProofTree.INSTANCE);
				} else {
					finishBranch(branch.getParent());
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see main.newstrategy.ISimpleStrategy#createOrigin(rules.Rule,
	 * main.proofTree.SignedFormulaNode, main.proofTree.SignedFormulaNode)
	 */
	public final IOrigin createOrigin(Rule rule, SignedFormulaNode main, SignedFormulaNode auxiliary) {
		if (isSaveOrigin()) {
			ArrayList<SignedFormulaNode> list = new ArrayList<SignedFormulaNode>();
			if (auxiliary!=null) list.add(auxiliary);
			return new SignedFormulaNodeOrigin(rule, main, list);
		} else
			return NamedOrigin.EMPTY;
	}

	public final IOrigin createOrigin(Rule rule, SignedFormulaNode main, SignedFormulaNode auxiliary1,SignedFormulaNode auxiliary2) {
		if (isSaveOrigin()) {
			ArrayList<SignedFormulaNode> list = new ArrayList<SignedFormulaNode>();
			// TODO refactor
			if (auxiliary1!=null) list.add(auxiliary1);
			if (auxiliary2!=null) list.add(auxiliary2);
			return new SignedFormulaNodeOrigin(rule, main, list);
		} else
			return NamedOrigin.EMPTY;
	}

	/**
	 * @return true if save discarded branches flag is set
	 */
	public boolean isSaveDiscardedBranches() {
		return saveDiscardedBranches;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see main.newstrategy.ISimpleStrategy#setSaveDiscardedBranches(boolean)
	 */
	public void setSaveDiscardedBranches(boolean saveDiscardedBranches) {
		this.saveDiscardedBranches = saveDiscardedBranches;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see main.newstrategy.ISimpleStrategy#getProofSaver()
	 */
	public ProofSaverLoader getProofSaver() {
		return proofSaver;
	}

	public ISignedFormulaComparator getComparator() {
		return comparator;
	}

	public void setComparator(ISignedFormulaComparator comparator) {
		this.comparator = comparator;
	}

	public SignedFormula getFBottomFormula() {
		return fBottomFormula;
	}

	public SignedFormula getTTopFormula() {
		return tTopFormula;
	}

	//EMERSON: Temporário Algoritmo Genético
	public void setProofTree(ClassicalProofTree cpt) {
		proofTree = cpt;
	}
}
