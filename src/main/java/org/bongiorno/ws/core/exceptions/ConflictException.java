package org.bongiorno.ws.core.exceptions;

import org.springframework.http.HttpStatus;

import java.util.Map;

/**
 * @author cbongiorno
 *         Date: 5/9/12
 *         Time: 5:24 PM
 *  409 Conflict
 *  Indicates that the request could not be processed because of conflict in the request, such as an edit conflict.
 */
public class ConflictException extends WebserviceException {

    public static final HttpStatus STATUS = HttpStatus.CONFLICT;


    public ConflictException(String messageFormat, Object... args) {
        super(STATUS,messageFormat, args);
    }

    public ConflictException(Throwable cause, String messageFormat, Object... args) {
        super(STATUS, cause, messageFormat, args);
    }


    public ConflictException(Map<String, String> details, String messageFormat, Object... args) {
        super(STATUS, details, messageFormat, args);
    }

}
