package org.bongiorno.ws.core.exceptions;

import org.springframework.http.HttpStatus;

import java.util.Map;

public class InternalServerException extends WebserviceException{

    public static final HttpStatus STATUS = HttpStatus.INTERNAL_SERVER_ERROR;

    public InternalServerException(Throwable cause) {
        super(STATUS, cause);
    }

    public InternalServerException(Throwable cause, String messageFormat, Object... args) {
        super(STATUS, cause, messageFormat, args);
    }

    public InternalServerException(Map<String, String> details, Throwable cause) {
        super(STATUS, details, cause);
    }

    public InternalServerException(String messageFormat, Object... args) {
        super(STATUS, messageFormat, args);
    }

    public InternalServerException(Map<String, String> details) {
        super(STATUS, details);
    }

    public InternalServerException(Map<String, String> details, String messageFormat, Object... args) {
        super(STATUS, details, messageFormat, args);
    }

    public InternalServerException(Map<String, String> details, Throwable cause, String messageFormat, Object... args) {
        super(STATUS, details, cause, messageFormat, args);
    }
}
