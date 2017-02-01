package eye.engine.nik.gameframework.GameFramework.Graphics.Journal.FeaturedPointsDetector;

import eye.engine.nik.gameframework.GameFramework.Graphics.Injector.Injector;
import eye.engine.nik.gameframework.GameFramework.Graphics.Journal.BreakingNews.BreakingNews;
import eye.engine.nik.gameframework.GameFramework.Graphics.Journal.Journal;
import eye.engine.nik.gameframework.GameFramework.Graphics.Journal.JournalSubscriber;
import eye.engine.nik.gameframework.GameFramework.Graphics.Journal.Journalist;

/**
 * Created by nikolay on 02.02.17.
 */

public class NautilusAlgorithm extends JournalSubscriber implements Journalist {

    private Injector injector;
    private Journal journal;

    @Override
    public void breakingNews(BreakingNews news) {

    }

    @Override
    public void start() {
        injector = Injector.main();
        journal = (Journal) injector.getInstance("Journal");
        journal.subscribe("Acceleration", this);
    }

    @Override
    public void stop() {

    }
}
