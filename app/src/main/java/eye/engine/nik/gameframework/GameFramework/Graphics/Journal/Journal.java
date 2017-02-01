package eye.engine.nik.gameframework.GameFramework.Graphics.Journal;

import eye.engine.nik.gameframework.GameFramework.Graphics.Journal.BreakingNews.BreakingNews;

/**
 * Created by nikolay on 26.01.17.
 */

public abstract class Journal {
    public abstract void release(String title, BreakingNews news);
    public abstract void subscribe(String title, JournalSubscriber subscriber);
    public abstract void start();
    public abstract void stop();
}
