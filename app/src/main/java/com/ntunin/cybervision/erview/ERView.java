package com.ntunin.cybervision.erview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * Created by mikhaildomrachev on 17.04.17.
 */

public abstract class ERView extends FrameLayout {

    public ERView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }
    public abstract void start();
}
