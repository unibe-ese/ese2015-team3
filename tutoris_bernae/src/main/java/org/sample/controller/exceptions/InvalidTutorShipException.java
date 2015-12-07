package org.sample.controller.exceptions;

/**
 * Thrown if no tutorship could be found
 * or one of its properties wasn't as expected
 */
public class InvalidTutorShipException extends RuntimeException {

	private static final long serialVersionUID = -5998830511476618512L;

	public InvalidTutorShipException(String s) {
        super(s);
    }
}
