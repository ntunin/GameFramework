package eye.engine.nik.gameframework.GameFramework.IO.XFile.XStreamBuilder;

import eye.engine.nik.gameframework.GameFramework.ERRNO;

/**
 * Created by nikolay on 07.10.16.
 */

class XInt extends XPrimitive {


    public XInt(XDocumentContext context) {
        super(context);
    }
    public XInt() {
        super();
    }
    @Override
    protected Object parse(String string) {
        try {
            return Integer.parseInt(string);
        } catch (Exception e) {
            ERRNO.write("Parse Int");
            return null;
        }
    }
}
