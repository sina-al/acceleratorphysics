package acceleratorphysics.util;

import java.io.Closeable;
import java.io.Flushable;
import java.io.IOException;
import java.io.DataOutputStream;

import java.io.OutputStream;


/**
 * Writes Vector3 data to a binary file
 */
public class VectorWriter implements Closeable, Flushable {

    private final DataOutputStream outputStream;
    private int vectorsWritten;

    public VectorWriter(OutputStream outputStream) {
        this.outputStream = new DataOutputStream(outputStream);
    }

    public void close() throws IOException {
        outputStream.close();
    }

    public void flush() throws IOException {
        outputStream.flush();
    }

    public void writeVector(Vector3 v) throws IOException {
        outputStream.writeDouble(v.getX());
        outputStream.writeDouble(v.getY());
        outputStream.writeDouble(v.getZ());
        vectorsWritten++;
    }

    public int size(){
        return vectorsWritten;
    }

}
