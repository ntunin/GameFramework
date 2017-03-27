package com.ntunin.cybervision.opengl.screen;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;

import com.ntunin.cybervision.injector.Injector;
import com.ntunin.cybervision.journal.breakingnews.BreakingNews;
import com.ntunin.cybervision.journal.cameracapturing.ImageFrame;
import com.ntunin.cybervision.journal.Journal;
import com.ntunin.cybervision.journal.JournalSubscriber;

import math.intsize.Size;

/**
 * Created by nikolay on 05.02.17.
 */

public class CameraView extends View implements JournalSubscriber{
    private Injector injector;
    private Journal journal;
    private ImageFrame modified;
    private Bitmap mCacheBitmap;
    private float xScale = 1;
    private  float scale = 1;
    private float yScale = 1;
    private int mFrameWidth;
    private int mFrameHeight;

    Handler mainHandler;
    public CameraView(Context context, AttributeSet attrs) {
        super(context, attrs);
        injector = Injector.main();
        journal = (Journal) injector.getInstance("Journal");
        journal.subscribe("Markup", this);
        mainHandler = new Handler(context.getMainLooper());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(modified == null) {
            return;
        }
        if(mCacheBitmap == null) {
            AllocateCache();
        }
        boolean bmpValid = true;
        if (modified != null) {
            try {
                mCacheBitmap = modified.getBitmap();
            } catch (Exception e) {
                bmpValid = false;
            }
        }

        if (bmpValid && mCacheBitmap != null) {
            if (canvas != null) {
                int canvasWidth = canvas.getWidth();
                int canvasHeight = canvas.getHeight();
                int cacheWidth = mCacheBitmap.getWidth();
                int cacheHeight = mCacheBitmap.getHeight();

                float scaledWidth = scale * cacheWidth;
                float scaledHeight = scale * cacheHeight;
                float halfWidth = (canvasWidth - scaledWidth) / 2;
                float halfHeight = (canvasHeight - scaledHeight) / 2;
                canvas.drawBitmap(mCacheBitmap, new Rect(0, 0, cacheWidth, cacheHeight),
                        new Rect((int) halfWidth, (int) halfHeight,
                                (int) (halfWidth+ scaledWidth),
                                (int) (halfHeight + scaledHeight)), null);
            }
        }
    }

    protected void AllocateCache()
    {
        mCacheBitmap = Bitmap.createBitmap(mFrameWidth, mFrameHeight, Bitmap.Config.ARGB_8888);
    }

    @Override
    public void breakingNews(BreakingNews news) {
        this.modified = (ImageFrame) news.read("Markup");

        int width = getWidth();
        int height = getHeight();
        Size frameSize = this.modified.size();
        mFrameWidth = frameSize.width;
        mFrameHeight = frameSize.height;
        xScale = ((float)width)/mFrameWidth;
        yScale = ((float)height)/mFrameHeight;
        scale = Math.max(xScale, yScale);
        final CameraView cameraView = this;
        Runnable myRunnable = new Runnable() {
            @Override
            public void run() {
                cameraView.invalidate();
            }
        };
        mainHandler.post(myRunnable);
    }
}
