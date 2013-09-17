package org.bongiorno.ws.core.dto;

import javax.xml.bind.JAXBException;

/**
 * @author cbongiorno
 *         Date: 4/19/12
 *         Time: 2:23 PM
 */
public interface Dto {

    public String toXml() throws JAXBException;

    public String toJson();
}
