package org.bongiorno.ws.core.dto.support;

import org.bongiorno.ws.core.dto.DtoUtils;
import org.codehaus.jackson.jaxrs.JacksonJsonProvider;
import org.springframework.util.ResourceUtils;

import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.*;


public class MarshalTester {

    public static <T> T perform(String file, Class<T> clazz) throws Exception {
        JAXBContext jc = JAXBContext.newInstance(clazz);


        Unmarshaller um = jc.createUnmarshaller();
        File testFile = ResourceUtils.getFile("classpath:" + file);

        if (!testFile.exists())
            throw new FileNotFoundException(testFile.getCanonicalPath() + " could not be found");

        @SuppressWarnings("unchecked")
        T c1 = (T) um.unmarshal(new FileInputStream(testFile));

        Marshaller marshaller = jc.createMarshaller();
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        marshaller.marshal(c1, os);

        @SuppressWarnings("unchecked")
        T c = (T) um.unmarshal(new ByteArrayInputStream(os.toByteArray()));

        if (!c.equals(c1))
            throw new Exception("Is equality defined on all objects? Expected " + c + " got " + c1);

        return c;
    }

    /**
     * Verifies that this object can be marshaled out and unmarshalled in and that do data was lost in between
     *
     * @param o the object to test
     * @throws javax.xml.bind.JAXBException if there was a problem marshalling
     */
    public static void perform(Object o) throws Exception {

//        Object o2 = marshalCopy(o, MediaType.APPLICATION_XML_TYPE);
//        if (!o.equals(o2))
//            throw new RuntimeException("Is equality defined on all objects? Some XML data may have failed to marshal. Expected " + o + " got " + o2);

        Object o3 = marshalCopy(o, MediaType.APPLICATION_JSON_TYPE);
        if (!o.equals(o3))
            throw new RuntimeException("Is equality defined on all objects? Some JSON data may have failed to marshal. Expected " + o + " got " + o3);
    }

    @SuppressWarnings(value = "unchecked")
    public static <T> T marshalCopy(T o, MediaType type) throws Exception {
        T result = null;
        if (type == MediaType.APPLICATION_JSON_TYPE) {
            result = marshalCopyJson(o);
        } else {
            if (type == MediaType.APPLICATION_XML_TYPE)
                result = marshalCopyWithXml(o);
            else
                throw new IllegalArgumentException("Media type '" + type + "'not supported");
        }
        return result;
    }

    @SuppressWarnings(value = "unchecked")
    private static <T> T marshalCopyJson(T o) throws IOException {
        JacksonJsonProvider provider = DtoUtils.getJsonProvider(false);
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        provider.writeTo(o, Object.class, o.getClass(), null, MediaType.APPLICATION_JSON_TYPE, null, out);

        InputStream in = new ByteArrayInputStream(out.toByteArray());
        return (T) provider.readFrom(Object.class, o.getClass(), null, MediaType.APPLICATION_JSON_TYPE, null, in);
    }

    @SuppressWarnings(value = "unchecked")
    private static <T> T marshalCopyWithXml(T o) throws JAXBException {
        JAXBContext jc = JAXBContext.newInstance(o.getClass());

        Marshaller marshaller = jc.createMarshaller();
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        marshaller.marshal(o, os);

        Unmarshaller um = jc.createUnmarshaller();

        return (T) um.unmarshal(new ByteArrayInputStream(os.toByteArray()));
    }
}
