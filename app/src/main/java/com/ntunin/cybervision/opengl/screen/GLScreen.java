package com.ntunin.cybervision.opengl.screen;

import com.ntunin.cybervision.R;
import com.ntunin.cybervision.ercontext.ERContext;
import com.ntunin.cybervision.ercontext.Screen;
import com.ntunin.cybervision.injector.Injectable;
import com.ntunin.cybervision.injector.Injector;
import com.ntunin.cybervision.res.ResMap;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by mikhaildomrachev on 18.04.17.
 */

public class GLScreen extends Screen implements Injectable {
    GL10 gl;

    @Override
    public void resume() {
        gl = (GL10) Injector.main().getInstance(R.string.gl);
    }

    @Override
    public void update(float deltaTime) {
    }
    @Override
    public void present(float deltaTime) {
        clean();
    }

    private void clean() {
        gl.glClearColor(0, 0f, 0f, 0f);
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
        gl.glEnable(GL10.GL_DEPTH_TEST);
    }

    @Override
    public void pause() {
    }

    @Override
    public void dispose() {
    }

    @Override
    public void init(ResMap<String, Object> data) {
    }
}
