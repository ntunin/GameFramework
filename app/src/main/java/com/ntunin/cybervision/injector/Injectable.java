package com.ntunin.cybervision.injector;

import com.ntunin.cybervision.ResMap;

import java.util.Map;

/**
 * Created by nikolay on 03.04.17.
 */

public interface Injectable {
    public void init(ResMap<String, Object> data);
}