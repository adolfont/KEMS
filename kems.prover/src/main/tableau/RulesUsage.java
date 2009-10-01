/*
 * Created on 22/12/2004
 *
 */
package main.tableau;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.jdom.Element;

import rules.Rule;

/**
 * @author adolfo
 *  
 */
public class RulesUsage {

    public static final int SIMPLE = 1;

    public static final int SUBFORMULA = 2;

    public static final int PB = 3;

    int _countSimple, _countSub, _countPB;

    Map _map = new HashMap();
    Map _mapRepeated = new HashMap();

    /**
     *  
     */
    public RulesUsage() {
        super();
        _countSimple = _countSub = _countPB = 0;
    }

    public Element asXMLElement() {
        Element root = new Element("rulesUsage");


        Element rules = new Element("rules");
        Iterator it = _map.keySet().iterator();

        int total = 0;
        while (it.hasNext()) {
            Rule r = (Rule) it.next();

            Element rule = new Element("rule");

            Element ruleName = new Element("name");
            ruleName.setText(r.toString());

            Element times = new Element("times");
            times.setText(Integer.toString(getUsage(r)));

            total += getUsage(r);

            rule.addContent(ruleName);
            rule.addContent(times);

            rules.addContent(rule);

        }

        Element totalEl = new Element("total");
        totalEl.setText(Integer.toString(total));

        Element rulesRepeated = new Element("repeatedFormulas");
        it = _mapRepeated.keySet().iterator();

        total = 0;
        while (it.hasNext()) {
            Rule r = (Rule) it.next();

            Element rule = new Element("rule");

            Element ruleName = new Element("name");
            ruleName.setText(r.toString());

            Element times = new Element("times");
            times.setText(Integer.toString(getUsage(r)));

            total += getUsage(r);

            rule.addContent(ruleName);
            rule.addContent(times);

            rulesRepeated.addContent(rule);

        }

        Element totalRepeatedEl = new Element("totalRepeatedFormulas");
        totalRepeatedEl.setText(Integer.toString(total));
        
        root.addContent(totalEl);
        root.addContent(generateRulesByType());
        root.addContent(rules);
        root.addContent(rulesRepeated);
        root.addContent(totalRepeatedEl);

        return root;
    }

    /**
     * 
     */
    private Element generateRulesByType() {
        Element rulesByType = new Element("rulesByType");
        
        Element simple = new Element("simple");
        simple.setText(Integer.toString(_countSimple));
        rulesByType.addContent(simple);
        
        Element sub = new Element("sub");
        sub.setText(Integer.toString(_countSub));
        rulesByType.addContent(sub);
        
        Element PB = new Element("PB");
        PB.setText(Integer.toString(_countPB));
        rulesByType.addContent(PB);
        
        return rulesByType;
    }

    public void setUsage(Rule r, int uses) {
        _map.put(r, new Integer(uses));
    }

    public void setUsageRepeated(Rule r, int uses) {
        _mapRepeated.put(r, new Integer(uses));
    }

    public int getUsage(Rule r) {
        return ((Integer) _map.get(r)).intValue();
    }

    public void addToUsage(Rule r) {
        if (_map.get(r) != null) {
            setUsage(r, getUsage(r) + 1);
        } else {
            setUsage(r, 1);
        }
    }

    public void addToUsageRepeated(Rule r) {
        if (_mapRepeated.get(r) != null) {
            setUsageRepeated(r, getUsage(r) + 1);
        } else {
            setUsageRepeated(r, 1);
        }
    }

    public void addToUsage(int type) {
        switch (type) {
        case (SIMPLE):
            _countSimple++;
            break;
        case (SUBFORMULA):
            _countSub++;
            break;
        case (PB):
            _countPB++;
            break;
        }
    }

}