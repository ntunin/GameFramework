package eye.engine.nik.gameframework.GameFramework.Graphics;

import java.util.HashMap;

/**
 * Created by nikolay on 12.09.16.
 */
public class FrameSet {
    HashMap<String, Frame> set = new HashMap<>();

    public void add(String name, Frame frame) {
        set.put(name, frame);
    }
}
