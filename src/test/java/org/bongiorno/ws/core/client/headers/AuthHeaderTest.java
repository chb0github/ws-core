package org.bongiorno.ws.core.client.headers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpHeaders;
import org.springframework.http.client.ClientHttpRequest;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

/**
 * @author chribong
 */
@RunWith(MockitoJUnitRunner.class)
public class AuthHeaderTest  {

    @Mock
    private ClientHttpRequest request;

    @Test
    public void testBasicAuthHeader() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        when(request.getHeaders()).thenReturn(headers);

        BasicAuthHeader authHeader = new BasicAuthHeader("christian","bongiorno");

        authHeader.doWithRequest(request);

        List<String> authorization = headers.get("Authorization");
        String value = authorization.get(0);
        assertEquals("Basic Y2hyaXN0aWFuOmJvbmdpb3Jubw==",value);
    }
}
