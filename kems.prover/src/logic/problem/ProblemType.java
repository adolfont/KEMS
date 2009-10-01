/*
 * Created on 21/12/2004
 *
 */
package logic.problem;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author Adolfo Gustavo Serra Seca neto
 * 
 */
public class ProblemType {

    String _name, _addString;

    static Map<String, ProblemType> _names = new HashMap<String, ProblemType>();

    /**
     * creates a problem type.
     * 
     * @param name -
     *            name of the problem type
     * @param addString -
     *            string to be added to the end of filenames of this type
     */
    public ProblemType(String name, String addString) {
        _name = name;
        _addString = addString;
        _names.put(_name, this);
    }

    public static ProblemType NORMAL = new ProblemType("normal", "");

    public static ProblemType CLAUSAL = new ProblemType("clausal", "_n");

    public static ProblemType WITH_LITERALS = new ProblemType("with_literals",
            "_l");

    public static ProblemType CLAUSAL_WITH_LITERALS = new ProblemType(
            "clausal_with_literals", "_n_l");

    public static ProblemType NULL_TYPE = new ProblemType("", "");

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return _addString;
    }

    /**
     * @param name
     * @return a problem type given its name.
     */
    public static ProblemType getInstanceByName(String name) {
        return (ProblemType) ((_names.get(name) != null) ? _names.get(name)
                : NULL_TYPE);
    }

    /**
     * @return the name of teh problem
     */
    public String getName() {
        return _name;
    }

}