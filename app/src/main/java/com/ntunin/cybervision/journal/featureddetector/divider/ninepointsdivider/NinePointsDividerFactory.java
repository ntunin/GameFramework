package com.ntunin.cybervision.journal.featureddetector.divider.ninepointsdivider;

import com.ntunin.cybervision.Releasable;
import com.ntunin.cybervision.journal.featureddetector.divider.DividerFactory;

/**
 * Created by nikolay on 12.03.17.
 */

public class NinePointsDividerFactory extends DividerFactory {
    @Override
    protected Releasable create() {
        return new NinePointsDivider();
    }
}
