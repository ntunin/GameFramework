package com.ntunin.cybervision.journal.featureddetector.pointfetcher.edge;

import com.ntunin.cybervision.Releasable;
import com.ntunin.cybervision.ReleasableFactory;

/**
 * Created by nikolay on 28.03.17.
 */

public class EdgeRegisterFactory extends ReleasableFactory {

    @Override
    protected String getTag() {
        return "Edge Register";
    }

    @Override
    protected Releasable create() {
        return new EdgeRegister();
    }
}
