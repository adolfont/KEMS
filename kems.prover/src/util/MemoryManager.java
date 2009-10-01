/*
 * Created on 01/02/2005
 *
 */
package util;

import org.apache.log4j.Logger;

/**
 * Helps to perform garbage collection and to get memory usage measures.
 * 
 * @author Adolfo Gustavo Serra Seca neto
 * 
 */
public class MemoryManager {

	/**
	 * logger
	 */
	public static Logger logger = Logger.getLogger(MemoryManager.class);

	private static final Runtime s_runtime = Runtime.getRuntime();

	/**
	 * Tries hardly to perform a garbage collection.
	 * 
	 * @throws Exception
	 */
	public static void runGC(int i, int j) throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug("Forcing garbage collection - Used memory: "
					+ usedMemory());
		}

		for (int r = 0; r < i; ++r)
			_runGC(j);

		if (logger.isDebugEnabled()) {
			logger.debug("After garbage collection - Used memory: "
					+ usedMemory());
		}

	}

	/**
	 * Tries hardly to perform a garbage collection.
	 * 
	 * @throws Exception
	 */
	public static void runGC() throws Exception {
		runGC(4, 500);
	}

	private static void _runGC(int j) throws Exception {
		long usedMem1 = usedMemory(), usedMem2 = Long.MAX_VALUE;
		for (int i = 0; (usedMem1 < usedMem2) && (i < j); ++i) {
			s_runtime.runFinalization();
			s_runtime.gc();
			Thread.yield();
			usedMem2 = usedMem1;
			usedMem1 = usedMemory();
		}
	}

	/**
	 * Returns amount of memory used.
	 * 
	 * @return memory used in bytes
	 */
	public static long usedMemory() {
		return s_runtime.totalMemory() - s_runtime.freeMemory();
	}

}
