package com.tanzimkabir.libmansys.exception;

import com.tanzimkabir.libmansys.model.ErrorHandlerModel;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;

/**
 * This class contains custom error handlers for Response codes 400,404,500.
 *
 * @author tanzim
 */

@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Handles error thrown when no data is found against the request parameters.
     */
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorHandlerModel>  handleDataNotFound(EntityNotFoundException e){
        ErrorHandlerModel errorHandlerModel = new ErrorHandlerModel(HttpStatus.NOT_FOUND, LocalDateTime.now(), "Data not found against provided params.", e.getMessage());
        return new ResponseEntity<>(errorHandlerModel,HttpStatus.NOT_FOUND);
    }

    /**
     * Handles error thrown when request data is not properly formatted for processing
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorHandlerModel>  handleBadRequest(IllegalArgumentException e){
        ErrorHandlerModel errorHandlerModel = new ErrorHandlerModel(HttpStatus.BAD_REQUEST, LocalDateTime.now(), "Provided data is not formatted correctly", e.getMessage());
        return new ResponseEntity<>(errorHandlerModel,HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ErrorHandlerModel errorHandlerModel = new ErrorHandlerModel(HttpStatus.BAD_REQUEST, LocalDateTime.now(), "Provided data is not formatted correctly", ex.getMessage());
        return new ResponseEntity<>(errorHandlerModel,HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles miscellaneous runtime errors due to business logic conflict
     */
    @ExceptionHandler(Exception.class)
    private ResponseEntity<ErrorHandlerModel>  handleOtherExceptions(Exception e){
        ErrorHandlerModel errorHandlerModel = new ErrorHandlerModel(HttpStatus.INTERNAL_SERVER_ERROR, LocalDateTime.now(), "Something went wrong while trying to process your request.", e.getMessage());
        return new ResponseEntity<>(errorHandlerModel,HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
