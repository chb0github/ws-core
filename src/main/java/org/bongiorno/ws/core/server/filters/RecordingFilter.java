package org.bongiorno.ws.core.server.filters;

import org.apache.commons.io.IOUtils;
import org.bongiorno.misc.utils.Function;
import org.bongiorno.misc.utils.io.CapturingInputStream;
import org.bongiorno.misc.utils.io.CompositeOutputStream;
import org.bongiorno.ws.core.server.WebserviceFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import javax.annotation.Nullable;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Filter which logs data about each call.
 */
public class RecordingFilter extends WebserviceFilter {

    private Logger logger = LoggerFactory.getLogger(RecordingFilter.class);

    private Function<Integer, Boolean> shouldLog;

    public RecordingFilter() {
        shouldLog = HTTP_FAIL;
    }

    public RecordingFilter(Function<Integer, Boolean> shouldLog) {
        this.shouldLog = shouldLog;
    }

    @Override
    protected void doFilterWebserviceInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        CapturingInputStream capturedInput = new CapturingInputStream(request.getInputStream());
        ByteArrayOutputStream capturedOutput = new ByteArrayOutputStream();
        InputSubstitutingRequest myRequest = new InputSubstitutingRequest(request, capturedInput);
        OutputSubstitutingResponse myResponse = new OutputSubstitutingResponse(response, new CompositeOutputStream(capturedOutput, response.getOutputStream()));

        filterChain.doFilter(myRequest, myResponse);

        int statusCode = myResponse.getStatus();
        if (shouldLog.apply(statusCode)) {
            String input = IOUtils.toString(capturedInput.getCapturedInputStream());
            String output = String.format("Resource: %s %s\nStatus code: %s\nRequest: %s\nResponse: %s",
                    request.getMethod(), request.getRequestURI(),statusCode, input, capturedOutput.toString());
            logger.trace(output);
        }
    }

    private static final Function<Integer, Boolean> HTTP_FAIL = new Function<Integer, Boolean>() {
        @Override
        public Boolean apply(@Nullable Integer input) {
            boolean result = false;
            if (input != null) {
                HttpStatus.Series series = HttpStatus.valueOf(input).series();
                result = series != HttpStatus.Series.SUCCESSFUL;
            }
            return result;
        }
    };

}
