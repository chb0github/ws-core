package org.bongiorno.common.utils.functions;

import org.bongiorno.common.utils.Function;
import org.bongiorno.ws.core.exceptions.ServiceException;
import org.bongiorno.ws.core.exceptions.ServiceException;

import javax.annotation.Nullable;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.ByteArrayOutputStream;
import java.io.StringReader;

/**
 * @author cbongiorno
 */
public class StyleSheetFunction implements Function<String, String> {

    private static TransformerFactory transformerFactory = TransformerFactory.newInstance("net.sf.saxon.TransformerFactoryImpl", null);


    private String xslt;

    public StyleSheetFunction(String xslt) {
        this.xslt = xslt;
    }


    public String getXslt() {
        return xslt;
    }

    @Override
    public String apply(@Nullable String xmlIn) {
        return StyleSheetFunction.apply(xmlIn, this.xslt);
    }

    public static String apply(String xmlIn, String xslt)  {
        Source styleSheet = new StreamSource(new StringReader(xslt));
        Source xmlInput = new StreamSource(new StringReader(xmlIn));
        ByteArrayOutputStream messageBuffer = new ByteArrayOutputStream();
        try {
            Transformer transformer = transformerFactory.newTransformer(styleSheet);
            transformer.transform(xmlInput, new StreamResult(messageBuffer));
        } catch (TransformerException e) {
            throw new ServiceException(e,"Unable to apply stylesheet to content %s", xmlIn);
        }
        return messageBuffer.toString();
    }
}
