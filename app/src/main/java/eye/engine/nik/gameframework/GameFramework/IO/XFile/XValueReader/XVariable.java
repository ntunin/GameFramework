package eye.engine.nik.gameframework.GameFramework.IO.XFile.XValueReader;

import android.util.TypedValue;

import eye.engine.nik.gameframework.GameFramework.IO.GameIOException;
import eye.engine.nik.gameframework.GameFramework.IO.XFile.XStreamReader.XTextStreamReader;
import eye.engine.nik.gameframework.GameFramework.IO.XFile.XWatchBatcher;

/**
 * Created by nikolay on 12.08.16.
 */
public class XVariable implements XTypedItem{
    private String name;
    protected XValueReader valueReader;
    private Object value;
    private XVariableCreator variableCreator = new XVariableCreator();
    private boolean dynamic;
    private XWatchBatcher builder;

    public XVariable() {

    }
    public XVariable(XVariable proto) {
        this.name = proto.name;
        this.valueReader = proto.valueReader;
    }
    public XVariable(XValueReader valueReader, String name) {
        this.valueReader = valueReader;
        setName(name);
    }
    public XVariable(XValueReader valueReader, XTextStreamReader reader) throws GameIOException {
        init(valueReader, reader);
    }
    protected void init(XValueReader valueReader, XTextStreamReader reader) throws GameIOException {
        this.valueReader = valueReader;
        new XNameReader(reader, this, ";");
    }
    public XVariable(XValueReader valueReader, XTextStreamReader reader, boolean dynamic) throws GameIOException {
        this.dynamic = dynamic;
        this.valueReader = valueReader;
        new XNameReader(reader, this, (dynamic)? "{": ";");
    }
    public XVariable(XValueReader valueReader, XTextStreamReader reader, String terminator) throws GameIOException {
        this.valueReader = valueReader;
        XNameReader nameReader = new XNameReader(reader, this, terminator);
    }
    public void handleName(String name) throws GameIOException {
        setName(name);
    }
    public void setName(String name)  {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Object readValue(XTextStreamReader reader) throws GameIOException {

        value = valueReader.readValue(reader);

        return value;
    }
    public Object readValue(XTextStreamReader reader, XNode parent) throws GameIOException {

        value = valueReader.readValue(reader);

        return value;
    }


    public void setValue(Object value) {
        this.value = value;
    }

    public Object getValue() {
        return value;
    }

    public void closeDynamicVar() {
        if(dynamic)
            builder.stop();
        builder.skip();
    }
    public XVariable clone() {
        return new XVariable(valueReader, name);
    }

    public void configure(String configuration) throws GameIOException {
        return;
    }

    @Override
    public String getType() {
        if (valueReader == null) return null;
        return valueReader.getType();
    }
}
