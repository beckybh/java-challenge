package com.challenge.project.model.exceptions;

import java.text.MessageFormat;

public class DataCreationException extends RuntimeException{
    public DataCreationException(String dataType) { super(MessageFormat.format("Error creating {0}", dataType)); }
}
