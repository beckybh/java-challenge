package com.challenge.project.util;

import com.challenge.project.model.ErrorMessage;
import com.challenge.project.model.exceptions.DataCreationException;
import com.challenge.project.model.exceptions.DataNotFoundException;
import com.challenge.project.model.exceptions.EmptyParamException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionControllerHandler {

    @ExceptionHandler(DataNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ErrorMessage handleDataNotFoundException(DataNotFoundException ex) {
        ErrorMessage error = new ErrorMessage();
        error.setCode(HttpStatus.NOT_FOUND.value());
        error.setDescription(ex.getMessage());
        return error;
    }

    @ExceptionHandler(DataCreationException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorMessage handleDataCreationException(DataCreationException ex) {
        ErrorMessage error = new ErrorMessage();
        error.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        error.setDescription(ex.getMessage());
        return error;
    }

    @ExceptionHandler(EmptyParamException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorMessage handleEmptyParamException(EmptyParamException ex){
        ErrorMessage error = new ErrorMessage();
        error.setCode(HttpStatus.BAD_REQUEST.value());
        error.setDescription(ex.getMessage());
        return error;
    }
}
