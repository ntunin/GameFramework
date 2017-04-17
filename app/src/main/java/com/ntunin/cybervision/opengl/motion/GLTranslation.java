package com.ntunin.cybervision.opengl.motion;

import javax.microedition.khronos.opengles.GL10;

import math.vector.Vector3;
import com.ntunin.cybervision.ercontext.Body;

/**
 * Created by nikolay on 17.10.16.
 */

public class GLTranslation extends GLTransition {

    @Override
    public void act(Body b) {
        Vector3 r = b.getR();
        gl.glTranslatef(r.x, r.y, r.z);
    }
}
