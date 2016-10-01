package eye.engine.nik.gameframework;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import eye.engine.nik.gameframework.GameFramework.Graphics.Frame;
import eye.engine.nik.gameframework.GameFramework.IO.GameIOException;
import eye.engine.nik.gameframework.GameFramework.IO.XFile.XFile;

public class XFileLoaderTest {

    @Test
    public void absolutePath(){
        try {
            Frame f = XFile.loadFrame("box.text.x");
        } catch (GameIOException e) {
            assert false;
        }
    }
}
