package math.intsize;

import com.ntunin.cybervision.Releasable;
import com.ntunin.cybervision.ReleasableFactory;

/**
 * Created by nikolay on 11.03.17.
 */

public class IntSizeFactory extends ReleasableFactory {

    public IntSizeFactory() {
        this.tag = "Int Size";
    }

    @Override
    protected Releasable create() {
        return new Size();
    }

}
