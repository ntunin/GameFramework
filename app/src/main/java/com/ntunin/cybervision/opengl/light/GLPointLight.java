package com.ntunin.cybervision.opengl.light;
import com.ntunin.cybervision.R;
import com.ntunin.cybervision.injector.Injector;

import javax.microedition.khronos.opengles.GL10;


/**
 * Created by nick on 01.03.16.
 */
public class GLPointLight extends GLLight {
    float[] position = { 0, 0, 0, 1 };

    public GLPointLight(int id) {
        super(id);
    }

    public void enable() {
        super.enable();
        GL10 gl = (GL10) Injector.main().getInstance(R.string.gl);
        gl.glLightfv(lightId, GL10.GL_POSITION, position, 0);
        lastLightId = lightId;
    }
    public void setPosition(float x, float y, float z) {
        position[0] = x;
        position[1] = y;
        position[2] = z;
    }

}
