package org.bongiorno.ws.core.dto;

import org.bongiorno.ws.core.context.StaticContext;
import org.codehaus.jackson.jaxrs.JacksonJsonProvider;
import org.codehaus.jackson.map.AnnotationIntrospector;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.introspect.JacksonAnnotationIntrospector;
import org.codehaus.jackson.xc.JaxbAnnotationIntrospector;

import javax.ws.rs.core.MediaType;
import javax.xml.bind.*;
import javax.xml.namespace.QName;
import java.io.*;

/**
 */
public final class DtoUtils {


    /**
     * @param o      the @XmlRootElement object to output
     * @param format format the output?
     * @return a String representation as it could be expected to appear on the wire
     * @throws javax.xml.bind.JAXBException if there was a problem with the marshalling
     */
    public static String toXml(Dto o, Boolean format) throws JAXBException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        toXml(o, out, format);
        return out.toString();
    }

    /**
     * @param o the @XmlRootElement object to output
     * @return a String representation as it could be expected to appear on the wire
     * @throws JAXBException if there was a problem with the marshalling
     */
    public static String toXml(Dto o) throws JAXBException {
        return toXml(o, false);
    }

    public static void toXml(Object o, OutputStream os) throws JAXBException {
        toXml(o, os, false);
    }

    public static void toXml(Object o, OutputStream os, Boolean format) throws JAXBException {
        JAXBContext jc = JAXBContext.newInstance(o.getClass());
        Marshaller marshaller = jc.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, format);
        marshaller.marshal(o, os);
    }

    public static String toXml(Object o, Boolean format, String rootElementName) throws JAXBException {
        JAXBContext jc = JAXBContext.newInstance(o.getClass());
        Marshaller marshaller = jc.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, format);
        JAXBElement rootElement = new JAXBElement(new QName(rootElementName), o.getClass(), o);
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        marshaller.marshal(rootElement, os);
        return os.toString();
    }

    public static void toXmlSafe(Object toLog, StringBuilder logBuilder) {
        if (toLog != null) {
            String result = null;
            try {
                result = DtoUtils.toXml(toLog, true, toLog.getClass().getSimpleName());
            } catch (JAXBException e) {
                logBuilder.append(e);
            }
            logBuilder.append(result).append('\n');
        }
    }

    public static String toJson(Dto o, boolean format) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try{
            toJson(o, out, format);
        }catch (IOException e){
            // This shouldn't happen with a ByteArrayOutputStream
            throw new RuntimeException(e);
        }
        return out.toString();
    }

    public static void toJson(Object o, OutputStream out) throws IOException {
        toJson(o, out, false);
    }

    public static void toJson(Object o, OutputStream out, boolean format) throws IOException {
        JacksonJsonProvider jsonProvider = getJsonProvider(format);

        jsonProvider.writeTo(o, Object.class, o.getClass(), null, MediaType.APPLICATION_JSON_TYPE, null, out);
    }

    public static String toJson(Dto o) throws IOException {
        return toJson(o, false);
    }

    @SuppressWarnings("unchecked")
    public static <T> T fromJson(String in, Class<T> resultClass) throws IOException {
        return fromJson(new ByteArrayInputStream(in.getBytes()), resultClass);
    }

    @SuppressWarnings("unchecked")
    public static <T> T fromJson(InputStream in, Class<T> resultClass) throws IOException {
        JacksonJsonProvider jsonProvider = getJsonProvider(false);

        return (T) jsonProvider.readFrom(Object.class, resultClass, null, MediaType.APPLICATION_JSON_TYPE, null, in);
    }

    @SuppressWarnings("unchecked")
    public static <T> T fromXml(InputStream inputStream, Class<T> clazz) throws JAXBException {

        JAXBContext jc = JAXBContext.newInstance(clazz);
        Unmarshaller um = jc.createUnmarshaller();
        return (T) um.unmarshal(inputStream);
    }

    public static JacksonJsonProvider getJsonProvider(boolean format) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationConfig.Feature.INDENT_OUTPUT, format);
        return new JacksonJsonProvider(objectMapper);
    }

}
