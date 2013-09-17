package org.bongiorno.ws.core.client;

import org.bongiorno.ws.core.client.headers.ConstantHeader;
import org.bongiorno.ws.core.dto.support.DefaultDateTimeAdapter;
import org.bongiorno.ws.core.client.headers.ConstantHeader;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpResponse;

import java.net.URI;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

/**
 * @author cbongiorno
 */
@RunWith(MockitoJUnitRunner.class)
public class RelativeRestOperationsTest {

    @InjectMocks
    private RelativeRestOperations operations = new RelativeRestOperations("http://www.google.com", new ConstantHeader("constant", "1"));

    private DefaultDateTimeAdapter adapter = new DefaultDateTimeAdapter();

    @Mock
    private ClientHttpRequestFactory requestFactory;

    @Before
    public void setUp() throws Exception {
        operations.addConverter(DateTime.class, adapter);
    }

    @Test
    public void testExecute() throws Exception {
        ClientHttpRequest request = mock(ClientHttpRequest.class);
        ClientHttpResponse response = mock(ClientHttpResponse.class);
        when(request.getHeaders()).thenReturn(new HttpHeaders());
        when(request.execute()).thenReturn(response);
        when(response.getStatusCode()).thenReturn(HttpStatus.NO_CONTENT);

        when(requestFactory.createRequest(any(URI.class), eq(HttpMethod.GET))).thenReturn(request);

        String BASE_URL = "/restaurant/{id}/report/nightly?from={from}&to={to}";
        DateTime from = adapter.unmarshal("01/20/2013 14:48:00");
        DateTime to = adapter.unmarshal("01/21/2013 14:48:00");
        operations.getForObject(BASE_URL, Object.class, 1L, from, to);
        verify(requestFactory).createRequest(new URI("http://www.google.com/restaurant/1/report/nightly?from=01/20/2013%2014:48:00&to=01/21/2013%2014:48:00"), HttpMethod.GET);
    }
}
