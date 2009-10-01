/*
 * Created on 08/11/2003, 07:52:42 
 *
 */
package rules;

import java.util.ArrayList;
import java.util.List;

import logic.formulas.Formula;

/**
 * @author Adolfo Neto
 *  
 */
public class KERuleRole {

    String _name;

    public KERuleRole(String name) {
        _name = name;
    }

    public String getName() {
        return _name;
    }

    public String toString() {
        return "KERuleRole: " + getName();
    }

    public List<Formula> getFormulas(Formula f) {
        List<Formula> result = new ArrayList<Formula>();
        if (this.equals(KERuleRole.LEFT)) {
            result.add(f.getImmediateSubformulas().get(0));
        } else if (this.equals(KERuleRole.RIGHT)) {
            result.add(f.getImmediateSubformulas().get(1));
        } else if (this.equals(KERuleRole.ANY)) {
            return f.getImmediateSubformulas();
        }

        return result;
    }

    public boolean equals(KERuleRole role) {
        return role._name.equals(_name);
    }

    public static KERuleRole LEFT = new KERuleRole("Left");

    public static KERuleRole RIGHT = new KERuleRole("Right");

    public static KERuleRole ANY = new KERuleRole("Any");

    public static KERuleRole OTHER = new KERuleRole("OTHER");
}