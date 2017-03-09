package com.ntunin.cybervision.journal.featureddetector;



import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.ntunin.cybervision.Size;
import com.ntunin.cybervision.injector.Injector;
import com.ntunin.cybervision.journal.breakingnews.BreakingNews;
import com.ntunin.cybervision.journal.cameracapturing.CameraFrame;
import com.ntunin.cybervision.journal.Journal;
import com.ntunin.cybervision.journal.JournalSubscriber;
import com.ntunin.cybervision.journal.Journalist;

/**
 * Created by nikolay on 02.02.17.
 */

public class NautilusAlgorithm implements Journalist, JournalSubscriber {

    private Injector injector;
    private Journal journal;
    private final double GOLDEN_SECTION = 1.62f;
    private  final double PI = Math.PI;
    private  final double _2_PI = 2/PI;
    private  final double _4_PI = 4/PI;
    private final int COLOR_DISTANCE_RIM = 50;

    @Override
    public void breakingNews(BreakingNews news) {
        CameraFrame frame = (CameraFrame) news.read("Camera Frame");
        new Nautilus(frame);
        news.write("Markup", frame);
        journal.release("Markup", news);
    }


    private static double sqr(double a) {
        return a * a;
    }
    private static int sqr(int a) {
        return a * a;
    }

    @Override
    public void start() {
        injector = Injector.main();
        journal = (Journal) injector.getInstance("Journal");
        journal.subscribe("Position", this);

    }

    @Override
    public void stop() {

    }

    private class Direction {
        int d;

        public Direction(int d) {
            this.d = d;
        }
    }

    private class Node {
        int x;
        int y;
        List<Direction> links;

        public Node(int x, int y) {
            this.x = x;
            this.y = y;
            links = new LinkedList<>();
        }
    }

    private class Point {
        int x;
        int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    private class Nautilus {
        Map<Integer, List<Point>> edges;
        List<Point> drawable;
        CameraFrame frame;
        Size size;


        int halfScreenX;
        int halfScreenY;
        int[] colorBuffer = new int[4];


        int currentX;
        int currentY;

        int anchorX;
        int anchorY;

        double current;
        double target = 6.86;
        int frameDirection = 1;
        int offsetDirection;

        double rCircle;
        double oXCircle;

        double xStart;
        double xFinish;

        double alpha = 0;
        double dAlpha;

        double x;
        double y;

        double cosA;
        double sinA;

        int u;
        int v;

        int minSize;
        int[][][] U = new int[][][]{
                new int[][]{
                        new int[]{0, 1, 7}, new int[]{0, 1}, new int[]{1, 3},  new int[]{3, 4}, new int[]{3, 4, 5}, new int[]{4, 5},  new int[]{5, 7},  new int[]{7, 0}
                },
                new int[][]{
                        new int[]{0, 1},  new int[]{0, 1, 2},  new int[]{1, 2}, new int[]{2, 4},  new int[]{4, 5},  new int[]{4, 5, 6},  new int[]{5, 6},  new int[]{6, 0}
                },
                new int[][]{
                        new int[]{1, 7},  new int[]{1, 2},  new int[]{1, 2, 3},  new int[]{2, 3},  new int[]{3, 5},  new int[]{5, 6},  new int[]{5, 6, 7},  new int[]{6, 7}
                },
                new int[][]{
                        new int[]{0, 7},  new int[]{0, 2},  new int[]{2, 3},  new int[]{2, 3, 4},  new int[]{3, 4},  new int[]{4, 6},  new int[]{6, 7},  new int[]{6, 7, 0}
                }
        };

        int[] D = new int[]{-1, 2, 3, 2, 0, 3, 0, 3, 1, 3, 0, 2, 0, 0, 0, 3};

        int[][] O = new int[][] {
                new int[]{1, 0},
                new int[]{1, 1},
                new int[]{0, 1},
                new int[]{-1, 1},
                new int[]{-1, 0},
                new int[]{-1, -1},
                new int[]{0, -1},
                new int[]{1, -1}
        };

        public  Nautilus(CameraFrame frame) {
            this.frame = frame;
            this.size = frame.size();
            this.edges = new HashMap<>();
            this.drawable = new LinkedList<>();
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
                    x = rCircle * s * (1 - p*p) + oXCircle;
                    y = rCircle * p * frameDirection ;
                    currentX = (int)x + halfScreenX;
                    currentY = (int)y + halfScreenY;
                    if(Math.abs(x) >= minSize || Math.abs(y) >= minSize) {
                        printDrawable();
                        return;
                    }

                    currentX = (int)x + halfScreenX;
                    currentY = (int)y + halfScreenY;


                }
            }
        }

        private int b() {
            return frame.getBrightness(currentX, currentY);
        }

        private int b(int dx, int dy) {
            return frame.getBrightness(currentX + dx, currentY + dy);
        }

        private int b(int o) {
            int[]d = O[o];
            return frame.getBrightness(currentX + d[0], currentY + d[1]);
        }

        private void printDrawable() {
            for(Point p: drawable) {
                frame.put(p.x, p.y, 255, 255, 0);
            }
        }
        private Integer edgeHash(int x, int y) {
            return (size.height * y + x) / 5;
        }
    }
}
