package com.ntunin.cybervision.injector;

import java.util.Map;

/**
 * Created by nikolay on 12.03.17.
 */

public class MapInjector extends Injector {

    protected Map<String, Object> instances;

    public MapInjector() {
        this.instances = null;
    }

    public MapInjector(Map<String, Object> instances) {
        this.instances = instances;
    }

    @Override
    public Object getInstance(String token) {
        return instances.get(token);
    }

    @Override
    public void setInstance(String token, Object instance) {
        instances.put(token, instance);
    }
}
