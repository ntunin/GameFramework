package eye.engine.nik.gameframework.GameFramework.IO.XFile;

import eye.engine.nik.gameframework.GameFramework.Graphics.GLDress;
import eye.engine.nik.gameframework.GameFramework.IO.XFile.XStreamReader.XTextStreamReader;

/**
 * Created by nik on 20.06.16.
 */
public interface XFactory {
    GLDress loadFrame(XTextStreamReader reader);
}
