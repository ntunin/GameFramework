package eye.engine.nik.gameframework.GameFramework.Graphics.OpenGL.GLActor;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import eye.engine.nik.gameframework.GameFramework.Game.Body;
import eye.engine.nik.gameframework.GameFramework.Graphics.Frame;
import eye.engine.nik.gameframework.GameFramework.IO.XFile.XFile;

/**
 * Created by nikolay on 17.10.16.
 */

public class GLDressingRoom {
    Map<String, Frame> dresses;
    public GLDressingRoom(Map<String, String> dressPathes) {
        dresses = new HashMap<>();
        Set<String> dressIds = dressPathes.keySet();
        for(String id: dressIds) {
            String path = dressPathes.get(id);
            Frame f = XFile.loadFrame(path);
            dresses.put(id, f);
        }
    }

    public Frame get(String name) {
        return dresses.get(name);
    }
}
