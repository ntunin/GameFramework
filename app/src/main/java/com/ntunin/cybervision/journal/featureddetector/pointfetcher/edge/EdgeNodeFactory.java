package com.ntunin.cybervision.journal.featureddetector.pointfetcher.edge;

import com.ntunin.cybervision.Releasable;
import com.ntunin.cybervision.ReleasableFactory;
import com.ntunin.cybervision.injector.Injectable;

import java.util.Map;

/**
 * Created by nikolay on 26.03.17.
 */

public class EdgeNodeFactory extends ReleasableFactory implements Injectable {

    @Override
    protected String getTag() {
        return "Edge Node";
    }

    @Override
    protected Releasable create() {
        return new EdgeNode();
    }

    @Override
    public void init(Map<String, Object> data) {

    }
}
