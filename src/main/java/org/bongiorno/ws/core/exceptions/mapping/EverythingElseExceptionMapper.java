package org.bongiorno.ws.core.exceptions.mapping;

import org.bongiorno.ws.core.dto.support.AbstractDto;
import org.bongiorno.ws.core.exceptions.ErrorResponse;
import org.springframework.http.HttpStatus;

public class EverythingElseExceptionMapper extends AbstractExceptionMapper<Throwable> {

    @Override
    protected AbstractDto getResponseEntity(Throwable exception, HttpStatus status) {
        //Don't use the message from generic exceptions, as they might contain implementation details of our system.
        return new ErrorResponse(status.value(), "Internal Server Error", status.getReasonPhrase(), getErrorDetails(exception));
    }

    @Override
    protected HttpStatus getHttpStatus(Throwable e) {
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }

    @Override
    protected Class<Throwable> getSupportedException() {
        return Throwable.class;
    }
}
