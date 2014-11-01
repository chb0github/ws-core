package org.bongiorno.ws.core.parameterhandlers;

import org.bongiorno.ws.core.exceptions.ResourceNotFoundException;
//import org.apache.cxf.jaxrs.ext.ParameterHandler;

import javax.validation.constraints.NotNull;


public abstract class AbstractParameterHandler<R, I>/* implements ParameterHandler<R>*/ {

//    @Override
    public final R fromString(@NotNull String id) {
        I realId = getId(id);
        R result = getResult(realId);
        if (result == null)
            throw new ResourceNotFoundException("%s '%s' not found", getResultType().getSimpleName(), id);

        return result;
    }

    protected abstract I getId(String id);

    protected abstract R getResult(I id);

    protected abstract Class<R> getResultType();
}
