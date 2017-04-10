package com.ntunin.cybervision.journal.featureddetector.pointfetcher.edge;

import com.ntunin.cybervision.Releasable;
import com.ntunin.cybervision.ReleasableFactory;
import com.ntunin.cybervision.ResMap;
import com.ntunin.cybervision.injector.Injectable;

import java.util.Map;

/**
 * Created by nikolay on 28.03.17.
 */

public class EdgeRegisterFactory extends ReleasableFactory implements Injectable{

    @Override
    protected String getTag() {
        return "Edge Register";
    }

    @Override
    protected Releasable create() {
        return new EdgeRegister();
    }

    @Override
    public void init(ResMap<String, Object> data) {
        return;
    }
}
