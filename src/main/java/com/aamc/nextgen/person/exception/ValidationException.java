package com.aamc.nextgen.person.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ValidationException extends RuntimeException {

    /**
	 * 
	 */
	private static final long serialVersionUID = -2325287119870803720L;

	public ValidationException() {
        super();
    }

    public ValidationException(String s) {
        super(s);
    }
}
