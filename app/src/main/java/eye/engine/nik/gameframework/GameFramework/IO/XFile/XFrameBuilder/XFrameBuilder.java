package eye.engine.nik.gameframework.GameFramework.IO.XFile.XFrameBuilder;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import eye.engine.nik.gameframework.GameFramework.Delegate;
import eye.engine.nik.gameframework.GameFramework.DelegateException;
import eye.engine.nik.gameframework.GameFramework.Graphics.Frame;
import eye.engine.nik.gameframework.GameFramework.Graphics.FrameSet;
import eye.engine.nik.gameframework.GameFramework.IO.GameIOException;
import eye.engine.nik.gameframework.GameFramework.IO.XFile.XValueReader.XNode;
import eye.engine.nik.gameframework.GameFramework.IO.XFile.XValueReader.XTypedItem;
import eye.engine.nik.gameframework.GameFramework.IO.XFile.XValueReader.XVariable;

/**
 * Created by nikolay on 12.09.16.
 */
public class XFrameBuilder extends XNodeIterable{

    private static XFrameBuilder builder;

    public static Frame get(XNode node) throws GameIOException {
        if(builder == null) builder = new XFrameBuilder();
        return builder._get(node);
    }
    private XFrameBuilder() throws GameIOException {
        addHandlers();
    }

    private void addHandlers() throws GameIOException {
        Map<String, Delegate> handlers = new HashMap<>();
        try {
            handlers.put("Frame", new Delegate(this, "setToFrameFromNode", Frame.class, String.class, XNode.class));
            handlers.put("Mesh", new Delegate(this, "setMeshToFrameFromNode", Frame.class, String.class, XNode.class));
        } catch (DelegateException e) {
            throw new GameIOException("XFrameBuilder.createFrameNode: internal delegate exception");
        }
        setHandlers(handlers);
    }
    public Frame _get(XNode node) throws GameIOException {
        Frame frame = new Frame(node.getName());
        iterate(node, frame);
        return frame;
    }

    protected String getParam(XTypedItem item) {
        return item.getType();
    }

    public void setToFrameFromNode(Frame parent, String name, XNode node) throws GameIOException {
        Frame frame = new Frame(name);
        iterate(node, frame);
        parent.addFrame(frame);
    }

    public void setMeshToFrameFromNode(Frame parent, String name, XNode node) throws GameIOException {
        XMeshBuilder.set(parent, node);
    }
}
