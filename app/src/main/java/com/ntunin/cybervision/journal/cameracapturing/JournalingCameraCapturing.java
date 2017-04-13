package com.ntunin.cybervision.journal.cameracapturing;


import java.util.Map;

import com.ntunin.cybervision.ObjectFactory;
import com.ntunin.cybervision.R;
import com.ntunin.cybervision.Res;
import com.ntunin.cybervision.ResMap;
import com.ntunin.cybervision.injector.Injectable;
import com.ntunin.cybervision.injector.Injector;
import com.ntunin.cybervision.injector.InternalInjector;
import com.ntunin.cybervision.journal.breakingnews.BreakingNews;
import com.ntunin.cybervision.journal.breakingnews.NewsFactory;
import com.ntunin.cybervision.journal.Journal;

import math.intsize.Size;

/**
 * Created by nikolay on 30.01.17.
 */

public abstract class JournalingCameraCapturing implements Injectable {

    private ImageFrame frame;
    private Journal journal;
    private ObjectFactory factory;
    private String tag;

    protected void handleFrame(ImageFrame frame) {
       // if(this.frame != null) return;
        this.frame = frame;
        ObjectFactory factory = (ObjectFactory) Injector.main().getInstance(R.string.object_factory);
        BreakingNews news = (BreakingNews) factory.get(R.string.news).init();
        news.write(R.string.image_frame, frame);
        journal.release(tag, news);
        news.release();
    }

    public abstract void start();

    @Override
    public void init(ResMap<String, Object> args) {
        tag = (String) args.get(R.string.camera_action);
        journal = (Journal) args.get(R.string.journal);
        factory = (ObjectFactory) args.get(R.string.object_factory);
    }
}
