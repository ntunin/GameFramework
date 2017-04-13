package com.ntunin.cybervision.opengl.screen;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;

import com.ntunin.cybervision.android.io.AndroidFileIO;
import com.ntunin.cybervision.audio.Audio;
import com.ntunin.cybervision.game.Game;
import com.ntunin.cybervision.game.Screen;
import com.ntunin.cybervision.graphics.Graphics;
import com.ntunin.cybervision.injector.Injector;
import com.ntunin.cybervision.io.FileIO;
import com.ntunin.cybervision.io.Input;

/**
 * Created by nikolay on 05.04.17.
 */

public class CVGLGameEmpty extends Game {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        current = this;
    }

    @Override
    public Input getInput() {
        return null;
    }

    @Override
    public FileIO getFileIO() {
        return null;
    }

    @Override
    public Graphics getGraphics() {
        return null;
    }

    @Override
    public Audio getAudio() {
        return null;
    }

    @Override
    public void setScreen(Screen screen) {

    }

    @Override
    public Screen getCurrentScreen() {
        return null;
    }

    @Override
    public Screen getStartScreen() {
        return null;
    }
}
