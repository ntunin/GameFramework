package com.ntunin.cybervision.opengl.screen;

/**
 * Created by mikhaildomrachev on 19/10/2016.
 */
public class GLGameScreen extends GLScreen{
    public GLGameScreen() {
        super();
    }

    @Override
    public void resume() {
        GLGame.current().resume();
    }

    @Override
    public void update(float deltaTime) {
        GLGame.current().update(deltaTime);
    }
    @Override
    public void present(float deltaTime) {
        GLGame.current().present(deltaTime);
    }

    @Override
    public void pause() {
        GLGame.current().pause();
    }

    @Override
    public void dispose() {
        GLGame.current().dispose();
    }
}
