package main.newstrategy.c1.simple.comparator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import logic.signedFormulas.SignedFormula;
import main.newstrategy.cpl.configurable.comparator.ISignedFormulaComparator;

/**
 * A signed formula comparator that gives preference to formulas whose
 * complexity degree is higher.
 * 
 * Limitation: only atomic formulas whose size as string is less than 2
 * 
 * @author Emerson Shigueo Sugimoto
 * 
 */
public class ConsistencyComplexityComparator implements
		ISignedFormulaComparator {

	public static final String DESCRIPTOR = "ccc";

	private Pattern consistencyPattern;

	public ConsistencyComplexityComparator() {
		consistencyPattern = Pattern.compile("!\\(.*&!.*\\)");

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	public int compare(SignedFormula sf0, SignedFormula sf1) {

		int rt = 0;

		if (getConsistencyDegree(sf0) == getConsistencyDegree(sf1)) {
			rt = 0;
		} else {
			if (getConsistencyDegree(sf0) > getConsistencyDegree(sf1)) {
				rt = 1;
			} else {
				rt = -1;
			}
		}

		return rt;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return "ConsistencyComplexityComparator";
	}

	public String getComparatorDescriptor() {
		return DESCRIPTOR;
	}

	private int getConsistencyDegree(SignedFormula f) {
		Matcher m = consistencyPattern.matcher(f.getFormula().toString());
		int cont = 0;
		while (m.find()) {
			if (validFormula(m.group())) {
				cont++;
			}
		}
		return cont;
	}

	/**
	 * Retorna true se por exemplo para uma fórmula: !(X&!Y) se X e Y são
	 * iguais, como em !(A&!A), e falso se X e Y são diferentes, como em:
	 * !(A&!B) <br />
	 * Verifica também fórmulas do tipo: !(A1&!A1)
	 * */
	private boolean validFormula(String formula) {
		int length = formula.length();

		// System.out.println(formula);

		if ((length - 5) % 2 == 0) {
			int middleAndPosition = ((length - 3) / 2);
			String part0 = formula.substring(2 + middleAndPosition - 1,
					2 + middleAndPosition + 1);

			if (!("&!".equals(part0)))
				return false;

			String part1 = formula.substring(2, 2 + middleAndPosition - 1);
			String part2 = formula.substring(2 + middleAndPosition + 1,
					length - 1);

			// System.out.println(part0 + " - " + part1 + " - " + part2);
			//
			// System.out.println(part1.equals(part2));
			return part1.equals(part2);

		} else {
			// System.out.println(false);
			return false;
		}

		// if (formula.length() == 7) { // !(A&!A)
		// char X = formula.charAt(2);
		// char Y = formula.charAt(5);
		// return X == Y;
		// } else if (formula.length() == 9) { // !(A1&!A1)
		// String X = formula.substring(2, 4);
		// String Y = formula.substring(6, 8);
		// return X.equals(Y);
		// } else {
		// return false;
		// }
	}
}
