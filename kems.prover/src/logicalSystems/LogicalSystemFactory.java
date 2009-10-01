/*
 * Created on 03/08/2005
 *
 */
package logicalSystems;

import java.util.HashMap;
import java.util.Map;

import logic.logicalSystem.ILogicalSystem;
import logicalSystems.classicalLogic.ClassicalLogicSystem;
import logicalSystems.classicalLogic.ClassicalRulesStructureBuilder;
import logicalSystems.classicalLogic.ClassicalSignatureFactory;

/**
 * A factory of logical system objects. It is a singleton.
 * 
 * @author Adolfo Gustavo Serra Seca Neto
 * 
 *  
 */
public class LogicalSystemFactory {
    /** constants for logical systems */
    public static final Object CLASSICAL_LOGIC = new Object();

    private static LogicalSystemFactory __logicalSystemFactory;

    private ClassicalSignatureFactory classicalSignatureFactory;

    private Map<Object,ILogicalSystem> logicalSystemsMap;

    private LogicalSystemFactory() {
        logicalSystemsMap = new HashMap<Object, ILogicalSystem>();
    }

    public static LogicalSystemFactory getInstance() {
        if (__logicalSystemFactory == null) {
            __logicalSystemFactory = new LogicalSystemFactory();
        }

        return __logicalSystemFactory;
    }

    /**
     * creates
     * 
     * @param id
     * @return a logical system
     */
    public ILogicalSystem getLogicalSystem(Object id) {
        if (id == CLASSICAL_LOGIC) {
            if (logicalSystemsMap.get(CLASSICAL_LOGIC) == null) {
                this.classicalSignatureFactory = ClassicalSignatureFactory
                        .getInstance();
                ILogicalSystem ils = new ClassicalLogicSystem(
                        classicalSignatureFactory.getNormalBXSignature(),
                        new ClassicalRulesStructureBuilder());
                logicalSystemsMap.put(CLASSICAL_LOGIC, ils);
                return ils;
            } else
                return (ILogicalSystem) logicalSystemsMap.get(CLASSICAL_LOGIC);
        }

        return null;

    }

}
