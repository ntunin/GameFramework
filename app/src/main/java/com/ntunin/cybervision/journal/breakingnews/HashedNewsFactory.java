package com.ntunin.cybervision.journal.breakingnews;

import com.ntunin.cybervision.injector.Injectable;

import java.util.Map;

/**
 * Created by nikolay on 01.02.17.
 */

public class HashedNewsFactory extends NewsFactory implements Injectable{
    @Override
    public BreakingNews create() {
        return new HashedBreakingNews();
    }

    @Override
    public void init(Map<String, Object> data) {
        return;
    }
}
