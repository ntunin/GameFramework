package com.ntunin.cybervision.journal.featureddetector.divider;

import com.ntunin.cybervision.ReleasableFactory;

/**
 * Created by nikolay on 12.03.17.
 */

public abstract class DividerFactory extends ReleasableFactory{
    public DividerFactory() {
        this.tag = "Divider";
    }
}
