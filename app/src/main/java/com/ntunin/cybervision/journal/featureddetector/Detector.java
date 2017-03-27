package com.ntunin.cybervision.journal.featureddetector;

import com.ntunin.cybervision.ObjectFactory;
import com.ntunin.cybervision.injector.Injector;
import com.ntunin.cybervision.journal.JournalSubscriber;
import com.ntunin.cybervision.journal.breakingnews.BreakingNews;
import com.ntunin.cybervision.journal.cameracapturing.ImageFrame;
import com.ntunin.cybervision.journal.featureddetector.pointfetcher.PointFetcher;
import com.ntunin.cybervision.journal.featureddetector.pointfetcher.edge.Edge;

import java.util.List;

/**
 * Created by nikolay on 12.03.17.
 */

public class Detector implements JournalSubscriber {
    private ObjectFactory factory;

    public Detector() {
        super();
    }

    public void start() {
        factory = (ObjectFactory) Injector.main().getInstance("Object Factory");
    }

    public void stop() {

    }

    @Override
    public void breakingNews(BreakingNews news) {
        ImageFrame frame = (ImageFrame) news.read("Camera Frame");
        PointFetcher fetcher = (PointFetcher) factory.get("Point Fetcher");
        List<Edge> edges = fetcher.start(frame);
        fetcher.release();
        news.write("Edges", edges);
    }
}
