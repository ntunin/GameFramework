package eye.engine.nik.gameframework.GameFramework.IO.XFile.XValueReader;

import java.util.ArrayList;
import java.util.List;

import eye.engine.nik.gameframework.GameFramework.Delegate;
import eye.engine.nik.gameframework.GameFramework.DelegateException;
import eye.engine.nik.gameframework.GameFramework.IO.GameIOException;
import eye.engine.nik.gameframework.GameFramework.IO.XFile.XWatchBatcher;
import eye.engine.nik.gameframework.GameFramework.IO.XFile.XStreamReader.XStringStreamReader;
import eye.engine.nik.gameframework.GameFramework.IO.XFile.XStreamReader.XTextStreamReader;
import eye.engine.nik.gameframework.GameFramework.IO.XFile.XWatcher;

/**
 * Created by nikolay on 15.08.16.
 */
public class XTemplate extends XVariable {

    private  XTextStreamReader reader;
    private  XWatchBatcher builder;
    private List<XWatcher> watchers;

    private String name;
    private String id;
    private List<String> order;
    private Delegate callback;
    private String appendix;
    private XTemplateContentHandler contentHandler;

    public XTemplate(XValueReader valueReader, XTextStreamReader reader) throws GameIOException {
        super(valueReader, reader, "}");
    }

    @Override
    public void handleName(String name) throws GameIOException {
        reader = new XStringStreamReader(name);
        watchers = new ArrayList<>();
        builder = getBuilder();
        builder.read();
    }

    XWatchBatcher getBuilder() throws GameIOException {
        XWatchBatcher builder = new XWatchBatcher(reader) {
            @Override
            protected List<XWatcher> getPrimitiveWatchers() throws DelegateException {
                return getWatchers();
            }
        };
        try {
            builder.setStreamOut(new Delegate(this, "closeTemplate"));
        } catch (DelegateException e) {
            throw new GameIOException("Internal delegate exception");
        }
        return builder;
    }

    private List<XWatcher> getWatchers() throws DelegateException {
        createWatcher("{", "parseName");
        createWatcher("}", "closeTemplate");
        createWatcher("<", "startReadId");
        createWatcher(">", "finishReadId");
        createWatcher(";", "readVar");
        createWatcher("]", "readDynamicVars");

        return watchers;
    }

    private void createWatcher(String lexema, String method) throws DelegateException {
        XWatcher watcher = new XWatcher(lexema, new Delegate(this, method));
        watchers.add(watcher);
    }

    public void parseName() {
        name = builder.getContent().trim();
        builder.clear();
        order = new ArrayList<>();
    }

    public void closeTemplate() throws GameIOException {
        builder.stop();
        builder.clear();
    }

    public void startReadId() {
        if(id != null) return;
        builder.clear();
    }

    public void finishReadId() {
        if(id != null) return;
        id = builder.getContent().trim();
        builder.clear();
    }

    public void readVar() {
        String varDefinition = builder.getContent().trim();
        builder.clear();
        if(appendix != null) {
            varDefinition = new StringBuilder(varDefinition).append(appendix).toString();
            appendix = null;
        }
        order.add(varDefinition);
    }

    public void dynamicModeRequest() {
    }
    public void readDynamicVars() {
        String type = builder.getContent().split(" ")[0].trim();
        if(!canActivateDynamicWith(type)) {
            appendix = "]";
            return;
        }
        order.add("[...]");
    }
    private boolean canActivateDynamicWith(String type) {
        return type.charAt(0) == '[';
    }
    public void returnValue() throws GameIOException {
        readValue(reader);
    }
    @Override
    public Object readValue(XTextStreamReader reader) throws GameIOException {
        this.reader = reader;
        builder.stop();
        contentHandler = new XTemplateContentHandler(this);
        XNode node = contentHandler.read(reader);
        return node;
    }


    private List<XWatcher> getContentWatchers() throws DelegateException {
        List<XWatcher> watchers = new ArrayList<>();
        watchers.add(new XWatcher("{", new Delegate(this, "readContent")));
        return watchers;
    }
    List<String> getOrder() {
        return order;
    }

    public String getName() {
        return name;
    }

    public void subscribe(XTextStreamReader reader) throws GameIOException {
        this.reader = reader;
        final XDocumentRegister table = XDocumentRegister.table();
        try {
            String name = getName();
            table.register(new XWatcher(name, new Delegate(this, "returnValue")));
            table.register(name, new XNodeReader(this));
        } catch (DelegateException e) {
            throw new GameIOException("Internal delegate exception");
        }
    }

}
