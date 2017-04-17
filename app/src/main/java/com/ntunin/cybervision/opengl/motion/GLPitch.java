package com.ntunin.cybervision.opengl.motion;

import javax.microedition.khronos.opengles.GL10;

import com.ntunin.cybervision.ercontext.Body;

/**
 * Created by nikolay on 17.10.16.
 */

public class GLPitch extends GLTransition {
    @Override
    public void act(Body b) {
        float pitch = b.getPitch();
        gl.glRotatef(pitch, 1, 0, 0);
    }
}
