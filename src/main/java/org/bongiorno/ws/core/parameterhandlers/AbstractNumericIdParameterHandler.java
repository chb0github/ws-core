package org.bongiorno.ws.core.parameterhandlers;

import org.bongiorno.ws.core.exceptions.BadRequestException;


public abstract class AbstractNumericIdParameterHandler<T> extends AbstractParameterHandler<T, Long> {

    @Override
    protected Long getId(String id) {
        long sId;
        try {
            sId = Long.parseLong(id);
        } catch (NumberFormatException e) {
            throw new BadRequestException("'%s' is a malformed id", id);
        }
        return sId;
    }

    protected abstract T getResult(Long id);
}
