package com.ntunin.cybervision.journal.breakingnews;

/**
 * Created by nikolay on 01.02.17.
 */

public class HashedNewsFactory extends NewsFactory {
    @Override
    public BreakingNews create() {
        return new HashedBreakingNews();
    }
}
