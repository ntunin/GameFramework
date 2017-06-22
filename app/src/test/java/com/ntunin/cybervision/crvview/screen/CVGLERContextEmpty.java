package com.ntunin.cybervision.crvview.screen;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;

import com.ntunin.cybervision.crvcontext.CRVContext;

/**
 * Created by nikolay on 05.04.17.
 */

public class CVGLERContextEmpty extends CRVContext {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        current = this;
    }

    @Override
    protected void start() {

    }
}
