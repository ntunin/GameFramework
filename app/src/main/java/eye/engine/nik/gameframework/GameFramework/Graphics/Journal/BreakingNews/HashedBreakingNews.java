package eye.engine.nik.gameframework.GameFramework.Graphics.Journal.BreakingNews;

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
        data.put(title, news);
    }

    public Object read(String title) {
        return data.get(title);
    }
}
