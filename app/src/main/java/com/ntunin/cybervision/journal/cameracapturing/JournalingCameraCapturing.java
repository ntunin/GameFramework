package com.ntunin.cybervision.journal.cameracapturing;


import java.util.Map;

import com.ntunin.cybervision.Size;
import com.ntunin.cybervision.injector.Injector;
import com.ntunin.cybervision.injector.InternalInjector;
import com.ntunin.cybervision.journal.breakingnews.BreakingNews;
import com.ntunin.cybervision.journal.breakingnews.NewsFactory;
import com.ntunin.cybervision.journal.Journal;
import com.ntunin.cybervision.journal.Journalist;

/**
 * Created by nikolay on 30.01.17.
 */

public class JournalingCameraCapturing extends CameraCapturing implements Journalist {

    private CameraFrame frame;
    private Injector injector;
    private Journal journal;
    private NewsFactory newsFactory;

    public JournalingCameraCapturing(int cameraId) {
        super(cameraId);
    }

    @Override
    protected void handleFrame(CameraFrame frame) {
       // if(this.frame != null) return;
        this.frame = frame;
        BreakingNews news = newsFactory.create();
        news.write("Camera Frame", frame);
        journal.release("Camera", news);
    }

    @Override
    public void start() {
        injector = InternalInjector.main();
        newsFactory = (NewsFactory) injector.getInstance("News Factory");
        journal = (Journal) injector.getInstance("Journal");
        Map<String, Object> settings = (Map<String, Object>) injector.getInstance("Settings");
        Size size = (Size) settings.get("Camera Size");
        connectCamera(size.width, size.height);
    }

    @Override
    public void stop() {
        disconnectCamera();
    }
}
