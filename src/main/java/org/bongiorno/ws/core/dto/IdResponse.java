package org.bongiorno.ws.core.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Response returned when any object is created
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class IdResponse<I> implements Dto {
    /**
     * The ID of the newly created object.
     */
    private I id;

    private URI resource;
    /*
     */

    public IdResponse(){}

    public IdResponse(I id, String baseResource) throws URISyntaxException {
        this.id = id;
        this.resource = new URI(baseResource + "/" + id);
    }

    public I getId() {
        return id;
    }

    public URI getResource() {
        return resource;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        IdResponse that = (IdResponse) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
