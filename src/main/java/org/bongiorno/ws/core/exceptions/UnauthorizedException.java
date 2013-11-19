package org.bongiorno.ws.core.exceptions;

import org.springframework.http.HttpStatus;

import java.util.Map;

/**
 *  401 Unauthorized
 *  Similar to 403 Forbidden, but specifically for use when authentication is possible but has failed or not yet been provided.
 *  The response must include a WWW-Authenticate header field containing a challenge applicable to the requested resource.
 *  See Basic access authentication and Digest access authentication.
 */
public class UnauthorizedException extends WebserviceException {

    public static final HttpStatus STATUS = HttpStatus.UNAUTHORIZED;

    public UnauthorizedException(String messageFormat, Object... args) {
        super(STATUS, messageFormat, args);
    }

    public UnauthorizedException(Throwable cause, String messageFormat, Object... args) {
        super(STATUS, cause, messageFormat, args);
    }
    public UnauthorizedException(Map<String, String> details, String messageFormat, Object... args) {
        super(STATUS, details, messageFormat, args);
    }

}
