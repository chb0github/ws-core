package org.bongiorno.ws.core.client;

import org.apache.commons.collections4.Transformer;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.bongiorno.misc.utils.Function;
import org.bongiorno.ws.core.client.headers.CompositeHeader;
import org.bongiorno.ws.core.client.headers.NoOpHeader;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestClientException;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 *         <p/>
 *         a delegate to other RestOperations with some bonuses: Allows for the embedding of configuration
 *         necessary for the client call (example: default URI paramters not necessarily call specific)
 *         and, accepts a relative URL (only) when the baseUrl is set. It is assumed that the only thing that changes per
 *         client call is the relative path.
 *         <p/>
 *         Operations that take a URL/URI class as input are NOT treated as above and allow you to still circumvent
 *         the relative address feature.
 *         <p/>
 *         uri default variables (as configured) will be overwritten by values passed in
 */
public class RelativeRestOperations extends MultiThreadedRestTemplate {


    private Map<String, Object> defaultUriVariables = new HashMap<String, Object>();

    private String baseUrl;

    private RequestCallback headerStrategy = new NoOpHeader();


    private Map<Class, Function> paramConverters = new HashMap< >();


    public RelativeRestOperations() {
    }

    public RelativeRestOperations(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public RelativeRestOperations(String baseUrl, RequestCallback headerStrategy) {
        this.baseUrl = baseUrl;
        this.headerStrategy = headerStrategy;
    }

    public RelativeRestOperations(String baseUrl, RequestCallback headerStrategy, ResponseErrorHandler errorHandler, HttpConnectionManagerParams connectionManagerParams) {
        this.baseUrl = baseUrl;
        this.headerStrategy = headerStrategy;
        this.setErrorHandler(errorHandler);
        this.setHttpConnectionManagerParams(connectionManagerParams);
    }

    public RelativeRestOperations(String baseUrl, RequestCallback headerStrategy, ResponseErrorHandler errorHandler,
                                  HttpConnectionManagerParams connectionManagerParams, Map<Class, Function> paramConverters) {
        this.baseUrl = baseUrl;
        this.paramConverters = paramConverters;
        this.headerStrategy = headerStrategy;
        this.setErrorHandler(errorHandler);
        this.setHttpConnectionManagerParams(connectionManagerParams);
    }

    private Map<String, ?> getUriVariables(Map<String, ?> uriVariables) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.putAll(defaultUriVariables);
        map.putAll(uriVariables);
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            Object value = entry.getValue();
            Function<Object, Object> converter = getConverter(value);
            entry.setValue(converter == null ? value : converter.apply(value));
        }
        return map;
    }

    public <T> Function<T, String> addConverter(Class<T> type, Function<T, String> converter) {
        return paramConverters.put(type, converter);
    }

    public void setParamConverters(Map<Class, Function> converters) {
        this.paramConverters = converters;
    }

    private void checkUrl(String url) {
        if (url.startsWith("http"))
            throw new IllegalArgumentException("Please pass urls relative to " + baseUrl);
    }

    @Override
    public <T> T getForObject(String url, Class<T> responseType, Map<String, ?> urlVariables) throws RestClientException {
        return super.getForObject(url, responseType, getUriVariables(urlVariables));
    }

    @Override
    public <T> ResponseEntity<T> getForEntity(String url, Class<T> responseType, Map<String, ?> urlVariables) throws RestClientException {
        return super.getForEntity(url, responseType, getUriVariables(urlVariables));
    }

    @Override
    public HttpHeaders headForHeaders(String url, Map<String, ?> urlVariables) throws RestClientException {
        return super.headForHeaders(url, getUriVariables(urlVariables));
    }

    @Override
    public URI postForLocation(String url, Object request, Map<String, ?> urlVariables) throws RestClientException {
        return super.postForLocation(url, request, getUriVariables(urlVariables));
    }

    @Override
    public <T> T postForObject(String url, Object request, Class<T> responseType, Map<String, ?> uriVariables) throws RestClientException {
        return super.postForObject(url, request, responseType, getUriVariables(uriVariables));
    }

    public <T> T postForObject(Object request, Class<T> responseType, Object... uriVariables) throws RestClientException {
        return postForObject("", request, responseType, uriVariables);
    }

    @Override
    public void put(String url, Object request, Map<String, ?> urlVariables) throws RestClientException {
        super.put(url, request, getUriVariables(urlVariables));
    }

    public <T> T putForObject(String url, Object request, Class<T> responseType, Object... uriVariables) {
        ResponseEntity<T> entity = super.exchange(url, HttpMethod.PUT, new HttpEntity(request), responseType, uriVariables);
        return entity.getBody();
    }

    @Override
    public void delete(String url, Map<String, ?> urlVariables) throws RestClientException {
        super.delete(url, getUriVariables(urlVariables));
    }

    @Override
    public Set<HttpMethod> optionsForAllow(String url, Map<String, ?> urlVariables) throws RestClientException {
        return super.optionsForAllow(url, getUriVariables(urlVariables));
    }

    @Override
    public <T> ResponseEntity<T> exchange(String url, HttpMethod method, HttpEntity<?> requestEntity, Class<T> responseType, Map<String, ?> uriVariables) throws RestClientException {
        return super.exchange(url, method, requestEntity, responseType, getUriVariables(uriVariables));
    }

    @Override
    public <T> T execute(String relativeUrl, HttpMethod method, RequestCallback requestCallback, ResponseExtractor<T> responseExtractor, Map<String, ?> urlVariables) throws RestClientException {

        checkUrl(relativeUrl);
        return super.execute(baseUrl + relativeUrl, method, new CompositeHeader(requestCallback, headerStrategy), responseExtractor, getUriVariables(urlVariables));
    }

    @Override
    public <T> T execute(String relativeUrl, HttpMethod method, RequestCallback requestCallback, ResponseExtractor<T> responseExtractor, Object... uriVariables) throws RestClientException {
        checkUrl(relativeUrl);

        uriVariables = convertTypes(uriVariables);
        return super.execute(baseUrl + relativeUrl, method, new CompositeHeader(requestCallback, headerStrategy), responseExtractor, uriVariables);
    }

    private Object[] convertTypes(Object[] in) {
        Object[] copy = new Object[in.length];

        for (int i = 0; i < in.length; i++) {
            Object o = in[i];
            if (o != null) {
                Function converter = getConverter(o);
                copy[i] = (converter == null ? o : converter.apply(o));
            }
        }

        return copy;
    }

    private Function getConverter(Object o) {
        Function converter = null;
        if (o != null) {
            for (Class c = o.getClass(); converter == null && c != Object.class; c = c.getSuperclass()) {
                converter = paramConverters.get(c);
            }
            paramConverters.put(o.getClass(), converter);
        }
        return converter;
    }

    public Map<String, Object> getDefaultUriVariables() {
        return defaultUriVariables;
    }

    public void setDefaultUriVariables(Map<String, Object> defaultUriVariables) {
        this.defaultUriVariables = defaultUriVariables;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public RequestCallback getHeaderStrategy() {
        return headerStrategy;
    }

    public void setHeaderStrategy(RequestCallback headerStrategy) {
        this.headerStrategy = headerStrategy;
    }

    @Override
    public String toString() {
        return "" + baseUrl;
    }

}
