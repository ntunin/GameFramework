package com.ntunin.cybervision.opengl.camera;


import android.opengl.GLU;

import javax.microedition.khronos.opengles.GL10;

import math.vector.Vector3;

import com.ntunin.cybervision.R;
import com.ntunin.cybervision.ercontext.ERContext;
import com.ntunin.cybervision.injector.Injector;
import com.ntunin.cybervision.opengl.graphics.GLGraphics;

/**
 * Created by nikolay on 12.10.16.
 */

public class PerspectiveCamera implements Camera {
    protected PerspectiveRacurs racurs;
    public PerspectiveCamera() {
        this.racurs = new PerspectiveRacurs(new Vector3(0, 0, 10));
    }
    public PerspectiveCamera(PerspectiveRacurs racurs) {
        this.racurs = racurs;
    }
    @Override
    public void motor() {
        GLGraphics glGraphics = (GLGraphics) ERContext.get(R.string.graphics);
        int height = GLGraphics.getHeight();
        int width = GLGraphics.getWidth();
        GL10 gl = glGraphics.getGL();


        gl.glViewport(0, 0, width, height);
        gl.glMatrixMode(GL10.GL_PROJECTION);
        gl.glLoadIdentity();
        GLU.gluPerspective(gl, racurs.angle, 1f*width / height, racurs.near, racurs.far);
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();
        Vector3 position = racurs.position;
        Vector3 target = racurs.target;
        Vector3 up = racurs.up;

        GLU.gluLookAt(gl, position.x, position.y, position.z, target.x, target.y, target.z, up.x, up.y, up.z);
        gl.glPushMatrix();
    }

    @Override
    public void stop() {
        GL10 gl = (GL10) ERContext.get(R.string.gl);
        gl.glPopMatrix();
    }
}
