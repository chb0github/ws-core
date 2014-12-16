package org.bongiorno.ws.core.dto.support;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.introspect.NopAnnotationIntrospector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.io.IOException;

public class AdapterBeanAnnotationIntrospector extends NopAnnotationIntrospector {
    @Autowired
    private ApplicationContext context;
    @Override
    public JsonSerializer<?> findSerializer(Annotated am) {
        XmlAdapter<String, Object> adapter = getAdapter(am);
        return adapter == null ? null : new AdaptedJsonSerializer<>(adapter);
    }
    @Override
    public JsonDeserializer<?> findDeserializer(Annotated am) {
        XmlAdapter<String, Object> adapter = getAdapter(am);
        return adapter == null ? null : new AdaptedJsonDeserializer<>(adapter);
    }
    @SuppressWarnings("unchecked")
    private XmlAdapter<String, Object> getAdapter(Annotated am) {
        AdapterBean annotation = am.getAnnotation(AdapterBean.class);
        return annotation == null ? null : (XmlAdapter<String, Object>) context.getBean(annotation.value());
    }
    public static class AdaptedJsonDeserializer<T> extends JsonDeserializer<T> {
        private XmlAdapter<String, T> xmlAdapter;
        public AdaptedJsonDeserializer(XmlAdapter<String, T> xmlAdapter) {
            this.xmlAdapter = xmlAdapter;
        }
        @Override
        public T deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
            String strValue = jp.getValueAsString();
            try {
                return xmlAdapter.unmarshal(strValue);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
    public static class AdaptedJsonSerializer<T> extends JsonSerializer<T> {
        private XmlAdapter<String, T> xmlAdapter;
        public AdaptedJsonSerializer(XmlAdapter<String, T> xmlAdapter) {
            this.xmlAdapter = xmlAdapter;
        }
        @Override
        public void serialize(T value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
            try {
                String stringValue = xmlAdapter.marshal(value);
                jgen.writeString(stringValue);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}