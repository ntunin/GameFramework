package eye.engine.nik.gameframework.GameFramework.IO.XFile.XStreamBuilder;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import eye.engine.nik.gameframework.GameFramework.ERRNO;
import eye.engine.nik.gameframework.GameFramework.Graphics.Frame;
import eye.engine.nik.gameframework.GameFramework.Graphics.Material;

/**
 * Created by nikolay on 09.10.16.
 */

public class XFrameBuilder {
    private Frame frame;
    private XTyped value;
    public static Frame read(XTyped value) {
        return new XFrameBuilder(value)._read();
    }
    private XFrameBuilder(XTyped value) {
        this.value = value;
        this.frame = null;
    }

    private Frame _read() {
        Map<String, Object> hierarchy = getHierarchy(value);
        frame = getFrame(hierarchy);
        return frame;
    }
    private Map<String, Object> getHierarchy(XTyped value) {
        try {
            Map<String, Object> hierarchy = (Map<String, Object>) value.getValue();
            return hierarchy;
        } catch (Exception e) {
            ERRNO.write("Cast");
        }
        return null;
    }

    private Frame getFrame(Map<String, Object> hierarchy) {
        String name = value.getName();
        frame = new Frame(name);
        frame = iterateHierarchy(hierarchy);
        return frame;
    }
    private void handle(Object value) {
        try {
            XTyped typed = (XTyped) value;
            handleTyped(typed);
        } catch (Exception e) {
            handleUntyped(value);
        }
    }

    private void handleTyped(XTyped value) {
        String type =value.getType();
        switch (type) {
            case "Mesh":
            case "MeshMaterialList":
            case "MeshNormals":
            case "MeshTextureCoords": {
                handleHierarchy(value);
                break;
            }
            case "Frame": {
                handleFrame(value);
                break;
            }
            case "array": {
                handleArray(value);
                break;
            }
            case "Material": {
                handleMaterial(value);
                break;
            }
            case "ObjectMatrixComment": {
                handleObjectMatrix(value);
                break;
            }
            case "FrameTransformMatrix": {
                handleRelativeMatrix(value);
                break;
            }
        }
    }

    private void handleUntyped(Object value) {
        int a = 0;
    }
    private Frame iterateHierarchy(Map<String, Object> hierarchy) {
        Set<String> keys = hierarchy.keySet();
        for (String key: keys) {
            Object value = hierarchy.get(key);
            handle(value);
        }
        return frame;
    }
    private void handleFrame(XTyped value) {
        Frame child = XFrameBuilder.read(value);
        frame.addFrame(child);
    }

    private void handleHierarchy(XTyped value) {
        Map<String, Object> hierarchy = getHierarchy(value);
        Frame frame = iterateHierarchy(hierarchy);
    }

    private void handleArray(XTyped array) {
        String name = array.getName();
        switch (name) {
            case "vertices": {
                handleVertices(array);
                break;
            }
            case  "faces": {
                handleFaces(array);
                break;
            }
            case "normals": {
                handleNormals(array);
                break;
            }
            case "textureCoords": {
                handleUV(array);
                break;
            }
        }
    }

    private void handleVertices(XTyped vertices) {
        Object[] array = (Object[]) vertices.getValue();
        float[] result = new float[3 * array.length];
        for(int i = 0; i < array.length; i++) {
            writeVector((XTyped) array[i], result, i);
        }
        frame.setVertices(result);
    }

    private void handleNormals(XTyped normals) {
        Object[] array = (Object[]) normals.getValue();
        float[] result = new float[3 * array.length];
        for(int i = 0; i < array.length; i++) {
            writeVector((XTyped) array[i], result, i);
        }
        frame.setNormals(result);
    }

    private void writeVector(XTyped vector, float[] buffer, int offset) {
        Map<String, Float> info = (Map<String, Float>) vector.getValue();
        int j = offset*3;
        buffer[j + 0] = info.get("x");
        buffer[j + 1] = info.get("y");
        buffer[j + 2] = info.get("z");

    }
    private void handleFaces(XTyped faces) {
        Object[] array = (Object[]) faces.getValue();
        short[] result = new short[3 * array.length];
        for(int i = 0; i < array.length; i++) {
            Map<String, Object> info = (Map<String, Object>) ((XTyped)array[i]).getValue();
            XTyped face = (XTyped) info.get("faceVertexIndices");
            Object[] indices = (Object[]) face.getValue();
            for(int j = 0; j < 3; j++) {
                result[3*i + j] = (short)((int)indices[j]);
            }

        }
        frame.setIndices(result);
    }
    private void handleUV(XTyped UVs) {
        Object[] array = (Object[]) UVs.getValue();
        float[] result = new float[2 * array.length];
        for(int i = 0; i < array.length; i++) {
            XTyped uv = (XTyped) array[i];
            Map<String, Object> info = (Map<String, Object>) uv.getValue();
            result[2*i + 0] = (float) info.get("u");
            result[2*i + 1] = (float) info.get("v");

        }
        frame.setUV(result);
    }
    private void handleMaterial(XTyped material) {
        Map<String, Object> structure = (Map<String, Object>) material.getValue();
        Material m = new Material();
        float[] specular = handleColor((XTyped) structure.get("specularColor"));
        m.setSpecular(specular);
        float[] emissive = handleColor((XTyped) structure.get("emissiveColor"));
        m.setDiffuse(emissive);
        float[] face = handleColor((XTyped) structure.get("faceColor"));
        m.setAmbient(face);
        float power = (float) structure.get("power");
        m.setPower(power);
        frame.setDefaultMaterial(m);
    }

    float[] handleColor(XTyped color) {
        float[] result = new float[4];
        String type = color.getType();
        Map<String, Object> info = (Map<String, Object>) color.getValue();
        result[0] = (float) info.get("red");
        result[1] = (float) info.get("green");
        result[2] = (float) info.get("blue");
        result[3] = (type.equals("ColorRGB"))? 1 : (float) info.get("alpha");
        return result;
    }

    private void handleObjectMatrix(XTyped objectMatrixComment) {
        Map<String, Object> info = (Map<String, Object>) objectMatrixComment.getValue();
        XTyped objectMatrix = (XTyped) info.get("objectMatrix");
        float[] result = getMatrix(objectMatrix);
        frame.setObjectMatrix(result);
    }
    private void handleRelativeMatrix(XTyped relative) {

        Map<String, Object> info = (Map<String, Object>) relative.getValue();
        XTyped objectMatrix = (XTyped) info.get("frameMatrix");
        float[] result = getMatrix(objectMatrix);
        frame.setRelativeMatrix(result);
    }
    float[] getMatrix(XTyped matrix4x4) {
        Map<String, Object> info = (Map<String, Object>) matrix4x4.getValue();
        XTyped matrix = (XTyped) info.get("matrix");
        Object[] array = (Object []) matrix.getValue();
        float[] result = new float[16];
        for(int i = 0; i < array.length; i++) {
            result[i] = (float) array[i];
        }
        return result;
    }
}