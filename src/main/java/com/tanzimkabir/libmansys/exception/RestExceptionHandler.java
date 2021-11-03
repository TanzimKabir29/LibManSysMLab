package com.tanzimkabir.libmansys.exception;

import com.tanzimkabir.libmansys.model.ErrorHandlerModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;

@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    private ResponseEntity<ErrorHandlerModel>  handleDataNotFound(EntityNotFoundException e){
        ErrorHandlerModel errorHandlerModel = new ErrorHandlerModel(HttpStatus.NOT_FOUND, LocalDateTime.now(), "Data not found against provided params.", e.getMessage());
        return new ResponseEntity<>(errorHandlerModel,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    private ResponseEntity<ErrorHandlerModel>  handleBadRequest(IllegalArgumentException e){
        ErrorHandlerModel errorHandlerModel = new ErrorHandlerModel(HttpStatus.BAD_REQUEST, LocalDateTime.now(), "Provided data is not formatted correctly", e.getMessage());
        return new ResponseEntity<>(errorHandlerModel,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    private ResponseEntity<ErrorHandlerModel>  handleOtherExceptions(Exception e){
        ErrorHandlerModel errorHandlerModel = new ErrorHandlerModel(HttpStatus.INTERNAL_SERVER_ERROR, LocalDateTime.now(), "Something went wrong while trying to process your request.", e.getMessage());
        return new ResponseEntity<>(errorHandlerModel,HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
