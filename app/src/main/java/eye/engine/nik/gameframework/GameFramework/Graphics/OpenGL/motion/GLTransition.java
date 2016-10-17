package eye.engine.nik.gameframework.GameFramework.Graphics.OpenGL.motion;

import javax.microedition.khronos.opengles.GL10;

import eye.engine.nik.gameframework.GameFramework.Game.Body;
import eye.engine.nik.gameframework.GameFramework.Graphics.OpenGL.GLGame;
import eye.engine.nik.gameframework.GameFramework.Graphics.OpenGL.GLGraphics;

/**
 * Created by nikolay on 17.10.16.
 */

public abstract class GLTransition {
    protected GL10 getGL() {
        GLGraphics glGraphics = GLGame.current().getGLGraphics();
        return glGraphics.getGL();
    }
    public abstract void act(Body b);
}
