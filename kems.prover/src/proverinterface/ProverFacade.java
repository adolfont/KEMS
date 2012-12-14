/*
 * Created on 16/11/2005
 *
 */
package proverinterface;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.sql.Time;

import logic.problem.Problem;
import logic.signedFormulas.SignedFormulaCreator;
import main.newstrategy.ISimpleStrategy;
import main.newstrategy.Prover;
import main.proofTree.IProofTree;
import main.strategy.NullClosedProofTree;
import main.tableau.Method;
import main.tableau.Proof;
import main.tableau.verifier.ExtendedProof;
import main.tableau.verifier.ProofVerifier;

import org.apache.log4j.Logger;

import util.MemoryManager;

/**
 * A facade for the Prover class. It allows one to configure a prover and its
 * associated classes. Its main fucntion is to prove and verify a file or string
 * (list of formulas).
 * 
 * @author Adolfo Gustavo Serra Seca Neto
 * 
 */
public class ProverFacade {

	/**
	 * logger
	 */
	public static Logger logger = Logger.getLogger(ProverFacade.class);

	private SignedFormulaCreator signedFormulaCreator;

	private int garbageCollectionArg1;

	private int garbageCollectionArg2;

	public ProverFacade(int garbageCollectionArg1, int garbageCollectionArg2) {
		this.garbageCollectionArg1 = garbageCollectionArg1;
		this.garbageCollectionArg2 = garbageCollectionArg2;
	}

	public ProverFacade() {
		this(4, 500);
	}

	/**
	 * @param filenameOrString
	 * @return
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws NoSuchMethodException
	 * @throws ClassNotFoundException
	 * @throws IllegalArgumentException
	 * @throws SecurityException
	 */
	public ExtendedProof proveAndVerifyFile(String filenameOrString,
			ProverConfiguration pc) throws Throwable {

		return proveAndVerify(true, filenameOrString, pc);
	}

	/**
	 * @param filenameOrString
	 * @return
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws NoSuchMethodException
	 * @throws ClassNotFoundException
	 * @throws IllegalArgumentException
	 * @throws SecurityException
	 */
	public ExtendedProof proveAndVerifyString(String filenameOrString,
			ProverConfiguration pc) throws Throwable {
		return proveAndVerify(false, filenameOrString, pc);
	}

	/**
	 * Proves and verify a list of formulas that is in a file or in a string.
	 * 
	 * @param fileOrString
	 * @param filenameOrString
	 * @return an ExtendedProof
	 * @throws Exception
	 */

	private ExtendedProof proveAndVerify(boolean fileOrString,
			String filenameOrString, ProverConfiguration proverConfiguration)
			throws Throwable {

		signedFormulaCreator = instantiateSignedFormulaCreator(proverConfiguration);
		signedFormulaCreator.setTwoPhases(proverConfiguration
				.getTwoPhasesParserOption());
		Problem problem = analyse(fileOrString, filenameOrString);
		problem.setName(fileOrString ? filenameOrString
				: ProblemEditor.CURRENT_PROBLEM);
		problem.setSignedFormulaCreator(signedFormulaCreator);

		// creating a proof method
		Method method = new Method(RuleStructureFactory
				.createRulesStructure(proverConfiguration
						.getRulesStructureName()));

		// creating a strategy
		ISimpleStrategy s = createStrategy(proverConfiguration, method);

		// creating and configuring a prover
		Prover prover = new Prover();

		prover.setMethod(method);
		prover.setStrategy(s);

		if (logger.isDebugEnabled()) {
			logger.debug("Starting proof procedure at "
					+ new Time(System.currentTimeMillis()));
		}

		long begin, interval = 0;
		Proof proof = null;

		for (int i = 0; i < proverConfiguration.getTimes(); i++) {
			proof = null;
			MemoryManager.runGC(garbageCollectionArg1, garbageCollectionArg2);

			begin = System.currentTimeMillis();

			proof = prover.prove(problem);

			interval += System.currentTimeMillis() - begin;
		}

		if (logger.isDebugEnabled()) {
			logger.debug("Finishing proof procedure at "
					+ new Time(System.currentTimeMillis()));
		}

		ExtendedProof extProof = null;
		if (proof != null) {
			if (logger.isDebugEnabled()) {
				logger.debug("Starting proof verification at "
						+ new Time(System.currentTimeMillis()));
			}

			ProofVerifier verifier = new ProofVerifier(prover);

			proof.setProofTree(rebuildProof(prover.getStrategy(), proof
					.getProofTree()));

			// System.err.println(proof.toStringShort());
			// System.err.println(proof.isClosed());
			extProof = verifier.verify(proof);

			if (logger.isDebugEnabled()) {
				logger.debug("Finishing proof verification at "
						+ new Time(System.currentTimeMillis()));
			}
			extProof.setTimeSpent((interval) / proverConfiguration.getTimes());
			extProof.setProverConfiguration(proverConfiguration);
		}

		proof = null;
		MemoryManager.runGC(garbageCollectionArg1, garbageCollectionArg2);

		return extProof;
	}

	// private void temporaryOpenBranchVerifier(Proof proof) throws Exception {
	// MemoryManager.runGC();
	// IProofTreeBackwardNodeIterator it = ((IClassicalProofTree) proof
	// .getProofTree()).getOpenCompletedBranch()
	// .getBackwardNodeIterator();
	// List<SignedFormulaNode> allNodes = new ArrayList();
	//
	// while (it.hasPrevious()) {
	// IProofTree node = it.previous();
	// IProofTreeBasicIterator bit = node.getBackwardLocalIterator();
	// while (bit.hasPrevious()) {
	//
	// SignedFormulaNode sfnode = (SignedFormulaNode) bit.previous();
	// allNodes.add(0, sfnode);
	// // System.err.println(sfnode.getState() +" "+ sfnode);
	// }
	// }
	//
	// Iterator<SignedFormulaNode> itsf = allNodes.iterator();
	//
	// while (itsf.hasNext()) {
	// SignedFormulaNode sfnode = itsf.next();
	// System.err.println(sfnode.getContent());
	//
	// // verificar se todas sao fullfilled ou analysed
	// if (sfnode.getState() != SignedFormulaNodeState.FULFILLED
	// && sfnode.getState() != SignedFormulaNodeState.ANALYSED) {
	// System.err.println("ERROR : " + sfnode.getState() + " "
	// + sfnode);
	// }
	//
	// SignedFormula content = (SignedFormula) sfnode.getContent();
	// Formula f = content.getFormula();
	//
	// // se for atomica ou zeroaria
	// if (f instanceof AtomicFormula
	// || ((CompositeFormula) f).getConnective().getArity()
	// .equals(Arity.ZEROARY)) {
	// if (sfnode.getState() != SignedFormulaNodeState.FULFILLED) {
	// System.err.println("ERROR - SHOULD BE FULLFILLED : "
	// + sfnode.getState() + " " + sfnode);
	// }
	// } else {
	// // se nao for atomica ou zeroaria
	// // verificar se uma ou duas formulas abaixo tem ela como
	// // principal
	// Iterator<SignedFormulaNode> lookingForMain = allNodes
	// .iterator();
	// int foundMain = 0;
	//
	// while (lookingForMain.hasNext()) {
	// SignedFormulaNode nodeToLook = lookingForMain.next();
	// SignedFormulaNode nodeToLookMain = nodeToLook.getOrigin()
	// .getMain();
	//
	// if (nodeToLookMain == sfnode) {
	// foundMain++;
	// }
	// }
	//
	// if (foundMain == 0) {
	// // System.err.println("NOT USED AS MAIN " + sfnode);
	// }
	//
	// }
	//
	// }
	//
	// }

	/**
	 * Rebuilds a proof tree reincorporating branches that were discarde to save
	 * memory.
	 * 
	 * @param strategy
	 * @param proofTree
	 * @return
	 */
	private IProofTree rebuildProof(ISimpleStrategy strategy,
			IProofTree proofTree) {
		String currentBranch = "";
		IProofTree current = proofTree;
		while (current.getRight() != null) {
			if (current.getLeft() == NullClosedProofTree.INSTANCE) {
				buildLeft(strategy, current, currentBranch);
			}
			current = current.getRight();
			currentBranch += "r";
		}

		// if the strategy has saved closed branches in a file, remove this file
		if (strategy.getProofSaver() != null) {
			strategy.getProofSaver().deleteFile();
		}

		return proofTree;
	}

	/**
	 * @param strategy
	 * @param current
	 * @param currentBranch
	 * @return
	 */
	private void buildLeft(ISimpleStrategy strategy, IProofTree current,
			String currentBranch) {
		if (strategy.getProofSaver() != null) {
			current.setLeft(strategy.getProofSaver().loadBranch(strategy,
					currentBranch + "l"));

			build(strategy, current.getLeft(), currentBranch + "l");
		}

	}

	/**
	 * @param strategy
	 * @param left
	 * @param string
	 */
	private void build(ISimpleStrategy strategy, IProofTree current,
			String currentBranch) {
		IProofTree left = strategy.getProofSaver().loadBranch(strategy,
				currentBranch + "l");
		IProofTree right = strategy.getProofSaver().loadBranch(strategy,
				currentBranch + "r");
		if (left != null) {
			current.setLeft(left);
			build(strategy, current.getLeft(), currentBranch + "l");
		}
		if (right != null) {
			current.setRight(right);
			build(strategy, current.getRight(), currentBranch + "r");
		}

	}

	/**
	 * @param strategyClassName2
	 * @param method
	 * @return
	 */
	private ISimpleStrategy createStrategy(
			ProverConfiguration proverConfiguration, Method method)
			throws Exception {

		ISimpleStrategy strategy;
		Class<?> strategyDefinition;
		Class<?>[] methodArgClass = new Class[] { Method.class };
		Object[] methodArgs = new Object[] { method };
		Constructor<?> methodArgConstructor;

		try {
			strategyDefinition = Class.forName(proverConfiguration
					.getStrategyFullClassName());
		} catch (ClassNotFoundException e) {
			throw new Exception(this.getClass().getName()
					+ " - Class not found " + e.getMessage());
		}
		try {
			methodArgConstructor = strategyDefinition
					.getConstructor(methodArgClass);
		} catch (SecurityException e1) {
			throw new Exception(this.getClass().getName()
					+ " - Constructor for strategy class not found "
					+ e1.getMessage());
		} catch (NoSuchMethodException e1) {
			throw new Exception(this.getClass().getName()
					+ " - Constructor for strategy class not found "
					+ e1.getMessage());
		}
		strategy = (ISimpleStrategy) createObject(methodArgConstructor,
				methodArgs);

		strategy.setDiscardClosedBranches(proverConfiguration
				.getDiscardClosedBranches());
		strategy.setSaveOrigin(proverConfiguration.getSaveOrigin());
		strategy.setSaveDiscardedBranches(proverConfiguration
				.getSaveDiscardedBranches());

		strategy
				.setComparator(proverConfiguration.getSignedFormulaComparator());

		//EMERSON: Temporário Algoritmo Genético
		strategy.setModoEstocastico(proverConfiguration.getModoEstocastico());
		
		// if (strategy instanceof ConfigurableSimpleStrategy) {
		// ((ConfigurableSimpleStrategy) strategy)
		// .setComparator(proverConfiguration
		// .getSignedFormulaComparator());
		// }
		//
		// if (strategy instanceof AbstractSimpleStrategy
		// && proverConfiguration.getSaveOrigin()) {
		//
		// // ((AbstractSimpleStrategy) strategy)
		// // .setUseBackjumping(proverConfiguration.getUseBackjumping());
		// }

		return strategy;
	}

	private static Object createObject(Constructor<?> constructor,
			Object[] arguments) throws Exception {

		Object object = null;

		try {
			object = constructor.newInstance(arguments);
		} catch (Exception e) {
			throw new Exception("Error instantiating strategy: "
					+ e.getMessage());
		}
		return object;
	}

	/**
	 * @param familyName
	 * @return
	 */
	private Problem analyse(boolean fileOrString, String filenameOrString)
			throws Throwable {

		try {
			return fileOrString ? signedFormulaCreator
					.parseFile(filenameOrString) : signedFormulaCreator
					.parseText(filenameOrString);
		} catch (Throwable e) {
			if (!(e instanceof OutOfMemoryError)) {
				e.printStackTrace();
				throw new Exception("Parsing error: " + e.getMessage());
			} else {
				throw e;
			}
		}
	}

	/**
	 * Generates a problem for a file name.
	 * 
	 * @param problemFileName
	 * @return
	 * @throws Exception
	 */
	public Problem analyse(String problemFileName,
			ProverConfiguration proverConfiguration) throws Throwable {

		signedFormulaCreator = instantiateSignedFormulaCreator(proverConfiguration);
		signedFormulaCreator.setTwoPhases(proverConfiguration
				.getTwoPhasesParserOption());
		return analyse(true, problemFileName);
	}

	private SignedFormulaCreator instantiateSignedFormulaCreator(
			ProverConfiguration proverConfiguration) {
		return new SignedFormulaCreator(proverConfiguration
				.getFirstParsingLibName());
	}

	public SignedFormulaCreator getSignedFormulaCreator() {
		return signedFormulaCreator;
	}

}
