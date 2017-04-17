package com.ntunin.cybervision.opengl.graphics;

import javax.microedition.khronos.opengles.GL10;
import android.opengl.GLSurfaceView;

import com.ntunin.cybervision.R;
import com.ntunin.cybervision.injector.Injector;

/**
 * Created by nick on 01.03.16.
 */
public class GLGraphics {
    private static GLGraphics graphics;
    GLSurfaceView glView;
    GL10 gl;

    public static GLGraphics create(GLSurfaceView view) {
        graphics = new GLGraphics(view);
        Injector injector = Injector.main();
        injector.setInstance(R.string.graphics, graphics);
        injector.setInstance(R.string.gl, graphics.gl);
        return graphics;
    }

    private GLGraphics(GLSurfaceView glView) {
        this.glView = glView;
    }

    public static GL10 getGL() {
        return graphics.gl;
    }

    public static void setGL(GL10 gl) {
        if(graphics == null) return;
        graphics.gl = gl;
    }
    public static int getWidth() {
        if(graphics == null) return 0;
        int w = graphics.glView.getWidth();
        return w;
    }
    public static int getHeight() {
        if(graphics == null) return 0;
        return graphics.glView.getHeight();
    }
}
