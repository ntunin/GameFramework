package com.ntunin.cybervision.journal.cameracapturing;

import android.graphics.Bitmap;

import com.ntunin.cybervision.objectfactory.ObjectFactory;
import com.ntunin.cybervision.R;
import com.ntunin.cybervision.releasable.Releasable;
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
        factory = (ObjectFactory) Injector.main().getInstance(R.string.object_factory);
    }

    public ImageFrame clone() {
        ImageFrame clone = (ImageFrame) factory.get(R.string.image_frame).init(this.size.width, this.size.height);
        clone.put(data.clone());
        return clone;
    }


    public void put(byte[] frame) {
        if(frame == null) {
            data = null;
        } else if(data != null && data.length == frame.length) {
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
        super.release();
    }


    @Override
    public ImageFrame init(Object... args) {
        if(args.length >= 2) {
            this.size = (Size) factory.get(R.string.int_size).init(args[0], args[1]);
        }
        return this;
    }
}
