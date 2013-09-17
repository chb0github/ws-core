package org.bongiorno.ws.core.server.filters;

import org.bongiorno.common.utils.SecurityUtils;
import org.bongiorno.common.utils.VdcRandom;
import org.bongiorno.ws.core.client.headers.DefaultAuthHeader;
import org.bongiorno.ws.core.exceptions.UnauthorizedException;
import org.bongiorno.common.utils.SecurityUtils;
import org.bongiorno.ws.core.exceptions.UnauthorizedException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.mockito.Mockito.*;

/**
 * @author cbongiorno
 */
@RunWith(MockitoJUnitRunner.class)
public class FilterUnitTest {

    private HttpServletRequest request;
    private HttpServletResponse response;
    private FilterChain filterChain;
    private long stamp;
    private String sharedSecret;
    private String signature;

    @Before
    public void setUp() throws Exception {
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        filterChain = mock(FilterChain.class);
        stamp = System.currentTimeMillis();
        sharedSecret = VdcRandom.hexString(8);
        String data = sharedSecret + stamp;
        signature = SecurityUtils.hashTo(data, DefaultAuthHeader.DEFAULT_STRATEGY);
        when(request.getHeader(DefaultAuthHeader.TOKEN_HEADER)).thenReturn(signature);
    }

    @Test
    public void testSharedSecretFilterPass() throws Exception {
        SharedSecretFilter filter = new SharedSecretFilter(sharedSecret);

        when(request.getHeader(DefaultAuthHeader.USER_NAME_HEADER)).thenReturn("unit-test");
        when(request.getHeader(DefaultAuthHeader.TIME_HEADER)).thenReturn(String.valueOf(stamp));

        filter.doFilterWebserviceInternal(request,response,filterChain);

        verify(filterChain).doFilter(request, response);

    }

    @Test(expected = UnauthorizedException.class)
    public void testNoClientName() throws Exception {
        SharedSecretFilter filter = new SharedSecretFilter(sharedSecret);

        when(request.getHeader(DefaultAuthHeader.TIME_HEADER)).thenReturn(String.valueOf(stamp));
        when(request.getHeader(DefaultAuthHeader.TOKEN_HEADER)).thenReturn(signature);

        filter.doFilterWebserviceInternal(request, response, filterChain);
    }


    @Test(expected = UnauthorizedException.class)
    public void testNoTStamp() throws Exception {
        SharedSecretFilter filter = new SharedSecretFilter(sharedSecret);

        when(request.getHeader(DefaultAuthHeader.USER_NAME_HEADER)).thenReturn("unit-test");
        when(request.getHeader(DefaultAuthHeader.TOKEN_HEADER)).thenReturn(signature);

        filter.doFilterWebserviceInternal(request, response, filterChain);
    }

    @Test(expected = UnauthorizedException.class)
    public void testBadToken() throws Exception {
        SharedSecretFilter filter = new SharedSecretFilter(sharedSecret);

        when(request.getHeader(DefaultAuthHeader.USER_NAME_HEADER)).thenReturn("unit-test");
        when(request.getHeader(DefaultAuthHeader.TIME_HEADER)).thenReturn(String.valueOf(stamp));
        when(request.getHeader(DefaultAuthHeader.TOKEN_HEADER)).thenReturn(VdcRandom.hexString(8));
        filter.doFilterWebserviceInternal(request, response, filterChain);
    }
}
