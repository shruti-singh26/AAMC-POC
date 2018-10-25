package com.aamc.nextgen.person.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class ConflictException extends RuntimeException {

    /**
	 * 
	 */
	private static final long serialVersionUID = -2325287119870803720L;

	public ConflictException() {
        super();
    }

    public ConflictException(String s) {
        super(s);
    }
}
