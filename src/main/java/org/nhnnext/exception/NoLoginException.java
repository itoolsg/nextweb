package org.nhnnext.exception;

/**
 * Title : 해당하는 유저가 없을 때 예외처리
 * 
 * @see User
 * 
 */
public class NoLoginException extends defaultException {

	public NoLoginException() {
		super("Please, Login");
	}
}
