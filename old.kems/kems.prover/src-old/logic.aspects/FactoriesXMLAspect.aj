/*
 * Created on 29/01/2005
 *  
 */
package logic.aspects;

import logic.formulas.FormulaFactory;
import logic.problem.Problem;
import logic.signedFormulas.SignedFormulaBuilder;
import logic.signedFormulas.SignedFormulaFactory;

import org.jdom.Element;

/**
 * Aspect used to generate XML representations of FormulaFactory,
 * SignedFormulaFactory and SignedFormulaBuilder objects.
 * 
 * @author Adolfo Gustavo Serra Seca Neto
 *  
 */
public aspect FactoriesXMLAspect {

    /**
     * returns a XML element representing the formula factory.
     * 
     * @return XML element representing the formula factory
     */
    public Element FormulaFactory.asXMLElement() {
        Element formulas = new Element("formulas");

        Element atomic = new Element("atomic");
        atomic.setText(Integer.toString(getAtomicFormulasSize()));
        Element composite = new Element("composite");
        composite.setText(Integer.toString(getCompositeFormulasSize()));
        Element connectives = new Element("connectives");
        connectives.setText(Integer.toString(getConnectivesSize()));
        Element measurement = new Element("measurement");
        measurement.setText(Integer.toString(getComplexity()));
        Element structuresSize = new Element("structuresSize");
        structuresSize.setText(Long.toString(getStructuresSize()));

        formulas.addContent(atomic);
        formulas.addContent(composite);
        formulas.addContent(connectives);
        formulas.addContent(measurement);
        formulas.addContent(structuresSize);

        return formulas;
    }

    /**
     * returns a XML element representing the signed formula factory.
     * 
     * @return XML element representing the signed formula factory
     */
    public Element SignedFormulaFactory.asXMLElement() {
        Element signedFormulas = new Element("signedFormulas");

        Element number = new Element("number");
        number.setText(Integer.toString(getSize()));
        Element measurement = new Element("measurement");
        measurement.setText(Integer.toString(getComplexity()));

        signedFormulas.addContent(number);
        signedFormulas.addContent(measurement);

        return signedFormulas;
    }

    /**
     * returns a XML element representing the signed formula builder.
     * 
     * @return XML element representing the signed formula builder
     */
    public Element SignedFormulaBuilder.asXMLElement() {
        Element root = new Element("signedFormulaBuilder");
        root.addContent(getFormulaFactory().asXMLElement());
        root.addContent(getSignedFormulaFactory().asXMLElement());

        return root;
    }

    public Element Problem.asXMLElement() {
        Element thisElement = new Element("problem");

        Element name = new Element("name");
        name.setText(getName());
        thisElement.addContent(name);

//        if (getSize() != Problem.UNDEFINED_SIZE) {
//            Element size = new Element("size");
//            size.setText(Integer.toString(getSize()));
//            thisElement.addContent(size);
//        }
        if (getType() != null) {
            Element type = new Element("type");
            type.setText(getType().getName());
            thisElement.addContent(type);
        }
        Element complexity = generateComplexity();

        thisElement.addContent(complexity);

        return thisElement;
    }

    /**
     * returns the complexity of the problem as a XML element.
     * 
     * @return the complexity of the problem as a XML element
     */
    private Element Problem.generateComplexity() {
        Element complexity = new Element("complexity");

        Element formulasComplexity = getFormulaFactory().asXMLElement();
        Element signedFormulasComplexity = getSignedFormulaFactory()
                .asXMLElement();

        complexity.addContent(formulasComplexity);
        complexity.addContent(signedFormulasComplexity);

        return complexity;
    }

}