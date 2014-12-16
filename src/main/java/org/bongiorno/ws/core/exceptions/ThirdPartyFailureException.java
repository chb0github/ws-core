package org.bongiorno.ws.core.exceptions;

import org.springframework.http.HttpStatus;

import java.util.Map;


public class ThirdPartyFailureException extends WebserviceException{

    public static final HttpStatus STATUS = HttpStatus.FAILED_DEPENDENCY;

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
