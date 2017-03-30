package math.intsize;

import com.ntunin.cybervision.Releasable;

/**
 * Created by nikolay on 11.03.17.
 */

public class Size extends Releasable{
    public int width;
    public int height;

    @Override
    public void release() {
        width = 0;
        height = 0;
        super.release();
    }

    @Override
    public Size init(Object... args) {
        if(args.length == 2) {
            this.width = (int) args[0];
            this.height = (int) args[1];
        }
        return this;
    }

}
