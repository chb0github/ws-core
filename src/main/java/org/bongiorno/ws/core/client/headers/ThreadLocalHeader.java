package org.bongiorno.ws.core.client.headers;

import org.springframework.http.client.ClientHttpRequest;
import org.springframework.web.client.RequestCallback;

import java.io.IOException;

public class ThreadLocalHeader extends ThreadLocal<RequestCallback> implements RequestCallback{

    @Override
    protected RequestCallback initialValue() {
        return new NoOpHeader();
    }

    @Override
    public void doWithRequest(ClientHttpRequest request) throws IOException {
        get().doWithRequest(request);
    }
}
