package com.ntunin.cybervision.journal.featureddetector.pointfetcher.edge;

import com.ntunin.cybervision.releasable.Releasable;
import com.ntunin.cybervision.releasable.ReleasableFactory;
import com.ntunin.cybervision.res.ResMap;
import com.ntunin.cybervision.injector.Injectable;

/**
 * Created by nikolay on 28.03.17.
 */

public class EdgeRootFactory extends ReleasableFactory implements Injectable{
    @Override
    protected String getTag() {
        return "Edge Root";
    }

    @Override
    protected Releasable create() {
        return new EdgeRoot();
    }

    @Override
    public void init(ResMap<String, Object> data) {

    }
}
