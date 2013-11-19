package org.bongiorno.ws.core.client.headers;

import org.bongiorno.common.utils.SecurityUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.web.client.RequestCallback;

import java.io.IOException;

public class DefaultAuthHeader implements RequestCallback {

    public static final String USER_NAME_HEADER = "x-username";
    public static final String TIME_HEADER = "x-time";
    public static final String TOKEN_HEADER = "x-token";    // this is the signature
    public static final String DEFAULT_STRATEGY = "SHA-256";

    private final String userName;
    private String strategy;
    private final transient String sharedSecret; // transient so the secret cannot be serialized out with debug

    public DefaultAuthHeader(String userName, String sharedSecret) {
        this.sharedSecret = sharedSecret;
        this.userName = userName;
        this.strategy = DEFAULT_STRATEGY;
    }

    public DefaultAuthHeader(String userName, String sharedSecret, String strategy) {
        this.sharedSecret = sharedSecret;
        this.userName = userName;
        this.strategy = strategy;
    }

    @Override
    public void doWithRequest(ClientHttpRequest request) throws IOException {
        long stamp = System.currentTimeMillis();

        String data = sharedSecret + stamp;
        String signature = SecurityUtils.hashTo(data, strategy);
        HttpHeaders headers = request.getHeaders();

        headers.add(USER_NAME_HEADER, userName);
        headers.add(TIME_HEADER, String.valueOf(stamp));
        headers.add(TOKEN_HEADER, signature); // token is the shared secret +  time

    }

    public String getUserName() {
        return userName;
    }
}
