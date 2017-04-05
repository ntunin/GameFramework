package com.ntunin.cybervision.journal.featureddetector.divider.ninepointsdivider;

import com.ntunin.cybervision.Releasable;
import com.ntunin.cybervision.injector.Injectable;
import com.ntunin.cybervision.journal.featureddetector.divider.DividerFactory;

import java.util.Map;

/**
 * Created by nikolay on 12.03.17.
 */

public class NinePointsDividerFactory extends DividerFactory implements Injectable {
    @Override
    protected Releasable create() {
        return new NinePointsDivider();
    }

    @Override
    public void init(Map<String, Object> data) {
        return;
    }
}
