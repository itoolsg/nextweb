package org.nhnnext.exception;

public class NoCommentException extends defaultException {

	public NoCommentException() {
		super("No Comment");
	}

	public NoCommentException(long id) {
		super("No Comment id : " + id);
	}
	
	
}
