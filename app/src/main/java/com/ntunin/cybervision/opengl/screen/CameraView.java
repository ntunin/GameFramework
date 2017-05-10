package com.ntunin.cybervision.opengl.screen;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.ntunin.cybervision.R;
import com.ntunin.cybervision.android.io.HardwareCamera;
import com.ntunin.cybervision.ercontext.ERContext;
import com.ntunin.cybervision.errno.ERRNO;
import com.ntunin.cybervision.injector.Injector;
import com.ntunin.cybervision.journal.breakingnews.BreakingNews;
import com.ntunin.cybervision.journal.cameracapturing.ImageFrame;
import com.ntunin.cybervision.journal.Journal;
import com.ntunin.cybervision.journal.JournalSubscriber;
import com.ntunin.cybervision.journal.cameracapturing.JournalingCameraCapturing;
import com.ntunin.cybervision.journal.featureddetector.pointfetcher.edge.Edge;
import com.ntunin.cybervision.journal.featureddetector.pointfetcher.edge.EdgeIterator;
import com.ntunin.cybervision.journal.featureddetector.pointfetcher.edge.EdgeRegister;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import math.intpoint.Point;
import math.intsize.Size;

/**
 * Created by nikolay on 05.02.17.
 */

public class CameraView extends ImageFrameView implements JournalSubscriber{
    private HardwareCamera camera;
    private Thread frameRenderWorker;
    private Timer renderTimer = new Timer();
    public CameraView(Context context) {
        super(context);
        init(context);
    }

    public CameraView(@NonNull Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private void init(Context context) {
        Injector injector = Injector.main();
        Journal journal = (Journal) injector.getInstance(R.string.journal);
        if(journal == null) {
            ERRNO.write(R.string.no_journal);
            return;
        }
        journal.subscribe("Camera", this);
    }

    @Override
    public void start() {
        camera = (HardwareCamera) ERContext.get(R.string.camera);
        if(camera == null) {
            ERRNO.write(R.string.no_frame_service);
            return;
        }
        camera.start();
        drawCameraFrame();
        frameRenderWorker = new Thread(new FrameRenderWorker());
        frameRenderWorker.start();
    }

    private void drawCameraFrame() {
        renderTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                final ImageFrame frame = camera.getFrame();
                if(frame == null) {
                    drawCameraFrame();
                    return;
                }
                ERContext.executeInMainTread(new Runnable() {
                    @Override
                    public void run() {
                        draw(frame);
                        drawCameraFrame();
                    }
                });
            }
        }, 20);
    }



    private class FrameRenderWorker implements Runnable {

        @Override
        public void run() {
            while(true) {
                try {
                    Thread.sleep(25);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void breakingNews(BreakingNews news) {
        ImageFrame frame = (ImageFrame) news.read(R.string.image_frame);
        draw(frame);
    }


}
