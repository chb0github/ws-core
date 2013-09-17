package org.bongiorno.ws.core.server.filters;

import org.bongiorno.ws.core.exceptions.ForbiddenException;
import org.bongiorno.ws.core.server.WebserviceFilter;
import org.bongiorno.ws.core.exceptions.ForbiddenException;
import org.bongiorno.ws.core.server.WebserviceFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Deny everything
 */
public class DenyFilter extends WebserviceFilter {

    @Override
    protected void doFilterWebserviceInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        throw new ForbiddenException("No soup for you! These are not the droids you're looking for. (Access forbidden. Permanently)");
    }
}
