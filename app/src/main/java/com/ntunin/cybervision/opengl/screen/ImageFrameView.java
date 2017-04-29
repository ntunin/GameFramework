package com.ntunin.cybervision.opengl.screen;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.View;

import com.ntunin.cybervision.R;
import com.ntunin.cybervision.injector.Injector;
import com.ntunin.cybervision.journal.Journal;
import com.ntunin.cybervision.journal.cameracapturing.ImageFrame;
import com.ntunin.cybervision.journal.cameracapturing.JournalingCameraCapturing;

import math.intsize.Size;

/**
 * Created by mikhaildomrachev on 17.04.17.
 */

public abstract class ImageFrameView extends View {

    private float scale;
    private Handler mainHandler;
    protected ImageFrame frame;
    private Bitmap cacheBitmap;

    public ImageFrameView(Context context) {
        super(context);
        mainHandler = new Handler(context.getMainLooper());
    }


    public ImageFrameView(@NonNull Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    public void draw(ImageFrame frame) {
        if(this.frame == null) {
            this.frame = frame.clone();
        } else {
            this.frame.put(frame);
        }
        int width = getWidth();
        int height = getHeight();
        Size frameSize = frame.size();
        float xScale = ((float)width)/frameSize.width;
        float yScale = ((float)height)/frameSize.height;
        scale = Math.max(xScale, yScale);
        final View view = this;
        Runnable myRunnable = new Runnable() {
            @Override
            public void run() {
                view.invalidate();
            }
        };
        mainHandler.post(myRunnable);
    }

    public abstract void start();

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(frame == null) {
            return;
        }

        if(cacheBitmap == null) {
            AllocateCache();
        }

        boolean bmpValid = true;
        try {
            cacheBitmap = frame.getBitmap();
        } catch (Exception e) {
            bmpValid = false;
        }

        if (bmpValid && cacheBitmap != null) {
            if (canvas != null) {
                int canvasWidth = canvas.getWidth();
                int canvasHeight = canvas.getHeight();
                int cacheWidth = cacheBitmap.getWidth();
                int cacheHeight = cacheBitmap.getHeight();

                float scaledWidth = scale * cacheWidth;
                float scaledHeight = scale * cacheHeight;
                float halfWidth = (canvasWidth - scaledWidth) / 2;
                float halfHeight = (canvasHeight - scaledHeight) / 2;
                canvas.drawBitmap(cacheBitmap, new Rect(0, 0, cacheWidth, cacheHeight),
                        new Rect((int) halfWidth, (int) halfHeight,
                                (int) (halfWidth+ scaledWidth),
                                (int) (halfHeight + scaledHeight)), null);
            }
        }
    }

    protected void AllocateCache() {
        Size size = frame.size();
        cacheBitmap = Bitmap.createBitmap(size.width, size.height, Bitmap.Config.ARGB_8888);
    }
}
