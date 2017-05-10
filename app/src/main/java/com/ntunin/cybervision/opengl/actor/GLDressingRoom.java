package com.ntunin.cybervision.opengl.actor;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.ntunin.cybervision.R;
import com.ntunin.cybervision.injector.Injectable;
import com.ntunin.cybervision.io.xfile.XFile;
import com.ntunin.cybervision.opengl.graphics.GLDress;
import com.ntunin.cybervision.res.ResMap;

/**
 * Created by nikolay on 17.10.16.
 */

public class GLDressingRoom implements Injectable {
    Map<String, GLDress> dresses;
    Map<String, String> paths;

    public GLDress get(String name) {
        return dresses.get(name);
    }

    @Override
    public void init(ResMap<String, Object> data) {
        this.paths = (Map<String, String>) data.get(R.string.skins);
    }

    public void prepare() {
        dresses = new HashMap<>();
        for(String name: paths.keySet()) {
            String path = paths.get(name);
            GLDress dress = XFile.loadFrame(path);
            dresses.put(name, dress);
        }
    }
}
