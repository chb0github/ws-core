package org.bongiorno.ws.core.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

/**
 * @author chribong
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class AbstractDto implements Dto {

    public String toString() {
        return toJson(false);
    }
}
