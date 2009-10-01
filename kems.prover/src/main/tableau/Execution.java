/*
 * Created on 22/12/2004
 *
 */
package main.tableau;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import logic.signedFormulas.SignedFormulaBuilder;
import main.strategy.Strategy;

/**
 * Class that records information about the execution of the TableauProver for a
 * problem.
 * 
 * @author adolfo
 *  
 */
public class Execution {

    Date _date;

    DateFormat _df;

    double _timeElapsed;

    String _filename, _hostname, _osName, _osVersion;

    long _maxMemory, _totalMemory, _freeMemory;

    Strategy _strategy;

    RulesUsage _rulesUsage;

    SignedFormulaBuilder _sfb;

    /**
     * @param hostname
     * @param date
     */
    public Execution(String hostname, Date date) {
        _date = date;
        _hostname = hostname;

        _df = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.FULL,
                Locale.US);

    }

    // comentado em 23-06-2006
//    public Element asXMLElement() {
//        Element root = new Element("execution");
//
//        Element hostname = new Element("hostname");
//        hostname.setText(_hostname);
//        root.addContent(hostname);
//
//        Element date = new Element("date");
//        DateFormat df = DateFormat.getDateInstance(DateFormat.LONG, Locale.US);
//        date.setText(df.format(_date));
//        root.addContent(date);
//
//        Element time = new Element("time");
//        df = DateFormat.getTimeInstance(DateFormat.LONG, Locale.US);
//        time.setText(df.format(_date));
//        root.addContent(time);
//
//        Element timeElapsed = new Element("timeElapsed");
//        timeElapsed.setText(Double.toString(_timeElapsed));
//        root.addContent(timeElapsed);
//
//        Element strategy = new Element("strategy");
//        strategy.setText(_strategy.getName());
//        root.addContent(strategy);
//
//        root.addContent(getOS());
//        root.addContent(getMemory());
//
//        // TODO REMOVE?
//        if (_rulesUsage != null) {
//            root.addContent(_rulesUsage.asXMLElement());
//        }
//
//        root.addContent(_sfb.asXMLElement());
//
//        return root;
//    }
//
//    private Element getOS() {
//        Element os = new Element("operatingSystem");
//
//        Element name = new Element("name");
//        name.addContent(_osName);
//        os.addContent(name);
//
//        Element version = new Element("version");
//        version.addContent(_osVersion);
//        os.addContent(version);
//
//        return os;
//    }
//
//    private Element getMemory() {
//        Element memory = new Element("memory");
//
//        Element max = new Element("max");
//        max.addContent(Long.toString(_maxMemory));
//        memory.addContent(max);
//
//        Element free = new Element("free");
//        free.addContent(Long.toString(_freeMemory));
//        memory.addContent(free);
//
//        Element total = new Element("total");
//        total.addContent(Long.toString(_totalMemory));
//        memory.addContent(total);
//
//        return memory;
//    }

    public void setTimeElapsed(double elapsed) {
        _timeElapsed = elapsed;
    }

    public void setFilename(String fname) {
        _filename = fname;
    }

    public void setStrategy(Strategy stra) {
        _strategy = stra;
    }

    public void setRulesUsage(RulesUsage ru) {
        _rulesUsage = ru;
    }

    /**
     * @param sfc
     */
    public void setSignedFormulaBuilder(SignedFormulaBuilder sfb) {
        _sfb = sfb;

    }

    /**
     * @param property
     * @param property2
     */
    public void setOS(String osName, String osVersion) {
        _osName = osName;
        _osVersion = osVersion;
    }

    /**
     * @param l
     * @param m
     * @param n
     */
    public void setMemory(long maxMem, long freeMem, long totalMem) {
        _maxMemory = maxMem;
        _freeMemory = freeMem;
        _totalMemory = totalMem;
    }
}