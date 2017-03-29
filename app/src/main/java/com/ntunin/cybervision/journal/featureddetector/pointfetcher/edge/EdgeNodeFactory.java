package com.ntunin.cybervision.journal.featureddetector.pointfetcher.edge;

import com.ntunin.cybervision.Releasable;
import com.ntunin.cybervision.ReleasableFactory;

/**
 * Created by nikolay on 26.03.17.
 */

public class EdgeNodeFactory extends ReleasableFactory {

    @Override
    protected String getTag() {
        return "Edge Node";
    }

    @Override
    protected Releasable create() {
        return new EdgeNode();
    }
}
