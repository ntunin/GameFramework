package com.ntunin.cybervision.journal.featureddetector.pointfetcher;

import com.ntunin.cybervision.ObjectFactory;
import com.ntunin.cybervision.R;
import com.ntunin.cybervision.Releasable;
import com.ntunin.cybervision.Res;
import com.ntunin.cybervision.injector.Injector;
import com.ntunin.cybervision.journal.cameracapturing.ImageFrame;
import com.ntunin.cybervision.journal.featureddetector.divider.Divider;
import com.ntunin.cybervision.journal.featureddetector.pointfetcher.edge.Edge;

import java.util.List;

import math.intpoint.Point;

/**
 * Created by nikolay on 11.03.17.
 */

public class PointFetcher extends Releasable {

    private ImageFrame frame;
    protected Divider divider;
    protected ObjectFactory factory;

    public List<Edge> start(ImageFrame frame) {
        this.frame = frame;
         return null;
    }

    @Override
    public PointFetcher init(Object... args) {
        factory = (ObjectFactory) Injector.main().getInstance(Res.string(R.string.object_factory));
        return this;
    }
}
