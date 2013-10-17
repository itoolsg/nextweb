package org.nhnnext.exception;

import javax.servlet.http.HttpSession;

import org.nhnnext.web.User;

/**
 * Title : 해당하는 유저가 없을 때 예외처리
 * 
 * @see User
 * 
 */
public class NoUserException extends defaultException {

	public NoUserException() {
		super("No User");
	}
	public NoUserException(String id) {
		super("No User :"+ id);
	}	
	public NoUserException(HttpSession session) {
		super("No User");
		session.removeAttribute("userid");
	}
}
