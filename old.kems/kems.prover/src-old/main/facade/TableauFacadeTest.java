/*
 * Created on 08/03/2005
 *
 */
package facade;

import java.util.StringTokenizer;

import junit.framework.TestCase;

/**
 * Class that tests the use of a Tableau Facade.
 * 
 * @author Adolfo Gustavo Serra Seca neto
 * 
 */
public class TableauFacadeTest extends TestCase {

    public void testMain() throws Exception {

        try {
            TableauFacade.main(configureParameters(""));
        } catch (Exception e) {
            assertTrue(false);
        }

        /** Analyse PHP instances */
        // try {
        // TableauFacade
        // .main(configureParameters("-r sequence -n php -a -b1 -e9 -s1 -m9"));
        // } catch (Exception e) {
        // assertTrue(false);
        // }
        // try {
        // TableauFacade
        // .main(configureParameters("-p TP_properties_MemorySaver.txt -r
        // instance -n
        // php/php_1.prove -u -y -h -l -f -x80"));
        // TableauFacade
        // .main(configureParameters("-p TP_properties_MemorySaver.txt -r
        // sequence -n
        // php -b1 -e3 -s1 -m9 -u -y -h -l -f -x80"));
        // } catch (Exception e) {
        // assertTrue(false);
        // }
        // TableauFacade
        // .main(configureParameters("-p TP_properties_generated_SATS5.txt -r
        // sequence
        // -n SquareTseitin -b1 -e4 -s1 -m10 -t normal -u -l -i1"));
        // try {
        // TableauFacade
        // .main(configureParameters("-p TP_properties_collected_SATS4.txt -r
        // sequence -n pelletier -b1 -e17 -s1 -m10 -t normal -u -l -i3"));
        // } catch (Exception e) {
        // assertTrue(false);
        // }
        // try {
        // TableauFacade.main(configureParameters("-r sequence -n php"
        // + " -b3 -e3 -s1 -m9 -t clausal -u -l"));
        // } catch (Exception e) {
        // assertTrue(false);
        // }
        //
        // try {
        // TableauFacade.main(configureParameters("-r sequence -n gamma"
        // + " -b8 -e10 -s2 -m10 -t normal -h -f"));
        // } catch (Exception e) {
        // e.printStackTrace();
        // assertTrue(false);
        // }
        //
        // try {
        // TableauFacade.main(configureParameters("-r sequence -a -n gamma"
        // + " -b2 -e6 -s2 -m10 -t normal -h"));
        // } catch (Exception e) {
        // assertTrue(false);
        // }
        //
        // try {
        // TableauFacade.main(configureParameters("-r sequence -n gamma"
        // + " -b20 -e40 -s20 -m100 -t clausal -h -l -u"));
        // } catch (Exception e) {
        // assertTrue(false);
        // }
        //
        // try {
        // TableauFacade.main(configureParameters("-r sequence -n php"
        // + " -b1 -e4 -s1 -m1 -t normal -u -l -i3"));
        // } catch (Exception e) {
        // assertTrue(false);
        // }
        //
        // try {
        // TableauFacade.main(configureParameters("-r sequence -n gamma"
        // + " -b20 -e200 -s20 -m100 -t normal -u -l -i1"));
        // } catch (Exception e) {
        // assertTrue(false);
        // }
        //
        try {
//            TableauFacade
//                    .main(configureParameters("-p TP_properties_MemorySaver.txt -r sequence -n php"
//                            + " -b1 -e4 -s1 -m1 -t normal -u -l -i1"));
        } catch (Exception e) {
            assertTrue(false);
        }

        try {
//            TableauFacade
//                    .main(configureParameters("-p TP_properties_OSS.txt -r sequence -n php"
//                            + " -b1 -e4 -s1 -m1 -t normal -u -l  -i1"));
        } catch (Exception e) {
            assertTrue(false);
        }

        try {
            TableauFacade.main(configureParameters("-r sequence -n php"
                    + " -b1 -e4 -s1 -m1 -t normal -u -h -l -f -i1"));
        } catch (Exception e) {
            assertTrue(false);
        }

        try {
//            TableauFacade
//                    .main(configureParameters("-p TP_properties_MemorySaver.txt -r sequence -n php"
//                            + " -b5 -e6 -s1 -m1 -t normal -u -l -i1"));
        } catch (Exception e) {
            assertTrue(false);
        }
        //
        // try {
        // TableauFacade
        // .main(configureParameters("-p TP_properties_created.txt -r instance
        // -n teatro.wagner -h -f -l"));
        // } catch (Exception e) {
        // assertTrue(false);
        // }
        //
        // try {
        // TableauFacade
        // .main(configureParameters("-p TP_properties_created.txt -r instance
        // -n bif_op.prove -l"));
        // } catch (Exception e) {
        // assertTrue(false);
        // }
        //
        // try {
        // TableauFacade
        // .main(configureParameters("-p TP_properties_created.txt -r instance
        // -a -n bif_op.prove"));
        // } catch (Exception e) {
        // assertTrue(false);
        // }
        //
        // // SATH tests
        //
        // try {
        // TableauFacade
        // .main(configureParameters("-p TP_properties_created_SATH.txt -r
        // instance -n teste.sat -w -h -f"));
        // } catch (Exception e) {
        // assertTrue(false);
        // }
        //
        // try {
        // TableauFacade
        // .main(configureParameters("-p TP_properties_created_SATH.txt -r
        // instance -n teste.sat -w -u -l"));
        // } catch (Exception e) {
        // assertTrue(false);
        // }
        //
        // try {
        // TableauFacade
        // .main(configureParameters("-p TP_properties_created_SATH.txt -r
        // instance -a -n teste.sat -w"));
        // } catch (Exception e) {
        // assertTrue(false);
        // }
        //
        // // SATCNF tests
        // try {
        // TableauFacade
        // .main(configureParameters("-p TP_properties_created_SATCNF.txt -r
        // instance -n teste.cnf -w -h -f"));
        // } catch (Exception e) {
        // assertTrue(false);
        // }
        //
        // try {
        // TableauFacade
        // .main(configureParameters("-p TP_properties_created_SATCNF.txt -r
        // instance -n teste.cnf -w -u -l"));
        // } catch (Exception e) {
        // assertTrue(false);
        // }
        //
        // try {
        // TableauFacade
        // .main(configureParameters("-p TP_properties_created_SATCNF.txt -r
        // instance -a -n teste.cnf -w"));
        // } catch (Exception e) {
        // assertTrue(false);
        // }
    }

    public String[] configureParameters(String parametersAsString) {
        String[] result;
        int i = 0; // index into the next empty array element

        // --- Declare and create a StringTokenizer
        StringTokenizer st;
        st = new StringTokenizer(parametersAsString);

        // --- Create an array which will hold all the tokens.
        result = new String[st.countTokens()];

        // --- Loop, getting each of the tokens
        while (st.hasMoreTokens()) {
            result[i++] = st.nextToken();
        }

        return result;
    }
}