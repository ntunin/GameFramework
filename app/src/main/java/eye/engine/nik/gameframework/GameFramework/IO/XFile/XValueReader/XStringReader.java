package eye.engine.nik.gameframework.GameFramework.IO.XFile.XValueReader;

import eye.engine.nik.gameframework.GameFramework.DelegateException;
import eye.engine.nik.gameframework.GameFramework.IO.GameIOException;


/**
 * Created by nikolay on 11.08.16.
 */
public class XStringReader extends XValueReader{

    @Override
    public String getType() {
        return "DWORD";
    }

    public XStringReader() throws GameIOException {
        super();
    }

    @Override
    public void parseContent() throws DelegateException {
        super.parseContent();
        value = builder.getContent();
    }
}