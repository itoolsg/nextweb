package org.nhnnext.exception;

public class EmptyStringException extends defaultException {

	public EmptyStringException() {
		super("Empty");
	}

	public EmptyStringException(String err) {
		super(err);
	}
	
	
}
