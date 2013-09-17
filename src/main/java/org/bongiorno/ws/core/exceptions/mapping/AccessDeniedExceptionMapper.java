/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.bongiorno.ws.core.exceptions.mapping;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;

/**
 *
 * @author vlazoryshyn
 */
public class AccessDeniedExceptionMapper extends AbstractExceptionMapper<AccessDeniedException> {

    @Override
    protected HttpStatus getHttpStatus(AccessDeniedException exception) {
        return HttpStatus.FORBIDDEN;
    }

    @Override
    protected Class<AccessDeniedException> getSupportedException() {
        return AccessDeniedException.class;
    }
    
}
