package problemGenerator.simple;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class ProblemConsistencyConverter {

	public static void main(String[] args) {
		ProblemConsistencyConverter pcc = new ProblemConsistencyConverter();

		String path = "/home/adolfo/Software/KEMS_eclipse_workspace_3.4/kems.problems/problems/collected/lfi_chapter/";

		String pName;
		pName="lf-chapter-mbc-not-valid_";
		for (int i = 1; i <= 23; i ++) {
			pcc.convert(path + "/mbC/not-valid/" + pName + zeros(i) + i + ".prove",
					path + "/C1/not-valid/" + pName + zeros(i) + i + ".prove");
		}

		pName="mCi-not-valid_";
		for (int i = 1; i <= 4; i ++) {
			pcc.convert(path + "/mCi/notvalid/" + pName +  i + ".prove",
					path + "/C1/not-valid/" + pName +  i + ".prove");
		}


		pName="lf-chapter-mbc-valid_";
		for (int i = 1; i <= 17; i ++) {
			pcc.convert(path + "/mbC/valid/" + pName + zeros(i) + i + ".prove",
					path + "/C1/valid/" + pName + zeros(i) + i + ".prove");
		}

		pName="mCi-valid_";
		for (int i = 1; i <= 4; i ++) {
			pcc.convert(path + "/mCi/valid/" + pName +  i + ".prove",
					path + "/C1/valid/" + pName +  i + ".prove");
		}

		pName="contraposition_rules/lf-chapter-mbc-valid-contrap_";
		for (int i = 1; i <= 4; i ++) {
			pcc.convert(path + "/mbC/valid/" + pName + i + ".prove",
					path + "/C1/valid/" + pName + i + ".prove");
		}

		pName="restricted_contraposition/mCi-valid-rescontrap_";
		for (int i = 1; i <= 4; i ++) {
			pcc.convert(path + "/mCi/valid/" + pName + i + ".prove",
					path + "/C1/valid/" + pName + i + ".prove");
		}
		System.out.println("Done!");
	}

	public static String zeros(int i) {
		if (i < 10)
			return "0";
		return "";
	}

	private void convert(String inputFile, String outputFile) {
		try {
			FileReader in = new FileReader(inputFile);
			BufferedReader b = new BufferedReader(in);

			FileWriter out = new FileWriter(outputFile);
			PrintWriter text = new PrintWriter(out);

			String s;
			while ((s = getLine(b)) != null) {
				if (!(s.startsWith("#"))) {
					// System.out.println(ConsistencyConverter.recursiveConvert(s));
					text.println(ConsistencyConverter.recursiveConvert(s));
				}
			}
			text.flush();
			out.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public String getLine(BufferedReader in) {
		String s;
		try {
			s = in.readLine();
		} catch (IOException e) {
			throw new RuntimeException("getLine: readLine() failed");
		}
		return s;
	}

	public static void generates_lfi_carnielli() {
		ProblemConsistencyConverter pcc = new ProblemConsistencyConverter();

		String path = "/home/adolfo/Software/KEMS_eclipse_workspace_3.4/kems.problems/problems/collected/lfi_carnielli/";

		String pName;
		pName = "diamonds_again.prove";
		pcc.convert(path + pName, path + "C1/" + pName);
		pName = "inventing_logic_puzzles.prove";
		pcc.convert(path + pName, path + "C1/" + pName);
		pName = "nixon_diamond.prove";
		pcc.convert(path + pName, path + "C1/" + pName);
	}

	public static void generates_lfi_families() {
		ProblemConsistencyConverter pcc = new ProblemConsistencyConverter();

		String path = "/home/adolfo/Software/KEMS_eclipse_workspace_3.4/kems.problems/problems/generated/lfiProblems/";

		for (int i = 10; i <= 90; i += 10) {
			pcc.convert(path + "/first/" + "FirstLFIFamily_" + i + ".prove", path + "/first/C1/"
					+ "family1_" + i + ".prove");
		}
		for (int i = 100; i <= 300; i += 100) {
			pcc.convert(path + "/first/" + "FirstLFIFamily_" + i + ".prove", path + "/first/C1/"
					+ "family1_" + i + ".prove");
		}
		for (int i = 1; i <= 20; i++) {
			String zero = (i < 10) ? "0" : "";
			pcc.convert(path + "/second/" + "SecondLFIFamily_" + zero + i + ".prove", path
					+ "/second/C1/" + "family2_" + zero + i + ".prove");
			pcc.convert(path + "/third/" + "ThirdLFIFamily_" + zero + i + ".prove", path + "/third/C1/"
					+ "family3_" + zero + i + ".prove");
			pcc.convert(path + "/fourth/" + "FourthLFIFamily_" + zero + i + ".prove", path
					+ "/fourth/C1/" + "family4_" + zero + i + ".prove");
			pcc.convert(path + "/family7/" + "family7_" + zero + i + ".prove", path + "/family7/C1/"
					+ "family7_" + zero + i + ".prove");
		}
		for (int i = 30; i <= 300; i += 10) {
			String zero = (i < 100) ? "0" : "";
			pcc.convert(path + "/fourth/" + "FourthLFIFamily_" + zero + i + ".prove", path
					+ "/fourth/C1/" + "family4_" + zero + i + ".prove");
		}
		for (int i = 1; i <= 50; i++) {
			String zero = (i < 10) ? "0" : "";
			pcc.convert(path + "/family8/" + "family8_" + zero + i + ".prove", path + "/family8/C1/"
					+ "family8_" + zero + i + ".prove");
		}
		for (int i = 1; i <= 5; i++) {
			String zero = (i < 10) ? "0" : "";
			pcc.convert(path + "/family9/" + "family9_" + zero + i + ".prove", path + "/family9/C1/"
					+ "family9_" + zero + i + ".prove");
		}
		for (int i = 100; i <= 300; i += 100) {
			String zero = (i < 10) ? "0" : "";
			pcc.convert(path + "/family9/" + "family9_" + zero + i + ".prove", path + "/family9/C1/"
					+ "family9_" + zero + i + ".prove");
		}
	}

}
