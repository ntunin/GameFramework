package com.ntunin.cybervision.opengl.motion;

import javax.microedition.khronos.opengles.GL10;

import com.ntunin.cybervision.R;
import com.ntunin.cybervision.ercontext.Body;
import com.ntunin.cybervision.injector.Injector;

/**
 * Created by nikolay on 17.10.16.
 */

public abstract class GLTransition {
    protected GL10 gl;
    protected GLTransition() {
        gl = (GL10) Injector.main().getInstance(R.string.gl);
    }
    public abstract void act(Body b);
}
