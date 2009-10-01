package problemGenerator.simple;

import java.util.HashSet;
import java.util.Set;

public class ConsistencyConverter {

	public static void main(String[] args) {

		String ls=System.getProperty("line.separator");
		String input;
		
		input = "T @A1&@(A3->B2)";
		System.out.println(convert(input));

		input = "T @@A1&@(A3->B2)" + ls + "F @ @  @ @ A1 ";
//		System.out.println("I1="+input);
		System.out.println();
		System.out.println(recursiveConvert(input));
		input="T A1&A2&A3"+ls+"T (B1->!A1)&(B2->!A2)&(B3->!A3)"+ls+"T @A1|@A2|@A3"+ls+"F (!@A1|!B1)&(!@A2|!B2)&(!@A3|!B3)";
		System.out.println();
		System.out.println(recursiveConvert(input));
		
		input="T *A";
		System.out.println();
		System.out.println(recursiveConvert(input));

	}

	public static String recursiveConvert(String input) {
		String output = input;
		// System.out.println("O=" + output);
		output=output.replaceAll("\\Q*\\E", "!@");
		while (contains(output, "@")) {
			// System.out.println("O=" + output);
			output = convert(output);
		}
		return output;
	}

	private static boolean contains(String output, String string) {
		for (int i = 0; i < string.length(); i++) {
			for (int j = 0; j < output.length(); j++) {
				if (output.charAt(j) == string.charAt(i)) {
					return true;
				}
			}
		}
		return false;
	}

	private static String convert(String input) {
		input = input.trim();
		input = removeAllSpaces(input);
		// System.out.println("I="+input);
		String output = (input.charAt(0)+"")+(input.charAt(1)+"");
//		System.out.println("OUTPUT=<"+output+">");
		for (int i = 2; i < input.length(); i++) {
			// System.out.println("Caracter atual:" + input.charAt(i));
			if (input.charAt(i) == '@') {
				if (input.charAt(i + 1) == '(') {
					String consForm = input.charAt(i + 1) + "";
					int parentCount = 1;
					i++;
					while (i < input.length()) {
						i++;
						if (input.charAt(i) == '(')
							parentCount++;
						if (input.charAt(i) == ')')
							parentCount--;
						consForm += input.charAt(i) + "";
						// System.out.println(consForm);
						if (parentCount == 0) {
							output += cons(consForm, true);
							break;
						}
					}
				} else {
					Set<Character> chars = new HashSet<Character>();
					chars.add(new Character('&'));
					chars.add(new Character('|'));
					chars.add(new Character('-'));
					chars.add(new Character('<'));
					chars.add(new Character(')'));
					chars.add(new Character(' '));
					chars.add(new Character('\n'));
					chars.add(new Character('\r'));
					chars.add(new Character(System.getProperty("line.separator").charAt(0)));
					String consForm = input.charAt(i + 1) + "";
					i += 2;
					// System.out.println("INPUT="+input);
					// System.out.println("CHAR="+input.charAt(i));
					// System.out.println("i="+i);
					// System.out.println("size="+input.length());
					while (i < input.length() && !chars.contains(new Character(input.charAt(i)))) {
						consForm += input.charAt(i) + "";
						i++;
					}
					i--;
					output += cons(consForm, true);
				}
			} else {
				output += input.charAt(i) + "";
			}
		}

		// // se encontrar @
		// // se o próximo for (
		// inicia e vai até fechar o parentese
		// quando encontrar, trocar @(X)
		// por !(X&!X)
		//		   
		// // se o próximo não for (
		// inicia e vai até encontrar &, |, -, < e outros de conectivos
		// substituir @XYX
		// por !(XYZ&!XYZ)

		return output;
	}

	private static String removeAllSpaces(String input) {
		String output = (input.charAt(0)+"")+(input.charAt(1)+"");
		for (int i = 2; i < input.length(); i++) {
			if (input.charAt(i) != ' ') {
				output += input.charAt(i) + "";
			}
		}
		// System.out.println("O2="+output);

		return output;
	}

	private static String cons(String formula, boolean C1Option) {
		return C1Option ? "!(" + formula + "&!" + formula + ")" : "@" + formula;
	}

}
