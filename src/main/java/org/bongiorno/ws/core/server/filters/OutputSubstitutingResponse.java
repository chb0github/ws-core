package org.bongiorno.ws.core.server.filters;

import org.bongiorno.common.utils.io.ServletOutputStreamAdapter;
import org.bongiorno.common.utils.io.ServletOutputStreamAdapter;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;


public class OutputSubstitutingResponse extends HttpServletResponseWrapper {

    private int status;

    private ServletOutputStream outputStream;
    private PrintWriter writer;

    public OutputSubstitutingResponse(HttpServletResponse delegate, OutputStream out) throws ServletException {
        super(delegate);
        outputStream = new ServletOutputStreamAdapter(out);
        writer = new PrintWriter(outputStream);
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        return outputStream;
    }

    @Override
    public PrintWriter getWriter() throws IOException {
        return writer;
    }

    @Override
    public void setStatus(int sc, String sm) {
        status = sc;
        super.setStatus(sc, sm);
    }

    @Override
    public void setStatus(int sc) {
        status = sc;
        super.setStatus(sc);
    }

    public int getStatus(){
        return status;
    }
}