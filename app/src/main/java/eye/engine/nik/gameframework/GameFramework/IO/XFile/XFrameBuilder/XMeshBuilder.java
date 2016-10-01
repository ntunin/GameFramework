package eye.engine.nik.gameframework.GameFramework.IO.XFile.XFrameBuilder;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import eye.engine.nik.gameframework.GameFramework.Delegate;
import eye.engine.nik.gameframework.GameFramework.DelegateException;
import eye.engine.nik.gameframework.GameFramework.Graphics.Frame;
import eye.engine.nik.gameframework.GameFramework.IO.GameIOException;
import eye.engine.nik.gameframework.GameFramework.IO.XFile.XValueReader.IntArray;
import eye.engine.nik.gameframework.GameFramework.IO.XFile.XValueReader.ObjectArray;
import eye.engine.nik.gameframework.GameFramework.IO.XFile.XValueReader.XArray;
import eye.engine.nik.gameframework.GameFramework.IO.XFile.XValueReader.XNode;
import eye.engine.nik.gameframework.GameFramework.IO.XFile.XValueReader.XTypedItem;
import eye.engine.nik.gameframework.GameFramework.IO.XFile.XValueReader.XVariable;

/**
 * Created by nikolay on 12.09.16.
 */
public class XMeshBuilder extends XNodeIterable{
    private static XMeshBuilder builder;
    public static void set(Frame frame, XNode node) throws GameIOException {
        if(builder == null) builder = new XMeshBuilder();
        builder._set(frame, node);
    }
    private  XMeshBuilder() throws GameIOException {
        Map<String, Delegate> handlers = new HashMap<>();
        try {
            handlers.put("MeshTextureCoords", new Delegate(this, "setTextureCoords", Frame.class, String.class, XNode.class));
            handlers.put("vertices", new Delegate(this, "setVertices", Frame.class, String.class, XVariable.class));
            handlers.put("faces", new Delegate(this, "setIndeces", Frame.class, String.class, XVariable.class));
            handlers.put("MeshNormals", new Delegate(this, "setNormals", Frame.class, String.class, XNode.class));
        } catch (DelegateException e) {
            throw new GameIOException("XMeshBuilder.[handlerName]: internal delegate exception");
        }
        setHandlers(handlers);
    }

    protected String getParam(XTypedItem item) {
        return item.getName();
    }


    public void _set(Frame parent, XNode node) throws GameIOException {
        iterate(node, parent);
    }

    public void setTextureCoords(Frame parent, String name, XNode node) {
        XVariable var = (XVariable)node.get("nTextureCoords");
        Short value = (Short)var.getValue();
        int count = (int)value;
        float[] coords = new float[ 2*count ];
        Object[] xcoords = ((ObjectArray) ((XVariable)node.get("textureCoords")).getValue()).getArray();

        for(int i = 0; i < count; i++) {
            XNode xuv = (XNode) xcoords[i];
            coords[2*i] = (float)((XVariable)xuv.get("u")).getValue();
            coords[2*i + 1] = (float)((XVariable)xuv.get("v")).getValue();
        }

        parent.setUV(coords);
    }

    public void setVertices(Frame parent, String name, XVariable var) {
        Object[] xcoords = ((ObjectArray)var.getValue()).getArray();
        int count = xcoords.length;
        float[] coords = new float[3 * count];
        for(int i = 0; i < count; i++) {
            readXVector((XNode) xcoords[i], coords, i);
        }
        parent.setVertices(coords);
    }
    public void setIndeces(Frame parent, String name, XVariable var) {
        Object[] xcoords = ((ObjectArray)var.getValue()).getArray();
        int count = xcoords.length;
        short[] coords = new short[3 * count];
        for(int i = 0; i < count; i++) {
            XNode xFace = (XNode) xcoords[i];
            int[] xIndeces =((IntArray)((XArray)xFace.get("faceVertexIndices")).getValue()).getArray();
            for(int j = 0; j < 3; j++) {
                coords[3*i + j] = (short) xIndeces[j];
            }
        }
        parent.setIndices(coords);

    }
    public void setNormals(Frame parent, String name, XNode node) {
        XVariable var = (XVariable)node.get("nNormals");
        Short value = (Short) var.getValue();
        int count = (int)value;
        float[] coords = new float[ 3*count ];
        Object[] xcoords = ((ObjectArray) ((XVariable)node.get("normals")).getValue()).getArray();

        for(int i = 0; i < count; i++) {
            readXVector((XNode) xcoords[i], coords, i);
        }

        parent.setNormals(coords);

    }

    private void readXVector(XNode vector, float[] buffer, int offset) {
        buffer[3*offset] = (float)getFrom("x", vector);
        buffer[3*offset + 1] = (float)getFrom("y", vector);
        buffer[3*offset + 2] = (float)getFrom("z", vector);

    }

    private Object getFrom(String key, XNode node) {
        return ((XVariable)node.get(key)).getValue();
    }
}
