package org.bongiorno.ws.core.dto.support;

import org.bongiorno.common.utils.QuickCollection;
import org.bongiorno.ws.core.dto.Dto;
import org.bongiorno.ws.core.dto.DtoUtils;

import javax.xml.bind.JAXBException;
import java.util.Collection;

public class DtoQuickCollection<T> extends QuickCollection<T> implements Dto {

    public DtoQuickCollection() {
    }

    public DtoQuickCollection(Collection<T> delegate) {
        super(delegate);
    }

    public String toXml() throws JAXBException {
        return toXml(true);
    }

    public String toXml(boolean format) throws JAXBException {
        return DtoUtils.toXml(this, format);
    }

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
