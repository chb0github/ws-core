package org.bongiorno.ws.core.exceptions.mapping;

import org.codehaus.jackson.JsonProcessingException;
import org.springframework.http.HttpStatus;

public class JsonProcessingExceptionMapper extends AbstractExceptionMapper<JsonProcessingException>{
    @Override
    protected HttpStatus getHttpStatus(JsonProcessingException exception) {
        return HttpStatus.BAD_REQUEST;
    }

    @Override
    protected Class<JsonProcessingException> getSupportedException() {
        return JsonProcessingException.class;
    }
}
