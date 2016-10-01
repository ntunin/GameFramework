package eye.engine.nik.gameframework.GameFramework.IO.XFile.XStreamReader;

import eye.engine.nik.gameframework.GameFramework.IO.GameIOException;

/**
 * Created by Николай on 30.07.2016.
 */
public interface XBinStreamReader {
    public String getString(int length);

    public int getInt() throws GameIOException;
    public short getShort() throws GameIOException;
    public char getChar() throws GameIOException;
    public Byte getByte() throws GameIOException;

    public double getDouble() throws GameIOException;
    public float getFloat() throws GameIOException;
    public boolean hasNext();
}
