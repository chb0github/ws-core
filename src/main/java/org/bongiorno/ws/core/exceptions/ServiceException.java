package org.bongiorno.ws.core.exceptions;

public class ServiceException extends RuntimeException{

    public ServiceException(String messageFormat, Object... args) {
        super(String.format(messageFormat, args));
    }

    public ServiceException(Throwable cause, String messageFormat, Object... args) {
        super(String.format(messageFormat, args), cause);
    }
}
