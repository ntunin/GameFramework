package com.ntunin.cybervision.journal.cameracapturing;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by nikolay on 11.02.17.
 */

public class YCbCrFrameFactory extends CameraFrameFactory {



    @Override
    public CameraFrame createFrame(int width, int height) {
        return new YCbCrFrame(width, height);
    }
}
