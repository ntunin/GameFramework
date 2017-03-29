package com.ntunin.cybervision.journal.featureddetector.pointfetcher.edge;

import com.ntunin.cybervision.Releasable;

import java.util.LinkedList;
import java.util.List;

import math.intpoint.Point;

/**
 * Created by nikolay on 28.03.17.
 */

public class EdgeRoot extends Releasable {

    Point point;

    List<Edge> edges;


    public Point getPoint() {
        return point;
    }

    public void link(Edge edge) {
        edges.add(edge);
    }

    @Override
    public Releasable init(Object... args) {
        this.edges = new LinkedList<>();
        if(args.length == 1) {
            this.point = (Point) args[0];
        }
        return this;
    }
}
