/*
 * Created on 21/10/2004
 *
 */
package logic.problem;

import logic.formulas.FormulaFactory;
import logic.signedFormulas.SignedFormulaCreator;
import logic.signedFormulas.SignedFormulaFactory;
import logic.signedFormulas.SignedFormulaList;

/**
 * Class that represents a problem that can be proved by the prover.
 * 
 * @author Adolfo Gustavo Serra Seca Neto
 * 
 */
public class Problem {
    
//    public static final int UNDEFINED_SIZE=-1; 

    private String _name = "", _filename = "", _format;

//    private int _size=UNDEFINED_SIZE;

    private FormulaFactory _ff;

    private SignedFormulaFactory _sff;

    private SignedFormulaList _formulas;

    private ProblemType _type;

    private SignedFormulaCreator signedFormulaCreator;

    /**
     * Creates a problem in a given format.
     * 
     * @param format -
     *            the name of the format
     */
    public Problem(String format) {
        _ff = new FormulaFactory();
        _sff = new SignedFormulaFactory();
        _formulas = new SignedFormulaList();
        _format = format;
    }

    /**
     * sets the type of the problem.
     * 
     * @param type
     */
    public void setType(ProblemType type) {
        _type = type;
    }

//    /**
//     * sets the size.
//     * 
//     * @param size
//     */
//    public void setSize(int _size) {
//        this._size = _size;
//    }

    /**
     * sets the list of signed formulas
     * 
     * @param sfl
     */
    public void setSignedFormulaList(SignedFormulaList sfl) {
        _formulas = sfl;
    }

    /**
     * @return the list of signed formulas
     */
    public SignedFormulaList getFormulas() {
        return _formulas;
    }

    /**
     * sets the formula factory used.
     * 
     * @param ff
     */
    public void setFormulaFactory(FormulaFactory ff) {
        _ff = ff;
    }

    /**
     * @return the formula factory used.
     */
    public FormulaFactory getFormulaFactory() {
        return _ff;
    }

    /**
     * sets the signed formula factory used.
     * 
     * @param sff
     */
    public void setSignedFormulaFactory(SignedFormulaFactory sff) {
        _sff = sff;
    }

    /**
     * sets the name
     * 
     * @param name
     */
    public void setName(String name) {
        _name = name;
    }

    /**
     * sets the file name.
     * 
     * @param filename
     */
    public void setFilename(String filename) {
        _filename = filename;
    }
    

    /**
     * @return Returns the filename.
     */
    public String getFilename() {
        return _filename;
    }
    /**
     * @return the signed formula factory used.
     */
    public SignedFormulaFactory getSignedFormulaFactory() {
        return _sff;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return "Problem name: " + _name + System.getProperty("line.separator")
                + "Filename: " + _filename
                + System.getProperty("line.separator") + "Format: " + _format
                + System.getProperty("line.separator") + "Formulas: "
                + _formulas + System.getProperty("line.separator")
                + "FormulaFactory: " + _ff
                + System.getProperty("line.separator")
                + "SignedFormulaFactory: " + _sff
                + System.getProperty("line.separator");

    }

    /**
     * @return a summary of the problem.
     */
    public String summary() {
        return _name;
    }

    /**
     * @return Returns the name.
     */
    public String getName() {
        return _name;
    }

//    /**
//     * @return Returns the size.
//     */
//    public int getSize() {
//        return _size;
//    }

    /**
     * @return Returns the type.
     */
    public ProblemType getType() {
        return _type;
    }

    /**
     * @return
     */
    public SignedFormulaCreator getSignedFormulaCreator() {
        return signedFormulaCreator;
    }

    public void setSignedFormulaCreator(SignedFormulaCreator signedFormulaCreator) {
        this.signedFormulaCreator = signedFormulaCreator;
    }
}