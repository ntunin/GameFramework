package com.ntunin.cybervision.game;

import com.ntunin.cybervision.audio.Audio;
import com.ntunin.cybervision.graphics.Graphics;
import com.ntunin.cybervision.io.FileIO;
import com.ntunin.cybervision.io.Input;

public interface Game {
    public Input getInput();

    public FileIO getFileIO();

    public Graphics getGraphics();

    public Audio getAudio();

    public void setScreen(Screen screen);

    public Screen getCurrentScreen();

    public Screen getStartScreen();
}
