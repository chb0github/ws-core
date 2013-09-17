package org.bongiorno.ws.core.client.headers;

import org.springframework.http.client.ClientHttpRequest;
import org.springframework.web.client.RequestCallback;

import java.io.IOException;

/**
 * @author cbongiorno
 *         Date: 7/1/12
 *         Time: 10:08 PM
 */
public class NoOpHeader implements RequestCallback {
    @Override
    public void doWithRequest(ClientHttpRequest request) throws IOException {
        // intentionally do nothing
    }
}
