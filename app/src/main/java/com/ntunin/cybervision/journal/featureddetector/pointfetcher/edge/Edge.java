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
    private EdgeRoot anchor;
    private EdgeNode first;
    private EdgeNode last;
    private EdgeRegister table;

    public void push(Point point) {
        if(factory == null) {
            ERRNO.write(ErrCodes.NOT_INITIALIZED);
            return;
        }

        if(this.root == null) {
            EdgeRoot root = (EdgeRoot) factory.get("Edge Root").init(point);
            root.link(this);
            table.writeRoot(root);
            this.root = root;
        } else {
            EdgeNode node = (EdgeNode) factory.get("Edge Node").init(point);
            if(first == null) {
                this.first = node;
                this.last = node;
                this.first.next = node;
            } else {
                this.last.push(node);
                this.last = node;
            }
        }
    }

    public Edge split(Point point) {
        if(table == null) {
            ERRNO.write(ErrCodes.NOT_INITIALIZED);
            return null;
        }

        EdgeNode node = table.readNode(point);
        if(node == null) {
            //// TODO: 28.03.17 parse root
            return null;
        } else {
            table.removeNode(point);
            EdgeRoot root = (EdgeRoot) factory.get("Edge Root").init(point);
            table.writeRoot(root);
            Edge second = (Edge) factory.get("Edge").init(table, node);
            this.anchor = root;
            node.prev.next = null;
            return second;
        }

    }





    @Override
    public Releasable init(Object... args) {
        Injector injector = Injector.main();
        this.factory = (ObjectFactory) injector.getInstance("Object Factory");
        if(args.length > 0) {
            table = (EdgeRegister) args[0];
        }
        if(args.length > 1) {
            setRootFromNode((EdgeNode) args[1]);
        }
        return this;
    }

    private void setRootFromNode(EdgeNode node) {
        this.first = node.next;
        Point point = node.getPoint();
        this.root = table.readRoot(point);
        root.link(this);
    }
}
