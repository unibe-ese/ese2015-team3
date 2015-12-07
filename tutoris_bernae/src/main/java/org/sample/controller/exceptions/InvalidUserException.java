package org.sample.controller.exceptions;

public class InvalidUserException extends RuntimeException {

	private static final long serialVersionUID = 5224978375261435026L;

	public InvalidUserException(String s) {
        super(s);
    }
}
