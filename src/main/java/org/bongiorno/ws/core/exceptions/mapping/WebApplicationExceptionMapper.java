package org.bongiorno.ws.core.exceptions.mapping;


import org.bongiorno.ws.core.dto.support.AbstractDto;
import org.bongiorno.ws.core.exceptions.ErrorResponse;
import org.bongiorno.ws.core.dto.support.AbstractDto;
import org.bongiorno.ws.core.exceptions.ErrorResponse;
import org.springframework.http.HttpStatus;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import static org.springframework.http.HttpStatus.valueOf;

/**
 * @author cbongiorno
 *         Date: 5/9/12
 *         Time: 4:23 PM
 */
public class WebApplicationExceptionMapper extends AbstractExceptionMapper<WebApplicationException> {


    @Override
    protected HttpStatus getHttpStatus(WebApplicationException e) {
        return valueOf(e.getResponse().getStatus());
    }

    @Override
    protected Class<WebApplicationException> getSupportedException() {
        return WebApplicationException.class;
    }

    @Override
    protected AbstractDto getResponseEntity(WebApplicationException exception, HttpStatus status) {
        Response response = exception.getResponse();
        Object oldEntity = response == null ? null : response.getEntity();
        String msg = oldEntity == null ? status.getReasonPhrase() : oldEntity.toString();

        return new ErrorResponse(status.value(), msg, status.getReasonPhrase(), getErrorDetails(exception));
    }
}
