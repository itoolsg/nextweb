package org.nhnnext.exception;

public class InvalidUserException extends defaultException {

	public InvalidUserException() {
		super("Invalid User");
	}
	
	public InvalidUserException(String err) {
		super(err);
	}
}
