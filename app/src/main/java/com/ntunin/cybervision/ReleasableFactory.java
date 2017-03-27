package com.ntunin.cybervision;

/**
 * Created by nikolay on 11.03.17.
 */

public abstract class ReleasableFactory {
    protected String tag = "Releasable";

    public Releasable get() {
        Releasable object = create();
        object.setTag(tag);
        return  object;
    }

    protected abstract Releasable create();
}
