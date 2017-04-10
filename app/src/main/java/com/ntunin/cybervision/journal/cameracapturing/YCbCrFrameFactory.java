package com.ntunin.cybervision.journal.cameracapturing;

import com.ntunin.cybervision.Releasable;
import com.ntunin.cybervision.ResMap;
import com.ntunin.cybervision.injector.Injectable;

import java.util.Map;

/**
 * Created by nikolay on 11.02.17.
 */

public class YCbCrFrameFactory extends ImageFrameFactory implements Injectable {

    @Override
    protected Releasable create() {
        return new YCbCrFrame();
    }

    @Override
    public void init(ResMap<String, Object> data) {
        return;
    }
}
