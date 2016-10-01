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
public class XNameReader {

    private List<XWatcher> watchers;
    private XWatchBatcher builder;
    private XVariable callback;
    private Delegate setName;
    public XNameReader(XTextStreamReader reader, XVariable callback, String terminator) throws GameIOException {

        watchers = createWatchers(terminator);
        builder = createBuilder(reader);
        builder.setStreamOut(setName);
        this.callback = callback;
        builder.read();

    }

    XWatchBatcher createBuilder(XTextStreamReader reader) {
        return new XWatchBatcher(reader) {
            @Override
            protected List<XWatcher> getPrimitiveWatchers() throws DelegateException {
                return watchers;
            }
        };
    }
    List<XWatcher> createWatchers(String terminator) throws GameIOException {
        List<XWatcher> watchers = new ArrayList<>();
        try {
            setName = new Delegate(this, "setName");
            watchers.add(new XWatcher(terminator, setName));
        } catch (DelegateException e) {
            throw new GameIOException("Internal value reader exception");
        }
        return watchers;
    }

    public void setName() throws GameIOException {
        builder.stop();
        String name = builder.getContent();
        callback.handleName(name);
    }
}