package com.ntunin.cybervision.journal.featureddetector.pointfetcher.nautilus;

import com.ntunin.cybervision.Releasable;
import com.ntunin.cybervision.journal.featureddetector.pointfetcher.PontFetcherFactory;

/**
 * Created by nikolay on 11.03.17.
 */

public class NautilusFactory extends PontFetcherFactory {
    @Override
    protected Releasable create() {
        return new Nautilus();
    }
}
