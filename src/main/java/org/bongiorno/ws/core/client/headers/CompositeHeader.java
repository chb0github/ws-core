package org.bongiorno.ws.core.client.headers;

import org.bongiorno.common.utils.QuickCollection;
import org.bongiorno.common.utils.QuickCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.web.client.RequestCallback;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;


/**
 *
 *         Applies all Callback Requests in the order they were added.
 *         If the Set constructor is used, the iterative order of that Set will be applied
 */
public class CompositeHeader extends QuickCollection<RequestCallback> implements RequestCallback {

    private static final Logger log = LoggerFactory.getLogger(CompositeHeader.class);

    public CompositeHeader() {
        super(new HashSet<RequestCallback>());
    }

    public CompositeHeader(Set<RequestCallback> calls) {
        super(calls);
    }

    public CompositeHeader(RequestCallback... calls) {
        this(new LinkedHashSet<RequestCallback>(Arrays.asList(calls)));
    }

    public Set<RequestCallback> getHeaders() {
        return (Set<RequestCallback>) super.delegate;
    }


    @Override
    public void doWithRequest(ClientHttpRequest request) throws IOException {
        for (RequestCallback call : super.delegate) {
            // this may be null for calls like DELETE. just ignore it
            if(call != null)
                call.doWithRequest(request);
        }

    }
}
