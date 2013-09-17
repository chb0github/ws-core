package org.bongiorno.ws.core.context;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import java.io.IOException;
import java.util.Properties;

public class QueryablePropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer{

    private Properties properties;

    @Override
    protected Properties mergeProperties() throws IOException {
        properties = super.mergeProperties();
        return properties;
    }

    public Properties getProperties(){
        return properties;
    }
}
