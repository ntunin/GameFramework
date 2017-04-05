package com.ntunin.cybervision.journal.featureddetector;

import com.ntunin.cybervision.ObjectFactory;
import com.ntunin.cybervision.injector.Injectable;
import com.ntunin.cybervision.injector.Injector;
import com.ntunin.cybervision.journal.Journal;
import com.ntunin.cybervision.journal.JournalSubscriber;
import com.ntunin.cybervision.journal.breakingnews.BreakingNews;
import com.ntunin.cybervision.journal.cameracapturing.ImageFrame;
import com.ntunin.cybervision.journal.featureddetector.pointfetcher.PointFetcher;
import com.ntunin.cybervision.journal.featureddetector.pointfetcher.edge.Edge;

import java.util.List;
import java.util.Map;

/**
 * Created by nikolay on 12.03.17.
 */

public class Detector implements JournalSubscriber, Injectable {
    private Injector injector;
    private ObjectFactory factory;
    private Journal journal;

    public Detector() {
        super();
    }

    public void start() {
        this.injector = Injector.main();
        factory = (ObjectFactory) injector.getInstance("Object Factory");
        journal = (Journal) injector.getInstance("Journal");
        journal.subscribe("Position", this);
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
        Journal journal = (Journal) Injector.main().getInstance("Journal");
        journal.release("Markup", news);
    }

    @Override
    public void init(Map<String, Object> data) {
        return;
    }
}
