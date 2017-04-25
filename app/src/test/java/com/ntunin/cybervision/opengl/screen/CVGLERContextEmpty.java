package com.ntunin.cybervision.opengl.screen;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;

import com.ntunin.cybervision.audio.Audio;
import com.ntunin.cybervision.ercontext.ERContext;
import com.ntunin.cybervision.ercontext.Screen;
import com.ntunin.cybervision.opengl.graphics.Graphics;
import com.ntunin.cybervision.io.FileIO;
import com.ntunin.cybervision.io.Input;

/**
 * Created by nikolay on 05.04.17.
 */

public class CVGLERContextEmpty extends ERContext {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        current = this;
    }

    @Override
    protected void start() {

    }
}
