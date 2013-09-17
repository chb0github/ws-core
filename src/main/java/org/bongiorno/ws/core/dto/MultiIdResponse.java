package org.bongiorno.ws.core.dto;

import org.bongiorno.ws.core.dto.support.DtoQuickCollection;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.HashSet;
import java.util.Set;

/**
 * Response returned when a request creates more than one object
 */
@XmlRootElement
public class MultiIdResponse extends DtoQuickCollection<Long> {


    public MultiIdResponse() {
        super.delegate = new HashSet<>();
    }

    public MultiIdResponse(Set<Long> ids) {
        super.delegate = ids;
    }

    public Set<Long> getIds() {
        return (Set<Long>) super.delegate;
    }

}
