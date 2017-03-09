package com.ntunin.cybervision.opengl.motion;

import javax.microedition.khronos.opengles.GL10;

import com.ntunin.cybervision.game.Body;
import com.ntunin.cybervision.opengl.screen.CVGLGame;
import com.ntunin.cybervision.opengl.screen.GLGraphics;

/**
 * Created by nikolay on 17.10.16.
 */

public abstract class GLTransition {
    protected GL10 getGL() {
        GLGraphics glGraphics = CVGLGame.current().getGLGraphics();
        return glGraphics.getGL();
    }
    public abstract void act(Body b);
}
