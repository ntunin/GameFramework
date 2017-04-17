package com.ntunin.cybervision.journal.featureddetector.pointfetcher;

import com.ntunin.cybervision.releasable.ReleasableFactory;

/**
 * Created by nikolay on 11.03.17.
 */

public abstract class PontFetcherFactory  extends ReleasableFactory{

    @Override
    protected String getTag() {
        return "Point Fetcher";
    }
}
