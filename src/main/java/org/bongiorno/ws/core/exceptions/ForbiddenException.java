package org.bongiorno.ws.core.exceptions;

import org.springframework.http.HttpStatus;

import java.util.Map;

/**
 * @author cbongiorno
 *         Date: 5/9/12
 *         Time: 5:24 PM
 *         403 Forbidden
 *         The request was a legal request, but the server is refusing to respond to it.[2]
 *         Unlike a 401 Unauthorized response, authenticating will make no difference.[2]
 */
public class ForbiddenException extends WebserviceException {

    public static final HttpStatus STATUS = HttpStatus.FORBIDDEN;

    public ForbiddenException(String messageFormat, Object... args) {
        super(STATUS, messageFormat, args);
    }

    public ForbiddenException(Throwable cause, String messageFormat, Object... args) {
        super(STATUS, cause, messageFormat, args);
    }

    public ForbiddenException(Map<String, String> details, String messageFormat, Object... args) {
        super(STATUS, details, messageFormat, args);
    }

    public ForbiddenException(Throwable cause) {
        super(STATUS, cause);
    }

}
