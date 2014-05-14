package org.bongiorno.ws.core.client.headers;

import org.bongiorno.misc.utils.WSCollections;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.web.client.RequestCallback;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.List;

public class CopyHeaderCallback implements RequestCallback{

    private static MediaTypeToMediaType mediaTypeAdapter = new MediaTypeToMediaType();

    private HttpHeaders inputHeaders;

    public CopyHeaderCallback(HttpHeaders inputHeaders){
        this.inputHeaders = inputHeaders;
    }

    @Override
    public void doWithRequest(ClientHttpRequest request) throws IOException {
        org.springframework.http.HttpHeaders outputHeaders = request.getHeaders();

        MediaType contentType = inputHeaders.getMediaType();
        if(contentType != null){
            outputHeaders.setContentType(mediaTypeAdapter.apply(contentType));
        }

        outputHeaders.setAccept(WSCollections.transform(inputHeaders.getAcceptableMediaTypes(), mediaTypeAdapter));
        List<String> bshwid = inputHeaders.getRequestHeader("X-BSHWID");
        if(bshwid != null && !bshwid.isEmpty()){
            outputHeaders.set("X-BSHWID", bshwid.get(0));
        }
    }
}
