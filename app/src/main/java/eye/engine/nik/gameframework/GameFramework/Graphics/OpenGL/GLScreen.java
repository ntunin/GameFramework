package eye.engine.nik.gameframework.GameFramework.Graphics.OpenGL;

import eye.engine.nik.gameframework.GameFramework.Game.Game;
import eye.engine.nik.gameframework.GameFramework.Game.Screen;

/**
 * Created by nik on 08.04.16.
 */
public class GLScreen extends Screen {

    protected final GLGraphics glGraphics;
    protected final GLGame glGame;

    public GLScreen(Game game) {
        super(game);
        glGame = (GLGame)game;
        glGraphics = ((GLGame)game).getGLGraphics();
    }

    @Override
    public void update(float deltaTime) {

    }

    @Override
    public void present(float deltaTime) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }
}
