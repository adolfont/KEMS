/*
 * Created on 24/10/2003, 18:53:58
 *
 */
package SATH;
import formulas.Formula;
import formulas.FormulaFactory;

/**
 * @author Adolfo Neto
 *
 */
public class SATHResult {

	FormulaFactory 	_ff;
	Formula 		_formula;
	int 			_numberOfFormulas;


	public SATHResult() {
		_ff = new FormulaFactory();
		_numberOfFormulas = 0;
	}

	public void setFormula (Formula f){
		_formula = f;
	}

	public Formula getFormula (){
		return _formula;
	}

	public void setFormulaFactory (FormulaFactory ff){
		_ff = ff;
	}

	public FormulaFactory getFormulaFactory(){
		return _ff;
	}

	public void setNumberOfFormulas (int numberOfFormulas){
		_numberOfFormulas = numberOfFormulas;
	}

	public String toString(){
		return  "Number of Formulas: " + _numberOfFormulas + "\n" +
				"Formula: " + _formula + "\n" +
				"FormulaFactory: " + _ff + "\n";

	}



}
