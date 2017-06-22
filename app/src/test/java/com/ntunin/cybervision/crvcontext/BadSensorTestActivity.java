package com.ntunin.cybervision.crvcontext;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.ntunin.cybervision.R;
import com.ntunin.cybervision.crvinjector.CRVInjector;
import com.ntunin.cybervision.crvinjector.MapInjector;
import com.ntunin.cybervision.journal.HashMapJournal;
import com.ntunin.cybervision.journal.Journal;
import com.ntunin.cybervision.journal.cameracapturing.CameraCapturing;
import com.ntunin.cybervision.crvobjectfactory.CRVObjectFactory;
import com.ntunin.cybervision.crvobjectfactory.ReleasableFactory;
import com.ntunin.cybervision.res.ResMap;

import math.intsize.IntSizeFactory;

/**
 * Created by nik on 27.04.17.
 */

public class BadSensorTestActivity extends CRVContext {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        current = this;

        ResMap<String, Object> args = new ResMap<>();

        ResMap<String, ReleasableFactory> factoryResMap = new ResMap<>();
        factoryResMap.put(R.string.int_size, new IntSizeFactory());
        args.put(R.string.factories, factoryResMap);

        CRVObjectFactory objectFactory = new CRVObjectFactory();
        objectFactory.init(args);

        Journal journal = new HashMapJournal();

        MapInjector injector = new MapInjector();
        injector.setInstance(R.string.object_factory, objectFactory);
        injector.setInstance(R.string.journal, journal);
        ResMap<String, GrantResolver> resolvers = new ResMap<>();
        injector.setInstance(R.string.grant_resolvers, resolvers);
        CRVInjector.setMain(injector);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected CRVScreen getScreen() {
        return null;
    }


}
