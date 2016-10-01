package eye.engine.nik.gameframework.GameFramework.IO.XFile.XValueReader;

import java.util.ArrayList;
import java.util.List;

import eye.engine.nik.gameframework.GameFramework.Delegate;
import eye.engine.nik.gameframework.GameFramework.DelegateException;
import eye.engine.nik.gameframework.GameFramework.IO.GameIOException;
import eye.engine.nik.gameframework.GameFramework.IO.XFile.XStreamReader.XTextStreamReader;
import eye.engine.nik.gameframework.GameFramework.IO.XFile.XWatchBatcher;
import eye.engine.nik.gameframework.GameFramework.IO.XFile.XWatcher;

/**
 * Created by nikolay on 16.08.16.
 */
public class XNodeReader extends XValueReader {
    private XTemplate template;
    public XNodeReader() throws GameIOException {
    }

    public XNodeReader(XTemplate template) throws GameIOException {
        this.template = template;
    }

    @Override
    public String getType() {
        return "node";
    }

    @Override
    public Object readValue(XTextStreamReader reader) throws GameIOException {
        value = template.readValue(reader);
        return value;
    }
    @Override
    public Object readValue(XTextStreamReader reader,  String... terminators) throws GameIOException {
        XNode node = (XNode) template.readValue(reader);
        try {
            Delegate parseContentDelegate = new Delegate(this, "parseContent");
            final List<XWatcher> watchers = new ArrayList<>();
            for(String terminator: terminators) {
                XWatcher terminateWatcher = new XWatcher(terminator, parseContentDelegate);
                watchers.add(terminateWatcher);
            }

            builder = new XWatchBatcher(reader) {
                @Override
                protected List<XWatcher> getPrimitiveWatchers() throws DelegateException {
                    return watchers;
                }
            };
            builder.setStreamOut(parseContentDelegate);
            builder.read();
        } catch (DelegateException e) {
            throw new GameIOException("XNodeReader.parseContent: internal delegate exception");
        }
        return node;
    }

    public void parseContent() throws DelegateException {
        builder.stop();
    };
}
