package com.ntunin.cybervision.opengl.motion;

import javax.microedition.khronos.opengles.GL10;

import com.ntunin.cybervision.ercontext.Body;

/**
 * Created by nikolay on 17.10.16.
 */

public class GLRoll extends GLTransition {
    @Override
    public void act(Body b) {
        float roll = b.getRoll();
        gl.glRotatef(roll, 0, 0, 1);
    }
}
