package com.ntunin.cybervision.journal.featureddetector.pointfetcher.edge;

import com.ntunin.cybervision.Releasable;

import java.util.HashMap;
import java.util.Map;

import math.intpoint.Point;
import math.intsize.Size;

/**
 * Created by nikolay on 28.03.17.
 */

public class EdgeRegister extends Releasable {

    private Map<Integer, EdgeNode> nodeTable;
    private Map<Integer, EdgeRoot> rootTable;
    private Size size;

    public void writeNode(EdgeNode node) {
        Point point = node.getPoint();
        int hash = hash(point);
        nodeTable.put(hash, node);
    }

    public EdgeNode readNode(Point point) {
        int hash = hash(point);
        return nodeTable.get(hash);
    }

    public void writeRoot(EdgeRoot root) {
        Point point = root.getPoint();
        int hash = hash(point);
        rootTable.put(hash, root);
    }

    public EdgeRoot readRoot(Point point) {
        int hash = hash(point);
        return rootTable.get(hash);
    }

    public void removeNode(Point point) {
        int hash = hash(point);
        nodeTable.put(hash, null);
    }

    public void removeRoot(Point point) {
        int hash = hash(point);
        rootTable.put(hash, null);
    }


    private int hash(Point point) {
        return size.width * point.y + point.x;
    }

    @Override
    public Releasable init(Object... args) {
        this.nodeTable = new HashMap<>();
        this.rootTable = new HashMap<>();
        if(args.length > 0) {
            size = (Size) args[0];
        }
        return this;
    }
}
