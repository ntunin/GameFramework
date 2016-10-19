package eye.engine.nik.gameframework.GameFramework.Graphics.OpenGL;

import eye.engine.nik.gameframework.GameFramework.Game.Game;
import eye.engine.nik.gameframework.GameFramework.Game.Screen;

/**
 * Created by nik on 08.04.16.
 */
public abstract class GLScreen extends Screen {

    protected final GLGraphics glGraphics;
    protected final GLGame glGame;

    public GLScreen() {
        super(GLGame.current());
        glGame = (GLGame)game;
        glGraphics = ((GLGame)game).getGLGraphics();
    }
}
