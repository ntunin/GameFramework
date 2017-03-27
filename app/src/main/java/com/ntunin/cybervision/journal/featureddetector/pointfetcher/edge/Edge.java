package com.ntunin.cybervision.journal.featureddetector.pointfetcher.edge;

import com.ntunin.cybervision.ERRNO;
import com.ntunin.cybervision.ErrCodes;
import com.ntunin.cybervision.ObjectFactory;
import com.ntunin.cybervision.Releasable;
import com.ntunin.cybervision.injector.Injector;

import java.util.LinkedList;
import java.util.List;

import math.intpoint.Point;

/**
 * Created by nikolay on 12.03.17.
 */

public class Edge extends Releasable {
    private Edge next;
    private EdgeNode first;
    private EdgeNode last;
    private int size = 0;
    private Injector injector;
    private ObjectFactory factory;

    public void add(Point p) {
        EdgeNode node = newEdgeNode(p);
        if(node == null) return;
        if(first == null) {
            first = node;
        }
        node.next = last;
        last = node;
        size++;
    }

    private EdgeNode newEdgeNode(Point p) {
        if(factory == null) {
            ERRNO.write(ErrCodes.NOT_INITIALIZED);
            return null;
        }
        return (EdgeNode) factory.get("Edge Node").init(p);
    }

    public int getSize() {
        if(next != null) {
            return next.getSize();
        } else {
            return this.size;
        }
    }


    @Override
    public void release() {
        first = null;
        last = null;
        next = null;
        super.release();
    }

    @Override
    public Edge init(Object... args) {
        this.injector = Injector.main();
        this.factory = (ObjectFactory) this.injector.getInstance("Object Factory");
        return this;
    }

    public  void merge(Edge edge) {
        if(this.next == null) {
            this.mergeAsRoot(edge);
        } else {
            this.next.merge(edge);
        }
    }

    private void mergeAsRoot(Edge edge) {
        this.size+= edge.getSize();
        int choice = getActionChoiceForEdge(edge);
        switch (choice) {
            case 0: {
                edge.inverse();
                edge.last.add(this.first);
                this.first = edge.first;
                break;
            }
            case 1: {
                edge.inverse();
                this.last.add(edge.first);
                this.last = edge.last;
                break;
            }
            case 2: {
                this.last.add(edge.first);
                this.last = edge.last;
                break;
            }
            case 3: {
                edge.last.add(this.first);
                this.last = edge.first;
                break;
            }
        }
        this.next = edge;
    }

    private int getActionChoiceForEdge(Edge edge) {
        int[] distances = getDistancesSqr(edge);
        int min = distances[0];
        int choice = 0;
        for(int i = 1; i < distances.length; i++) {
            int d = distances[0];
            if(d < min) {
                min = d;
                choice = i;
            }
        }
        return choice;
    }

    private int[] getDistancesSqr(Edge edge) {
        Point f1 = this.first.point;
        Point f2 = edge.first.point;
        Point l1 = this.last.point;
        Point l2 = edge.last.point;
        int[] distances = new int[]{
                Math.abs(f1.x*f1.x - f2.x*f2.x),
                Math.abs(l1.x*l1.x - l2.x*l2.x),
                Math.abs(l1.x*l1.x - f2.x*f2.x),
                Math.abs(f1.x*f1.x - l2.y*l2.y)
        };
        return distances;
    }

    public boolean isRoot() {
        return next == null;
    }

    private void inverse() {
        EdgeNode node = this.first;
        node.inverse();
        this.first = this.last;
        this.last = node;
    }

}
