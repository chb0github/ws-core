package org.bongiorno.ws.core.dto;

import org.bongiorno.ws.core.dto.support.AbstractDto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Response returned when any object is created
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class IdResponse extends AbstractDto{
    /**
     * The ID of the newly created object.
     */
    // Note: In the new DB, Timezones and States have varchars instead of bigints for keys.  I don't anticipate creating
    // those through the APIs, though.
    private Long id;

    public IdResponse(){}

    public IdResponse(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
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
