package org.bongiorno.ws.core.client;

import org.bongiorno.ws.core.dto.DtoUtils;
import org.bongiorno.ws.core.exceptions.ErrorResponse;
import org.bongiorno.ws.core.dto.DtoUtils;
import org.bongiorno.ws.core.exceptions.ErrorResponse;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.io.InputStream;

public class XmlErrorReader implements ErrorReader{
    @Override
    public ErrorResponse readError(InputStream in) throws IOException {
        try {
            return DtoUtils.fromXml(in, ErrorResponse.class);
        } catch (JAXBException e) {
            throw new IOException(e);
        }
    }
}
