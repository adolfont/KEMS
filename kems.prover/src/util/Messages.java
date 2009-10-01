/*
 * Created on 19/04/2004
 *
 */
package util;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * Used for recording names of connectives, signs and special formulas.
 * 
 * @author Adolfo Gustavo Serra Seca Neto
 * 
 */
public class Messages {

    private static final String BUNDLE_NAME = "util.symbols"; //$NON-NLS-1$

    private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle
            .getBundle(BUNDLE_NAME);

    /**
     * 
     */
    private Messages() {

    }

    /**
     * Returns string corresponding to key.
     * 
     * @param key
     * @return string corresponding to key
     */
    public static String getString(String key) {
        try {
            return RESOURCE_BUNDLE.getString(key);
        } catch (MissingResourceException e) {
            return '%' + key + '%';
        }
    }
}
