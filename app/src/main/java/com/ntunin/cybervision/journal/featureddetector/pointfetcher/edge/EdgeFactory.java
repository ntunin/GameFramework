package com.ntunin.cybervision.journal.featureddetector.pointfetcher.edge;

import com.ntunin.cybervision.Releasable;
import com.ntunin.cybervision.ReleasableFactory;

/**
 * Created by nikolay on 12.03.17.
 */

public class EdgeFactory extends ReleasableFactory {

    public EdgeFactory() {
        this.tag = "Edge";
    }

    @Override
    protected Releasable create() {
        return new Edge();
    }
}
