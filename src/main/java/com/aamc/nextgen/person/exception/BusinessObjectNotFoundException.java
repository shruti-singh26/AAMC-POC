package com.aamc.nextgen.person.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class BusinessObjectNotFoundException extends RuntimeException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 3179155230259205093L;

	public BusinessObjectNotFoundException() {
        super();
    }

    public BusinessObjectNotFoundException(String s) {
        super(s);
    }
}
