package com.ntunin.cybervision.journal.breakingnews;

import com.ntunin.cybervision.Res;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by nikolay on 01.02.17.
 */

public class HashedBreakingNews extends BreakingNews {
    private Map<String, Object> data;

    public HashedBreakingNews() {
        data = new HashMap<>();
    }

    public void write(String title, Object news) {
        synchronized (this) {
            data.put(title, news);
        }
    }

    public Object read(String title) {
        return data.get(title);
    }

    @Override
    public void write(int id, Object news) {
        write(Res.string(id), news);
    }

    @Override
    public Object read(int id) {
        return read(Res.string(id));
    }
}
