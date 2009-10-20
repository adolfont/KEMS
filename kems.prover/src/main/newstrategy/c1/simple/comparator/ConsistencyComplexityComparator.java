package main.newstrategy.c1.simple.comparator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import logic.signedFormulas.SignedFormula;
import main.newstrategy.cpl.configurable.comparator.ISignedFormulaComparator;

/**
 * A comparator that ...
 * 
 * @author Emerson Shigueo Sugimoto
 * 
 */
public class ConsistencyComplexityComparator implements
		ISignedFormulaComparator {

	public static final String DESCRIPTOR = "ccc";

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	public int compare(SignedFormula sf0, SignedFormula sf1) {

		int rt = 0;

		if (getGrauConsistencia(sf0) == getGrauConsistencia(sf1)) {
			rt = 0; // mesmo grau
		} else {
			if (getGrauConsistencia(sf0) >= getGrauConsistencia(sf1)) {
				rt = -1;
			} else {
				rt = 1;
			}
		}
		// System.out.println("- " + sf0 + " ("+sf0.getGrauConsistencia()+"), "
		// + sf1 + " ("+sf1.getGrauConsistencia()+"), rt: " + rt);

		return rt;

		/*
		 * if (f0 instanceof AtomicFormula && f1 instanceof AtomicFormula) {
		 * return f0.toString().compareTo(f1.toString()); } else { if (f0
		 * instanceof AtomicFormula) { return -1; } else { if (f1 instanceof
		 * AtomicFormula) { return 1; } else { FormulaSign s0 = sf0.getSign();
		 * FormulaSign s1 = sf1.getSign();
		 * 
		 * if (s0.equals(sign)) { return -1; } else { if (s1.equals(sign)) {
		 * return 1; } else { return 0; } } } } }
		 */
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

	private int getGrauConsistencia(SignedFormula f) {
		String frase = f.getFormula().toString();
		// .{1,2} - pois pode ser uma fórmula do tipo: !(A&!A)
		// ou do tipo: !(A1&!A1)
		return grauConsistencia("!\\(.{1,2}&!.{1,2}\\)", frase);
	}

	/**
	 * Retorna o grau de consistência
	 * */
	private int grauConsistencia(String frase) {
		// .{1,2} - pois pode ser uma fórmula do tipo: !(A&!A)
		// ou do tipo: !(A1&!A1)
		return grauConsistencia("!\\(.{1,2}&!.{1,2}\\)", frase);
	}

	/**
	 * Retorna o grau de consistência
	 * */
	private int grauConsistencia(String ER, String frase) {
		Pattern p = Pattern.compile(ER);
		Matcher m = p.matcher(frase);
		String formula = "";
		int cont = 0;
		while (m.find()) {
			try {
				formula = m.group();
				// System.out.println(formula+ " \ti:" + m.start()+
				// " \tf:"+m.end());

				if (formulaValida(formula)) {
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
