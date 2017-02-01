package eye.engine.nik.gameframework;

import org.opencv.android.OpenCVLoader;

import eye.engine.nik.gameframework.GameFramework.Game.Screen;
import eye.engine.nik.gameframework.GameFramework.Graphics.OpenGL.screen.CVGLGame;
import eye.engine.nik.gameframework.GameFramework.Graphics.OpenGL.screen.CVGLScreen;

/**
 * Created by nikolay on 25.01.17.
 */

public class CVGLTest extends CVGLGame {

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
        return new CVGLScreen();
    }
}
