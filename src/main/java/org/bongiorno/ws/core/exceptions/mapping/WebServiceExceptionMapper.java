package org.bongiorno.ws.core.exceptions.mapping;


import org.bongiorno.ws.core.exceptions.WebserviceException;
import org.springframework.http.HttpStatus;

import java.util.Map;
import java.util.TreeMap;

public class WebServiceExceptionMapper extends AbstractExceptionMapper<WebserviceException> {

    @Override
    protected HttpStatus getHttpStatus(WebserviceException e) {
        return e.getStatus();
    }

    @Override
    protected Map<String, String> getErrorDetails(WebserviceException exception) {
        return exception.getDetails();
    }

    @Override
    protected Class<WebserviceException> getSupportedException() {
        return WebserviceException.class;
    }

    @Override
    protected void logException(WebserviceException e) {
        if (log.isDebugEnabled()) {
            StringBuilder msg = new StringBuilder("Webservice error: ").append(e);
            Map<String, String> details = e.getDetails();
            if (details != null) {
                msg.append(", ").append(new TreeMap<>(details));
            }
            StackTraceElement srcLocation = e.getStackTrace()[0];
            msg.append(" @ ");
            msg.append(srcLocation.getFileName()).append(", line ").append(srcLocation.getLineNumber());
            log.debug(msg.toString());
        }
    }
}
