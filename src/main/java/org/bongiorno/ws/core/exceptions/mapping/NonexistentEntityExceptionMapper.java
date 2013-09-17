package org.bongiorno.ws.core.exceptions.mapping;

import org.bongiorno.ws.core.exceptions.NonexistentEntityException;
import org.bongiorno.ws.core.exceptions.NonexistentEntityException;
import org.springframework.http.HttpStatus;

public class NonexistentEntityExceptionMapper extends AbstractExceptionMapper<NonexistentEntityException> {
    @Override
    protected HttpStatus getHttpStatus(NonexistentEntityException exception) {
        return HttpStatus.NOT_FOUND;
    }

    @Override
    protected Class<NonexistentEntityException> getSupportedException() {
        return NonexistentEntityException.class;
    }
}
