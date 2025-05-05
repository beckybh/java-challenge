package com.challenge.project.model.exceptions;

import java.text.MessageFormat;

public class DataNotFoundException extends RuntimeException {
    public DataNotFoundException(String dataType) { super(MessageFormat.format("{0} not found", dataType)); }
}
