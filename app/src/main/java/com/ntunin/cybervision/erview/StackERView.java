package com.ntunin.cybervision.erview;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.opengl.GLSurfaceView;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver;

import com.ntunin.cybervision.R;
import com.ntunin.cybervision.ercontext.Screen;
import com.ntunin.cybervision.injector.Injector;
import com.ntunin.cybervision.journal.JournalSubscriber;
import com.ntunin.cybervision.journal.breakingnews.BreakingNews;
import com.ntunin.cybervision.journal.cameracapturing.ImageFrame;
import com.ntunin.cybervision.journal.cameracapturing.JournalingCameraCapturing;
import com.ntunin.cybervision.opengl.graphics.GLGraphics;
import com.ntunin.cybervision.opengl.screen.CameraView;
import com.ntunin.cybervision.opengl.screen.GLScreenView;
import com.ntunin.cybervision.opengl.screen.ImageFrameView;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;


/**
 * Created by mikhaildomrachev on 17.04.17.
 */

public class StackERView extends ERView {

    private ImageFrameView frameView;
    private GLScreenView screenView;
    private Context context;
    private ERView view;

    public StackERView(@NonNull Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        this.view = this;
        frameView = new CameraView(context);
        screenView = new GLScreenView(context);
        view.addView(frameView);
        view.addView(screenView);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        layoutChildren();
    }

    private void layoutChildren() {
        layoutChild(frameView);
        layoutChild(screenView);
    }

    private void layoutChild(View view) {
        int l = 0, t = 0, r = this.getWidth(), b = this.getHeight();
        view.layout(l, t, r, b);
    }

    @Override
    public void start() {
        frameView.start();
    }


}
