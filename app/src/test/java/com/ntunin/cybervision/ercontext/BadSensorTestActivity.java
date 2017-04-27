package com.ntunin.cybervision.ercontext;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.ntunin.cybervision.R;
import com.ntunin.cybervision.injector.Injector;
import com.ntunin.cybervision.injector.MapInjector;
import com.ntunin.cybervision.journal.HashMapJournal;
import com.ntunin.cybervision.journal.Journal;
import com.ntunin.cybervision.journal.cameracapturing.CameraCapturing;
import com.ntunin.cybervision.journal.motionsensor.MotionSensor;
import com.ntunin.cybervision.objectfactory.ObjectFactory;
import com.ntunin.cybervision.opengl.screen.CameraView;
import com.ntunin.cybervision.opengl.screen.ImageFrameView;
import com.ntunin.cybervision.releasable.ReleasableFactory;
import com.ntunin.cybervision.res.ResMap;

import math.intsize.IntSizeFactory;

/**
 * Created by nik on 27.04.17.
 */

public class BadSensorTestActivity extends ERContext {


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
        injector.setInstance(R.string.motion_sensor, new MotionSensor());
        ResMap<String, GrantResolver> resolvers = new ResMap<>();
        resolvers.put(R.string.motion_sensor, new MotionSensor());
        injector.setInstance(R.string.grant_resolvers, resolvers);
        Injector.setMain(injector);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void start() {
        MotionSensor sensor = (MotionSensor) Injector.main().getInstance(R.string.motion_sensor);
        sensor.start();
    }

}
