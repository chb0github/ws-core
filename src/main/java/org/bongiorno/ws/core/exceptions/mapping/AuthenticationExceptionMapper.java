package org.bongiorno.ws.core.exceptions.mapping;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;

public class AuthenticationExceptionMapper extends AbstractExceptionMapper<AuthenticationException>{

    @Override
    protected HttpStatus getHttpStatus(AuthenticationException exception) {
        return HttpStatus.UNAUTHORIZED;
    }

    @Override
    protected Class<AuthenticationException> getSupportedException() {
        return AuthenticationException.class;
    }
}
