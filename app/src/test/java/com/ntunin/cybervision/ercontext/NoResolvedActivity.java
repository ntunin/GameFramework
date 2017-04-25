package com.ntunin.cybervision.ercontext;

import com.ntunin.cybervision.injector.Injector;

/**
 * Created by nik on 25.04.17.
 */

public class NoResolvedActivity extends ERContext {
    public NoResolvedActivity() {
        init();
    }

    @Override
    protected void start() {
        Service service = (Service) Injector.main().getInstance("NoResolvedService");
        service.serve();
    }
}
