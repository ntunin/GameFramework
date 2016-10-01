package eye.engine.nik.gameframework.GameFramework.IO.XFile.XValueReader;

import java.util.ArrayList;

import eye.engine.nik.gameframework.GameFramework.Delegate;
import eye.engine.nik.gameframework.GameFramework.DelegateException;
import eye.engine.nik.gameframework.GameFramework.IO.GameIOException;
import eye.engine.nik.gameframework.GameFramework.IO.XFile.XStreamReader.XTextStreamReader;
import eye.engine.nik.gameframework.GameFramework.IO.XFile.XWatcher;

/**
 * Created by nikolay on 11.08.16.
 */
public class XArrayReader extends XValueReader{
    private XValueReader itemReader;
    private int size;
    private Array array;

    public XArrayReader() throws GameIOException {
        Delegate closeArray;
        watchers = new ArrayList<>();
        try {
            closeArray = new Delegate(this, "closeArray");
            watchers.add(new XWatcher(",", closeArray));
            watchers.add(new XWatcher(";", closeArray));
        } catch (DelegateException e) {
            throw new GameIOException("XArrayRerader.closeArray: internal delegate exception");
        }

    }


    @Override
    public String getType() {
        return "array";
    }

    @Override
    public Object readValue(XTextStreamReader reader) throws GameIOException {
        String type = itemReader.getType();
        switch (type) {
            case "WORD": {
                array = new ShortArray(size);
                break;
            }
            case "DWORD": {
                array = new IntArray(size);
                break;
            }
            case "FLOAT": {
                array = new FloatArray(size);
                break;
            }
            default: {
                array = new ObjectArray(size);
                break;
            }
        }

        for(int i = 0; i < size; i++) {
            Object value = itemReader.readValue(reader, ",", ";");
            array.addValue(value);
        }
        return array;
    }

    public void setItemReader(XValueReader itemReader) {
        this.itemReader = itemReader;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void closeArray() throws GameIOException {
        builder.stop();
    }

}
