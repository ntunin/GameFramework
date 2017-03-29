package math.intpoint;

import com.ntunin.cybervision.Releasable;
import com.ntunin.cybervision.ReleasableFactory;

/**
 * Created by nikolay on 11.03.17.
 */

public class IntPointFactory extends ReleasableFactory {
    @Override
    protected Releasable create() {
        return new Point();
    }

    @Override
    protected String getTag() {
        return "Int Point";
    }
}
