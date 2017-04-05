package com.ntunin.cybervision.journal.featureddetector.pointfetcher.edge;

import com.ntunin.cybervision.Releasable;
import com.ntunin.cybervision.ReleasableFactory;
import com.ntunin.cybervision.injector.Injectable;

import java.util.Map;

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
    public void init(Map<String, Object> data) {

    }
}
