package org.bongiorno.ws.core.exceptions;

public class InvalidRelationshipException extends ServiceException {
    public InvalidRelationshipException(String messageFormat, Object... args) {
        super(messageFormat, args);
    }

    public InvalidRelationshipException(Throwable cause, String messageFormat, Object... args) {
        super(cause, messageFormat, args);
    }
}
