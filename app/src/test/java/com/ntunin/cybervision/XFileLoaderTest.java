package com.ntunin.cybervision;
import com.ntunin.cybervision.graphics.GLDress;
import com.ntunin.cybervision.io.xfile.XFile;

import org.junit.Test;


public class XFileLoaderTest {

    @Test
    public void absolutePath(){
        GLDress f = XFile.loadFrame("box.text.x");
    }
}
