package org.bongiorno.ws.core.server.filters;

import org.bongiorno.common.utils.io.ServletInputStreamAdapter;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.IOException;
import java.io.InputStream;

public class InputSubstitutingRequest extends HttpServletRequestWrapper {

    private ServletInputStream substitute;

    public InputSubstitutingRequest(HttpServletRequest request,InputStream substitute) {
        super(request);
        this.substitute = new ServletInputStreamAdapter(substitute);
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        return substitute;
    }
}
