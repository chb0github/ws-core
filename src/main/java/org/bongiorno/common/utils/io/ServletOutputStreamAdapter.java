package org.bongiorno.common.utils.io;

import javax.servlet.ServletOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class ServletOutputStreamAdapter extends ServletOutputStream {

    private OutputStream delegate;

    public ServletOutputStreamAdapter(OutputStream delegate) {
        this.delegate = delegate;
    }

    public void close() throws IOException {
        delegate.close();
    }

    public void write(byte[] b) throws IOException {
        delegate.write(b);
    }

    public void flush() throws IOException {
        delegate.flush();
    }

    public void write(int b) throws IOException {
        delegate.write(b);
    }

    public void write(byte[] b, int off, int len) throws IOException {
        delegate.write(b, off, len);
    }
}
