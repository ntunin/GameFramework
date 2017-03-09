package com.ntunin.cybervision.io.xfile;

import com.ntunin.cybervision.graphics.GLDress;
import com.ntunin.cybervision.io.GameIOException;
import com.ntunin.cybervision.io.xfile.xsreambuilder.XStreamBuilder;
import com.ntunin.cybervision.io.xfile.xstreamreader.XTextStreamReader;

/**
 * Created by nikolay on 19.08.16.
 */
public class XDocument {
    private static XDocument document;

    public static GLDress getFrame(XTextStreamReader reader) throws GameIOException {
        if(document == null) document = new XDocument();
        return document._getFrame(reader);
    }

    private GLDress _getFrame(XTextStreamReader stream) throws GameIOException {
        GLDress frame = XStreamBuilder.read(stream);
        return frame;
    }
    private XDocument() throws GameIOException {

    }

}
