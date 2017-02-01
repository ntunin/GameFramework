package eye.engine.nik.gameframework.GameFramework.Graphics.Journal.BreakingNews;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by nikolay on 01.02.17.
 */

public abstract class BreakingNews {
    public abstract void write(String title, Object news);
    public abstract Object read(String title);
}
