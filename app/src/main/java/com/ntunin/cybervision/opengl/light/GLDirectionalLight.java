package com.ntunin.cybervision.opengl.light;

import javax.microedition.khronos.opengles.GL10;

import com.ntunin.cybervision.opengl.screen.GLGame;

/**
 * Created by nick on 01.03.16.
 */
public class GLDirectionalLight extends GLLight {
    float[] direction = { 0, 0, -1, 0 };

    public GLDirectionalLight(int id) {
        super(id);
    }

    public void enable(int lightId) {
        GL10 gl = GLGame.current().getGLGraphics().getGL();
        gl.glLightfv(lightId, GL10.GL_POSITION, direction, 0);
        lastLightId = lightId;
    }
    public void setDirection(float x, float y, float z) {
        direction[0] = -x;
        direction[1] = -y;
        direction[2] = -z;
    }
}
