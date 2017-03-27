package com.ntunin.cybervision.journal.cameracapturing;

import com.ntunin.cybervision.ReleasableFactory;

/**
 * Created by nikolay on 11.02.17.
 */

public abstract class ImageFrameFactory extends ReleasableFactory {
    public ImageFrameFactory() {
        this.tag = "Image Frame";
    }
}
