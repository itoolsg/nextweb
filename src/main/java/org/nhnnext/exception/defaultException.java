package org.nhnnext.exception;

import org.nhnnext.log.Mylog;

public class defaultException extends Exception {

	public defaultException(String err) {
		super(err);
		Mylog.printError(this);
	}
	
	
}
