package eye.engine.nik.gameframework.GameFramework.IO.XFile.XValueReader;

import java.util.ArrayList;
import java.util.List;

import eye.engine.nik.gameframework.GameFramework.Delegate;
import eye.engine.nik.gameframework.GameFramework.DelegateException;
import eye.engine.nik.gameframework.GameFramework.IO.GameIOException;
import eye.engine.nik.gameframework.GameFramework.IO.XFile.XWatchBatcher;
import eye.engine.nik.gameframework.GameFramework.IO.XFile.XStreamReader.XTextStreamReader;
import eye.engine.nik.gameframework.GameFramework.IO.XFile.XWatcher;

/**
 * Created by Николай on 30.07.2016.
 */
abstract class XValueReader {
    private XTextStreamReader reader;
    private Delegate parseContentDelegate;
    protected List<XWatcher> watchers = new ArrayList<>();
    protected XWatchBatcher builder;
    protected Object value;
    public abstract String getType();

    public XValueReader() throws GameIOException {
        try {
            parseContentDelegate = new Delegate(this, "parseContent");
            watchers.add(new XWatcher(";", parseContentDelegate));
        } catch (DelegateException e) {
            throw new GameIOException("internal delegate exception");
        }
    }
    public Object readValue(XTextStreamReader reader) throws GameIOException {
        this.reader = reader;
        builder = getBuilder();
        builder.setStreamOut(parseContentDelegate);
        builder.read();
        return value;
    }
    public Object readValue(XTextStreamReader reader, String... terminators) throws GameIOException {
        List<XWatcher> terminatorList = new ArrayList<>();
        this.reader = reader;
        builder = getBuilder();
        for(String terminator: terminators) {
            XWatcher terminateWatcher = new XWatcher(terminator, parseContentDelegate);
            builder.addWatcher(terminateWatcher);
        }
        builder.setStreamOut(parseContentDelegate);
        builder.read();
        for(XWatcher terminateWatcher: terminatorList) {
            builder.removeWatcher(terminateWatcher);
        }
        return value;
    }


    private XWatchBatcher getBuilder() {
        return new XWatchBatcher(reader) {
            @Override
            protected List<XWatcher> getPrimitiveWatchers() throws DelegateException {
                return getWatchers() ;
            }
        };
    }
    private List<XWatcher> getWatchers() throws DelegateException {
        return watchers;
    }


    public void parseContent() throws DelegateException {
        builder.stop();
    };
}
