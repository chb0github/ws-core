package org.bongiorno.ws.core.client.headers;

import org.apache.commons.codec.binary.Base64;
import org.springframework.http.HttpHeaders;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.web.client.RequestCallback;

import java.io.IOException;

public class BasicAuthHeader implements RequestCallback {

    private String userName;

    private String password;

    public BasicAuthHeader(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    @Override
    public void doWithRequest(ClientHttpRequest request) throws IOException {
        HttpHeaders headers = request.getHeaders();
        String token = userName +":"+password;
        token = Base64.encodeBase64String(token.getBytes());
        headers.add("Authorization","Basic " + token);
    }
}
