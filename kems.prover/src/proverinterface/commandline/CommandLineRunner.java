package proverinterface.commandline;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Time;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import logic.problem.Problem;
import main.ag.strategy.util.AGConfiguration;
import main.ag.strategy.util.AGConfiguration.Abordagens;
import main.ag.strategy.util.MemoriaToFile;
import main.newstrategy.cpl.configurable.comparator.ISignedFormulaComparator;
import main.proofTree.ProofTree;
import main.tableau.verifier.ExtendedProof;

import org.apache.log4j.Logger;

import proverinterface.ProverConfiguration;
import proverinterface.StrategyFactory;
import proverinterface.runner.several.SeveralProblemProofResultsFrameTableLine;
import util.FileUtil;
import util.StringUtil;

/**
 * Runs a program that contains configurations and problems to be run by the
 * prover.
 * 
 * @author Adolfo Gustavo Serra Seca Neto
 * 
 */
public class CommandLineRunner {

	/**
	 * logger
	 */
	public static Logger logger = Logger.getLogger(CommandLineRunner.class);

	private static String resultSeparator = " ; ";

	private static final String EQUALS = "=";

	private static final String COMMENT = "#";

	// private static final String STRATEGY_KEYWORD = "strategy";
	//
	// private static final String COMPARATOR_KEYWORD = "comparator";
	//
	// private static final String RULES_KEYWORD = "ruleStructure";
	
	//EMERSON: Temporário Algoritmo Genético
	private static final String MODO_AG = "modoag";
	//private static long _mem0 = 0; //Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

	private static final String PARSER_KEYWORD = "parser";

	private static final String SAVE_ORIGIN_KEYWORD = "saveOrigin";

	private static final String DISCARD_BRANCHES_KEYWORD = "discardClosedBranches";

	private static final String SAVE_DISCARDED_BRANCHES_KEYWORD = "saveDiscardedBranches";

	private static final String TIMES_KEYWORD = "times";

	private static final String TIME_LIMIT_KEYWORD = "timeLimit";

	private static final String PROBLEMS_KEYWORD = "problems";

	private static final String ONE_PROBLEM_KEYWORD = "problem";

	private static final String STRATEGIES_KEYWORD = "strategies";

	private static final String COMPARATORS_KEYWORD = "comparators";

	private static final String RUN_KEYWORD = "run";

	private static final String SATCNF2 = "satcnf2";

	public static final long TIME_LIMIT_INCREASE = 1000l;

	public static final long TIME_UNIT = 60000l;

	public static final String TIME_UNIT_NAME = "minute";

	private static final String SEPARATOR_ARGUMENT = "-separator:";

	private static final String ONE_PROBLEM_ARGUMENT = "-problem";

	private static final String SHOW_PROOF_ARGUMENT = "-showProof";

	public static final String NOT_AVAILABLE = "n/a";

	private static StrategyFactory STRATEGY_FACTORY;

	private static ComparatorMap SIGNED_FORMULA_COMPARATOR_MAP;

	private static RuleStructureMap RULE_STRUCTURE_MAP;

	private static boolean firstResult;

	private static boolean showProof = false;
	
	//Emerson - memory
	private final static Runtime runtime = Runtime.getRuntime();
	//private static long _heap1 = 0;
	private static long getMemoriaUsada(){return runtime.totalMemory() - runtime.freeMemory();} 
	private static long getTotalUsadoBytes(){
		long rt = getMemoriaUsada() - MemoriaToFile.getNumero();
		MemoriaToFile.Clear(); 
		if (rt<0){rt= Math.abs(rt);}
		return rt;
	}
	
	public static void main(String[] args) {
		STRATEGY_FACTORY = new StrategyFactory();
		SIGNED_FORMULA_COMPARATOR_MAP = new ComparatorMap();
		RULE_STRUCTURE_MAP = new RuleStructureMap();
		firstResult = true;
	
		// Verifies if second argument is a new separator
		if (args.length > 1
				&& args[args.length - 1].startsWith(SEPARATOR_ARGUMENT)) {
			resultSeparator = " "
					+ args[args.length-1].substring(SEPARATOR_ARGUMENT.length()) + " ";
		}

		try {
			if (args.length < 1) {
				System.err
						.println("Usage: java -jar kems.jar filename.seq [-showProof] [-separator:ASeparator] ");
				System.err
						.println("or java -jar kems.jar -problem filename.problem [-showProof] [-separator:ASeparator] ");
				System.exit(0);
			} else {
				if (args[0].equals(ONE_PROBLEM_ARGUMENT)) {
					if (args.length > 2 && args[2].equals(SHOW_PROOF_ARGUMENT)) {
						showProof = true;
					}
					processFile(args[1]);
				} else {
					processFile(args[0]);
				}
				System.out.println("Finished!");
			}
		} catch (Throwable t) {
			System.err.println("Error: " + t);
			logger.error("Error", t);
		}
	}

	private static void processFile(String filename) throws Exception {
		File f = new File(filename);

		if (f.exists()) {
			System.out.println("Processing file " + filename);
			FileReader fr = new FileReader(f);
			readLines(fr);
		} else {
			System.out.println("File " + filename + " does not exist!");
		}

	}

	private static void readLines(FileReader fr) throws Exception {
		List<String> commands = new ArrayList<String>();
		BufferedReader br = new BufferedReader(fr);
		while (br.ready()) {
			String line = br.readLine().trim();

			if (line.startsWith(COMMENT) || line.length() == 0) {
				// do nothing
			} else {
				commands.add(line);
			}
		}

		processCommands(commands);

	}

	private static void processCommands(List<String> commands) throws Exception {
		ProverConfiguration pc = null;

		pc = initializeBaseConfiguration(commands);
		getOtherCommandsAndRun(pc, commands);
	}

	private static void getOtherCommandsAndRun(ProverConfiguration pc,
			List<String> commands) {
		ListIterator<String> it = commands.listIterator();
		List<String> filenames = null, comparators = null, strategies = null, modoAG = null;
		List<String> problemLines = null;

		while (it.hasNext()) {

			String command = it.next();

			if (command.startsWith(ONE_PROBLEM_KEYWORD + EQUALS)) {
				it.remove();
				problemLines = getLines(it, commands);
			} else if (command.startsWith(PROBLEMS_KEYWORD + EQUALS)) {
				it.remove();
				filenames = getLines(it, commands);
			} else if (command.startsWith(COMPARATORS_KEYWORD + EQUALS)) {
				it.remove();
				comparators = getLines(it, commands);
			} else if (command.startsWith(STRATEGIES_KEYWORD + EQUALS)) {
				it.remove();
				strategies = getLines(it, commands);
			} else if (command.startsWith(MODO_AG + EQUALS)) {
				it.remove();
				modoAG = getLines(it, commands);
			} else

			if (command.startsWith(RUN_KEYWORD)) {
				if (comparators != null && strategies != null) {
					if (filenames != null) {
						runProblems(pc, filenames, strategies, comparators, modoAG);
					} else {
						if (problemLines != null) {
							runOneProblem(pc, problemLines, strategies,
									comparators, modoAG);
						} else {
							System.out.println("No problem to run");
						}
					}

				}
			}
			// else {
			// it.previous();
			// pc = changeConfiguration(pc, it, commands);
			// }
		}

	}

	private static void runOneProblem(ProverConfiguration pc,
			List<String> problemLines, List<String> strategies,
			List<String> comparators,
			List<String> modoAG) {
		File f = new File("."+System.currentTimeMillis() + ".kems.temp.problem");

		while (f.exists()) {
			f = new File("."+System.currentTimeMillis() + ".kems.temp.problem");
		}

		Iterator<String> it = problemLines.iterator();
		try {
			FileWriter fw = new FileWriter(f);

			while (it.hasNext()) {
				fw.write(it.next() + StringUtil.LINE_SEPARATOR);
				fw.flush();
			}

			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		List<String> problems = new ArrayList<String>();
		problems.add(f.getAbsolutePath());

		runProblems(pc, problems, strategies, comparators, modoAG);

		f.deleteOnExit();
	}

	@SuppressWarnings("unused")
	private static ProverConfiguration changeConfiguration(
			ProverConfiguration pc, ListIterator<String> it,
			List<String> commands,
			List<String> modoAG) {
		try {
			while (it.hasNext()) {
				String command = it.next();

				// if (command.startsWith(STRATEGY_KEYWORD)) {
				// String strategyName = getValueFor(command, STRATEGY_KEYWORD);
				// pc.setStrategyName(strategyName);
				// pc.setStrategyFullClassName(STRATEGY_FACTORY
				// .getFullStrategyClassName(strategyName));
				// } else if (command.startsWith(COMPARATOR_KEYWORD)) {
				// pc.setSignedFormulaComparator(new ComparatorMap()
				// .getComparator(getValueFor(command,
				// COMPARATOR_KEYWORD)));
				// } else if (command.startsWith(RULES_KEYWORD)) {
				// pc
				// .setRulesStructureName(getValueFor(command,
				// RULES_KEYWORD).toUpperCase());
				// } else
				//					
				if (command.startsWith(PARSER_KEYWORD)) {
					pc.setFirstParsingLibName(getValueFor(command,
							PARSER_KEYWORD));
					if (pc.getFirstParsingLibName().equals(SATCNF2)) {
						pc.setTwoPhasesParserOption(false);
					} else {
						pc.setTwoPhasesParserOption(true);
					}
				} else if (command.startsWith(SAVE_ORIGIN_KEYWORD)) {
					pc.setSaveOrigin(Boolean.parseBoolean(getValueFor(command,
							SAVE_ORIGIN_KEYWORD)));
				} else if (command.startsWith(DISCARD_BRANCHES_KEYWORD)) {
					pc.setDiscardClosedBranches(Boolean
							.parseBoolean(getValueFor(command,
									DISCARD_BRANCHES_KEYWORD)));
				} else if (command.startsWith(SAVE_DISCARDED_BRANCHES_KEYWORD)) {
					pc.setSaveDiscardedBranches(Boolean
							.parseBoolean(getValueFor(command,
									SAVE_DISCARDED_BRANCHES_KEYWORD)));
				} else if (command.startsWith(TIMES_KEYWORD)) {
					pc.setTimes(Integer.parseInt(getValueFor(command,
							TIMES_KEYWORD)));
				} else if (command.startsWith(TIME_LIMIT_KEYWORD)) {
					pc.setTimeLimit(TIME_UNIT
							* Long.parseLong(getValueFor(command,
									TIME_LIMIT_KEYWORD)) + TIME_LIMIT_INCREASE);
				//} else if (command.startsWith(MODO_AG)) {
					
				} else {
					it.previous();
					return pc;
				}

			}
		} catch (Exception e) {
			logger.error(e);
		}

		return pc;
	}

	private static void runProblems(ProverConfiguration basePC,
			List<String> filenames, List<String> strategies,
			List<String> comparators,
			List<String> modoAG) {
		Iterator<String> problemIterator = filenames.iterator();
		
		CommandLineRunnable clr = null;
		
		while (problemIterator.hasNext()) {
			String problem = problemIterator.next();

			Iterator<String> strategyIterator = strategies.iterator();
			while (strategyIterator.hasNext()) {
				String strategyName = strategyIterator.next();
				
				//EMERSON: Temporário Algoritmo Genético
				if (modoAG!=null && modoAG.size() > 0) {
					//estocastico,elitista
					Iterator<String> comparatorIterator = comparators.iterator();
					while (comparatorIterator.hasNext()) {
						String comparator = comparatorIterator.next();
						Iterator<String> modoAGIterator = modoAG.iterator();
						while (modoAGIterator.hasNext()) {
							String agMode = modoAGIterator.next();
							
							//-string agMode
							basePC.setAbordagemAG(AGConfiguration.GetAbordagemFromString(agMode));
							
							//strategyName = "SimpleStrategy";
							//comparator = "OrComparator";
							if (basePC.getAbordagensAG() == Abordagens.NotApplyAG) {
								//limpa o modo AG
								//strategyName = "ConfigurableSimpleStrategy";
								//comparator = "InsertionOrderComparator";
							}
						
							main.ag.strategy.util.MemoriaCompartilhada.Clear();
							MemoriaToFile.setNumero(getMemoriaUsada());
							
							//System.out.println("comparator: " + comparator);
							
							basePC.setStrategyName(strategyName);
							basePC.setStrategyFullClassName(STRATEGY_FACTORY
									.getFullStrategyClassName(strategyName));

							basePC.setSignedFormulaComparator(SIGNED_FORMULA_COMPARATOR_MAP
									.getComparator(comparator));

							basePC.setRulesStructureName(RULE_STRUCTURE_MAP
									.getRuleStructure(strategyName));

							clr = new CommandLineRunnable(problem, basePC);
							Thread t = new Thread(clr);
							t.start();

							while (!clr.isFinished()) {
								try {
									Thread.sleep(1000);
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
							}

							// if (clr.getProof() != null) {
							showResultsLine(clr.getProof(), clr.getProblem(),
									basePC, clr.getMessage());
							if (showProof)
								System.out.println(clr.getProof());
							// }
							clr = null;
						
						}
						
					} //end while comparator
					
					//end modo ag
				} else {
					
					Iterator<String> comparatorIterator = comparators.iterator();
					while (comparatorIterator.hasNext()) {
						String comparator = comparatorIterator.next();

						basePC.setStrategyName(strategyName);
						basePC.setStrategyFullClassName(STRATEGY_FACTORY
								.getFullStrategyClassName(strategyName));

						basePC.setSignedFormulaComparator(SIGNED_FORMULA_COMPARATOR_MAP
								.getComparator(comparator));

						basePC.setRulesStructureName(RULE_STRUCTURE_MAP
								.getRuleStructure(strategyName));

						clr = new CommandLineRunnable(problem, basePC);
						Thread t = new Thread(clr);
						t.start();
						while (!clr.isFinished()) {
							try {
								Thread.sleep(1000);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}

						// if (clr.getProof() != null) {
						showResultsLine(clr.getProof(), clr.getProblem(),
								basePC, clr.getMessage());
						if (showProof)
							System.out.println(clr.getProof());
						// }
						clr = null;
					}
				}

			}

			// try {
			// ep = pf.proveAndVerifyFile(it.next(), pc);
			// showResultsLine(ep);
			// } catch (Throwable e) {
			// e.printStackTrace();
			// }
		}
		
	}

	private static void showResultsLine(ExtendedProof ep, Problem problem,
			ProverConfiguration pc, String message) {

		// SeveralProblemProofResultsFrameTableLine line = new
		// SeveralProblemProofResultsFrameTableLine(
		// ep.getProblem().getName(), ep, problem);

		if (firstResult) {
			// showResultsColumnNames(line);
			showColumnNames();
			firstResult = false;
		}

		showValues(ep, problem, pc, message);

		// Vector v = line.getValues();
		// Iterator it = v.iterator();
		// boolean first = true;
		// while (it.hasNext()) {
		// String value = (String) it.next().toString();
		// if (first) {
		// value = removePath(value);
		// first = false;
		// }
		// result += value;"Problem name"
		// if (it.hasNext()) {
		// result += resultSeparator;
		// }
		// }

//		System.out.println(
//				new Time(System.currentTimeMillis())
//				+ " - Finished proving " + problem.getFilename() + " with "
//				+ pc
//				);
		
		logger.debug(new Time(System.currentTimeMillis())
				+ " - Finished proving " + problem.getFilename() + " with "
				+ pc);
		 //println(result);
	}

	private static void showColumnNames() {
		String result = "";

		//result = result + "Problem name" + resultSeparator;
		result = result + "Problem" + resultSeparator; //Emerson
		
		//result = result + "Instance count" + resultSeparator;
		result = result + "Ins. count" + resultSeparator; //Emerson
		
		//result = result + "Problem size" + resultSeparator;
		result = result + "Tam" + resultSeparator; //Emerson
		
		//result = result + "Prover configuration" + resultSeparator;
		result = result + "Configuracao" + resultSeparator; //Emerson
		
		//result = result + "Time spent (in ms)" + resultSeparator;
		result += "Tempo" + resultSeparator; //Emerson
		
		result = result + "Result" + resultSeparator;
		//result = result + "Verification result" + resultSeparator;
		result = result + "Proof size" + resultSeparator;
		result = result + "Nodes count" + resultSeparator;
		//result = result + "Used nodes count" + resultSeparator;
		result = result + "Proof tree height" + resultSeparator;
		result = result + "Message" + resultSeparator;
		result = result + "AG" + resultSeparator;
		//result = result + "Memory";
		result += "Numero Bif." + resultSeparator;
		result += "Memoria (kB)";
		
		println(result);
	}

	private static void showValues(ExtendedProof ep, Problem problem,
			ProverConfiguration pc, String message) {
		String result = "";

		result = result
				+
				// removePath(FileUtil
				// .showNameAndPath(ep.getProblem().getName()))
				removePath(FileUtil.showNameAndPath(problem.getFilename()))
				+ resultSeparator;
		result = result
		// + getInstanceNumber(removePath(FileUtil.showNameAndPath(ep
				// .getProblem().getName()))) + resultSeparator;
				+ getInstanceNumber(removePath(FileUtil.showNameAndPath(problem
						.getFilename()))) + resultSeparator;
		result = result + problem.getSignedFormulaFactory().getComplexity()
				+ resultSeparator;
		result = result + createProverConfigurationString(pc) + resultSeparator;
		result = result + ((ep != null) ? ep.getTimeSpent() : NOT_AVAILABLE)
				+ resultSeparator;
		result = result
				+ ((ep != null) ? (ep.isClosed() ? "y" : "n")
						: NOT_AVAILABLE) + resultSeparator;
//		result = result
//				+ ((ep != null) ? (ep.getVerificationResult() == Boolean.TRUE ? "verified"
//						: "not verified")
//						: NOT_AVAILABLE) + resultSeparator;
		result = result + ((ep != null) ? ep.getSize() : NOT_AVAILABLE)
				+ resultSeparator;
		result = result
				+ ((ep != null) ? ep.getNodesQuantity() : NOT_AVAILABLE)
				+ resultSeparator;
//		result = result + ((ep != null) ? ep.getUsedQuantity() : NOT_AVAILABLE)
//				+ resultSeparator;
		result = result
				+ ((ep != null) ? ((ProofTree) ep.getProofTree()).getHeight()
						: NOT_AVAILABLE) + resultSeparator;
		result = result + message + resultSeparator;
		
		if (pc.getAbordagensAG() == AGConfiguration.Abordagens.NotApplyAG ){
			result += "KEMS" + resultSeparator;
		} else {
			result += pc.getAbordagensAG().toString() + resultSeparator;
		}
		
		//result += (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) - _mem0;
		
		result +=
				(ep.isClosed() ?
				main.ag.strategy.util.MemoriaCompartilhada.getNumero()
				:
				"-"
				) + 
				resultSeparator;
		result += 
				(ep.isClosed() ?
				//Kilobyte - kB
				("" + getTotalUsadoBytes()/1024.0).replace(",", "").replace('.', ',')  
				: 
				"-");
		
		//main.ag.strategy.util.MemoriaCompartilhada.Clear();
		
		println(result);

	}

	private static String createProverConfigurationString(
			ProverConfiguration proverConfiguration) {
		String strategyName = proverConfiguration.getStrategyName();

		String strategyMnemonic = "";
		for (int i = 0; i < strategyName.length(); i++) {
			if (Character.isUpperCase(strategyName.charAt(i))) {
				strategyMnemonic += strategyName.charAt(i);
			}
		}

		String comparatorMnemonic = ((ISignedFormulaComparator) proverConfiguration
				.getSignedFormulaComparator()).getComparatorDescriptor();

		String result = "<";
		result += strategyMnemonic + ",";
		result += comparatorMnemonic + ",";
		result += (proverConfiguration.getSaveOrigin() ? "SO" : "NSO");
		result += ","
				+ (proverConfiguration.getDiscardClosedBranches() ? "D" : "ND");
		result += ","
				+ (proverConfiguration.getSaveDiscardedBranches() ? "SD"
						: "NSD");
		result += ">";

		return result;
	}

	private static String getInstanceNumber(String problemSimpleName) {
		int underlineIndex = problemSimpleName.lastIndexOf("_");
		if (underlineIndex != -1) {
			int pointIndex = problemSimpleName.lastIndexOf(".");
			if (pointIndex == -1 || pointIndex < underlineIndex) {
				return problemSimpleName.substring(underlineIndex + 1);
			} else {
				return problemSimpleName.substring(underlineIndex + 1,
						pointIndex);
			}
		}

		return "";
	}

	private static String removePath(String filename) {
		int indexOfLastSeparator = filename.indexOf(" at ");
		if (indexOfLastSeparator == -1) {
			System.err.println(filename);
			return filename;
		} else {
			return filename.substring(0, indexOfLastSeparator);
		}
	}

	@SuppressWarnings("unused")
	private static void showResultsColumnNames(
			SeveralProblemProofResultsFrameTableLine line) {
		String result = "";

		List<String> l = Arrays.asList(line.getColumnNames());

		Iterator<String> it = l.iterator();
		while (it.hasNext()) {
			result += it.next();
			if (it.hasNext()) {
				result += resultSeparator;
			}
		}

		println(result);
	}

	private static List<String> getLines(ListIterator<String> it,
			List<String> commands) {
		List<String> result = new ArrayList<String>();

		while (it.hasNext()) {
			String command = it.next();

			if (command.startsWith(RUN_KEYWORD)
					|| command.startsWith(ONE_PROBLEM_KEYWORD + EQUALS)
					|| command.startsWith(PROBLEMS_KEYWORD + EQUALS)
					|| command.startsWith(STRATEGIES_KEYWORD + EQUALS)
					|| command.startsWith(COMPARATORS_KEYWORD + EQUALS)
					|| command.startsWith(MODO_AG + EQUALS)
				) {
				it.previous();
				return result;
			} else {
				result.add(command);
				it.remove();
			}

		}
		return result;
	}

	private static ProverConfiguration initializeBaseConfiguration(
			List<String> commands) throws Exception {
		ProverConfiguration pc;

		ListIterator<String> it = commands.listIterator();

		pc = new ProverConfiguration("DummyStrategyName");
		// obtem todos na ordem correta
		// String strategyName = getValueFor(it.next(), STRATEGY_KEYWORD);
		// pc = new ProverConfiguration(strategyName);
		// pc.setStrategyFullClassName(STRATEGY_FACTORY
		// .getFullStrategyClassName(strategyName));
		// it.remove();
		// pc.setSignedFormulaComparator(SIGNED_FORMULA_COMPARATOR_MAP
		// .getComparator(getValueFor(it.next(), COMPARATOR_KEYWORD)));
		// it.remove();
		// pc.setRulesStructureName(getValueFor(it.next(), RULES_KEYWORD)
		// .toUpperCase());
		// it.remove();
		pc.setFirstParsingLibName(getValueFor(it.next(), PARSER_KEYWORD));
		it.remove();

		pc.setSaveOrigin(Boolean.parseBoolean(getValueFor(it.next(),
				SAVE_ORIGIN_KEYWORD)));
		it.remove();
		pc.setDiscardClosedBranches(Boolean.parseBoolean(getValueFor(it.next(),
				DISCARD_BRANCHES_KEYWORD)));
		it.remove();
		pc.setSaveDiscardedBranches(Boolean.parseBoolean(getValueFor(it.next(),
				SAVE_DISCARDED_BRANCHES_KEYWORD)));
		it.remove();

		pc.setTimes(Integer.parseInt(getValueFor(it.next(), TIMES_KEYWORD)));
		it.remove();
		pc.setTimeLimit(TIME_UNIT
				* Long.parseLong(getValueFor(it.next(), TIME_LIMIT_KEYWORD))
				+ TIME_LIMIT_INCREASE);
		it.remove();

		if (pc.getFirstParsingLibName().equals(SATCNF2)) {
			pc.setTwoPhasesParserOption(false);
		} else {
			pc.setTwoPhasesParserOption(true);
		}

		return pc;
	}

	private static String getValueFor(String command, String keyword)
			throws Exception {
		// if (command == null) {
		// throw new ParseException("Keyword " + keyword + EQUALS
		// + " expected.", 0);
		// }
		if (command != null && command.startsWith(keyword + EQUALS)) {
			return command.substring(command.indexOf(EQUALS) + 1);
		} else {
			throw new ParseException(
					"Error parsing file: " + "a value for the parameter "
							+ keyword + " was expected.", 0);
		}
	}

	public static void showArgs(String[] args) {
		for (int i = 0; i < args.length; i++) {
			println("Arg #" + i + ": " + args[i]);
		}

	}

	public static void println(String string) {
		System.out.println(string);
	}

}
