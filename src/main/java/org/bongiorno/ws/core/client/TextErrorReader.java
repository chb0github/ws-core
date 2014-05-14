package org.bongiorno.ws.core.client;

import org.bongiorno.misc.utils.StringUtils;
import org.bongiorno.ws.core.exceptions.ErrorResponse;

import java.io.IOException;
import java.io.InputStream;

public class TextErrorReader implements ErrorReader {
    @Override
    public ErrorResponse readError(InputStream in) throws IOException {
        return new ErrorResponse(null, StringUtils.convertStreamToString(in), null, null);
    }
}
