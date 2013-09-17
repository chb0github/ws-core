package org.bongiorno.ws.core.client;

import org.bongiorno.ws.core.dto.DtoUtils;
import org.bongiorno.ws.core.exceptions.ErrorResponse;
import org.bongiorno.ws.core.dto.DtoUtils;
import org.bongiorno.ws.core.exceptions.ErrorResponse;

import java.io.IOException;
import java.io.InputStream;

public class JsonErrorReader implements ErrorReader{
    @Override
    public ErrorResponse readError(InputStream in) throws IOException {
        return DtoUtils.fromJson(in, ErrorResponse.class);
    }
}
