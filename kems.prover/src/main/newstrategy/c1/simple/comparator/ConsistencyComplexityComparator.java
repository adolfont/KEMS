package main.newstrategy.c1.simple.comparator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import logic.signedFormulas.SignedFormula;
import main.newstrategy.cpl.configurable.comparator.ISignedFormulaComparator;

/**
 * A signed formula comparator that gives preference to formulas whose
 * complexity degree is higher.
 * 
 * @author Emerson Shigueo Sugimoto
 * 
 */
public class ConsistencyComplexityComparator implements
		ISignedFormulaComparator {

	public static final String DESCRIPTOR = "ccc";
	
	private Pattern p;
	
	public ConsistencyComplexityComparator() {
	  p = Pattern.compile("!\\(.{1,2}&!.{1,2}\\)");

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
				rt = -1;
			} else {
				rt = 1;
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
		String formulaText = f.getFormula().toString();
		// .{1,2} - pois pode ser uma fórmula do tipo: !(A&!A)
		// ou do tipo: !(A1&!A1)
		return grauConsistencia(formulaText);
	}

	/**
	 * Returns consistency degree
	 */
	private int grauConsistencia(String frase) {
		Matcher m = p.matcher(frase);
		String formula = "";
		int cont = 0;
		while (m.find()) {
			try {
				if (formulaValida(m.group())) {
					cont++;
				}

			} catch (Exception err) {
				// System.err.println("Erro - " + err.getMessage());
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
	private boolean formulaValida(String formula) {
		if (formula.length() == 7) { // !(A&!A)
			char X = formula.charAt(2);
			char Y = formula.charAt(5);
			return X == Y;
		} else if (formula.length() == 9) { // !(A1&!A1)
			String X = formula.substring(2, 4);
			String Y = formula.substring(6, 8);
			return X.equals(Y);
		} else {
			return false;
		}
	}

}
