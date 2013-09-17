package org.bongiorno.ws.core.dto.support;

import org.codehaus.jackson.map.JsonDeserializer;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.introspect.Annotated;
import org.codehaus.jackson.map.introspect.NopAnnotationIntrospector;
import org.codehaus.jackson.xc.XmlAdapterJsonDeserializer;
import org.codehaus.jackson.xc.XmlAdapterJsonSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.lang.annotation.Annotation;

public class AdapterBeanAnnotationIntrospector extends NopAnnotationIntrospector {

    @Autowired
    private ApplicationContext context;

    @Override
    public boolean isHandled(Annotation ann) {
        return ann.annotationType() == AdapterBean.class;
    }

    @Override
    public JsonSerializer<?> findSerializer(Annotated am) {
        XmlAdapter<Object, Object> adapter = getAdapter(am);
        return adapter == null ? null : new XmlAdapterJsonSerializer(adapter);
    }

    @Override
    public JsonDeserializer<?> findDeserializer(Annotated am) {
        XmlAdapter<Object, Object> adapter = getAdapter(am);
        return adapter == null ? null : new XmlAdapterJsonDeserializer(adapter);
    }

    @SuppressWarnings("unchecked")
    private XmlAdapter<Object, Object> getAdapter(Annotated am){
        AdapterBean annotation = am.getAnnotation(AdapterBean.class);
        return annotation == null ? null : (XmlAdapter<Object, Object>) context.getBean(annotation.value());
    }
}
