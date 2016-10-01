package eye.engine.nik.gameframework.GameFramework.IO.XFile.XValueReader;

import java.util.ArrayList;
import java.util.List;

import eye.engine.nik.gameframework.GameFramework.Delegate;
import eye.engine.nik.gameframework.GameFramework.DelegateException;
import eye.engine.nik.gameframework.GameFramework.IO.GameIOException;
import eye.engine.nik.gameframework.GameFramework.IO.XFile.XStreamReader.XStringStreamReader;
import eye.engine.nik.gameframework.GameFramework.IO.XFile.XWatchBatcher;
import eye.engine.nik.gameframework.GameFramework.IO.XFile.XStreamReader.XTextStreamReader;
import eye.engine.nik.gameframework.GameFramework.IO.XFile.XWatcher;

/**
 * Created by nikolay on 12.08.16.
 */
public class XArray extends XVariable {
    XTextStreamReader reader;
    XWatchBatcher builder;
    XValueReader itemReader;
    XArrayReader arrayReader;
    Object[] array;
    String type;
    int index;

    public XArray(XArray prototype) {
        super(prototype.arrayReader, prototype.getName());
        this.arrayReader = prototype.arrayReader;
        this.itemReader = prototype.itemReader;
    }
    public XArray(XArrayReader arrayReader, XTextStreamReader reader) throws GameIOException {
        super(arrayReader, reader);
    }

    @Override
    public void handleName(String name) throws GameIOException {
        reader = new XStringStreamReader(name);
        builder = getBuilder();
        builder.read();
    }

    XWatchBatcher getBuilder() {
        return new XWatchBatcher(reader) {
            @Override
            protected List<XWatcher> getPrimitiveWatchers() throws DelegateException {
                return getWatchers();
            }
        };
    }

    private List<XWatcher> getWatchers() throws DelegateException {
        List<XWatcher> watchers = new ArrayList<>();
        watchers.add(new XWatcher(" ", new Delegate(this, "parseType")));
        watchers.add(new XWatcher("[", new Delegate(this, "parseName")));
        watchers.add(new XWatcher("]", new Delegate(this, "parseSize")));
        return watchers;
    }
    public void parseType() {
        type = builder.getContent();
        XDocumentRegister table = XDocumentRegister.table();
        itemReader = table.getReader(type);
        arrayReader = (XArrayReader)valueReader;
        arrayReader.setItemReader(itemReader);
        builder.clear();

    }
    public void parseName() {
        String name = builder.getContent();
        builder.clear();
        setName(name);
    }

    public void parseSize() {
        String sizeText = builder.getContent();
        builder.clear();
        int size = Integer.parseInt(sizeText);
        arrayReader.setSize(size);
        builder.stop();

    }

    @Override
    public void setValue(Object value) {
        super.setValue(value);
    }


    @Override
    public void configure(String configuration) throws GameIOException {
        init(new XArrayReader(), new XStringStreamReader(configuration));
    }
    @Override
    public XVariable clone() {
        return new XArray(this);
    }
}
