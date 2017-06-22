package com.ntunin.cybervision.crvcontext;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.ntunin.cybervision.R;
import com.ntunin.cybervision.crvinjector.CRVInjector;
import com.ntunin.cybervision.crvinjector.MapInjector;
import com.ntunin.cybervision.res.ResMap;

/**
 * Created by nik on 25.04.17.
 */

public class NoResolvedActivity extends CRVContext {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        current = this;
        CRVInjector.setMain(new MapInjector());
        CRVInjector.main().setInstance(R.string.grant_resolvers, new ResMap<>());
        super.onCreate(savedInstanceState);
    }



    @Override
    protected void start() {
        Service service = (Service) CRVInjector.main().getInstance("NoResolvedService");
        service.serve();
    }
}
