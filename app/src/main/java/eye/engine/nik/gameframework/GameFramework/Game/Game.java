package eye.engine.nik.gameframework.GameFramework.Game;

import eye.engine.nik.gameframework.GameFramework.Audio.Audio;
import eye.engine.nik.gameframework.GameFramework.Graphics.Graphics;
import eye.engine.nik.gameframework.GameFramework.IO.FileIO;
import eye.engine.nik.gameframework.GameFramework.IO.Input;

public interface Game {
    public Input getInput();

    public FileIO getFileIO();

    public Graphics getGraphics();

    public Audio getAudio();

    public void setScreen(Screen screen);

    public Screen getCurrentScreen();

    public Screen getStartScreen();
}
