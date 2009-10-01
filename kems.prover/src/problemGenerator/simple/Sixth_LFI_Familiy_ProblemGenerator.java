package problemGenerator.simple;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Sixth_LFI_Familiy_ProblemGenerator {

	public static void main(String[] args) {
		// # sixth family, instance 3
		// T B1&B2&B3
		// T (!(C1&!C1)) & (!(C2&!C2)) & (!(C3&!C3))
		// T ((A1|B1)->(!(A2&!A2))) & ((A2|B2)->(!(A3&!A3))) &
		// ((A3|B3)->(!(A4&!A4)))
		// T (C1&C2&C3)->(D&!C1)
		// F (!((A2->C1)&!(A2->C1))) | (!((A3->C2)&!(A3->C2))) |
		// (!((A4->C3)&!(A4->C3))) | D

		boolean C1Consistency=true;
		
//		System.out.println(cons("C1",C1Consistency));
//		System.out.println(cons("C1",false));
		
		
		int family = 6;
		int min = 1;
		int max = 99;
		String path = "/home/adolfo/Software/KEMS_eclipse_workspace_3.4/kems.problems/problems/generated/";
		for (int instance = min; instance <= max; instance++) {

			try {
				FileWriter write = new FileWriter(path + "lfiProblems/family6/family" + family + "_"+
						(instance<10?"0":"")
						+ instance + ".prove");
				PrintWriter text = new PrintWriter(write);

				// T B1&B2&B3
				String line1 = "T B1";
				for (int i = 2; i <= instance; i++) {
					line1 += "&B" + i;
				}
				text.println(line1);

				// T (!(C1&!C1)) & (!(C2&!C2)) & (!(C3&!C3))
				String line2 = "T "+cons("C1",C1Consistency);
				for (int i = 2; i <= instance; i++) {
					line2 += "&"+cons("C"+i,C1Consistency);
					//(!(C" + i + "&!C" + i + "))";
				}
				text.println(line2);
//				System.out.println(line2);

				// T ((A1|B1)->(!(A2&!A2))) & ((A2|B2)->(!(A3&!A3))) &
				// ((A3|B3)->(!(A4&!A4)))
				String line3 = "T ((A1|B1)->("+cons("A2",C1Consistency)+"))";
				for (int i = 2; i <= instance; i++) {
					line3 += "&((A" + i + "|B" + i + ")->("+cons("A"+(i+1),C1Consistency)+"))";
//					line3 += "&((A" + i + "|B" + i + ")->(!(A" + (i + 1) + "&!A" + (i + 1) + ")))";
				}
				text.println(line3);
//				System.out.println(line3);

				// T (C1&C2&C3)->(D&!C1)
				String line4 = "T (C1";
				for (int i = 2; i <= instance; i++) {
					line4 += "&C" + i;
				}
				line4 += ")->(D&!C1)";
				text.println(line4);
//				System.out.println(line4);
				
			// F (!((A2->C1)&!(A2->C1))) | (!((A3->C2)&!(A3->C2))) |
				// (!((A4->C3)&!(A4->C3))) | D
//				String line5 = "F (!((A2->C1)&!(A2->C1)))";
				String line5 = "F "+cons("(A2->C1)",C1Consistency);
				for (int i = 2; i <= instance; i++) {
					line5 += "|"+cons("(A" + (i + 1) + "->C" + i+")",C1Consistency);
//					line5 += "|(!((A" + (i + 1) + "->C" + i + ")&!(A" + (i + 1) + "->C" + i + ")))";
				}
				line5 += "|D";
//				System.out.println(line5);
				text.println(line5);
				text.flush();
				write.close();
			} catch (IOException ie) {
				System.out.println(ie);
			}
		}

	}

	private static String cons(String formula, boolean C1Option) {
		return C1Option?"!("+formula+"&!"+formula+")":"@"+formula;
	}

}
