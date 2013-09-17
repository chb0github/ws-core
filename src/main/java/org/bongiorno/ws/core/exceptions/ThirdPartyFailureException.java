package org.bongiorno.ws.core.exceptions;

import org.springframework.http.HttpStatus;

import java.util.Map;

/**
 * An example of when this exception is meant to be used is when a payment cannot be processed because the credit card
 * was declined.
 */
public class ThirdPartyFailureException extends WebserviceException{

    public static final HttpStatus STATUS = HttpStatus.METHOD_FAILURE;

    public ThirdPartyFailureException(Throwable cause, String messageFormat, Object... args) {
        super(STATUS, cause, messageFormat, args);
    }

    public ThirdPartyFailureException(Map<String, String> details, Throwable cause) {
        super(STATUS, details, cause);
    }

    public ThirdPartyFailureException(String messageFormat, Object... args) {
        super(STATUS, messageFormat, args);
    }

    public ThirdPartyFailureException(Map<String, String> details) {
        super(STATUS, details);
    }

    public ThirdPartyFailureException(Map<String, String> details, String messageFormat, Object... args) {
        super(STATUS, details, messageFormat, args);
    }

    public ThirdPartyFailureException(Map<String, String> details, Throwable cause, String messageFormat, Object... args) {
        super(STATUS, details, cause, messageFormat, args);
    }
}
