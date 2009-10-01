package problemGenerator;

import logic.formulas.FormulaFactory;
import logic.problem.Problem;
import logic.signedFormulas.SignedFormulaCreator;
import logic.signedFormulas.SignedFormulaFactory;
import logic.signedFormulas.SignedFormulaList;
import logicalSystems.classicalLogic.ClassicalConnectives;
import logicalSystems.classicalLogic.ClassicalSigns;

public class DSSProblemGenerator extends ProblemGenerator {

	protected DSSProblemGenerator() {
		super("DSS");
	}

	@Override
	protected Problem generate(Problem p, int index) {
		SignedFormulaCreator sfc = new SignedFormulaCreator("satlfiinconsdef");
		FormulaFactory ff = sfc.getFormulaFactory();
		SignedFormulaFactory sff = sfc.getSignedFormulaFactory();

		p.setFormulaFactory(ff);
		p.setSignedFormulaFactory(sff);

		SignedFormulaList sfl = new SignedFormulaList();

		sfl.addAll(sfc.parseText("T @A1\nT @A2\nT @R1\nT @R2\nT @D1\nT @D2\nT !PN->FC\nT A1->!PN\n"+
				"T A2->!PN\nT R1->PN|FC\nT R2->PN|FC\nT D1->PN\nT D2->PN\nT !A1&!A2->PN\nT !A1&!A2&!R1&!R2->PN&!FC").getFormulas());


		String[] conclusions= new String[]{"FC", "PN","!PN", "*PN", "@PN"};

		int indexConclusion=index/conclusions.length;
//		System.err.println(index);
//		System.err.println(indexConclusion);
//		System.err.println(index%conclusions.length);
//		System.err.println();
		sfl.addAll(generateInstance(ff, sff, generateArray(indexConclusion)));
		
		sfl.add(sfc.parseString("F "+conclusions[index%conclusions.length]));

		p.setSignedFormulaList(sfl);

		return p;
	}

	private int[] generateArray(int index) {
		int aux = index;
		int i6 = aux % 3;
		aux = aux / 3;
		int i5 = aux % 3;
		aux = aux / 3;
		int i4 = aux % 3;
		aux = aux / 3;
		int i3 = aux % 3;
		aux = aux / 3;
		int i2 = aux % 3;
		aux = aux / 3;
		int i1 = aux % 3;
		return new int[] { i1, i2, i3, i4, i5, i6 };
	}

	// private static String generatePack (int i1, int i2, int i3, int i4, int
	// i5, int i6){
	// return generateSpecial(new int[]{i1, i4, i2, i5, i3, i6});
	// }
	//		
	// private static void generateAll(String filenameBase, String
	// filenameEnding) {
	// String result = "";
	// for (int i1 = 0; i1 <= 2; i1++) {
	// for (int i2 = 0; i2 <= 2; i2++) {
	// for (int i3 = 0; i3 <= 2; i3++) {
	// for (int i4 = 0; i4 <= 2; i4++) {
	// for (int i5 = 0; i5 <= 2; i5++) {
	// for (int i6 = 0; i6 <= 2; i6++) {
	//
	// result = generatePack(i6, i5, i4, i3, i2,
	// i1 ) + StringUtil.LINE_SEPARATOR;
	// String label=i6+""+i5+""+i4+""+i3+""+i2+""+i1;
	// String filename = filenameBase+label+filenameEnding;
	//									
	//
	// // System.err.println("xxxxxxxxxxxxxxxx "+ StringUtil.LINE_SEPARATOR +
	// result);
	// }
	// }
	// }
	// }
	// }
	// }
	// }

	private SignedFormulaList generateInstance(FormulaFactory ff,
			SignedFormulaFactory sff, int[] options) {

		SignedFormulaList sfl = new SignedFormulaList();

		String[] letters = { "A", "R", "D" };
		String[] indices = { "1", "2" };

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 2; j++) {

				String atom = letters[i] + indices[j];
				if (options[i + (j * 3)] == 1) {
					sfl.add(sff.createSignedFormula(ClassicalSigns.TRUE, ff
							.createCompositeFormula(ClassicalConnectives.NOT,
									ff.createAtomicFormula(atom))));
				} else {
					if (options[i + (j * 3)] == 0) {
						sfl.add(sff.createSignedFormula(ClassicalSigns.TRUE, ff
								.createAtomicFormula(atom)));
					}
				}
			}
		}

		return sfl;
	}

}
