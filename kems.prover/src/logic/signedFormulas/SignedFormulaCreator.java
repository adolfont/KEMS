/*
 * Created on 09/06/2004
 *
 */
package logic.signedFormulas;

import logic.formulas.FormulaFactory;
import logic.problem.Problem;
import parsers.ParserUser;
import ConversorWagnerSATLIB.ConversorWagnerSATLIBLexer;
import ConversorWagnerSATLIB.ConversorWagnerSATLIBParser;

/**
 * Class that allows the creation of formulas from strings (using Wagner Dias's
 * syntax). Ex.: FormulaCreator fc = new FormulaCreator(); fc.parseString ("T
 * A-> B");
 */
public class SignedFormulaCreator {

	/** conversor wagner lexer and parser class names * */
	// "ConversorWagnerSATLIB.ConversorWagnerSATLIBLexer";
	private static final String CW_LEXER = ConversorWagnerSATLIBLexer.class
			.getCanonicalName();

	// "ConversorWagnerSATLIB.ConversorWagnerSATLIBParser";
	private static final String CW_PARSER = ConversorWagnerSATLIBParser.class
			.getCanonicalName();

	private SignedFormulaFactory _sff;

	private FormulaFactory _ff;

	private Problem _problem;

	private String _packageName;

	boolean _twoPhases = true;

	/**
	 * Creates a SignedFormulaCreator for a default lib dir.
	 * 
	 * @param packageName
	 */
	public SignedFormulaCreator(String packageName) {
		_sff = new SignedFormulaFactory();
		_ff = new FormulaFactory();
		_packageName = packageName;
	}

	public void setTwoPhases(boolean option) {
		_twoPhases = option;
	}

	private String removeUselessCharacters(String s) {
		return s.trim();
	}

	public Problem parseText(String signedFormulasAsString) {
		signedFormulasAsString = removeUselessCharacters(signedFormulasAsString);

		String s;

		if (_twoPhases) {

			ParserUser pu1 = new ParserUser();

			s = (String) pu1.parseString(CW_LEXER, CW_PARSER,
					signedFormulasAsString);
		} else {
			s = signedFormulasAsString;
		}

		ParserUser pu2 = new ParserUser();
		_problem = (Problem) pu2.parseString(_packageName + "." + _packageName
				+ "Lexer", _packageName + "." + _packageName + "Parser", s);

		_sff.cloneAll(_problem.getSignedFormulaFactory(), _ff);

		return _problem;
	}

	/**
	 * Converts a string to a signed formula.
	 * 
	 * @param signedFormulaAsString
	 * @return
	 */
	public SignedFormula parseString(String signedFormulaAsString) {

		this.parseText(signedFormulaAsString);

		return (SignedFormula) _sff.getSignedFormulas().get(
				_problem.getSignedFormulaFactory().getLastSignedFormulaAdded()
						.toString());
	}

	public Problem parseFile(String completeFilename) {

		String s;
		if (_twoPhases) {
			ParserUser pu1 = new ParserUser();

			s = (String) pu1.parseFile(CW_LEXER, CW_PARSER, completeFilename);

			ParserUser pu2 = new ParserUser();
			_problem = (Problem) pu2.parseString(_packageName + "."
					+ _packageName + "Lexer", _packageName + "." + _packageName
					+ "Parser", s);
			_sff.cloneAll(_problem.getSignedFormulaFactory(), _ff);
		} else {
			ParserUser pu2 = new ParserUser();
			_problem = (Problem) pu2.parseFile(_packageName + "."
					+ _packageName + "Lexer", _packageName + "." + _packageName
					+ "Parser", completeFilename);

			_sff.cloneAll(_problem.getSignedFormulaFactory(), _ff);
		}

		_problem.setFilename(completeFilename);
		_problem.setName(completeFilename);

		return _problem;

	}

	public SignedFormulaFactory getSignedFormulaFactory() {
		return _sff;
	}

	public FormulaFactory getFormulaFactory() {
		return _ff;
	}

}