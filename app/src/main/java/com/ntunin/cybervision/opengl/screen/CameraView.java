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

import math.intpoint.Point;
import math.intsize.Size;

/**
 * Created by nikolay on 05.02.17.
 */

public class CameraView extends ImageFrameView implements JournalSubscriber{

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
        JournalingCameraCapturing camera = (JournalingCameraCapturing) Injector.main().getInstance(R.string.camera);
        if(camera == null) {
            ERRNO.write(R.string.no_frame_service);
            return;
        }
        camera.start();
    }

    @Override
    public void breakingNews(BreakingNews news) {
        Log.d("cameraview", "draw");
        ImageFrame frame = (ImageFrame) news.read(R.string.image_frame);
        draw(frame);
    }


}
