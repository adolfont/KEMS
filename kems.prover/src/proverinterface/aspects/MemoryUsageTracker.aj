/*
 * Created on 26/11/2005
 *
 */
package proverinterface.aspects;

import logic.formulas.CompositeFormula;
import logic.formulas.FormulaFactory;
import logic.signedFormulas.SignedFormulaList;
import main.newstrategy.IStrategy;
import main.newstrategy.simple.SimpleStrategyImplementation;
import main.proofTree.IProofTree;
import main.proofTree.SignedFormulaNode;
import main.strategy.IClassicalProofTree;

import org.apache.log4j.Logger;

import rules.IRule;
import util.MemoryManager;

/**
 * Tracks memory usage.
 * 
 * @author Adolfo Gustavo Serra Seca Neto
 * 
 */
public aspect MemoryUsageTracker {

	private static int MINIMUM_MEMORY_BEFORE_GARBAGE_COLLECTION = 70000;

	private static int MINIMUM_MEMORY_AFTER_GARBAGE_COLLECTION = 120000;

	/**
	 * logger
	 */
	public static Logger logger = Logger.getLogger(MemoryUsageTracker.class);

	/**
	 * Default value for enable is true.
	 */
	private static boolean enabled = true;

	/**
	 * Habilita ou desabilita todos os advices do aspecto
	 * 
	 */
	public static void setEnabled(boolean value) {
		enabled = value;
	}

	/** aspect attributes * */

	/** initial amount of memory and maximal memory used * */
	private long initialMemory, maxMemoryUsed = 0;

	/** final formula factory * */
	private FormulaFactory _lastFF;

	/** ********* pointcuts *********** */
	pointcut callClose():execution (* IStrategy+.close(..));

	pointcut callSimpleRule(IRule r, SignedFormulaList sfl):
        target(r) && args(..,sfl) &&
        call (* IRule+.getPossibleConclusions(..));

	pointcut callSubformulaRule(IRule r, SignedFormulaList sfl,
			CompositeFormula f):
        target(r) && args(..,sfl, f) &&
        call (* IRule+.getPossibleConclusions(..));

	pointcut callPBRule():
        call (* SimpleStrategyImplementation.applyPBRule(..));

	pointcut callRule_toGetFormulaFactory(FormulaFactory ff):
        args(*,ff, ..) &&
        call (* IRule+.getPossibleConclusions(..));

	pointcut addNodeToProofTree(IProofTree pt, SignedFormulaNode sfn):
        target(pt) && args (sfn) && (call (* IProofTree+.addLast(*)) ||
        call (IProofTree+.new(*)));

	pointcut OutOfMemory():
        handler (OutOfMemoryError);

	pointcut ClassicalProofTree(IClassicalProofTree cpt):
        target(cpt) &&
        (execution  (* IClassicalProofTree+.addLast(*)));

	/** ********** pieces of advice ************ */
	/**
	 * Before starting a proof, create a new RulesUsage object and initializes
	 * some attributes.
	 */
	before(): callClose(){

		if (enabled) {
			// System.gc();
			try {
				MemoryManager.runGC();
			} catch (Exception e) {
				// do nothing
			}

			initialMemory = Runtime.getRuntime().freeMemory();
		}
	}

	/** checks memory and adds rule to RulesUsage object * */
	SignedFormulaList around(IRule r, SignedFormulaList sfl): 
        callSimpleRule(r, sfl){
		if (enabled) {
			try {
				checkMemory();

				SignedFormulaList sflResult = proceed(r, sfl);
				return sflResult;
			} catch (OutOfMemoryError e) {
				logger.debug("Memory finished after calling simple rule...");
				exitGracefully();
			}
			return null;
		} else {
			return proceed(r, sfl);
		}
	}

	/** checks memory and adds rule to RulesUsage object * */
	SignedFormulaList around(IRule r, SignedFormulaList sfl, CompositeFormula f): 
        callSubformulaRule(r, sfl,f) {
		if (enabled) {
			try {
				checkMemory();

				SignedFormulaList sflResult = proceed(r, sfl, f);
				return sflResult;
			} catch (OutOfMemoryError e) {
				logger
						.debug("Memory finished after calling subformula rule...");
				exitGracefully();
			}

			return null;
		} else {
			return proceed(r, sfl, f);
		}

	}

	/** checks memory and adds rule to RulesUsage object * */
	
	boolean around(): 
        callPBRule() {

		if (enabled) {
			try {
				checkMemory();

				boolean result = proceed();
				return result;
			} catch (OutOfMemoryError e) {
				logger.debug("Memory finished after calling PB rule...");
				exitGracefully();
			}

			return false;
		} else
			return proceed();

	}

	/** checks memory and records lengthBefore and _lastPT * */
	void around(IProofTree pt, SignedFormulaNode sfn): addNodeToProofTree(pt, sfn){
		if (enabled) {
			checkMemory();
			try {

				proceed(pt, sfn);
			} catch (OutOfMemoryError e) {
				logger
						.debug("Memory finished after adding node to proof tree...");
				exitGracefully();;
			}

			checkMemory();
		} else
			proceed(pt, sfn);
	}

	/** records _lastFF * */
	after(FormulaFactory ff): callRule_toGetFormulaFactory(ff){
		if (enabled) {
			_lastFF = ff;
		}

	}

	/** *********** aspect methods ************ */

	/**
	 * verifies memory usage, possibly exiting.
	 */
private void checkMemory() {
		
// System.err.println(Runtime.getRuntime().freeMemory());

		long freeMemory = Runtime.getRuntime().freeMemory(); 
		if (freeMemory< MINIMUM_MEMORY_BEFORE_GARBAGE_COLLECTION) {

			if (logger.isDebugEnabled()){
				logger.debug("Memory too low...");
				logger.debug("Memory available before garbage collection "
					+ freeMemory + " bytes");
				logger.debug("Performing gargabe collection...");
			}

			try{
				MemoryManager.runGC();
			}catch(Exception e){
				// do nothing
			}
			
			freeMemory = Runtime.getRuntime().freeMemory();
			if (logger.isDebugEnabled()){
				logger.debug("Memory available after garbage collection "
					+ freeMemory  + " bytes");
			}
			
			if (freeMemory < MINIMUM_MEMORY_AFTER_GARBAGE_COLLECTION) {
				exitGracefully();
			}

		}

	}
	/**
	 * Shows info about formula factory
	 */
	private void showFormulaFactorySize() {
		// System.out.println("Measurement of Formula Factory : "
		// + _lastFF.getComplexity());
		// System.out.println("Number of atomic formulas in Formula Factory : "
		// + _lastFF.getAtomicFormulasSize());
		// System.out.println("Number of composite formulas in Formula Factory :
		// "
		// + _lastFF.getCompositeFormulasSize());
		// System.out
		// .println("Measurement of complexity of the Formula Factory : "
		// + _lastFF.getComplexity());
		// System.out.println("Size of Formula Factory structures: "
		// + _lastFF.getStructuresSize());

		logger.debug("Measurement of Formula Factory : "
				+ _lastFF.getComplexity());
		logger.debug("Number of atomic formulas in Formula Factory : "
				+ _lastFF.getAtomicFormulasSize());
		logger.debug("Number of composite formulas in Formula Factory : "
				+ _lastFF.getCompositeFormulasSize());
		logger.debug("Measurement of complexity of the Formula Factory : "
				+ _lastFF.getComplexity());
		logger.debug("Size of Formula Factory structures: "
				+ _lastFF.getStructuresSize());
	}

	/**
	 * Exits gracefully
	 */
	private void exitGracefully() {
		logger.error("Finishing due to lack of available memory");

		try {
			MemoryManager.runGC();
		} catch (Exception e) {
			// do nothing
		}

		// throw new RuntimeException("Out of memory error");
		throw new OutOfMemoryError();
	}

	/**
	 * Shows free memory with message
	 * 
	 * @param message
	 */
	/// COMENTADO EM 24/03/2009
	// Raz�o:
//	2008-08-29 18:22:12.778 - ERROR: Finished with error proof and verify procedure for file with current prover configuration: SimpleStrategy,CPL_NORMAL_BX,SO,ND,NSD,InsertionOrderComparator
//	2008-08-29 18:22:12.742 - ERROR: (class: proverinterface/aspects/MemoryUsageTracker, method: showFreeMemory signature: (Ljava/lang/String;)V) Stack size too large
//	2008-08-29 18:22:10.912 - INFO: Started proof and verify procedure for file with current prover configuration: SimpleStrategy,CPL_NORMAL_BX,SO,ND,NSD,InsertionOrderComparator
//	2008-08-29 18:21:51.951 - ERROR: Finished with error proof and verify procedure for file with current prover configuration: SimpleStrategy,CPL_NORMAL_BX,SO,ND,NSD,InsertionOrderComparator
//	2008-08-29 18:21:51.787 - ERROR: (class: proverinterface/aspects/MemoryUsageTracker, metho

//	private void showFreeMemory(String message) {
//		try {
//			MemoryManager.runGC();
//		} catch (Exception e) {
//			// do nothing
//		}
//
//		// Runtime.getRuntime().gc();
//
//		// System.out
//		// .println("*** " + message + Runtime.getRuntime().freeMemory());
//	}

}
