package org.bongiorno.ws.core.client;

import org.bongiorno.misc.utils.WSCollections;
import org.bongiorno.ws.core.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.EnumMap;
import java.util.Map;

public class ErrorResponseErrorHandler extends DefaultResponseErrorHandler {

    private Map<MediaType, ErrorReader> errorReaderMap;

    private static Map<HttpStatus,Constructor<? extends WebserviceException>> STATUS_TO_EXCEPTION =
            new EnumMap<HttpStatus, Constructor<? extends WebserviceException>>(HttpStatus.class);
    static {
        try {
            STATUS_TO_EXCEPTION.put(BadRequestException.STATUS,findConstructor(BadRequestException.class));
            STATUS_TO_EXCEPTION.put(ConflictException.STATUS,findConstructor(ConflictException.class));
            STATUS_TO_EXCEPTION.put(ForbiddenException.STATUS,findConstructor(ForbiddenException.class));
            STATUS_TO_EXCEPTION.put(ResourceNotFoundException.STATUS,findConstructor(ResourceNotFoundException.class));
            STATUS_TO_EXCEPTION.put(ThirdPartyFailureException.STATUS,findConstructor(ThirdPartyFailureException.class));
            STATUS_TO_EXCEPTION.put(UnauthorizedException.STATUS,findConstructor(UnauthorizedException.class));
            STATUS_TO_EXCEPTION.put(UnprocessableEntityException.STATUS,findConstructor(UnprocessableEntityException.class));
            STATUS_TO_EXCEPTION.put(InternalServerException.STATUS, findConstructor(InternalServerException.class));
        }
        catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    private static <T> Constructor<T> findConstructor(Class<T> clazz) throws NoSuchMethodException {
        return clazz.getDeclaredConstructor(Map.class,String.class,Object[].class);
    }

    public ErrorResponseErrorHandler(Map<MediaType,ErrorReader> errorReaderMap, ErrorReader defaultErrorReader){
        this.errorReaderMap = WSCollections.defaultValueMap(errorReaderMap, defaultErrorReader);
    }

    public void handleError(ClientHttpResponse response) throws IOException {
        MediaType responseType = response.getHeaders().getContentType();
        HttpStatus statusCode = response.getStatusCode();
        String message = null;
        Map<String, String> details = null;

        if (responseType != null) {
            ErrorReader errorReader = errorReaderMap.get(responseType);
            ErrorResponse error = errorReader.readError(response.getBody());
            message = error.getMessage();
            details = error.getDetails();
        }
        if(message == null)
            message = statusCode.getReasonPhrase();


        WebserviceException exception = new WebserviceException(statusCode, details, "%s", message);

        Constructor<? extends WebserviceException> constructor = STATUS_TO_EXCEPTION.get(statusCode);
        try {
            if(constructor != null)
                exception = constructor.newInstance(details, "%s", new Object[] {message});
        }
        catch (Exception e) {
            // do nothing
        }

        throw exception;
    }
}
