package org.nhnnext.exception;

public class NoBoardException extends defaultException {

	public NoBoardException() {
		super("No Document");
	}

	public NoBoardException(long id) {
		super("No Document id : " + id);
	}
	
	
}
