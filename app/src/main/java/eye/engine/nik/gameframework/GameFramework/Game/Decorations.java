package eye.engine.nik.gameframework.GameFramework.Game;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import eye.engine.nik.gameframework.GameFramework.ERRNO;
import eye.engine.nik.gameframework.GameFramework.Graphics.Frame;
import eye.engine.nik.gameframework.GameFramework.IO.XFile.XFile;

/**
 * Created by nikolay on 12.10.16.
 */

public class Decorations {
    private Map<Object, Object> bodyMapping;
    public Decorations(Map<Object, Object> bodyMapping) {
        this.bodyMapping = bodyMapping;
    }

    public void load() {
        Set<Object> keys = bodyMapping.keySet();
        Map<Body, Frame> frameMapping = new HashMap<>();
        for(Object key: keys) {
            String bodyName = (String) key;
            String frameName = (String) bodyMapping.get(bodyName);
        }
    }

    public Frame get(Body body) {
        try {
            return (Frame) bodyMapping.get(body);
        } catch (Exception e) {
            ERRNO.write("Have not decorations");
            return null;
        }
    }
}
