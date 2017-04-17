package math.vector;

import com.ntunin.cybervision.releasable.Releasable;
import com.ntunin.cybervision.releasable.ReleasableFactory;
import com.ntunin.cybervision.res.ResMap;
import com.ntunin.cybervision.injector.Injectable;

/**
 * Created by mikhaildomrachev on 10.04.17.
 */

public class Vector3Factory extends ReleasableFactory implements Injectable{
    @Override
    protected String getTag() {
        return "Vector3";
    }

    @Override
    protected Releasable create() {
        return new Vector3();
    }

    @Override
    public void init(ResMap<String, Object> data) {

    }
}
