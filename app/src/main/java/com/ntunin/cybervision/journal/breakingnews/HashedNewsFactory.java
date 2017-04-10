package com.ntunin.cybervision.journal.breakingnews;

import com.ntunin.cybervision.ResMap;
import com.ntunin.cybervision.injector.Injectable;

import java.util.Map;

/**
 * Created by nikolay on 01.02.17.
 */

public class HashedNewsFactory extends NewsFactory implements Injectable{
    @Override
    protected String getTag() {
        return "news";
    }

    @Override
    public BreakingNews create() {
        return new HashedBreakingNews();
    }

    @Override
    public void init(ResMap<String, Object> data) {
        return;
    }
}
