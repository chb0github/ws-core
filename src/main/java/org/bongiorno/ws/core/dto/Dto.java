package org.bongiorno.ws.core.dto;


import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;

import javax.ws.rs.core.MediaType;
import javax.xml.bind.*;
import javax.xml.namespace.QName;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.util.SortedMap;
import java.util.TreeMap;


public interface Dto {


    default public String toXml() throws JAXBException {
        return toXml(true);
    }


    /**
     * output this as XML
     *
     * @param format do you want it pretty formatted?
     * @return duh!
     */
    default String toXml(boolean format) throws JAXBException {
        return toXml(this, format);
    }

    default String toJson() {
        return toJson(true);
    }

    default String toJson(boolean format) {
        return toJson(this, format);
    }

    default String stylize(String xslt) throws TransformerException, JAXBException {
        String xmlIn = this.toXml(false);
        return stylize(xmlIn, xslt);
    }

    default String stylize(InputStream xsltStream) throws TransformerException, JAXBException, IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        toXml(this, baos, false);
        ByteArrayOutputStream styleOut = new ByteArrayOutputStream();
        stylize(new ByteArrayInputStream(baos.toByteArray()), xsltStream, styleOut);
        return styleOut.toString();
    }

    public static OutputStream stylize(InputStream xmlIn, InputStream xslt, OutputStream out) {
        Source styleSheet = new StreamSource(new BufferedInputStream(xslt));
        Source xmlInput = new StreamSource(new BufferedInputStream(xmlIn));
        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer(styleSheet);
            transformer.transform(xmlInput, new StreamResult(out));
        } catch (TransformerException e) {
            throw new RuntimeException(String.format("Unable to apply stylesheet to content %s", xmlIn), e);
        }
        return out;
    }

    public static String stylize(String xmlIn, String xslt) {
        // this should be in a provider discovery mechanism
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Source styleSheet = new StreamSource(new StringReader(xslt));
        Source xmlInput = new StreamSource(new StringReader(xmlIn));
        ByteArrayOutputStream messageBuffer = new ByteArrayOutputStream();
        try {
            Transformer transformer = transformerFactory.newTransformer(styleSheet);
            transformer.transform(xmlInput, new StreamResult(messageBuffer));
        } catch (TransformerException e) {
            throw new RuntimeException(String.format("Unable to apply stylesheet to content %s", xmlIn), e);
        }
        return messageBuffer.toString();
    }

    public static JacksonJaxbJsonProvider getJsonProvider(boolean format) {
        JacksonJaxbJsonProvider provider = new JacksonJaxbJsonProvider();
        provider.configure(SerializationFeature.INDENT_OUTPUT, format);
        provider.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        return provider;
    }


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


    public static String toXml(Object o, Boolean format) throws JAXBException {
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

    @java.lang.SuppressWarnings("unchecked")
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
                result = toXml(toLog, true, toLog.getClass().getSimpleName());
            } catch (JAXBException e) {
                logBuilder.append(e);
            }
            logBuilder.append(result).append('\n');
        }
    }

    public static String toJson(Object o, boolean format) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            toJson(o, out, format);
        } catch (IOException e) {
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

    public static String toJson(Object o) throws IOException {
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

    public static SortedMap<String, Object> fromJson(InputStream in) throws IOException {
        JacksonJaxbJsonProvider jsonProvider = getJsonProvider(false);
        return (SortedMap<String, Object>) jsonProvider.readFrom(Object.class, TreeMap.class, null, MediaType.APPLICATION_JSON_TYPE, null, in);
    }

    @SuppressWarnings("unchecked")
    public static <T> T fromXml(InputStream inputStream, Class<T> clazz) throws JAXBException {

        JAXBContext jc = JAXBContext.newInstance(clazz);
        Unmarshaller um = jc.createUnmarshaller();
        return (T) um.unmarshal(inputStream);
    }

}
