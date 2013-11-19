package org.bongiorno.ws.core.client.headers;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.web.client.RequestCallback;

import java.io.IOException;

/**
 *
 *         Sets the content type to be submited with a REST client
 */
public class ContentTypeHeader implements RequestCallback {

    private MediaType contentType;

    public ContentTypeHeader(String contentType) {
        this.contentType = MediaType.parseMediaType(contentType);
    }

    @Override
    public void doWithRequest(ClientHttpRequest request) throws IOException {
        HttpHeaders headers = request.getHeaders();
        headers.setContentType(contentType);
    }
}
