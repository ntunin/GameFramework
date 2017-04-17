package math.intpoint;

import com.ntunin.cybervision.releasable.Releasable;
import com.ntunin.cybervision.releasable.ReleasableFactory;
import com.ntunin.cybervision.res.ResMap;
import com.ntunin.cybervision.injector.Injectable;

/**
 * Created by nikolay on 11.03.17.
 */

public class IntPointFactory extends ReleasableFactory implements Injectable {
    @Override
    protected Releasable create() {
        return new Point();
    }

    @Override
    protected String getTag() {
        return "Int Point";
    }

    @Override
    public void init(ResMap<String, Object> data) {
        return;
    }
}
