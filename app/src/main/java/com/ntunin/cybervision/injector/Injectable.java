package com.ntunin.cybervision.injector;

import java.util.Map;

/**
 * Created by nikolay on 03.04.17.
 */

public interface Injectable {
    public void init(Map<String, Object> data);
}
