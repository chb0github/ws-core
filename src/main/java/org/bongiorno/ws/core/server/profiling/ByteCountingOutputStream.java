package org.bongiorno.ws.core.server.profiling;

import javax.servlet.ServletOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class ByteCountingOutputStream extends ServletOutputStream {

    private int byteCount = 0;
    private OutputStream delegate;

    public ByteCountingOutputStream(OutputStream out){
        delegate = out;
    }

    @Override
    public void write(int b) throws IOException {
        ++byteCount;
        delegate.write(b);
    }

    @Override
    public void write(byte[] b, int off, int len) throws java.io.IOException {
        byteCount += len;
        delegate.write(b, off, len);
    }

    @Override
    public void flush() throws IOException {
        delegate.flush();
    }

    @Override
    public void close() throws IOException {
        delegate.close();
    }

    public int getByteCount(){
        return byteCount;
    }
}
