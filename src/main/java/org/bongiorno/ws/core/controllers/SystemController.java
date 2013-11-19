package org.bongiorno.ws.core.controllers;

import org.bongiorno.common.utils.Function;
import org.bongiorno.common.utils.WSCollections;
import org.bongiorno.ws.core.context.StaticContext;
import org.apache.commons.lang.time.DurationFormatUtils;
import org.apache.cxf.annotations.GZIP;
import org.apache.cxf.common.util.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import java.util.*;

import static org.bongiorno.common.utils.WSCollections.delimitedMap;

@Service
@Path("/system")
@Produces({MediaType.TEXT_PLAIN})
@GZIP(threshold = 60)
public class SystemController extends AbstractController {

    @Autowired
    StatusReporter statusReporter;

    @Value("${app.version}")
    protected String version;

    @Value("${app.name}")
    private String appName;

//    @Value("${app.build.date}")
//    protected String buildDate;

    protected final DateTime startTime = new DateTime();

    protected String status;

    private Logger log = LoggerFactory.getLogger(getClass());

    private Function<Object, Boolean> sensitivePropertyFunction;

    private SystemVerifier verifier;

    public SystemController(Function<Object, Boolean> sensitivePropertyFunction, SystemVerifier verifier) {
        this.sensitivePropertyFunction = sensitivePropertyFunction;
        this.verifier = verifier;
    }

    @Autowired
    private ApplicationContext context;

    @PostConstruct
    public void init() {
        Map<Object, Object> statusMap = WSCollections.delimitedMap(new LinkedHashMap<>(), ": ", "\n");
        statusMap.put("App name", appName);
        statusMap.put("Version", version);
        //This is the format that comes from maven.  Make it match StartTime.
//        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyyMMdd-HHmm");
//        statusMap.put("Build Date", formatter.parseDateTime(buildDate).toLocalDate());
        statusMap.put("Start time", startTime);
        status = statusMap.toString();
        log.info("{} version {} online", appName, version);
        statusReporter.sendStatusMessage(appName, version, "startup");
    }

    @PreDestroy
    public void goodbye() {
        log.info("{} version {} going offline", appName, version);
        statusReporter.sendStatusMessage(appName, version, "shutdown");
    }

    private Collection<String> getUrls() {
        Collection<String> urls = new TreeSet<>();

        for (AbstractController bean : context.getBeansOfType(AbstractController.class).values()) {
            urls.addAll(bean.getExposedUrls());
        }
        return urls;
    }

    @GET
    @Path("/status")
    public String getStatus() {
        return status;
    }

    public String getUptime() {
        return DurationFormatUtils.formatDurationWords(System.currentTimeMillis() - startTime.getMillis(), true, false);
    }

    @GET
    @Path("/urls")
    public String getUrlString() {
        return WSCollections.delimitedCollection(getUrls(), "\n").toString();
    }


    @GET
    @Path("/version")
    public String getVersion() {
        return version;
    }

    @GET
    @Path("/properties")
    public String getSystemProperties() {
        SortedMap<String, String> appProperties = maskProperties((Map) StaticContext.getProperties());

        SortedMap<String, String> sysProps = maskProperties((Map) System.getProperties());

        String appResults = delimitedMap(appProperties, ": ", "\n\t").toString();
        String sysResults = delimitedMap(sysProps, ": ", "\n\t").toString();

        StringBuilder builder = new StringBuilder();
        builder.append("appzone.properties:\n\t");
        builder.append(appResults);
        builder.append("\n\n");
        builder.append("System properties:\n\t");
        builder.append(sysResults);

        return builder.toString();
    }

    private SortedMap<String, String> maskProperties(Map<String, String> properties) {
        SortedMap<String, String> retVal = new TreeMap<>(properties);
        Collection<String> toMask = WSCollections.findAll(retVal.keySet(), sensitivePropertyFunction);
        for (String key : toMask) {
            if (!StringUtils.isEmpty(retVal.get(key))) {
                retVal.put(key, "**********");
            }
        }
        return retVal;
    }


    @POST
    @Path("/test")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Object selfTest(MultivaluedMap<String, String> params) {
        return verifier.execute(params);
    }

    public static interface StatusReporter {
        public void sendStatusMessage(String appName, String version, String status);
    }

    public static class NoOpStatusReporter implements StatusReporter {
        @Override
        public void sendStatusMessage(String appName, String version, String status) {
            // This space intentionally left blank
        }
    }
}
