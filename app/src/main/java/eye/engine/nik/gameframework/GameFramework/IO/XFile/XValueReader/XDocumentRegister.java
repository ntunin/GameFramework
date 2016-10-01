package eye.engine.nik.gameframework.GameFramework.IO.XFile.XValueReader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import eye.engine.nik.gameframework.GameFramework.IO.GameIOException;
import eye.engine.nik.gameframework.GameFramework.IO.XFile.XStreamReader.XTextStreamReader;
import eye.engine.nik.gameframework.GameFramework.IO.XFile.XWatchBatcher;
import eye.engine.nik.gameframework.GameFramework.IO.XFile.XWatcher;

/**
 * Created by nikolay on 12.08.16.
 */
public class XDocumentRegister {
    private static XDocumentRegister _table;
    private List<XWatcher> _watchers;
    private Map<String, XNode> _frames = new HashMap<>();
    private Map<String, XVariableCacheItem> cache = new HashMap<>();
    private XWatchBatcher _typesButcher;
    
    public static XDocumentRegister table() {
        if(_table == null) _table = new XDocumentRegister();
        return _table;
    }
    private Map<String, XValueReader> _readers;
    private XDocumentRegister() {
        _readers = new HashMap<>();
        _watchers = new ArrayList<>();
    }
    public Set<String> registered() {
        return _readers.keySet();
    }
    public void register(String name, XValueReader reader) {
        name = name.trim() + " ";
        _readers.put(name, reader);

    }
    public void register(String name, XNode frame) {
        name = name.trim();
        _frames.put(name, frame);
    }
    public void register(XWatcher watcher) {
        _watchers.add(watcher);

    }
    public void register(String name, XVariableCacheItem item) {
        cache.put(name, item);
    }
    private  XVariable createVariableWithType(String name, XTextStreamReader reader) throws GameIOException {
        XValueReader valueReader = _table.getReader("name");
        XVariable var = new XVariable(valueReader, reader);
        return var;

    }
    public XNode getFrame(String name) {
        name = name.trim();
        return _frames.get(name);
    }
    public XValueReader getReader(String name) {
        name = name.trim() + " ";
        return _readers.get(name);
    }
    public XVariableCacheItem getCached(String name) {
        return cache.get(name);
    }
    public List<XWatcher> getWatchers() {
        return _watchers;
    }



}
