package eye.engine.nik.gameframework.GameFramework.IO.XFile.XStreamBuilder;

import eye.engine.nik.gameframework.GameFramework.ERRNO;

/**
 * Created by nikolay on 07.10.16.
 */

class XShort extends XPrimitive {


    public XShort(XDocumentContext context) {
        super(context);
    }
    public XShort() {
        super();
    }
    @Override
    protected Object parse(String string) {
        try {
            return Short.parseShort(string);
        } catch (Exception e) {
            ERRNO.write("Parse Float");
            return null;
        }
    }
}
