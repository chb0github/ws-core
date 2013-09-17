package org.bongiorno.ws.core.dto.support;

import org.bongiorno.common.utils.functions.StyleSheetFunction;
import org.bongiorno.ws.core.dto.Dto;
import org.bongiorno.ws.core.dto.DtoUtils;
import org.bongiorno.ws.core.dto.DtoUtils;

import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.transform.TransformerException;

/**
 * @author cbongiorno
 *         Date: 4/21/12
 *         Time: 12:56 PM
 */
@XmlAccessorType(XmlAccessType.FIELD)
public abstract class AbstractDto implements Dto {


    /**
     * Output 'this' as formatted XML
     *
     * @return formatted XML
     */
    public String toXml() throws JAXBException {
        return toXml(true);
    }

    /**
     * output this as XML
     *
     * @param format do you want it pretty formatted?
     * @return duh!
     */
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

    public String applyStyleSheet(String xslt) throws TransformerException, JAXBException {
        String xmlIn = this.toXml(false);
        return StyleSheetFunction.apply(xmlIn,xslt);
    }

    @Override
    public String toString() {
        return toJson(false);
    }
}
