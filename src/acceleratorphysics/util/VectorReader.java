package acceleratorphysics.util;

import java.io.Closeable;
import java.io.InputStream;
import java.io.DataInputStream;
import java.io.IOException;

/**
 * Reads Vector3 data from a binary file.
 */
public class VectorReader implements Closeable {

    private final DataInputStream inputStream;

    public VectorReader(InputStream inputStream){
        this.inputStream = new DataInputStream(inputStream);
    }

    public Vector3 readVector() throws IOException {
        return new Vector3(
                inputStream.readDouble(),
                inputStream.readDouble(),
                inputStream.readDouble()
        );
    }

    public void close() throws IOException{
        inputStream.close();
    }

    // TODO:  skip vector, mark, reset. (see DataInputStream)

}

