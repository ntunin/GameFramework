package com.ntunin.cybervision.journal.cameracapturing;

import android.graphics.Bitmap;

import com.ntunin.cybervision.Size;

/**
 * Created by nikolay on 11.02.17.
 */


public abstract class CameraFrame {

    protected byte[] data;
    protected Size size;

    public CameraFrame(int width, int height) {
        this.size = new Size(width, height);
    }

    public void put(byte[] frame) {
        if(data != null && data.length == frame.length) {
            for(int i = 0; i < frame.length; i++) {
                data[i] = frame[i];
            }
        } else {
            this.data = frame.clone();
        }
    }

    public Size size() {
        return size;
    }

    public abstract void put(int x, int y, int r, int g, int b);
    public abstract int[] get(int x, int y);
    public abstract int getBrightness(int x, int y);

    public abstract Bitmap getBitmap();

}
