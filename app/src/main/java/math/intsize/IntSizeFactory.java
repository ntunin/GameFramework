package math.intsize;

import com.ntunin.cybervision.Releasable;
import com.ntunin.cybervision.ReleasableFactory;
import com.ntunin.cybervision.ResMap;
import com.ntunin.cybervision.injector.Injectable;

import java.util.Map;

/**
 * Created by nikolay on 11.03.17.
 */

public class IntSizeFactory extends ReleasableFactory implements Injectable {

    @Override
    protected Releasable create() {
        return new Size();
    }

    @Override
    protected String getTag() {
        return "Int Size";
    }

    @Override
    public void init(ResMap<String, Object> data) {
        return;
    }
}
