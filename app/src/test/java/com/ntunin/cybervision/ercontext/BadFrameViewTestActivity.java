package com.ntunin.cybervision.ercontext;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.ntunin.cybervision.R;
import com.ntunin.cybervision.ercontext.ERContext;
import com.ntunin.cybervision.erview.ERView;
import com.ntunin.cybervision.erview.StackERView;
import com.ntunin.cybervision.injector.Injector;
import com.ntunin.cybervision.injector.MapInjector;
import com.ntunin.cybervision.journal.HashMapJournal;
import com.ntunin.cybervision.journal.Journal;
import com.ntunin.cybervision.journal.cameracapturing.CameraCapturing;
import com.ntunin.cybervision.objectfactory.ObjectFactory;
import com.ntunin.cybervision.opengl.screen.CameraView;
import com.ntunin.cybervision.opengl.screen.GLScreenView;
import com.ntunin.cybervision.opengl.screen.ImageFrameView;
import com.ntunin.cybervision.releasable.ReleasableFactory;
import com.ntunin.cybervision.res.ResMap;

import math.intsize.IntSizeFactory;

/**
 * Created by nik on 26.04.17.
 */

public class BadFrameViewTestActivity extends ERContext {
    private ImageFrameView frameView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        current = this;

        ResMap<String, Object> args = new ResMap<>();

        ResMap<String, ReleasableFactory> factoryResMap = new ResMap<>();
        factoryResMap.put(R.string.int_size, new IntSizeFactory());
        args.put(R.string.factories, factoryResMap);

        ObjectFactory objectFactory = new ObjectFactory();
        objectFactory.init(args);

        Journal journal = new HashMapJournal();

        MapInjector injector = new MapInjector();
        injector.setInstance(R.string.object_factory, objectFactory);
        injector.setInstance(R.string.journal, journal);
        injector.setInstance(R.string.camera, new CameraCapturing());
        injector.setInstance(R.string.grant_resolvers, new ResMap<>());
        Injector.setMain(injector);
        super.onCreate(savedInstanceState);
        frameView = new CameraView(this);
    }

    @Override
    protected void start() {
        frameView.start();
    }

}
