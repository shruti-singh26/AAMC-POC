package com.aamc.nextgen.person.exception;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.TypeMismatchException;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
@RestController
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler{

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ErrorDetails> handleAllExceptions(Exception ex, WebRequest request) {
      ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(),
          request.getDescription(false));
      return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

   /* @ExceptionHandler(DataAccessException.class)
    public ModelAndView handleDataAccessExceptions(HttpServletRequest request, DataAccessException ex) {
        logger.warn("DataAccessException: " + request.getRequestURL(), ex);
        ModelAndView mav = new ModelAndView();
        mav.setViewName("error");
        return mav;
    }*/

    @ExceptionHandler(BusinessObjectNotFoundException.class)
    public final ResponseEntity<ErrorDetails> handleBusinessObjectNotFoundException(BusinessObjectNotFoundException ex, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(),
            request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
      }

    @ExceptionHandler(ValidationException.class)
    public final ResponseEntity<ErrorDetails> handleValidationExceptions(ValidationException ex,  WebRequest request) {
    	ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

   /* @ExceptionHandler(ActionNotAllowedException.class)
    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    public ModelAndView handleActionNotAllowedExceptions(HttpServletRequest request, ActionNotAllowedException ex) {
        logger.warn("ActionNotAllowedException: " + request.getRequestURL(), ex);
        ModelAndView mav = new ModelAndView();
        mav.setViewName("error");
        return mav;
    }*/

    @ExceptionHandler(NoContentException.class)
    public final ResponseEntity<ErrorDetails> handleNoContentExceptions(NoContentException ex,  WebRequest request) {
    	ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.NO_CONTENT);
    }
    
    @ExceptionHandler(ConflictException.class)
    public final ResponseEntity<ErrorDetails> handleConflictExceptions(ConflictException ex,  WebRequest request) {
    	ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.CONFLICT);
    }
}
