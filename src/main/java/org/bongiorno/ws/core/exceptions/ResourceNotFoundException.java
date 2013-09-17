package org.bongiorno.ws.core.exceptions;

import org.springframework.http.HttpStatus;

import java.util.Map;

/**
 * @author cbongiorno
 *         Date: 5/9/12
 *         Time: 5:24 PM
 * 404 Not Found
 * The requested resource could not be found but may be available again in the future.
 * Subsequent requests by the client are permissible
 */
public class ResourceNotFoundException extends WebserviceException {

    public static final HttpStatus STATUS = HttpStatus.NOT_FOUND;

    public ResourceNotFoundException(String messageFormat, Object... args) {
        super(STATUS,messageFormat, args);
    }

    public ResourceNotFoundException(Throwable cause, String messageFormat, Object... args) {
        super(STATUS, cause, messageFormat, args);
    }

    public ResourceNotFoundException(Throwable cause){
        super(STATUS, cause);
    }

    public ResourceNotFoundException(Map<String, String> details, String messageFormat, Object... args) {
        super(STATUS, details, messageFormat, args);
    }
}
