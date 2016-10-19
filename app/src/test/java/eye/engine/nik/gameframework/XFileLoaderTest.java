package eye.engine.nik.gameframework;
import org.junit.Test;

import eye.engine.nik.gameframework.GameFramework.Graphics.GLDress;
import eye.engine.nik.gameframework.GameFramework.IO.GameIOException;
import eye.engine.nik.gameframework.GameFramework.IO.XFile.XFile;

public class XFileLoaderTest {

    @Test
    public void absolutePath(){
        try {
            GLDress f = XFile.loadFrame("box.text.x");
        } catch (GameIOException e) {
            assert false;
        }
    }
}
