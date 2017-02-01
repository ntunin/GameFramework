package eye.engine.nik.gameframework.GameFramework.Graphics.Journal.BreakingNews;

/**
 * Created by nikolay on 01.02.17.
 */

public class HashedNewsFactory extends NewsFactory {
    @Override
    public BreakingNews create() {
        return new HashedBreakingNews();
    }
}
