package eye.engine.nik.gameframework;


import eye.engine.nik.gameframework.GameFramework.Game.Screen;
import eye.engine.nik.gameframework.GameFramework.Graphics.OpenGL.GLGame;

/**
 * Created by nik on 08.04.16.
 */
public class LightTest extends GLGame {
    @Override
    public void resume() {
        super.onResume();
    }

    @Override
    public void update(float deltaTime) {

    }

    @Override
    public void present(float deltaTime) {

    }

    @Override
    public void pause() {
        super.onPause();
    }

    @Override
    public void dispose() {
    }

    @Override
    public Screen getStartScreen() {
        return new LightScreen(this);
    }
}
