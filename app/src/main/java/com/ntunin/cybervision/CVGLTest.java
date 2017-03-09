package com.ntunin.cybervision;


import com.ntunin.cybervision.game.Screen;
import com.ntunin.cybervision.opengl.screen.CVGLGame;
import com.ntunin.cybervision.opengl.screen.CVGLScreen;

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
