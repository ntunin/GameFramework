package eye.engine.nik.gameframework.GameFramework.Graphics.OpenGL.motion;

import javax.microedition.khronos.opengles.GL10;

import eye.engine.nik.gameframework.GameFramework.Game.Body;
import eye.engine.nik.gameframework.GameFramework.Graphics.OpenGL.screen.CVGLGame;
import eye.engine.nik.gameframework.GameFramework.Graphics.OpenGL.screen.GLGame;
import eye.engine.nik.gameframework.GameFramework.Graphics.OpenGL.screen.GLGraphics;

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
