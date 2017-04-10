package com.ntunin.cybervision.journal.cameracapturing;


import java.util.Map;

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

public class JournalingCameraCapturing extends CameraCapturing implements Injectable {

    private ImageFrame frame;
    private Journal journal;
    private NewsFactory newsFactory;
    private String tag;


    @Override
    protected void handleFrame(ImageFrame frame) {
       // if(this.frame != null) return;
        this.frame = frame;
        BreakingNews news = newsFactory.create();
        news.write(R.string.image_frame, frame);
        journal.release(tag, news);
    }

    public void start() {
        ResMap<String, Object> settings = (ResMap<String, Object>) Injector.main().getInstance(R.string.settings);
        Size size = (Size) settings.get(R.string.camera_size);
        connectCamera(size.width, size.height);
    }

    public void stop() {
        disconnectCamera();
    }

    @Override
    public void init(ResMap<String, Object> args) {
        super.init(args);
        tag = (String) args.get(R.string.camera_action);
        journal = (Journal) args.get(R.string.journal);
        newsFactory = (NewsFactory) args.get(R.string.news);
    }
}
