package org.bongiorno.ws.core.client.headers;

import org.springframework.http.HttpHeaders;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.web.client.RequestCallback;

import java.io.IOException;

/**
 * @author cbongiorno
 *         Date: 7/1/12
 *         Time: 10:08 PM
 */
public class ConstantHeader implements RequestCallback {

    private String header;
    private String value;

    public ConstantHeader(String header, String value) {
        this.header = header;
        this.value = value;
    }

    @Override
    public void doWithRequest(ClientHttpRequest request) throws IOException {
        HttpHeaders headers = request.getHeaders();
        headers.add(header,value);
    }
}
