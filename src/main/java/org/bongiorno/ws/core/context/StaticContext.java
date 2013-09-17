package org.bongiorno.ws.core.context;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import javax.annotation.PostConstruct;
import java.util.Properties;

public class StaticContext {

    private static boolean initialized = false;

    private static ApplicationContext staticContext;
    private static Properties properties;

    @Autowired
    private void setContext(ApplicationContext context) {
        staticContext = context;
    }

    @Autowired
    private void setProperties(QueryablePropertyPlaceholderConfigurer propertyPlaceholderConfigurer) {
        properties = propertyPlaceholderConfigurer.getProperties();
    }

    @PostConstruct
    private void postConstruct(){
        initialized = true;
    }

    public static boolean isInitialized(){
        return initialized;
    }

    public static Object getBean(String name) {
        return staticContext.getBean(name);
    }

    public static <T> T getBean(Class<T> type){
        return staticContext.getBean(type);
    }

    public static String getProperty(String name){
        String retVal = (String) properties.get(name);
        if(retVal == null){
           retVal = System.getProperty(name);
        }
        return retVal;
    }

    public static Properties getProperties(){
        return properties;
    }
}