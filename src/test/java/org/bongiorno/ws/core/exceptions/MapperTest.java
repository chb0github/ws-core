package org.bongiorno.ws.core.exceptions;


import org.bongiorno.ws.core.exceptions.mapping.*;
import org.junit.Test;
import org.springframework.http.HttpStatus;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static junit.framework.Assert.assertEquals;

public class MapperTest {
    @Test
    public void testExceptionMapper() throws Exception {
        WebServiceExceptionMapper mapper = new WebServiceExceptionMapper();
        ResourceNotFoundException rnfe = new ResourceNotFoundException("test");
        Response r = mapper.toResponse(rnfe);
        assertEquals(HttpStatus.NOT_FOUND.value(),r.getStatus());
        assertEquals("test",((ErrorResponse)r.getEntity()).getMessage());
    }

    @Test
    public void testHandlesErrors() throws Exception {
        Set<AbstractExceptionMapper> mappers = new HashSet<AbstractExceptionMapper>(Arrays.asList(new WebServiceExceptionMapper(), new WebApplicationExceptionMapper()));
        DispatcherExceptionMapper mapper = new DispatcherExceptionMapper(mappers, new EverythingElseExceptionMapper());
        Error rnfe = new InstantiationError("test");
        Response r = mapper.toResponse(rnfe);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(),r.getStatus());
        assertEquals("Internal Server Error",((ErrorResponse)r.getEntity()).getMessage());
    }

    @Test
    public void testUnsupportedExceptionCheck() throws Exception {

        EverythingElseExceptionMapper mapper = new EverythingElseExceptionMapper();
        WebApplicationException waex = new WebApplicationException(new RuntimeException());
        Response r = mapper.toResponse(waex);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(),r.getStatus());
    }
}
