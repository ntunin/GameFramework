package eye.engine.nik.gameframework.GameFramework.IO.XFile;

import eye.engine.nik.gameframework.GameFramework.Graphics.Frame;
import eye.engine.nik.gameframework.GameFramework.IO.GameIOException;
import eye.engine.nik.gameframework.GameFramework.IO.XFile.XStreamBuilder.XStreamBuilder;
import eye.engine.nik.gameframework.GameFramework.IO.XFile.XStreamReader.XTextStreamReader;

/**
 * Created by nik on 20.06.16.
 */
public class XText implements XFactory {
    private static XText _factory = new XText();
    public static XText factory() {
        return _factory;
    }
    public Frame loadFrame(XTextStreamReader reader) throws GameIOException {
        Frame document = XStreamBuilder.read(reader);
        return document;
    }

}
