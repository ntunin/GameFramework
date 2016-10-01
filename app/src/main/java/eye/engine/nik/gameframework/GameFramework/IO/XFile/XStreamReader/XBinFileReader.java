package eye.engine.nik.gameframework.GameFramework.IO.XFile.XStreamReader;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.HashMap;
import java.util.Map;

import eye.engine.nik.gameframework.GameFramework.IO.GameIOException;

/**
 * Created by nik on 19.06.16.
 */
public class XBinFileReader implements XTextStreamReader, XBinStreamReader {
    private ByteBuffer in;
    public XBinFileReader(byte[] buffer) {
        in = ByteBuffer.wrap(buffer);
    }
    public XBinFileReader(String path) throws GameIOException {
        try {
            File f = new File(path);
            byte[] buffer = new byte[(int)f.length()];
            InputStream stream = new FileInputStream(f);
            stream.read(buffer);
            stream.close();
            in = ByteBuffer.wrap(buffer);
            in.order(ByteOrder.LITTLE_ENDIAN);

        } catch (FileNotFoundException e) {
            throw new GameIOException("File not found");
        } catch (IOException e) {
            throw new GameIOException("Cannot read file");
        }
    }
    public String getString(int length) {
        byte[] chars = new byte[length];
        in.get(chars, 0, length);
        return new String(chars);
    }

    public int getInt() throws GameIOException {
        return (Integer) get("Int");
    }
    public short getShort() throws GameIOException {
        return (Short) get("Short");
    }
    public char getChar() throws GameIOException {
        return (Character) get("Char");
    }
    public Byte getByte() throws GameIOException {
        return (Byte) get("");
    }

    public double getDouble() throws GameIOException {
        return (Double) get("Double");
    }
    public float getFloat() throws GameIOException {
        return (Float) get("Float");
    }
    public boolean hasNext() {
        return in.hasRemaining();
    }

    @Override
    public int offset() {
        return 0;
    }

    @Override
    public void skip(int relative) {

    }

    private Object get(String type) throws GameIOException {
        String methodName = getMethodName(type);
        Method method = null;
        try {
            method = in.getClass().getMethod(methodName);
            method.setAccessible(true);
            return method.invoke(in);
        } catch (NoSuchMethodException e) {
            doNotResolve(methodName);
        } catch (InvocationTargetException e) {
            doNotResolve(methodName);
        } catch (IllegalAccessException e) {
            doNotResolve(methodName);
        }
        return null;
    }
    private String getMethodName(String type) {
        return String.format("getReader%s", type);
    }
    private void doNotResolve(String methodName) throws GameIOException {
        throw new GameIOException(String.format("Cannot resolve method %s", methodName));
    }

}
