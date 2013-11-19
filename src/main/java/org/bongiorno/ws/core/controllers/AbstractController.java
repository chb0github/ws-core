package org.bongiorno.ws.core.controllers;

import org.bongiorno.common.utils.OtherUtils;
import org.bongiorno.common.utils.WSCollections;
import org.bongiorno.ws.core.exceptions.BadRequestException;
import org.joda.time.DateTime;

import javax.annotation.PostConstruct;
import javax.ws.rs.Path;
import java.lang.reflect.Method;
import java.util.*;

/**
 * This class exists so we can get at the URL details of any controller. the Spring proxying hides
 * the @Path annotation from reflection. To get around that we had to roll some code that, on it's own is useful
 * and thusly, we expose it
 *
 */
public abstract class AbstractController {

    private String baseUrl;

    private Map<String, String> methodUrlMap = new HashMap<String, String>();
    private final Set<String> results = new HashSet<String>();

    @PostConstruct
    private void init() {
        Path rootPath = this.getClass().getAnnotation(Path.class);

        baseUrl = rootPath.value();

        Method[] methods = this.getClass().getMethods();
        for (Method method : methods) {
            Path p = method.getAnnotation(Path.class);
            if (p != null) {
                String path = p.value();
                methodUrlMap.put(method.getName(), path);
                results.add(String.format("%s%s", getBaseUrl(), path));
            }
        }
    }

    public Set<String> getExposedUrls() {
        return results;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public String getUrlForMethod(String methodName) {
        return methodUrlMap.get(methodName);
    }

    public String getUrlForMethod(Method method) {
        return getUrlForMethod(method.getName());
    }

    protected void checkDateRange(Date from, Date to) {
        if (to == null)
            throw new BadRequestException("you must supply a 'to' date");

        if (from == null)
            throw new BadRequestException("you must supply a 'from' date");

        if (to.before(from))
            throw new BadRequestException("'from' time must be BEFORE 'to'");

    }

    protected void checkDateRange(DateTime from, DateTime to) {
        if (to == null)
            throw new BadRequestException("you must supply a 'to' date");

        if (from == null)
            throw new BadRequestException("you must supply a 'from' date");

        if (to.isBefore(from))
            throw new BadRequestException("'from' time must be BEFORE 'to'");
    }

    protected void checkOptionalDateRange(DateTime from, DateTime to){
        Map<String, String> details = WSCollections.asMap(
                "from", "" + from,
                "to", "" + to);
        if (!OtherUtils.zeroOrAllNull(from, to)) {
            throw new BadRequestException(details, "'from' and 'to' must both be provided, or neither.");
        }
        if(to != null && to.isBefore(from)){
            throw new BadRequestException(details, "'from' must be BEFORE 'to'");
        }
    }
}
