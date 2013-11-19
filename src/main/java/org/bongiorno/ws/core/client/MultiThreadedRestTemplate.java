package org.bongiorno.ws.core.client;

import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.springframework.http.client.CommonsClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("deprecated")
public class MultiThreadedRestTemplate extends RestTemplate {

    private MultiThreadedHttpConnectionManager manager = new MultiThreadedHttpConnectionManager();

    public MultiThreadedRestTemplate() {
        super();
        CommonsClientHttpRequestFactory factory = new CommonsClientHttpRequestFactory();
        factory.getHttpClient().setHttpConnectionManager(manager);
        setRequestFactory(factory);
    }

    public void setMessageConverter(HttpMessageConverter converter) {
        List<HttpMessageConverter<?>> list = new ArrayList<HttpMessageConverter<?>>();
        list.add(converter);
        super.setMessageConverters(list);
    }

    public void setHttpConnectionManagerParams(HttpConnectionManagerParams param) {
        manager.setParams(param);
    }

}
