package org.bongiorno.ws.core.exceptions.mapping;


import org.bongiorno.ws.core.server.RawResponseWriter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author cbongiorno
 *         Date: 5/17/12
 *         Time: 9:35 AM
 *         <p/>
 *         Because of the wild way that CXF dispatches REST providers (some kinda sort based on the generic type???)
 *         We need to install just 1 and take over the job. This class is for doing just that
 */
@Provider
public class DispatcherExceptionMapper implements ExceptionMapper<Throwable>, AuthenticationFailureHandler {

    private Map<Class<? extends Throwable>, AbstractExceptionMapper> lookupTable = new HashMap<Class<? extends Throwable>, AbstractExceptionMapper>();


    @SuppressWarnings("unchecked")
    public DispatcherExceptionMapper(Set<? extends AbstractExceptionMapper> mappers, AbstractExceptionMapper defaultMapper) {
        lookupTable.put(Throwable.class, defaultMapper);
        for (AbstractExceptionMapper mapper : mappers)
            lookupTable.put(mapper.getSupportedException(), mapper);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Response toResponse(Throwable exception) {
        AbstractExceptionMapper<Throwable> mapper = null;
        // check to see if any of the super types of this exception have a mapper
        for(Class c = exception.getClass(); mapper == null; c = c.getSuperclass()) {
            mapper = lookupTable.get(c);
        }

        lookupTable.put(exception.getClass(),mapper); // cache it
        //noinspection ConstantConditions -- Won't be null; Throwable is in the map
        return mapper.toResponse(exception);
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        RawResponseWriter.writeResponse(response, this.toResponse(exception), request.getHeaders("Accept"));
    }

    /**
     * Can I use this to add more beans to this mapper after it's been instantiated? I don't know
     * @param mapper the mapper to add/overwrite
     * @return any previous mapper
     */
    public AbstractExceptionMapper addMapper(AbstractExceptionMapper<?> mapper) {
        return lookupTable.put(mapper.getSupportedException(),mapper);
    }
}
