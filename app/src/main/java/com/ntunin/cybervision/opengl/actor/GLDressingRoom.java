package com.ntunin.cybervision.opengl.actor;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.ntunin.cybervision.opengl.graphics.GLDress;

/**
 * Created by nikolay on 17.10.16.
 */

public class GLDressingRoom {
    Map<String, GLDress> dresses;
    public GLDressingRoom(Map<String, String> dressPathes) {
        dresses = new HashMap<>();
        Set<String> dressIds = dressPathes.keySet();
        for(String id: dressIds) {
            String path = dressPathes.get(id);
           // GLDress f = XFile.loadFrame(path);
            //dresses.put(id, f);
        }
    }

    public GLDress get(String name) {
        return dresses.get(name);
    }
}
