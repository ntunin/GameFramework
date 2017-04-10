package com.ntunin.cybervision.journal.featureddetector;

import com.ntunin.cybervision.ObjectFactory;
import com.ntunin.cybervision.R;
import com.ntunin.cybervision.Res;
import com.ntunin.cybervision.ResMap;
import com.ntunin.cybervision.injector.Injectable;
import com.ntunin.cybervision.injector.Injector;
import com.ntunin.cybervision.journal.Journal;
import com.ntunin.cybervision.journal.JournalSubscriber;
import com.ntunin.cybervision.journal.breakingnews.BreakingNews;
import com.ntunin.cybervision.journal.cameracapturing.ImageFrame;
import com.ntunin.cybervision.journal.featureddetector.pointfetcher.PointFetcher;
import com.ntunin.cybervision.journal.featureddetector.pointfetcher.edge.Edge;
import com.ntunin.cybervision.journal.featureddetector.pointfetcher.edge.EdgeRegister;

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
        ImageFrame frame = (ImageFrame) news.read(R.string.image_frame);
        PointFetcher fetcher = (PointFetcher) factory.get(R.string.point_fetcher).init();
        EdgeRegister table = fetcher.start(frame);
        fetcher.release();
        news.write(R.string.edge_register, table);
        Journal journal = (Journal) Injector.main().getInstance(R.string.journal);
        journal.release(R.string.markup, news);
    }

    @Override
    public void init(ResMap<String, Object> data) {
        factory = (ObjectFactory) data.get(R.string.object_factory);
        journal = (Journal) data.get(R.string.journal);
        String action = (String) data.get(R.string.position_action);
        journal.subscribe(action, this);
    }
}
