package com.ntunin.cybervision.opengl.motion;

import javax.microedition.khronos.opengles.GL10;

import com.ntunin.cybervision.ercontext.Body;

/**
 * Created by nikolay on 17.10.16.
 */

public class GLYaw extends GLTransition {
    @Override
    public void act(Body b) {
        float yaw = b.getYaw();
        gl.glRotatef(yaw, 0, 1, 0);
    }
}
