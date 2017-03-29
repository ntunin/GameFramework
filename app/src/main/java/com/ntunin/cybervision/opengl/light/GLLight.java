package com.ntunin.cybervision.opengl.light;

import javax.microedition.khronos.opengles.GL10;

import com.ntunin.cybervision.opengl.screen.CVGLGame;

/**
 * Created by nick on 01.03.16.
 */
public class GLLight {
    float[] ambient = { 0.2f, 0.2f, 0.2f, 1.0f };
    float[] diffuse = { 1.0f, 1.0f, 1.0f, 1.0f };
    float[] specular = { 0.0f, 0.0f, 0.0f, 1.0f };
    float[] position = { 0, 0, 0, 1 };

    protected static int lastLightId = 0;
    protected int lightId;
    public GLLight(int id) {
        this.lightId = id;
    }
    public GLLight() {
        this.lightId = 0;
    }
    public void setAmbient(float r, float g, float b, float a) {
        ambient = createColorFromRGBA(r, g, b, a);
    }
    public void setDiffuse(float r, float g, float b, float a) {
        diffuse = createColorFromRGBA(r, g, b, a);
    }
    public void setSpecular(float r, float g, float b, float a) {
        specular = createColorFromRGBA(r, g, b, a);
    }
    private float[] createColorFromRGBA(float r, float g, float b, float a) {
        float[] result = new float[4];
        result[0] = r;
        result[1] = g;
        result[2] = b;
        result[3] = a;
        return result;
    }
    public void enable() {
        GL10 gl = CVGLGame.current().getGLGraphics().getGL();
        gl.glEnable(lightId);
        gl.glLightfv(lightId, GL10.GL_AMBIENT, ambient, 0);
        gl.glLightfv(lightId, GL10.GL_DIFFUSE, diffuse, 0);
        gl.glLightfv(lightId, GL10.GL_SPECULAR, specular, 0);
        lastLightId = lightId;
    }
    public void disable(GL10 gl) {
        gl.glDisable(lastLightId);
    }
}