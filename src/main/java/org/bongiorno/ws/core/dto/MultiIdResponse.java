package org.bongiorno.ws.core.dto;


import javax.xml.bind.annotation.XmlRootElement;

/**
 * Response returned when a request creates more than one object
 */
@XmlRootElement
public class MultiIdResponse<I> /*extends DtoQuickCollection<IdResponse<I>>*/ {

//
//    public MultiIdResponse() {
//        super.delegate = new HashSet<>();
//    }
//
//    public MultiIdResponse(Set<IdResponse<I>> ids) {
//        super.delegate = ids;
//    }
//
//    public Set<IdResponse<I>> getIds() {
//        return (Set<IdResponse<I>>) super.delegate;
//    }

}
