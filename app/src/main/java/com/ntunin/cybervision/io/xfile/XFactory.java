package com.ntunin.cybervision.io.xfile;

import com.ntunin.cybervision.opengl.graphics.GLDress;
import com.ntunin.cybervision.io.xfile.xstreamreader.XTextStreamReader;

/**
 * Created by nik on 20.06.16.
 */
public interface XFactory {
    GLDress loadFrame(XTextStreamReader reader);
}
