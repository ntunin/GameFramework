package com.ntunin.cybervision.game;

import android.app.Activity;

import com.ntunin.cybervision.audio.Audio;
import com.ntunin.cybervision.graphics.Graphics;
import com.ntunin.cybervision.io.FileIO;
import com.ntunin.cybervision.io.Input;
import com.ntunin.cybervision.opengl.screen.CVGLGame;

public abstract class Game extends Activity {
    protected static Game current;
    public static Game current() {
        return current;
    }
    public static void setCurrent(Game g) {
        current = g;
    }

    public abstract Input getInput();

    public abstract FileIO getFileIO();

    public abstract Graphics getGraphics();

    public abstract Audio getAudio();

    public abstract void setScreen(Screen screen);

    public abstract Screen getCurrentScreen();

    public abstract Screen getStartScreen();
}
