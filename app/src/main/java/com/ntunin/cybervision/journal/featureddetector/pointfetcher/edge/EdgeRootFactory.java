package com.ntunin.cybervision.journal.featureddetector.pointfetcher.edge;

import com.ntunin.cybervision.Releasable;
import com.ntunin.cybervision.ReleasableFactory;

/**
 * Created by nikolay on 28.03.17.
 */

public class EdgeRootFactory extends ReleasableFactory {
    @Override
    protected String getTag() {
        return "Edge Root";
    }

    @Override
    protected Releasable create() {
        return new EdgeRoot();
    }
}
