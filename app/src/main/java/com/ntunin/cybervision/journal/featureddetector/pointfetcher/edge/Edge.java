package com.ntunin.cybervision.journal.featureddetector.pointfetcher.edge;

import com.ntunin.cybervision.ERRNO;
import com.ntunin.cybervision.ErrCodes;
import com.ntunin.cybervision.ObjectFactory;
import com.ntunin.cybervision.Releasable;
import com.ntunin.cybervision.injector.Injector;

import org.w3c.dom.Node;

import math.intpoint.Point;

/**
 * Created by nikolay on 12.03.17.
 */

public class Edge extends Releasable {
    private ObjectFactory factory;
    private EdgeRoot root;
    private EdgeNode first;
    private EdgeNode last;
    private EdgeRegister table;
    private int size = 0;

    public void push(Point point) {
        if(factory == null) {
            ERRNO.write(ErrCodes.NOT_INITIALIZED);
            return;
        }
        EdgeNode node = (EdgeNode) factory.get("Edge Node").init(point);
        table.writeNode(node);
        if(this.root == null) {
            this.root = (EdgeRoot) factory.get("Edge Root").init(point, this);
            table.writeRoot(root);
            this.first = node;
            this.last = node;
            this.first.next = node;
        } else {
            this.last.push(node);
            this.last = node;
        }
    }

    public Point getFirst() {
        return first.point;
    }

    public Point getLast() {
        return last.point;
    }

    public Edge split(Point point) {
        if(table == null) {
            ERRNO.write(ErrCodes.NOT_INITIALIZED);
            return null;
        }

        EdgeNode node = table.readNode(point);
        if(node == null) {
            return null;
        } else if(node.prev == null) {
            return this;
        } else {
            table.removeNode(point);
            Edge second = (Edge) factory.get("Edge").init(table, node);
            node.next = null;
            second.last = this.last;
            this.last = node;
            return second;
        }
    }

    public void push(Edge edge) {
        this.last.next = edge.first;
        edge.first.prev = this.last;
        this.last = edge.last;
        table.removeRoot(edge.root.getPoint());
    }





    @Override
    public Releasable init(Object... args) {
        Injector injector = Injector.main();
        this.factory = (ObjectFactory) injector.getInstance("Object Factory");
        if(args.length >= 1) {
            table = (EdgeRegister) args[0];
        }
        if(args.length >= 2) {
            initFromNode((EdgeNode) args[1]);
        }
        return this;
    }

    private void initFromNode(EdgeNode node) {
        Point point = node.point;
        this.root = (EdgeRoot) factory.get("Edge Root").init(point, this);
        root.link(this);
        table.writeRoot(root);
        this.first = (EdgeNode) factory.get("Edge Node").init(point);
        this.first.next = node.next;
    }
}
