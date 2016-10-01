package eye.engine.nik.gameframework.GameFramework.IO.XFile.XFrameBuilder;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import eye.engine.nik.gameframework.GameFramework.Delegate;
import eye.engine.nik.gameframework.GameFramework.DelegateException;
import eye.engine.nik.gameframework.GameFramework.Graphics.Frame;
import eye.engine.nik.gameframework.GameFramework.IO.GameIOException;
import eye.engine.nik.gameframework.GameFramework.IO.XFile.XValueReader.XNode;
import eye.engine.nik.gameframework.GameFramework.IO.XFile.XValueReader.XTypedItem;

/**
 * Created by nikolay on 12.09.16.
 */
public class XNodeIterable {
    private Map<String, Delegate> handlers = new HashMap<>();
    protected void setHandlers(Map<String, Delegate> handlers) {
        this.handlers = handlers;
    }
    protected void iterate(XNode node, Frame frame) throws GameIOException {
        Set<String> fields = node.getVariableNames();
        for(String field: fields) {
            XTypedItem child = (XTypedItem) node.get(field);
            String type = getParam(child);
            Delegate method = handlers.get(type);
            if(method == null) continue;
            try {
                method.invoke(frame, field, child);
            } catch (DelegateException e) {
                throw new GameIOException("XNodeIterable.[handlerName]: internal delegate exception");
            }
            int a = 0;
            a++;
        }
    }

    protected String getParam(XTypedItem item) {
        return item.getType();
    }
}
