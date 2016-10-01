package eye.engine.nik.gameframework.GameFramework.Graphics;

import java.util.ArrayList;
import java.util.List;

import eye.engine.nik.gameframework.GameFramework.IO.GameIOException;
import eye.engine.nik.gameframework.GameFramework.IO.XFile.XFile;

/**
 * Created by nikolay on 17.09.16.
 */
public class LightScene implements Scene{

    List<Frame> frames = new ArrayList<>();

    public LightScene() throws GraphicsException {

        try {
            Frame frame = XFile.loadFrame("animation.X");
            frames.add(frame);
        } catch (GameIOException e) {
            throw new GraphicsException("Could not load frame from \"animation.x\"");
        }
    }
    @Override
    public void draw() {
        for(Frame frame: frames) {
            frame.draw();
        }
    }
}
