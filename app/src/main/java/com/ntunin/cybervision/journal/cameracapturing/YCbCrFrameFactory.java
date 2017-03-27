package com.ntunin.cybervision.journal.cameracapturing;

import com.ntunin.cybervision.Releasable;

/**
 * Created by nikolay on 11.02.17.
 */

public class YCbCrFrameFactory extends ImageFrameFactory {

    @Override
    protected Releasable create() {
        return new YCbCrFrame();
    }
}
