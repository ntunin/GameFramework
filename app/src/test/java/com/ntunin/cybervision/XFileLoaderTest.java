package com.ntunin.cybervision;
import com.ntunin.cybervision.virtualmanagement.crvactor.CRVSkin.CRVSkin;
import com.ntunin.cybervision.io.xfile.XFile;

import org.junit.Test;


public class XFileLoaderTest {

    @Test
    public void absolutePath(){
        CRVSkin f = XFile.loadFrame("box.text.x");
    }
}
