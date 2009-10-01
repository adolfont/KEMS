/*
 * Created on Dec 10, 2004
 *
 */
package main.exceptions;

/**
 * Class that represents KEMS exceptions - exceptions thrown when some
 * serious problem happens
 * 
 * @author Adolfo Neto
 *  
 */
public class KEMSException extends RuntimeException {

	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Creates a TPException.
	 * 
	 * @param message
	 *            for the exception
	 */
	public KEMSException(String message) {
		System.err.println("KEMS Exception: " + message);
		printStackTrace();
		System.exit(1);
	}

}
