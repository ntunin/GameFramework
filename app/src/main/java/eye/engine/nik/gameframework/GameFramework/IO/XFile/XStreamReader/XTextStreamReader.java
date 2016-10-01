package eye.engine.nik.gameframework.GameFramework.IO.XFile.XStreamReader;

import eye.engine.nik.gameframework.GameFramework.IO.GameIOException;

/**
 * Created by Николай on 30.07.2016.
 */
public interface XTextStreamReader {
    public String getString(int length);
    public char getChar() throws GameIOException;
    public boolean hasNext();
    public int offset();
    public void skip(int relative);
}
