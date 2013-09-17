package org.bongiorno.common.esper;

import com.espertech.esper.client.*;
import org.bongiorno.common.esper.events.MethodCalledEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@org.springframework.context.annotation.Configuration
public class EsperConfig {

    @Bean(destroyMethod = "destroy")
    public EPServiceProvider getEPServiceProvider(ApplicationContext context) {
        Configuration configuration = new Configuration();
        configuration.addEventType("MethodCalledEvent", MethodCalledEvent.class);

        return EPServiceProviderManager.getProvider(context.getId(), configuration);
    }

    @Bean
    public EPRuntime getEPRuntime(EPServiceProvider provider) {
        return provider.getEPRuntime();
    }

    @Bean(name = "esperAdmin")
    public EPAdministrator getEPAdministrator(EPServiceProvider provider) {
        return provider.getEPAdministrator();
    }
}
