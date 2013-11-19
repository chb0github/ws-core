package org.bongiorno.ws.core.exceptions.mapping;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;


public class UnknownUserExceptionMapper extends AbstractExceptionMapper<AuthenticationCredentialsNotFoundException>  {

    @Override
    protected HttpStatus getHttpStatus(AuthenticationCredentialsNotFoundException exception) {
       return HttpStatus.UNAUTHORIZED;
    }

    @Override
    protected Class<AuthenticationCredentialsNotFoundException> getSupportedException() {
       return AuthenticationCredentialsNotFoundException.class;
    }
    
}
