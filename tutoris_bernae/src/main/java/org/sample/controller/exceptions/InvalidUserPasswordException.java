package org.sample.controller.exceptions;

public class InvalidUserPasswordException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public InvalidUserPasswordException(String s) {
        super(s);
    }
	
}
