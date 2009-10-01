/*
 * Created on 24/10/2003, 18:53:58
 *
 */
package satcnf;
import java.util.ArrayList;
import java.util.List;

import signedFormulasNew.SignedFormulaFactory;
import signedFormulasNew.SignedFormulaList;
import formulasNew.Formula;
import formulasNew.FormulaFactory;

/**
 * @author Adolfo Neto
 *
 */
public class satcnfResult {

	FormulaFactory _ff;
	SignedFormulaFactory _sff;
	SignedFormulaList _formulas;

	public satcnfResult() {
		_ff = new FormulaFactory();
		_sff = new SignedFormulaFactory();
		_formulas = new SignedFormulaList();
	}

	public void setSignedFormulaList(SignedFormulaList sfl) {
		_formulas = sfl;
	}

	public SignedFormulaList getFormulas() {
		return _formulas;
	}

	public void setFormulaFactory(FormulaFactory ff) {
		_ff = ff;
	}

	public FormulaFactory getFormulaFactory() {
		return _ff;
	}

	public void setSignedFormulaFactory(SignedFormulaFactory sff) {
		_sff = sff;
	}

	public SignedFormulaFactory getSignedFormulaFactory() {
		return _sff;
	}

	public String toString() {
		return "Formulas: "
			+ _formulas
			+ "\n"
			+ "FormulaFactory: "
			+ _ff
			+ "\n"
			+ "SignedFormulaFactory: "
			+ _sff
			+ "\n";

	}

}
