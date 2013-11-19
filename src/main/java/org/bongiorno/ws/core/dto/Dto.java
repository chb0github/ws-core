package org.bongiorno.ws.core.dto;

import javax.xml.bind.JAXBException;


public interface Dto {

    public String toXml() throws JAXBException;

    public String toJson();
}
