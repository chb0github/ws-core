package org.bongiorno.ws.core.client.headers;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.web.client.RequestCallback;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;

/**
 *
 *         Adds accept headers to a rest client
 */
public class AcceptHeader implements RequestCallback {

    private List<MediaType> acceptTypes = new LinkedList<MediaType>();

    public AcceptHeader(List<String> acceptTypes) {
        for (String acceptType : acceptTypes)
            this.acceptTypes.add(MediaType.parseMediaType(acceptType));
    }

    @Override
    public void doWithRequest(ClientHttpRequest request) throws IOException {
        HttpHeaders headers = request.getHeaders();
        LinkedHashSet<MediaType> accept = new LinkedHashSet<MediaType>(headers.getAccept());
        accept.addAll(acceptTypes);
        headers.setAccept(new ArrayList<MediaType>(accept));
    }
}
