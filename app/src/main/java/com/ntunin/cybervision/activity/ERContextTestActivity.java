package com.ntunin.cybervision.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.ntunin.cybervision.R;
import com.ntunin.cybervision.ercontext.ERContext;
import com.ntunin.cybervision.erview.ERView;

/**
 * Created by mikhaildomrachev on 17.04.17.
 */

public class ERContextTestActivity extends ERContext {

    private ERView view;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_er);
        view = (ERView) findViewById(R.id.er_view);
    }
}
