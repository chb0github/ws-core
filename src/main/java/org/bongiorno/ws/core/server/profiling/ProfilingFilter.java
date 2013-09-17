package org.bongiorno.ws.core.server.profiling;

import org.bongiorno.ws.core.server.filters.OutputSubstitutingResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

/**
 * Filter which logs data about each call.
 */
public class ProfilingFilter extends OncePerRequestFilter {

    private Logger logger = LoggerFactory.getLogger("com.bongiorno.ws.profiling.filter");

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        long startTime = System.currentTimeMillis();

        ByteCountingOutputStream counter;
        try {
            counter = new ByteCountingOutputStream(response.getOutputStream());
        } catch (IOException e) {
            throw new ServletException("Error getting OutputStream from response", e);
        }
        OutputSubstitutingResponse profilingResponse = new OutputSubstitutingResponse(response, counter);

        filterChain.doFilter(request, profilingResponse);

        int requestLength = Math.max(request.getContentLength(), 0); //GET has no Content-Length, returns -1
        int responseLength = counter.getByteCount();
        requestLength += computeHeaderSize(request);


        long endTime = System.currentTimeMillis();

        logger.trace("{} {},{},{},{},{}", new Object[]{request.getMethod(), request.getRequestURI(), (endTime-startTime), requestLength, responseLength, profilingResponse.getStatus()});
    }

    private int computeHeaderSize(HttpServletRequest request) {
        int requestLength = 0;
        for (Enumeration e = request.getHeaderNames(); e.hasMoreElements();) {
            String headerName = (String) e.nextElement();
            requestLength += headerName.getBytes().length;

            String headerValue = request.getHeader(headerName);
            requestLength += headerValue.getBytes().length;
        }
        return requestLength;
    }
}
