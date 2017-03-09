package com.ntunin.cybervision.opengl.screen;

import com.ntunin.cybervision.game.Screen;

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
