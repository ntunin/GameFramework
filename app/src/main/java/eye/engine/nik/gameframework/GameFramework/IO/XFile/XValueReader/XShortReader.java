package eye.engine.nik.gameframework.GameFramework.IO.XFile.XValueReader;

import eye.engine.nik.gameframework.GameFramework.DelegateException;
import eye.engine.nik.gameframework.GameFramework.IO.GameIOException;

/**
 * Created by nikolay on 11.08.16.
 */
public class XShortReader extends XValueReader{

    @Override
    public String getType() {
        return "WORD";
    }

    public XShortReader() throws GameIOException {
        super();
    }

    @Override
    public void parseContent() throws DelegateException {
        super.parseContent();
        String textValue = builder.getContent();
        value = Short.parseShort(textValue);
    }
}
