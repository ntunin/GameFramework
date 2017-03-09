package com.ntunin.cybervision.opengl.screen;

import javax.microedition.khronos.opengles.GL10;

import com.ntunin.cybervision.game.Screen;

/**
 * Created by mikhaildomrachev on 19/10/2016.
 */
public class GLScreenedGame  extends GLGame {

    public GLScreenedGame() {
        super();
    }

    @Override
    public Screen getStartScreen() {
        return new GLGameScreen();
    }

    @Override
    public void resume() {

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
    public void dispose() {

    }

    protected void clean(float r, float g, float b, float a) {
        GL10 gl = glGraphics.getGL();
        gl.glClearColor(0f, 0f, 0f, 0f);
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
        gl.glEnable(GL10.GL_DEPTH_TEST);
    }
}
