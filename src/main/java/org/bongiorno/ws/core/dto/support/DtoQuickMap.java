package org.bongiorno.ws.core.dto.support;

import org.bongiorno.common.utils.QuickMap;
import org.bongiorno.ws.core.dto.Dto;
import org.bongiorno.ws.core.dto.DtoUtils;
import org.bongiorno.ws.core.dto.DtoUtils;

import javax.xml.bind.JAXBException;

/**
 * @author cbongiorno
 */
public class DtoQuickMap<K,V> extends QuickMap<K,V> implements Dto {

    public String toXml() throws JAXBException {
        return toXml(true);
    }

    public String toXml(boolean format) throws JAXBException {
        return DtoUtils.toXml(this, format);
    }

    /**
     * Output 'this' as formatted JSON
     *
     * @return formatted JSON
     */
    @Override
    public String toJson() {
        return toJson(true);
    }

    public String toJson(boolean format) {
        return DtoUtils.toJson(this, format);
    }

    @Override
    public String toString() {
        return toJson();
    }
}
