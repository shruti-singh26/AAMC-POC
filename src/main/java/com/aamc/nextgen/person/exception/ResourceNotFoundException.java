package com.aamc.nextgen.person.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.Getter;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = -8661541499806789849L;
	
	/**
	 * 
	 */
	@Getter
	private String resourceName;
	/**
	 * 
	 */
	@Getter
    private String fieldName;
	/**
	 * 
	 */
	@Getter
    private Object fieldValue;

    /**
     * @param resourceName
     * @param fieldName
     * @param fieldValue
     */
    public ResourceNotFoundException( String resourceName, String fieldName, Object fieldValue) {
        super(String.format("%s not found with %s : '%s'", resourceName, fieldName, fieldValue));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }
}
