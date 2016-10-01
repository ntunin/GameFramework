package eye.engine.nik.gameframework.GameFramework.IO.XFile.XValueReader;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import eye.engine.nik.gameframework.GameFramework.Delegate;
import eye.engine.nik.gameframework.GameFramework.DelegateException;
import eye.engine.nik.gameframework.GameFramework.IO.GameIOException;
import eye.engine.nik.gameframework.GameFramework.IO.XFile.XStreamReader.XStringStreamReader;
import eye.engine.nik.gameframework.GameFramework.IO.XFile.XStreamReader.XTextStreamReader;
import eye.engine.nik.gameframework.GameFramework.IO.XFile.XWatchBatcher;
import eye.engine.nik.gameframework.GameFramework.IO.XFile.XWatcher;

/**
 * Created by nikolay on 21.08.16.
 */
public class XTemplateContentHandler {
    private XTextStreamReader reader;
    List<XWatcher> watchers;
    XNode node;
    XWatchBatcher builder;
    XTextStreamReader definitionReader;
    private boolean needDynamicMode = false;
    private boolean dynamicMode = false;
    private XTemplate template;
    private boolean onDynamicReading;
    private boolean handled;
    private boolean onParametrization;
    private XVariableCacheItem cache;

    public XTemplateContentHandler(XTemplate template) {
        this.template = template;
    }

    public XNode read(XTextStreamReader reader) throws GameIOException {
        this.reader = reader;
        node = new XNode(template.getName());
        readDefinedContent();
        readDynamicContent();
        return node;
    }
    private void readDefinedContent() throws GameIOException {
        dynamicMode = false;
        handled = false;
        cache = XDocumentRegister.table().getCached(template.getName());
        createBuilder();
        if(cache == null) {
            readOrder();
        } else {
            readCache();
        }
    }
    private void readOrder() throws GameIOException {
        createCache();
        List<String> order = template.getOrder();
        for(String definition: order) {
            definition = configure(definition);
            definitionReader = new XStringStreamReader(definition);
            builder.setReader(definitionReader);
            try {
                builder.setStreamOut(new Delegate(this, "closeDefinition"));
            } catch (DelegateException e) {
                throw new GameIOException("XTemplateContentHandler.closeDefinition: internal delegate exception");
            }
            builder.read();
        }
        registerCahce();

    }
    private void createBuilder() {
        builder = new XWatchBatcher(reader) {
            @Override
            protected List<XWatcher> getPrimitiveWatchers() throws DelegateException {
                return getTypesWatchers();
            }
        };
    }
    private void createCache() {
        cache = new XVariableCacheItem();
    }
    private String configure(String definition) {
        Set<String> keys = node.getVariableNames();
        StringBuilder definitionBuilder = new StringBuilder(definition);
        for (String name: keys) {
            int start = definitionBuilder.indexOf(name);
            if(start < 0) continue;
            int length = name.length();
            XVariable var = (XVariable) node.get(name);
            String value = var.getValue().toString();
            definitionBuilder.replace(start, start + length, value);
        }
        return definitionBuilder.toString();
    }


    private void readCache() throws GameIOException {
        List<String> order = template.getOrder();
        List<XVariable> cacheList = cache.getList();
        int count = cacheList.size();
        for(int i = 0; i < count; i++) {
            XVariable var = cacheList.get(i).clone();
            String definition = order.get(i);
            configure(definition, var);
            var.readValue(reader);
            node.set(var.getName(), var);
        }
        needDynamicMode = cache.getNeedDynamic();
    }

    private void configure(String definition, XVariable var) throws GameIOException {
        Set<String> keys = node.getVariableNames();
        StringBuilder definitionBuilder = new StringBuilder(definition);
        for (String name: keys) {
            int start = definitionBuilder.indexOf(name);
            if(start < 0) continue;
            int length = name.length();
            String value = ((XVariable)node.get(name)).getValue().toString();
            definitionBuilder.replace(start, start + length, value);
            var.configure(definitionBuilder.toString());
        }

    }

    private void readDynamicContent() throws GameIOException {
        if(!needDynamicMode) return;

        dynamicMode = true;
        this.definitionReader = this.reader;
        builder.setReader(this.definitionReader);
        try {
            Delegate closeContent = new Delegate(this, "closeContent");
            builder.addWatcher(new XWatcher("}", closeContent));
            builder.setStreamOut(closeContent);
        } catch (DelegateException e) {
            throw new GameIOException("XTemplateContentHandler.closeContent: internal delegate exception");
        }
        builder.clear();
        try {
            builder.read();
        } catch (GameIOException e) {
            closeContent();
        }
    }
    private List<XWatcher> getTypesWatchers() throws DelegateException {
        watchers = new ArrayList<>();
        XDocumentRegister table = XDocumentRegister.table();
        Set<String> names = table.registered();
        for (String name:
                names) {
            addDelegateFor(name);
        }
        addDelegate("array", "createArrayVariable");
        addDelegate("template", "createNewTemplate");
        addDelegate("[...]", "activateDynamicMode");
        addDelegate("array ", "createArrayVariable");
        addDelegate("template ", "createNewTemplate");
        addDelegate("{", "startParametrization");
        return watchers;
    }
    private void addDelegateFor(String name) throws DelegateException {
        String type = XDocumentRegister.table().getReader(name).getType();
        String method = (type.equals("node"))? "createNodeVariable" : "createVariable";
        addDelegate(name, method, String.class);
    }
    private void addDelegate(String lexema, String method, Class... args) throws DelegateException {
        watchers.add(new XWatcher(lexema, new Delegate(this, method, args)));

    }



    public void createVariable(String type) throws GameIOException {
        XVariable var = new XVariable(XDocumentRegister.table().getReader(type), definitionReader);
        builder.stop();
        builder.clear();
        var.readValue(reader);
        node.set(var.getName(), var);
        if(dynamicMode) return;
        cache.add(var);
    }

    public void createNodeVariable(String type) throws GameIOException {
        XDocumentRegister table = XDocumentRegister.table();
        XVariable var = new XVariable(table.getReader(type), definitionReader, dynamicMode);
        builder.clear();
        if(dynamicMode) {
            onDynamicReading = true;
        }
        if(type.equals("Frame ")) {
            int a = 0;
            a++;
        }
        XNode child = (XNode) var.readValue(reader);
        String name = var.getName();
        if(type.equals("Frame ")) {
            table.register(name, child);
        }
        XNode parent = (XNode) child.get("__parent");
        if(parent == null)
            node.set(name, child);
        else
            parent.set(name, child);
        char c = reader.getChar();
        if(c != ';') {
            reader.skip(-1);
        }
        if(dynamicMode) return;
        cache.add(var);
    }

    public void createNodeFromVar(XVariable var) {

    }


    public void createArrayVariable() throws GameIOException {
        XArrayReader arrayReader = new XArrayReader();
        XVariable var = new XArray(arrayReader, definitionReader);
        builder.stop();
        Array array = (Array) var.readValue(reader);
        String name = var.getName();
        node.set(name, var);
        if(dynamicMode) return;
        cache.add(var);
    }
    public void activateDynamicMode() {
        String type = builder.getContent();
        builder.clear();
        needDynamicMode = true;
        builder.stop();
        cache.setNeedDynamic(true);
    }
    public void closeContent() {
        if(onParametrization) {
            parametrize();
            return;
        }
        if(onDynamicReading)
            onDynamicReading = false;
        else {
            if(handled) {
                builder.stop();
                return;
            }
            if(dynamicMode) {
                handled = true;
                reader.skip(-1);
            }
            builder.stop();
        }
        builder.clear();
    }

    public void createNewTemplate() throws GameIOException {
        XTemplate template = new XTemplate(null, reader);
        template.subscribe(reader);
        try {
            builder.subscribe(new Delegate(this, "updateBuilder"));
        } catch (DelegateException e) {
            throw new GameIOException("XTemplateContentHandler.updateBuilder: internal delegate exception");
        }
        builder.clear();
    }

    public void updateBuilder() throws DelegateException {
        List<XWatcher> watchers = getTypesWatchers();
        builder.updateWatchers(watchers);
    }
    public void closeDefinition() {
        builder.stop();
    }

    public void startParametrization() {
        builder.clear();
        onParametrization = true;
    }

    private void parametrize() {
        String parameter = builder.getContent();
        node.set("__parent", XDocumentRegister.table().getFrame(parameter));
        onParametrization = false;
    }

    private void registerCahce() {
        XDocumentRegister.table().register(template.getName(), cache);
    }
}
