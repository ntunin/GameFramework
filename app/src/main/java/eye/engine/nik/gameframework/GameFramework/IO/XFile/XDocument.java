package eye.engine.nik.gameframework.GameFramework.IO.XFile;

import eye.engine.nik.gameframework.GameFramework.Graphics.Frame;
import eye.engine.nik.gameframework.GameFramework.IO.GameIOException;
import eye.engine.nik.gameframework.GameFramework.IO.XFile.XStreamBuilder.XStreamBuilder;
import eye.engine.nik.gameframework.GameFramework.IO.XFile.XStreamReader.XTextStreamReader;

/**
 * Created by nikolay on 19.08.16.
 */
public class XDocument {
    private static XDocument document;

    public static Frame getFrame(XTextStreamReader reader) throws GameIOException {
        if(document == null) document = new XDocument();
        return document._getFrame(reader);
    }

    private Frame _getFrame(XTextStreamReader stream) throws GameIOException {
        Frame frame = XStreamBuilder.read(stream);
        return frame;
    }
    private XDocument() throws GameIOException {

    }

}
