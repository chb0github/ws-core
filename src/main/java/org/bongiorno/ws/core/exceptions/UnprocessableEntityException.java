package org.bongiorno.ws.core.exceptions;

import org.springframework.http.HttpStatus;

import java.util.Map;

/**
 * The 422 (Unprocessable Entity) status code means the server
 * understands the content type of the request entity (hence a
 * 415(Unsupported Media Type) status code is inappropriate), and the
 * syntax of the request entity is correct (thus a 400 (Bad Request)
 * status code is inappropriate) but was unable to process the contained
 * instructions.  For example, this error condition may occur if an XML
 * request body contains well-formed (i.e., syntactically correct), but
 * semantically erroneous, XML instructions.
 */
public class UnprocessableEntityException extends WebserviceException {

    public static final HttpStatus STATUS = HttpStatus.UNPROCESSABLE_ENTITY;


    public UnprocessableEntityException(Throwable cause){
        super(STATUS, cause);
    }

    public UnprocessableEntityException(String messageFormat, Object... args) {
        super(STATUS, messageFormat, args);
    }

    public UnprocessableEntityException(Throwable cause, String messageFormat, Object... args) {
        super(STATUS, cause, messageFormat);
    }

    public UnprocessableEntityException(Map<String,String> details){
        super(STATUS, details);
    }

    public UnprocessableEntityException(Throwable cause, Map<String, String> details){
        super(STATUS, details, cause);
    }

    public UnprocessableEntityException(Map<String, String> details, String messageFormat, Object... args){
        this(null, details, messageFormat, args);
    }

    public UnprocessableEntityException(Throwable cause, Map<String, String> details, String messageFormat, Object... args){
        super(STATUS, details, cause, messageFormat, args);
    }
}
