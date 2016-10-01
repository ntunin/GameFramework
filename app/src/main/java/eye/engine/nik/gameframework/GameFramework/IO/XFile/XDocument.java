package eye.engine.nik.gameframework.GameFramework.IO.XFile;

import eye.engine.nik.gameframework.GameFramework.Graphics.Frame;
import eye.engine.nik.gameframework.GameFramework.IO.GameIOException;
import eye.engine.nik.gameframework.GameFramework.IO.XFile.XFrameBuilder.XFrameBuilder;
import eye.engine.nik.gameframework.GameFramework.IO.XFile.XStreamReader.XStringStreamReader;
import eye.engine.nik.gameframework.GameFramework.IO.XFile.XValueReader.XFloatReader;
import eye.engine.nik.gameframework.GameFramework.IO.XFile.XValueReader.XIntReader;
import eye.engine.nik.gameframework.GameFramework.IO.XFile.XValueReader.XDocumentRegister;
import eye.engine.nik.gameframework.GameFramework.IO.XFile.XValueReader.XNode;
import eye.engine.nik.gameframework.GameFramework.IO.XFile.XValueReader.XShortReader;
import eye.engine.nik.gameframework.GameFramework.IO.XFile.XValueReader.XStringReader;
import eye.engine.nik.gameframework.GameFramework.IO.XFile.XValueReader.XTemplate;
import eye.engine.nik.gameframework.GameFramework.IO.XFile.XStreamReader.XTextStreamReader;

/**
 * Created by nikolay on 19.08.16.
 */
public class XDocument {
    private XTemplate documentTemplate;
    private static XDocument document;

    public static Frame getFrame(XTextStreamReader reader) throws GameIOException {
        if(document == null) document = new XDocument();
        return document._getFrame(reader);
    }

    private Frame _getFrame(XTextStreamReader reader) throws GameIOException {
        XNode document = (XNode) documentTemplate.readValue(reader);
        Frame frame = XFrameBuilder.get(document);
        return frame;
    }
    private XDocument() throws GameIOException {

        documentTemplate = new XTemplate(null, new XStringStreamReader(
                "document {" +
                    "[...]" +
                "}"
        ));
        registerTableItems();
    }

    private void registerTableItems() throws GameIOException {
        XDocumentRegister table = XDocumentRegister.table();

        table.register("FLOAT", new XFloatReader());
        table.register("DWORD", new XIntReader());
        table.register("WORD", new XShortReader());
        table.register("STRING", new XStringReader());

    }

}
