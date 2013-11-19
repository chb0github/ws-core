package org.bongiorno.ws.core.exceptions;

import org.springframework.http.HttpStatus;

import java.util.Map;


public class BadRequestException extends WebserviceException {

    public static final HttpStatus STATUS = HttpStatus.BAD_REQUEST;

    public BadRequestException(Throwable cause){
        super(STATUS, cause);
    }

    public BadRequestException(String messageFormat, Object... args) {
        super(STATUS, messageFormat, args);
    }

    public BadRequestException(Map<String, String> details){
        super(STATUS,details);
    }

    public BadRequestException(Throwable cause, String messageFormat, Object... args) {
        super(STATUS, cause, messageFormat, args);
    }

    public BadRequestException(Map<String, String> details, Throwable cause) {
        super(STATUS,details, cause);
    }

    public BadRequestException(Map<String, String> details, String messageFormat, Object... args) {
        super(STATUS, details, messageFormat, args);
    }
}
