package com.ntunin.cybervision.journal.featureddetector;

import com.ntunin.cybervision.ObjectFactory;
import com.ntunin.cybervision.R;
import com.ntunin.cybervision.Res;
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
    private ObjectFactory factory;
    private Journal journal;




    @Override
    public void breakingNews(BreakingNews news) {
        ImageFrame frame = (ImageFrame) news.read(Res.string(R.string.image_frame));
        PointFetcher fetcher = (PointFetcher) factory.get(Res.string(R.string.point_fetcher)).init();
        List<Edge> edges = fetcher.start(frame);
        fetcher.release();
        news.write(Res.string(R.string.edges), edges);
        Journal journal = (Journal) Injector.main().getInstance(Res.string(R.string.journal));
        journal.release(Res.string(R.string.markup), news);
    }

    @Override
    public void init(Map<String, Object> data) {
        factory = (ObjectFactory) data.get(Res.string(R.string.object_factory));
        journal = (Journal) data.get(Res.string(R.string.journal));
        String action = (String) data.get(Res.string(R.string.position_action));
        journal.subscribe(action, this);
    }
}
