package org.bongiorno.ws.core.server;

import org.bongiorno.common.utils.functions.CsvToList;
import org.bongiorno.ws.core.dto.DtoUtils;
import org.bongiorno.common.utils.functions.CsvToList;
import org.bongiorno.ws.core.dto.DtoUtils;
import org.springframework.http.HttpStatus;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class RawResponseWriter {

    private static Map<String, ErrorHandler> errorHandlers;
    private static ErrorHandler defaultErrorHandler;
    static {
        errorHandlers = new HashMap<String, ErrorHandler>();
        errorHandlers.put(MediaType.APPLICATION_XML, new XmlErrorHandler());
        errorHandlers.put(MediaType.APPLICATION_JSON, new JsonErrorHandler());
        errorHandlers.put(MediaType.TEXT_PLAIN, new TextErrorHandler());
        defaultErrorHandler = errorHandlers.get(MediaType.TEXT_PLAIN);
    }

    public static void writeResponse(HttpServletResponse response, Response msg, Enumeration acceptHeaders) throws IOException {
        ErrorHandler errorHandler = getErrorHandler(acceptHeaders);
        errorHandler.sendError(response, msg);
    }

    private static ErrorHandler getErrorHandler(Enumeration acceptHeaders) {
        List<String> acceptedTypes = parseAcceptedTypes(acceptHeaders);
        ErrorHandler errorHandler = null;
        for(int i = 0; errorHandler == null && i < acceptedTypes.size(); ++i){
            errorHandler = errorHandlers.get(acceptedTypes.get(i));
        }
        if( errorHandler == null ){
            errorHandler = defaultErrorHandler;
        }
        return errorHandler;
    }

    private static List<String> parseAcceptedTypes(Enumeration acceptHeaders){
        List<String> result = new ArrayList<String>();

        while(acceptHeaders.hasMoreElements()){
            result.addAll(CsvToList.transform(acceptHeaders.nextElement().toString()));
        }

        return result;
    }


    private static interface ErrorHandler {
        public void sendError(HttpServletResponse response, Response msg) throws IOException;
    }

    private static class XmlErrorHandler implements ErrorHandler {
        @Override
        public void sendError(HttpServletResponse response, Response msg) throws IOException {
            response.setStatus(msg.getStatus());
            response.setContentType(MediaType.APPLICATION_XML);
            ServletOutputStream outputStream = response.getOutputStream();
            try {
                DtoUtils.toXml(msg.getEntity(), outputStream);
            } catch (JAXBException e) {
                throw new IOException(e);
            }
            outputStream.flush();
        }
    }

    private static class JsonErrorHandler implements ErrorHandler {
        @Override
        public void sendError(HttpServletResponse response, Response msg) throws IOException {
            response.setStatus(msg.getStatus());
            response.setContentType(MediaType.APPLICATION_JSON);
            ServletOutputStream outputStream = response.getOutputStream();
            DtoUtils.toJson(msg.getEntity(), outputStream);
            outputStream.flush();
        }
    }

    private static class TextErrorHandler implements ErrorHandler {
        @Override
        public void sendError(HttpServletResponse response, Response msg) throws IOException {
            int statusCode = msg.getStatus();
            response.setStatus(statusCode);
            response.setContentType(MediaType.TEXT_PLAIN);

            PrintWriter writer = response.getWriter();
            writer.printf("%d %s: %s", statusCode, HttpStatus.valueOf(statusCode).getReasonPhrase(), msg.getEntity().toString());
            writer.flush();
        }
    }
}
