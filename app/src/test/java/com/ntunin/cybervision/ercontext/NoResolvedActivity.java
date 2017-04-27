package com.ntunin.cybervision.ercontext;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;

import com.ntunin.cybervision.R;
import com.ntunin.cybervision.injector.Injector;
import com.ntunin.cybervision.injector.MapInjector;
import com.ntunin.cybervision.res.ResMap;

/**
 * Created by nik on 25.04.17.
 */

public class NoResolvedActivity extends ERContext {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        current = this;
        Injector.setMain(new MapInjector());
        Injector.main().setInstance(R.string.grant_resolvers, new ResMap<>());
        super.onCreate(savedInstanceState);
    }



    @Override
    protected void start() {
        Service service = (Service) Injector.main().getInstance("NoResolvedService");
        service.serve();
    }
}
