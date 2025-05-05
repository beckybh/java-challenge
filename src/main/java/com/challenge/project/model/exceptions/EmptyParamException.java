package com.challenge.project.model.exceptions;

import java.text.MessageFormat;

public class EmptyParamException extends RuntimeException{
    public EmptyParamException(String type) { super(MessageFormat.format("{0} must not be empty", type)); }
}
