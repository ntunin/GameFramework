package com.ntunin.cybervision.journal.featureddetector.pointfetcher.edge;

import com.ntunin.cybervision.Releasable;

import math.intpoint.Point;

/**
 * Created by nikolay on 26.03.17.
 */

public class EdgeNode extends Releasable{
    EdgeNode next;
    EdgeNode prev;
    Point point;

    void add(EdgeNode next) {
        this.next = next;
        next.prev = this;
    }

    void inverse() {
        if(this.next != null) {
            this.next.inverse();
        }
        EdgeNode node = this.next;
        this.next = this.prev;
        this.prev = node;
    }

    @Override
    public Releasable init(Object... args) {
        if(args.length == 1) {
            this.point = (Point) args[0];
        }
        return this;
    }
}
