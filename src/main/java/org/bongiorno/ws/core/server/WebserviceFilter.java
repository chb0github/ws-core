package org.bongiorno.ws.core.server;

import org.bongiorno.ws.core.context.StaticContext;
import org.bongiorno.ws.core.exceptions.mapping.DispatcherExceptionMapper;
import org.bongiorno.ws.core.exceptions.mapping.EverythingElseExceptionMapper;
import org.bongiorno.ws.core.exceptions.mapping.WebServiceExceptionMapper;
import org.bongiorno.ws.core.exceptions.mapping.WebServiceExceptionMapper;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.ext.ExceptionMapper;
import java.io.IOException;
import java.util.Collections;

public abstract class WebserviceFilter extends OncePerRequestFilter {

    private ExceptionMapper<? super Exception> exceptionMapper;

    public WebserviceFilter(){
        if(StaticContext.isInitialized()){
            exceptionMapper = StaticContext.getBean(DispatcherExceptionMapper.class);
        }else {
            exceptionMapper = new DispatcherExceptionMapper(Collections.singleton(new WebServiceExceptionMapper()), new EverythingElseExceptionMapper());
        }
    }

    public WebserviceFilter(ExceptionMapper<? super Exception> exceptionMapper) {
        this.exceptionMapper = exceptionMapper;
    }

    @Override
    protected final void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try{
            doFilterWebserviceInternal(request, response, filterChain);
        }catch (Exception e){
            RawResponseWriter.writeResponse(response, exceptionMapper.toResponse(e), request.getHeaders("Accept"));
        }
    }

    protected abstract void doFilterWebserviceInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException;
}
