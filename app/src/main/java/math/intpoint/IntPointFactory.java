package math.intpoint;

import com.ntunin.cybervision.Releasable;
import com.ntunin.cybervision.ReleasableFactory;

/**
 * Created by nikolay on 11.03.17.
 */

public class IntPointFactory extends ReleasableFactory {
    public IntPointFactory() {
        this.tag = "Int Point";
    }
    @Override
    protected Releasable create() {
        return new Point();
    }
}
