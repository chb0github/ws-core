package org.bongiorno.ws.core.client.headers;

import org.bongiorno.common.utils.Function;
import org.springframework.http.MediaType;

public class MediaTypeToMediaType implements Function<javax.ws.rs.core.MediaType, MediaType> {

    @Override
    public MediaType apply(javax.ws.rs.core.MediaType mediaType) {
        if(mediaType == null ){
            return null;
        }
        return MediaType.parseMediaType(mediaType.toString());
    }
}
