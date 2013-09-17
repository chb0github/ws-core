package org.bongiorno.ws.core.client;

import org.bongiorno.ws.core.exceptions.ErrorResponse;
import org.bongiorno.ws.core.exceptions.ErrorResponse;

import java.io.IOException;
import java.io.InputStream;

public interface ErrorReader {
    public ErrorResponse readError(InputStream in) throws IOException;
}
