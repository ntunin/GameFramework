package math.intsize;

import com.ntunin.cybervision.Releasable;
import com.ntunin.cybervision.ReleasableFactory;

/**
 * Created by nikolay on 11.03.17.
 */

public class IntSizeFactory extends ReleasableFactory {

    @Override
    protected Releasable create() {
        return new Size();
    }

    @Override
    protected String getTag() {
        return "Int Size";
    }
}
