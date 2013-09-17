package org.bongiorno.ws.core.exceptions.mapping;

import org.bongiorno.ws.core.dto.support.AbstractDto;
import org.bongiorno.ws.core.exceptions.ErrorResponse;
import org.bongiorno.ws.core.exceptions.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import java.util.EnumSet;
import java.util.Map;
import java.util.Set;

/**
 * @author cbongiorno
 * @version 04/23/12 16:14
 */
public abstract class AbstractExceptionMapper<E extends Throwable> implements ExceptionMapper<E> {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    private static final Set<HttpStatus> noLogStatusCodes = EnumSet.noneOf(HttpStatus.class);

    static {
        // all client side errors are ignorable
        for (HttpStatus httpStatus : HttpStatus.values()) {
            if (httpStatus.series() == HttpStatus.Series.CLIENT_ERROR)
                noLogStatusCodes.add(httpStatus);
        }
    }

    public Response toResponse(E exception) {
        logException(exception);
        HttpStatus httpStatus = getHttpStatus(exception);
        Response.ResponseBuilder status = Response.status(httpStatus.value());
        AbstractDto errorResponse = getResponseEntity(exception, httpStatus);
        status = status.entity(errorResponse);
        //TODO: I'd like to make this XML or JSON depending on Accept headers, but I haven't found a way to access Accept headers here.
        status = status.type(MediaType.APPLICATION_JSON);
        return status.build();
    }

    protected AbstractDto getResponseEntity(E exception, HttpStatus status) {
        return new ErrorResponse(status.value(), exception.getMessage(), status.getReasonPhrase(), getErrorDetails(exception));
    }

    protected abstract HttpStatus getHttpStatus(E exception);

    protected Map<String, String> getErrorDetails(E exception) {
        return null;
    }

    protected void logException(E exception) {
        // if it's NOT an ignorable exception then we can use default logging behavior
        HttpStatus httpStatus = getHttpStatus(exception);
        if (noLogStatusCodes.contains(httpStatus)) {
            StringBuilder msg = new StringBuilder();
            msg.append(exception);
            StackTraceElement srcLocation = exception.getStackTrace()[0];
            msg.append(" @ ");
            msg.append(srcLocation.getFileName()).append(", line ").append(srcLocation.getLineNumber());
            log.error(msg.toString());
        } else {
            log.debug(exception.getMessage(), exception);
        }

    }

    protected abstract Class<E> getSupportedException();
}
