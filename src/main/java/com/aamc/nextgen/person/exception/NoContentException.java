package com.aamc.nextgen.person.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NO_CONTENT)
public class NoContentException extends RuntimeException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 3179155230259205093L;

	public NoContentException() {
        super();
    }

    public NoContentException(String s) {
        super(s);
    }
}
