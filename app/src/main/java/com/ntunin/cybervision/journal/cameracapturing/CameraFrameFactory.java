package com.ntunin.cybervision.journal.cameracapturing;

/**
 * Created by nikolay on 11.02.17.
 */

public abstract class CameraFrameFactory {
    public abstract CameraFrame createFrame(int width, int height);
}
