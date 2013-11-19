package org.bongiorno.ws.core.exceptions;

import org.springframework.http.HttpStatus;

import java.util.Map;

public class WebserviceException extends javax.xml.ws.WebServiceException {

    private HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

    private Map<String, String> details;

    public WebserviceException(HttpStatus status, Throwable cause){
        super(cause.getMessage(), cause);
        this.status = status;
    }

    public WebserviceException(HttpStatus status, Throwable cause, String messageFormat, Object... args) {
        super(String.format(messageFormat, args),cause);
        this.status = status;
    }

    public WebserviceException(HttpStatus status, Map<String, String> details, Throwable cause){
        super(details.toString(), cause);
        this.details = details;
        this.status = status;
    }

    public WebserviceException(HttpStatus status, String messageFormat, Object... args) {
        super(String.format(messageFormat, args));
        this.status = status;
    }

    public WebserviceException(HttpStatus status, Map<String, String> details){
        super(details.toString());
        this.details = details;
        this.status = status;
    }

    public WebserviceException(HttpStatus status, Map<String,String> details, String messageFormat, Object... args){
        this(status, details, null, messageFormat, args);
    }

    public WebserviceException(HttpStatus status, Map<String,String> details, Throwable cause, String messageFormat, Object... args){
        super(messageFormat == null ? details.toString() : String.format(messageFormat, args), cause);
        this.details = details;
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public Map<String, String> getDetails() {
        return details;
    }

    @Override
    public String toString() {
        return status.toString() + ": " + getMessage();
    }

}
