package org.bongiorno.ws.core.exceptions;

import org.bongiorno.ws.core.dto.support.AbstractDto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Map;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "errorResponse")
public class ErrorResponse extends AbstractDto{
    private String message;
    private Integer httpStatusCode;
    private String httpMessage;
    private Map<String, String> details;

    public ErrorResponse() {
    }

    public ErrorResponse(Integer httpStatusCode, String message, String httpMessage) {
        this(httpStatusCode, message, httpMessage, null);
    }

    public ErrorResponse(Integer httpStatusCode, String message, String httpMessage, Map<String, String> details) {
        this.httpStatusCode = httpStatusCode;
        this.message = message;
        this.httpMessage = httpMessage;
        this.details = details;
    }

    public String getMessage() {
        return message;
    }

    public String getHttpMessage() {
        return httpMessage;
    }

    public Integer getHttpStatusCode() {
        return httpStatusCode;
    }

    public Map<String, String> getDetails() {
        return details;
    }

    @Override
    public String toString() {
        return message;
    }
}
