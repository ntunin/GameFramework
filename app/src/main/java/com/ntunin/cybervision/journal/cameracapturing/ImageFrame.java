package com.ntunin.cybervision.journal.cameracapturing;

import android.graphics.Bitmap;

import com.ntunin.cybervision.ObjectFactory;
import com.ntunin.cybervision.Releasable;
import com.ntunin.cybervision.injector.Injector;

import math.intsize.Size;

/**
 * Created by nikolay on 11.02.17.
 */


public abstract class ImageFrame extends Releasable{

    protected byte[] data;
    protected Size size;
    private ObjectFactory factory;

    public ImageFrame() {
        factory = (ObjectFactory) Injector.main().getInstance("Object Factory");
    }

    public void set(int width, int height) {
        this.size = (Size) factory.get("Int Size").init(width, height);
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

    @Override
    public void release() {
        this.size.release();
        this.size = null;
        this.data = null;
        super.release();
    }
}
