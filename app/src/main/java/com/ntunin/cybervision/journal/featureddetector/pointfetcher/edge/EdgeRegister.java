package com.ntunin.cybervision.journal.featureddetector.pointfetcher.edge;

import android.util.Log;

import com.ntunin.cybervision.Releasable;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import math.intpoint.Point;
import math.intsize.Size;

/**
 * Created by nikolay on 28.03.17.
 */

public class EdgeRegister extends Releasable {

    private Map<Integer, EdgeNode> nodeTable;
    private Map<Integer, EdgeRoot> rootTable;
    private Map<Integer, Edge> edgeCache;
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

    public Edge edgeFor(Point point) {
        EdgeNode start = readNode(point);
        EdgeNode node = start;
        boolean cycled = false;
        while(node.prev != null) {
            if(cycled) {
                EdgeRoot root = readRoot(node.point);
                if(root != null) {
                    break;
                } else {
                    node = node.prev;
                    if(node == start) {
                        return null;
                    }
                }
            } else {
                node = node.prev;
                Edge cached = edgeCache.get(hash(point));
                if(cached != null) {
                    return cached;
                }
                if(node == start) {
                    cycled = true;
                }
            }

        }
        point = node.point;
        EdgeRoot root = readRoot(point);
        Edge edge = root.edge;
        edgeCache.put(hash(point), edge);
        return edge;
    }

    public List<Edge> readAllEdges() {
        List<Edge> result = new LinkedList<>();
        Set<Integer> keys = rootTable.keySet();
        for(Integer key : keys ) {
            EdgeRoot root = rootTable.get(key);
            if(root != null) {
                result.add(root.edge);
            }
        }
        return result;
    }


    public void clearCache() {
        this.edgeCache = new HashMap<>();
    }

    private int hash(Point point) {
        return size.width * point.y + point.x;
    }

    @Override
    public Releasable init(Object... args) {
        this.nodeTable = new HashMap<>();
        this.rootTable = new HashMap<>();
        this.edgeCache = new HashMap<>();
        if(args.length > 0) {
            size = (Size) args[0];
        }
        return this;
    }

    @Override
    public void release() {
        super.release();
        for(Integer hash: rootTable.keySet()) {
            EdgeRoot r = rootTable.get(hash);
            if(r != null) {
                r.release();

            }
        }
        for(Integer hash: nodeTable.keySet()) {
            EdgeNode n = nodeTable.get(hash);
            if(n != null) {
                n.release();
            }
        }
    }
}
