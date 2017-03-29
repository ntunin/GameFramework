package com.ntunin.cybervision.journal.featureddetector.pointfetcher.nautilus;

import com.ntunin.cybervision.ERRNO;
import com.ntunin.cybervision.ErrCodes;
import com.ntunin.cybervision.injector.Injector;
import com.ntunin.cybervision.journal.Journal;
import com.ntunin.cybervision.journal.cameracapturing.ImageFrame;
import com.ntunin.cybervision.journal.featureddetector.divider.Divider;
import com.ntunin.cybervision.journal.featureddetector.divider.DividerDelegate;
import com.ntunin.cybervision.journal.featureddetector.pointfetcher.PointFetcher;
import com.ntunin.cybervision.journal.featureddetector.pointfetcher.edge.Edge;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import math.intpoint.Point;
import math.intsize.Size;

/**
 * Created by nikolay on 11.03.17.
 */

public class Nautilus extends PointFetcher {
    private final double GOLDEN_SECTION = 1.62f;
    private  final double PI = Math.PI;
    private  final double _2_PI = 2/PI;

    @Override
    public void release() {
        super.release();
    }

    Map<Integer, Edge> edgeMap;
    List<Edge> edges;
    Size size;


    int halfScreenX;
    int halfScreenY;



    double current;
    double target = 6.86;
    int frameDirection = 1;

    double rCircle;
    double oXCircle;

    double xStart;
    double xFinish;

    double alpha = 0;
    double dAlpha;

    int x;
    int y;

    int minSize;
    Edge edge;


    public Nautilus() {
        super();
    }

    @Override
    public List<Edge> start(ImageFrame frame) {
        if(frame == null) {
            ERRNO.write(ErrCodes.INVALID_ARGUMENT);
            return null;
        }
        super.start(frame);
        this.size = frame.size();
        edgeMap = new HashMap<>();
        divider = (Divider) factory.get("Divider").init(frame, new DividerDelegate() {
            @Override
            public boolean addPoint(int x, int y) {
                int hash = y*size.width + x;
                Edge previous = edgeMap.get(hash);
                if(previous != null) {
                    return false;
                }
                Point p = (Point) factory.get("Int Point").init(x, y);
                edgeMap.put(hash, edge);
                if(x == 0 || y == 0 || x == size.width - 1 || y == size.height - 1) {
                    return false;
                }
                int sqr = x*x + y*y;
                if(sqr < current*current || sqr > target*target) {
                    return false;
                }
                return true;
            }

            @Override
            public int getDirection() {
                return 0;
            }
        });
        halfScreenX = size.width  / 2;
        halfScreenY = size.height / 2;

        minSize = Math.min(halfScreenX, halfScreenY);
        while(true) {
            current = target;
            target = current * GOLDEN_SECTION;
            rCircle = Math.abs(target + current) / 2;



            xStart = frameDirection * current;
            frameDirection *= -1;
            xFinish = frameDirection * target;

            oXCircle = Math.min(xStart, xFinish) + rCircle;

            dAlpha = 2 / rCircle;
            for(alpha = 0; alpha < PI; alpha += dAlpha) {
                double p = _2_PI * alpha - 1;
                int s = (int) Math.signum(p);
                p = 1 - Math.abs(p);
                x = (int) (rCircle * s * (1 - p*p) + oXCircle) + halfScreenX;
                y = (int) (rCircle * p * frameDirection) + halfScreenY;
                edge = (Edge) factory.get("Edge");
                divider.handle(x, y);
                if(x == 0 || y == 0 || x == size.width - 1 || y == size.height - 1) {
                    divider.release();
                    edges = new LinkedList<>();
                    for(Integer hash: edgeMap.keySet()) {
                        Edge e = edgeMap.get(hash);
//                        if(e.isRoot()) {
//                            edges.add(e);
//                        } else {
//                            e.release();
//                        }
                    }
                    return edges;
                }
            }
        }
    }

}
