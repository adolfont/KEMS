package problemGenerator.simple;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Fifth_LFI_Familiy_ProblemGenerator {

	public static void main(String[] args) {
		// # fifth faniliy, instance 3
		// T !(A1&!A1)
		// T A1 & A2 & A3
		// T (A4 -> ((A1|B1)->(!(A2&!A2)))) & (A4 -> ((A2|B2)->(!(A3&!A3)))) & (A4
		// -> ((A3|B3)->(!(A4&!A4))))
		// T ((!(A1&!A1))&(!(A2&!A2))&(!(A3&!A3))) -> !A4
		// F !!!A4
		boolean C1Consistency = true;

		// System.out.println(cons("C1",C1Consistency));
		// System.out.println(cons("C1",false));

		int family = 5;
		int min = 1;
		int max = 99;
		String path = "/home/adolfo/Software/KEMS_eclipse_workspace_3.4/kems.problems/problems/generated/";
		for (int instance = min; instance <= max; instance++) {

			try {
				FileWriter write = new FileWriter(path + "lfiProblems/family5/family" + family + "_"
						+ (instance < 10 ? "0" : "") + instance + ".prove");
				PrintWriter text = new PrintWriter(write);

				// T !(A1&!A1)
				String line1 = "T " + cons("A1", C1Consistency);
				text.println(line1);
				System.out.println(line1);

				// T A1 & A2 & A3
				String line2 = "T A1";
				for (int i = 2; i <= instance; i++) {
					line2 += "&A"+i;
				}
				text.println(line2);
				System.out.println(line2);

				// T (A4 -> ((A1|B1)->(!(A2&!A2)))) & (A4 -> ((A2|B2)->(!(A3&!A3)))) & (A4
				// -> ((A3|B3)->(!(A4&!A4))))
				String line3 = "T (A"+(instance+1)+"-> ((A1|B1)->(" + cons("A2", C1Consistency) + ")))";
				for (int i = 2; i <= instance; i++) {
					line3 += "&(A"+(instance+1)+"-> ((A" + i + "|B" + i + ")->(" + cons("A" + (i + 1), C1Consistency) + ")))";
				}
				text.println(line3);
				System.out.println(line3);

				// T ((!(A1&!A1))&(!(A2&!A2))&(!(A3&!A3))) -> !A4
				String line4 = "T ("+cons("A1",C1Consistency);
				for (int i = 2; i <= instance; i++) {
					line4 += "&" + cons("A"+i, C1Consistency);
				}
				line4 += ")->(!A"+(instance+1)+")";
				text.println(line4);
				 System.out.println(line4);

				// F !!!A4
				String line5 = "F !!!A"+(instance+1);
				text.println(line5);
				System.out.println(line5);

				text.flush();
				write.close();
			} catch (IOException ie) {
				System.out.println(ie);
			}
		}

	}

	private static String cons(String formula, boolean C1Option) {
		return C1Option ? "!(" + formula + "&!" + formula + ")" : "@" + formula;
	}

}
