package main.newstrategy.c1.simple.comparator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import logic.signedFormulas.SignedFormula;
import main.newstrategy.cpl.configurable.comparator.ISignedFormulaComparator;

/**
 * A signed formula comparator that gives preference to formulas whose
 * complexity degree is higher.
 * 
 * @author Emerson Shigueo Sugimoto (original version)
 * @author Adolfo Neto (revisions)
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

		if (getConsistencyDegree(sf0) == getConsistencyDegree(sf1)) {
			return 0;
		} else {
			if (getConsistencyDegree(sf0) > getConsistencyDegree(sf1)) {
				return 1;
			} else {
				return -1;
			}
		}
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
	 * Returns "true" when, for a formula whose pattern is !(X&!Y), X and Y are
	 * the same formula. Returns "false" otherwise.
	 * */
	private boolean validFormula(String formula) {
		int length = formula.length();

		// System.out.println(formula);
		// length - 5 must be an even number. This is true because if one
		// removes the "(!&!)" characters, in order to have the same string two times,
		// there must be an even number of characters.
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
			// System.out.println(part1.equals(part2));
			return part1.equals(part2);
		} else {
			// System.out.println(false);
			return false;
		}
	}
}
