package eye.engine.nik.gameframework.GameFramework.IO.XFile.XStreamReader;

import eye.engine.nik.gameframework.GameFramework.ERRNO;
import eye.engine.nik.gameframework.GameFramework.IO.GameIOException;

/**
 * Created by Николай on 30.07.2016.
 */
public class XStringStreamReader implements XTextStreamReader {
    protected StringBuilder stream;
    int index = 0;
    public XStringStreamReader(String stream) {
        this.stream = new StringBuilder(stream);
    }

    @Override
    public String getString(int length) {
        String result = stream.subSequence(index, index + length).toString();
        index+=length;
        return result;
    }

    @Override
    public char getChar() {
        try {
            return stream.charAt(index++);
        } catch (StringIndexOutOfBoundsException e) {
            ERRNO.write("Read out end of stream");
        }
        return '\10';
    }

    @Override
    public boolean hasNext() {
        return index < stream.length();
    }

    @Override
    public int offset() {
        return index;
    }

    @Override
    public void skip(int relative) {
        index += relative;
    }
}